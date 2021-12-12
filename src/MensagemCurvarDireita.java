

public class MensagemCurvarDireita extends Mensagem{

private int angulo;
private int raio;
	
	public MensagemCurvarDireita(int angulo, int raio) {
		super(Mensagem.typeCurvaDireita );
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
		return super.toString() + "CurvarDireita: <" + angulo + ", " +  raio + ">";
	}
}
