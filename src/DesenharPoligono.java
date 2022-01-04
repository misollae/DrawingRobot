public class DesenharPoligono extends Comportamento {
	private int lado;
	private int numLados;
	private int ultLado;
	private int ultNumLados;
	private EspacarFormasGeometricas espacador;

	public DesenharPoligono(EspacarFormasGeometricas espacador) {
		super();
		this.frameCliente = new FrameDesenharPoligono();
		((FrameDesenharPoligono) this.frameCliente).setComportamento(this);
		lado     = 10;
		numLados = 5;
		this.espacador = espacador;
	}
	
	public void openFrame() {
		frameCliente.setVisible(true);
	}

	public int getLado() {
		return lado;
	}
	
	public void setLado(int lado) {
		this.lado = lado;
	}
	
	public int getNumLados() {
		return numLados;
	}
	
	public void setNumLados(int numLados) {
		this.numLados = numLados;
	}
	
	public int getUltLado() {
		return ultLado;
	}
	
	public int getUltNumLados() {
		return ultNumLados;
	}
	
	public int getUltCircleRadius() {
		return (int) Math.ceil((ultLado) / (2 * Math.sin(Math.toRadians(180/ultNumLados))));
	}
	
	public int getCircleRadius() {
		return (int) Math.ceil((lado) / (2 * Math.sin(Math.toRadians(180/numLados))));
	}
	
	public void desenha() {
		((FrameDesenharPoligono) frameCliente).write("Execução de um Polígono com " + numLados + " lados de " + lado);
		cliente.Comeca();
		
		int anguloDesenho = (180 * (numLados-2)) / numLados;
		for (int i = 0 ; i < numLados ; i++) {
			cliente.Reta(lado);
			if (i != numLados-1) cliente.CurvarDireita(anguloDesenho, 0);
			cliente.Parar(false);
		}
		
		cliente.Acaba();
		((FrameDesenharPoligono) frameCliente).write("Fim do um Polígono com " + numLados + " lados de " + lado);
		espacador.setUltimo(this);
		this.ultLado     = lado;
		this.ultNumLados = numLados;
	}
}