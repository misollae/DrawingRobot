import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

public abstract class Comportamento extends Thread {
	public enum Estado {
		Espera, Desenha, Para
	}

	protected ClienteDoRobot cliente;
	protected JFrame frameCliente;
	protected Estado estado;
	protected Semaphore semaforo;
	protected EspacarFormasGeometricas espacar;

	public void setCliente(ClienteDoRobot cliente, Semaphore semafore) {
		this.cliente  = cliente;
		this.estado   = Estado.Espera;
		this.semaforo = semafore;
//		this.espacar  = espacar;
	}

	public abstract void openFrame();

	public abstract void desenha();
	
	public void avanca() {
		estado = Estado.Desenha;
	}
	
	public void para() {
		estado = Estado.Para;
	}

	public void run() {
		while (estado != Estado.Para) {
			switch (estado) {
			case Espera:
				break;

			case Desenha:
				desenha();
				semaforo.release();
				estado = Estado.Espera;
				break;
				
			default:
				break;
			
			}
		}
	}

}
