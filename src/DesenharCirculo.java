public class DesenharCirculo extends Comportamento {
	private int raio;
	private int ultimoRaio;
	private EspacarFormasGeometricas espacador;

	public DesenharCirculo(EspacarFormasGeometricas espacador) {
		super();
		this.frameCliente = new FrameDesenharCirculo();
		((FrameDesenharCirculo) this.frameCliente).setComportamento(this);
		raio = 10;
		ultimoRaio = 0;
		this.espacador = espacador;
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
		((FrameDesenharCirculo) frameCliente).write("Execu��o de um C�rculo de Raio " + raio);
		cliente.Comeca();
		cliente.CurvarDireita(360, raio);
		cliente.Parar(false);
		cliente.Acaba();
		((FrameDesenharCirculo) frameCliente).write("Fim do C�rculo de Raio " + raio);
		espacador.setUltimo(this);
		this.ultimoRaio = raio;

	}

}