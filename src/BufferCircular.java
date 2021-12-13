import java.util.concurrent.Semaphore;

public class BufferCircular {
	private final int DIMENSAOBUFFER= 16;
	private Mensagem[] bufferCircular;
	private int putBuffer, getBuffer, numE;
	// o sem�foro elementosLivres indica se h� posi��es livres para inserir Strings
	// o sem�foro acessoElemento garante exclus�o m�tua no acesso a um elemento
	// o sem�foro elementosOcupados indica se h� posi��es com Strings v�lidas
	private Semaphore elementosLivres, acessoElemento, elementosOcupados;
	private Integer IDatual;

	public BufferCircular() {
		bufferCircular= new Mensagem[DIMENSAOBUFFER];
		putBuffer = 0;
		getBuffer = 0;
		numE      = 0;
		IDatual   = 0;			
		
		elementosLivres   = new Semaphore(DIMENSAOBUFFER);
		elementosOcupados = new Semaphore(0);
		acessoElemento    = new Semaphore(1);		
	}

	// Recebe a mensagem
	public Mensagem get() {
		if (numE == 0) return null;
		Mensagem msg = null;
		try {
			elementosOcupados.acquire();
			acessoElemento.acquire();
		} catch (InterruptedException e) {}
		msg = bufferCircular[getBuffer];
		getBuffer= ++getBuffer % DIMENSAOBUFFER;
		numE--;
		acessoElemento.release();
		elementosLivres.release();
		return msg;
	}
	

	// Envia a mensagem
	public void put (Mensagem mensagem) {
		try {
			elementosLivres.acquire();
			acessoElemento.acquire();
			mensagem.setID(IDatual++);
			bufferCircular[putBuffer] = mensagem;
			putBuffer= ++putBuffer % DIMENSAOBUFFER;
			numE++;
			acessoElemento.release();
		} catch (InterruptedException e) {}
		elementosOcupados.release();
	}
}