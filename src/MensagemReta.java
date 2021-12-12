

public class MensagemReta extends Mensagem{
	
	private int distancia;
	
	public MensagemReta(int distancia) {
		super(Mensagem.typeReta);
		this.distancia = distancia;
	}
	
	public int getDistancia() {
		return this.distancia;
	}

	@Override
	protected String getTexto() {
		return String.valueOf(this.getDistancia());
	}
	
	@Override
	public String toString() {	
		return super.toString() + "Reta <" + distancia + ">";
	}
}
