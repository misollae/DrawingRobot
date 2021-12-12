import javax.swing.JFrame;

public abstract class Comportamento extends Thread {
	protected BufferCircular buffer;
	protected ClienteDoRobot cliente;
	protected enum Estado { EM_ESPERA, ESPAÇAR, DESENHAR };
	protected Estado estadoAtual;
	protected JFrame frameCliente;

	
	public Comportamento() {
		this.estadoAtual = Estado.EM_ESPERA;
	}
	
	public void setCliente(ClienteDoRobot cliente) {
		this.cliente = cliente;
	}
	
	public abstract void openFrame();

	public abstract void desenha();
	
}
