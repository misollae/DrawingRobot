public class DesenharQuadrado extends Comportamento {
	private int lado;
	private EspacarFormasGeometricas espacador;
	
	public DesenharQuadrado(EspacarFormasGeometricas espacador) {
		super();
		this.frameCliente = new FrameDesenharQuadrado();
		((FrameDesenharQuadrado) this.frameCliente).setComportamento(this);
		lado = 10;
		this.espacador = espacador;
	}
	
	public void openFrame() {
		frameCliente.setVisible(true);
	}
	
	public void setLado(int lado) {
		this.lado = lado;
	}
	
	public void desenha() {
		((FrameDesenharQuadrado) frameCliente).write("Execu��o de um Quadrado de Lado: " + lado);
		cliente.Comeca();
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
		cliente.Acaba();
		((FrameDesenharQuadrado) frameCliente).write("Fim do Quadrado de Lado: " + lado);
		espacador.setUltimo(this);
	}
}
