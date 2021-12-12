public class DesenharQuadrado extends Comportamento {
	private int lado;
	
	public DesenharQuadrado() {
		super();
		this.frameCliente = new FrameDesenharQuadrado();
		((FrameDesenharQuadrado) this.frameCliente).setComportamento(this);
		lado = 10;
	}
	
	public void openFrame() {
		frameCliente.setVisible(true);
	}
	
	public void setLado(int lado) {
		this.lado = lado;
	}
	
	
	public void desenha() {
		((FrameDesenharQuadrado) frameCliente).write("Execução de um Quadrado de Lado: " + lado);
		cliente.Reta(lado);
		cliente.CurvarDireita(90, 0);
		cliente.Parar(false);
		cliente.Reta(lado);
		cliente.CurvarDireita(90, 0);
		cliente.Parar(false);	
		cliente.Reta(lado);
		cliente.CurvarDireita(90, 0);
		cliente.Parar(false);
		cliente.Reta(lado);
		cliente.Parar(false);
		((FrameDesenharQuadrado) frameCliente).write("Fim do Quadrado de Lado: " + lado);
	}
	
	public static void main(String[] args) {
		new DesenharQuadrado();
	}

}
