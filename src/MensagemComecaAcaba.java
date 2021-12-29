
public class MensagemComecaAcaba extends Mensagem{

private boolean comeca;
	
	public MensagemComecaAcaba(boolean comeca) {
		super(Mensagem.typeComecaAcaba);
		this.comeca = comeca;

	}
	
	public boolean getComeca() {
		return this.comeca;
	}

	@Override
	protected String getTexto() {
		return String.valueOf(this.getComeca());
	}
	
	@Override
	public String toString() {	
		return super.toString() + "MensagemComecaPara: <" + comeca + ">";
	}
}
