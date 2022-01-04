
public class ClienteDoRobot implements IEV3{
	private BufferCircular buffer;
	
	public ClienteDoRobot(BufferCircular buffer) {
		this.buffer = buffer;
	}
	
	public void Comeca() {
		this.buffer.put(new MensagemComeca());
	}
	
	public void Acaba() {
		this.buffer.put(new MensagemAcaba());
	}
	
	public void Reta(int distancia) {
		this.buffer.put(new MensagemReta(distancia));
	}
	
	public void Parar(boolean agora) {
		this.buffer.put(new MensagemParar(agora));
	}

	public void CurvarDireita(int raio, int angulo) {
		this.buffer.put( new MensagemCurvarDireita(raio, angulo));
	}
	
	public void CurvarEsquerda(int raio, int angulo) {
		this.buffer.put(new MensagemCurvarEsquerda(raio, angulo));
	}
}
