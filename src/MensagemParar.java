

public class MensagemParar extends Mensagem{
	
	private boolean agora;

	public MensagemParar(boolean agora) {
		super(Mensagem.typePara);
		this.agora = agora;
	}

	public Boolean getBoolean() {
		return this.agora;
	}
	
	@Override
	protected String getTexto() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getBoolean());
	}
	
	@Override
	public String toString() {	
		return super.toString() + "Parar: <" + agora + ">";
	}


}
