

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class CanalComunicacao {
	// ficheiro
	private File ficheiro;
	// canal que liga o conteudo do ficheiro ao Buffer
	private FileChannel canal;
	// cadeado do canal
	private FileLock lock;
	// buffer
	private MappedByteBuffer buffer;
	// dimensao maxima em bytes do buffer
	final int BUFFER_MAX = 30;
	// Variáveis auxiliares ao ID
	private int currentID;
	private int lastID;
	// Indica se o canal foi aberto
	private boolean aberto;
	// Indica se se pode escrever
	private boolean canWrite;

	public CanalComunicacao() {
		ficheiro = null;
		lock = null;
		canal = null;
		buffer = null;
		aberto = false;
	}

	public boolean isOpen() {
		return aberto;
	}

	@SuppressWarnings("resource")
	public boolean abrirCanal(File file) {
		this.ficheiro = file; // recebe o ficheiro sobre o qual funcionará o canal

		try {
			// Testa-se a abertura
			canal = new RandomAccessFile(ficheiro, "rw").getChannel();
			this.aberto = true;
			System.out.println("Canal aberto!\n");
			
		} catch (FileNotFoundException e) {
			return false;
		}
		// mapeia para memória o conteúdo do ficheiro
		try {
			buffer = canal.map(FileChannel.MapMode.READ_WRITE, 0, BUFFER_MAX);
			// Se for um novo ficheiro (ficheiro vazio)
			if (canal == null) {
				this.currentID = 0;
				this.lastID = this.currentID;
			} else { // Verificação do estado do canal
				lock = canal.lock();
				buffer.position(0);
				if (buffer.getChar() == 'r')
					this.canWrite = true;
				else
					this.canWrite = false;
				this.currentID = buffer.getInt();
				this.lastID = this.currentID;
				lock.release();
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	// Recebe a mensagem
	public Mensagem receberMensagem() {
		if (!this.ficheiro.equals(null)) {
			String msg = "";
			try {
				lock = canal.lock();

				// Primeiro, vemos a variável indicativa da leitura - se for um r é porque já foi lida, voltando para trás
				buffer.position(0);
				if (buffer.getChar() == 'r')
					return null;

				// Se não foi lida, vamos ler agora, marcando o 'r'
				buffer.position(0);
				buffer.putChar('r');
				// Depois vamos buscar as próximas 2 informações - o ID e o Tipo
				int ID = buffer.getInt();
				int tipo = buffer.getInt();
				// Começamos então a recuperar o texto
				char c;
				while ((c = buffer.getChar()) != '\0')
					msg += c;
				// Uma vez que no caso nas MensagensCurva o texto é da forma "(angulo),(raio)" temos de separar as duas variáveis, usando um split
				String[] inteiros = msg.split(",");
				lock.release();

				Mensagem mensagemRecebida;

				// Agora reconstruimos o objeto Mensagem de acordo com o tipo obtido, fazendo o seu return e set o ID retirado do buffer
				switch (tipo) {
				case Mensagem.typeReta:
					mensagemRecebida = new MensagemReta(Integer.parseInt(msg));
					mensagemRecebida.setID(ID);
					return mensagemRecebida;
				case Mensagem.typeCurvaDireita:
					mensagemRecebida = new MensagemCurvarDireita(Integer.parseInt(inteiros[0]),
							Integer.parseInt(inteiros[1]));
					mensagemRecebida.setID(ID);
					return mensagemRecebida;
				case Mensagem.typeCurvaEsquerda:
					mensagemRecebida = new MensagemCurvarEsquerda(Integer.parseInt(inteiros[0]),
							Integer.parseInt(inteiros[1]));
					mensagemRecebida.setID(ID);
					return mensagemRecebida;
				case Mensagem.typePara:
					mensagemRecebida = new MensagemParar(Boolean.getBoolean(msg));
					mensagemRecebida.setID(ID);
					return mensagemRecebida;
				}
			} catch (Exception e) {
				// System.out.println("Erro ao receber mensagem -> " + e.getMessage());
				try {
					lock.release();
				} catch (IOException e1) {
					// e1.printStackTrace();
				}
				return null;
			}
		}
		return null;
	}

	// Envia a mensagem
	public void enviarMensagem(Mensagem mensagem) {
		if (!this.ficheiro.equals(null)) {
			try {
				// Uma vez que existirão vários clientes a trabalhar sob um mesmo canal, realizamos constantes updates para garantir que não
				// existem perdas de informação
				updateCanal();
				
				// Agora vamos obter as várias informações que temos de escrever no buffer - o tipo, o texto, e o ID
				int tipo = mensagem.getTipo();
				String texto = mensagem.getTexto();
				currentID++;
				mensagem.setID(currentID);

				// As mensagens têm de ficar à espera, de forma ordenada, enquanto a mensagem previamente escrita ainda não tiver sido lida
				// usa-se um Thread.sleep até se ler 'r' no buffer. A primeira a sair corresponde à que incrementa de 1 ID da ùltima mensagem no buffer
				while (!canWrite || !(mensagem.getID() == lastID + 1)) {
					lock = canal.lock();
					buffer.position(0);
					if (buffer.getChar() == 'r')
						canWrite = true;
					lastID = buffer.getInt();
					lock.release();
					Thread.sleep(1);
				}

				lock = canal.lock();

				// Começamos aqui a escrever no buffer, colocando, por ordem, o carater de controlo de leitura, o ID, o Tipo e o texto, usando
				// os métodos da classe Mensagem
				buffer.position(0);
				buffer.putChar('u');
				buffer.putInt(mensagem.getID());
				lastID = mensagem.getID();

				buffer.putInt(tipo);
				char c;
				for (int i = 0; i < texto.length(); ++i) {
					c = texto.charAt(i);
					buffer.putChar(c);
				}
				buffer.putChar('\0');

				canWrite = false;

				lock.release();

				System.out.println("Mensagem Enviada -> " + mensagem.toString());

			} catch (Exception e) {
				System.out.println(e.getMessage());
				try {
					lock.release();
				} catch (IOException e1) {
					// e1.printStackTrace();
				}
			}
		}
	}

	// fecha o canal entre o buffer e o ficheiro
	public void fecharCanal() {
		if (canal != null)
			try {
				canal.close();
			} catch (IOException e) {
				canal = null;
			}
	}

	public void updateCanal() {
		try {
			lock = canal.lock();
			buffer.position(0);
			if (buffer.getChar() == 'r')
				this.canWrite = true;
			else
				this.canWrite = false;
			this.currentID = buffer.getInt();
			lock.release();
		} catch (Exception e) {
		}
	}
}