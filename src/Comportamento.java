import javax.swing.JFrame;

public abstract class Comportamento extends Thread {
	protected ClienteDoRobot cliente;
	protected JFrame frameCliente;
	
	public void setCliente(ClienteDoRobot cliente) {
		this.cliente = cliente;
	}
	
	public abstract void openFrame();

	public abstract void desenha();
	
}
