public class EspacarFormasGeometricas extends Comportamento {
	
	private int distancia;
	
	public EspacarFormasGeometricas() {
		this.distancia = 0;
		this.frameCliente = new FrameEspacarFormas();
		((FrameEspacarFormas) this.frameCliente).setComportamento(this);
	}
	
	public void openFrame() {
		frameCliente.setVisible(true);
	}
	
	public void setDistancia(int distancia) {
		this.distancia = distancia;
//		System.out.println("Mudei a distancia");
	}
	
	public void desenha() {
//		System.out.println("VOU ESPACAR");
		((FrameEspacarFormas) frameCliente).write("Espaçamento de: " + distancia);
		this.cliente.Reta(distancia);
	}

}