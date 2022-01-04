import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GravarFormas extends Thread {
	private ArrayList<Mensagem> mensagensGravacao  = new ArrayList<Mensagem>();

	private FrameGravarFormas frameGravar;
	private String[] strings;
	private FileOutputStream output;
	private FileInputStream  input;
	private String name;
	private String line;	
	private BufferedReader br;

	public enum Estado { Espera, Gravar, Parar }

	private Estado estado;

	public GravarFormas() {
		this.frameGravar = new FrameGravarFormas(this);
		estado = Estado.Espera;
		this.br = null;
	}

	public void abrirFrame() {
		frameGravar.setVisible(true);
	}

	public Estado getEstado() {
		return estado;
	}
	
	public boolean hasGravacao() {
		return !(mensagensGravacao.isEmpty());
	}
	
	public ArrayList<Mensagem> transferirGravacao() {
		@SuppressWarnings("unchecked")
		ArrayList<Mensagem> copia = (ArrayList<Mensagem>) mensagensGravacao.clone();
		mensagensGravacao.clear();
		return copia;
	}
	
	public void receberMensagem(String msg) {
		String id       = msg.split("; ")[0];	
		String mensagem = msg.split("; ")[1];

		String comando = mensagem.split(":")[0];
		String params  = mensagem.split(":")[1];
		if (comando.equals("Reta") || comando.equals("Parar")) {
			try {
				output.write(id.getBytes());
				output.write((", ").getBytes());
				output.write(comando.getBytes());
				output.write((", ").getBytes());
				output.write((params.substring(params.indexOf("<") + 1, params.indexOf(">"))).getBytes());
				output.write(("\n").getBytes());

			} catch (IOException e) {
//				e.printStackTrace();
			}
		}
		if (comando.equals("CurvarEsquerda") || comando.equals("CurvarDireita")) {
			try {
				output.write(id.getBytes());
				output.write((", ").getBytes());
				output.write(comando.getBytes());
				output.write((", ").getBytes());
				output.write((params.split(", ")[0].substring((params.split(", ")[0]).indexOf("<") + 1, (params.split(", ")[0]).indexOf(">"))).getBytes());
				output.write((", ").getBytes());
				output.write((params.split(", ")[1].substring((params.split(", ")[1]).indexOf("<") + 1, (params.split(", ")[1]).indexOf(">"))).getBytes());
				output.write(("\n").getBytes());
			} catch (IOException e) {
//				e.printStackTrace();
			}
		}
	}
	
	public void repetirMensagem() {
		File chosenFile = frameGravar.getChosenFile();
		try {
			this.br = new BufferedReader(new InputStreamReader(new FileInputStream(chosenFile), StandardCharsets.UTF_8));	
			try {
				String line;
				ArrayList<Mensagem> gravacaoLida = new ArrayList<Mensagem>();
			    while ((line = br.readLine()) != null) {
			    	String[] comando = line.split(", ");
			    	if (comando[1].equals("Reta")) {
			    		MensagemReta msgReta = new MensagemReta(Integer.valueOf(comando[2]));
			    		msgReta.setID(Integer.valueOf(comando[0]));
			    		gravacaoLida.add(msgReta);
			    	}
			    	if (comando[1].equals("Parar")) {
			    		MensagemParar msgParar = new MensagemParar(Boolean.valueOf(comando[2]));
			    		msgParar.setID(Integer.valueOf(comando[0]));
			    		gravacaoLida.add(msgParar);
			    	}
			    	if (comando[1].equals("CurvarDireita")) {
			    		MensagemCurvarDireita msgDireita = new MensagemCurvarDireita(Integer.valueOf(comando[2]), Integer.valueOf(comando[3]));
			    		msgDireita.setID(Integer.valueOf(comando[0]));
			    		gravacaoLida.add(msgDireita);
			    	}
			    	if (comando[1].equals("CurvarEsquerda")) {
			    		MensagemCurvarEsquerda msgEsquerda = new MensagemCurvarEsquerda(Integer.valueOf(comando[2]), Integer.valueOf(comando[3]));
			    		msgEsquerda.setID(Integer.valueOf(comando[0]));
			    		gravacaoLida.add(msgEsquerda);
			    	}
			    }
			    this.mensagensGravacao.addAll(gravacaoLida);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void iniciarGravacao() {
		estado = Estado.Gravar;
		try {
			File novoFicheiro = new File(frameGravar.getFileName());
			output  = new FileOutputStream(novoFicheiro, true);
			if (novoFicheiro.createNewFile()) {
//				System.out.println("File created.");
			} else {
//				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void pararGravacao() {
		estado = Estado.Espera;
	}

	public void run() {
		while (estado != Estado.Parar) {
			switch (estado) {
			case Espera:
				break;
			case Gravar:
				break;
			default:
				break;
			}
		}
	}
}
