

public abstract class Mensagem {
	
	private int ID;
	private int tipo;
	public static final int typeReta          = 1;
	public static final int typeCurvaDireita  = 2;
	public static final int typeCurvaEsquerda = 3;
	public static final int typePara          = 4;
	
	public Mensagem(int tipo) {
		this.tipo  = tipo;	
	}
	
	public int getID() { 
		return ID;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	public void setID(int novoID) {
		this.ID = novoID;
	}
	
	public String toString() {
		return "ID: " + ID + "   Tipo: " + tipo + "   Mensagem: ";
		
	}

	protected abstract String getTexto();
}
