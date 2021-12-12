

public class MensagemCurvarEsquerda extends Mensagem{

private int angulo;
private int raio;
	
	public MensagemCurvarEsquerda(int angulo, int raio) {
		super(Mensagem.typeCurvaEsquerda);
		this.angulo = angulo;
		this.raio = raio;

	}
	
	public int getAngulo() {
		return this.angulo;
	}
	
	public int getRaio() {
		return this.raio;
	}

	@Override
	protected String getTexto() {
		return String.valueOf(this.getAngulo()) + "," + String.valueOf(this.getRaio());
	}
	
	@Override
	public String toString() {	
		return super.toString() + "CurvarEsquerda: <" + angulo + ", " +  raio + ">";
	}
}
