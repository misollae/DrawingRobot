
public class MensagemComeca extends Mensagem{
		
	public MensagemComeca() {
		super(Mensagem.typeComeca);
	}

	@Override
	protected String getTexto() {
		return "Comeca";
	}
	
	@Override
	public String toString() {	
		return super.toString() + "MensagemComeca";
	}
}
