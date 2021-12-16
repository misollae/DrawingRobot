public class EspacarFormasGeometricas extends Comportamento {

	private Comportamento ultimo;
	private Comportamento proximo;

	public EspacarFormasGeometricas() {
		this.frameCliente = new FrameEspacarFormas();
		this.ultimo  = null;
		this.proximo = null;
		((FrameEspacarFormas) this.frameCliente).setComportamento(this);
	}

	public void setUltimo(Comportamento comp) {
		this.ultimo = comp;
	}

	public void setProximo(Comportamento comp) {
		this.proximo = comp;
	}

	public void openFrame() {
		frameCliente.setVisible(true);
	}

	public int getDistancia() {
		// Sincroniza��o -> com Sem�foros ou Monitores
		if (ultimo == null) {
			return 0;
		}

		// N�O ANDA
		if ((ultimo instanceof DesenharQuadrado && proximo instanceof DesenharQuadrado) 
		 || (ultimo instanceof DesenharQuadrado && proximo instanceof DesenharPoligono && ((DesenharPoligono) proximo).getNumLados() == 3)
		 || (ultimo instanceof DesenharPoligono && ((DesenharPoligono) ultimo).getUltNumLados() == 3 && proximo instanceof DesenharQuadrado)) {
			return 0;
		}

		// Anda o diametro do pr�ximo circulo
		if ((ultimo instanceof DesenharQuadrado && proximo instanceof DesenharCirculo)
		 || (ultimo instanceof DesenharPoligono && ((DesenharPoligono) ultimo).getUltNumLados() == 3 && proximo instanceof DesenharCirculo)) {
			return (((DesenharCirculo) proximo).getRaio() * 2);
		}

		// Anda o raio do circulo anterior
		if ((ultimo instanceof DesenharCirculo && proximo instanceof DesenharQuadrado)
		 || (ultimo instanceof DesenharCirculo && proximo instanceof DesenharPoligono && ((DesenharPoligono) proximo).getNumLados() == 3)) {
			return (((DesenharCirculo) ultimo).getUltimoRaio());
		}

		// Anda o raio do circulo anterior + o raio do pr�ximo circulo
		if (ultimo instanceof DesenharCirculo && proximo instanceof DesenharCirculo) {
			System.out.println("c apos c");
			return (((DesenharCirculo) ultimo).getUltimoRaio() + ((DesenharCirculo) proximo).getRaio());
		}

		// Anda o raio do circulo anterior + (o raio da circunfer�ncia sobreescrita no
		// poligono - metade do seu lado)
		if (ultimo instanceof DesenharCirculo && proximo instanceof DesenharPoligono && ((DesenharPoligono) proximo).getNumLados() > 3) {
			return ((int) (Math.ceil(((DesenharCirculo) ultimo).getUltimoRaio() + ((DesenharPoligono) proximo).getCircleRadius() - (((DesenharPoligono) proximo).getLado() / 2))));
		}

		// Anda o raio do pr�ximo circulo + (o raio da circunfer�ncia sobreescrita no
		// poligono anterior - metade do seu lado)
		if (ultimo instanceof DesenharPoligono && ((DesenharPoligono) ultimo).getUltNumLados() > 3 && proximo instanceof DesenharCirculo) {
			return ((int) (Math.ceil(((DesenharCirculo) proximo).getRaio() + ((DesenharPoligono) ultimo).getUltCircleRadius() - (((DesenharPoligono) ultimo).getUltLado() / 2))));
		}

		// Anda o raio da circunfer�ncia sobreescrita no poligono anterior - metade do
		// seu lado
		if (ultimo instanceof DesenharQuadrado && proximo instanceof DesenharPoligono && ((DesenharPoligono) proximo).getNumLados() > 3) {
			return ((int) (Math.ceil(((DesenharPoligono) proximo).getCircleRadius() - (((DesenharPoligono) proximo).getLado() / 2))));
		}

		// Anda o raio da circunfer�ncia sobreescrita no poligono anterior - metade do
		// seu lado
		if (ultimo instanceof DesenharPoligono && ((DesenharPoligono) ultimo).getUltNumLados() > 3 && proximo instanceof DesenharQuadrado) {
			return ((int) (Math.ceil(((DesenharPoligono) ultimo).getUltCircleRadius() - (((DesenharPoligono) ultimo).getUltLado() / 2))));
		}
		
		return 0;
	}

	public void desenha() {
		int distancia = getDistancia();
		((FrameEspacarFormas) frameCliente).write("Espa�amento de: " + distancia);
		if (distancia != 0) {
			this.cliente.Reta(distancia);
			this.cliente.Parar(false);
		}
	}

}