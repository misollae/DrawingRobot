public class DesenharCirculo extends Comportamento {
	private int raio;
	private int ultimoRaio;

	public DesenharCirculo() {
		super();
		this.frameCliente = new FrameDesenharCirculo();
		((FrameDesenharCirculo) this.frameCliente).setComportamento(this);
		raio = 10;
		ultimoRaio = 0;
	}
	
	public void openFrame() {
		frameCliente.setVisible(true);
	}

	public void setRaio(int raio) {
		this.raio = raio;
	}
	
	public int getRaio() {
		return this.raio;
	}
	
	public int getUltimoRaio() {
		return this.ultimoRaio;
	}
	
	public void desenha() {
		cliente.CurvarDireita(360, raio);
		((FrameDesenharCirculo) frameCliente).write("Execu��o de um C�rculo de Raio " + raio);
		cliente.Parar(false);
		((FrameDesenharCirculo) frameCliente).write("Fim do C�rculo de Raio " + raio);
		this.ultimoRaio = raio;
	}

	public static void main(String[] args) {
		new DesenharCirculo();
	}

}