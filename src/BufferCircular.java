import java.util.concurrent.Semaphore;

public class BufferCircular {
	private final int DIMENSAOBUFFER= 16;
	private Mensagem[] bufferCircular;
	private int putBuffer, getBuffer, numE;
	// o semáforo elementosLivres indica se há posições livres para inserir Strings
	// o semáforo acessoElemento garante exclusão mútua no acesso a um elemento
	// o semáforo elementosOcupados indica se há posições com Strings válidas
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