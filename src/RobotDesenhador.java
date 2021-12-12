public class RobotDesenhador implements IEV3{
	private RobotLegoEV3 r;
	private FrameRobotDesenhador frameRobot;
	
	public RobotDesenhador(RobotLegoEV3 r) {
		if(r != null) {
			this.r = r;
		}
		this.frameRobot = new FrameRobotDesenhador();
	}
	
	public void openFrame() {
		frameRobot.openFrame();
	}
	
	public void enviarMensagemFrame(String string) {
		if (string != null) {
			frameRobot.write(string);
		}
	}
	
	public boolean OpenEV3(String nome) {
		if (r !=null) {
			return r.OpenEV3(nome);
		}
		return true;
	}
	
	public void Close() {
		if (r != null) {
			r.CloseEV3();
		}
		else {
			System.out.println("Fechar robot\n");
		}	
	}
	
	public void Reta(int distancia) {
		enviarMensagemFrame("Reta: <" + distancia + ">");
		if (r != null) {
			r.Reta(distancia);
			
		}
		else {
			System.out.printf("Reta: <%d>\n", distancia);
		}
	}
	
	public void CurvarEsquerda(int angulo, int raio) {	
		enviarMensagemFrame("CurvarEsquerda: <" + angulo + ">, <" + raio +">");
		if (r != null) {
			r.CurvarEsquerda(raio, angulo);
		}
		else {
			System.out.printf("CurvarEsquerda: <%d>, <%d>\n", angulo, raio);
		}
	}
	
	public void CurvarDireita(int angulo, int raio) {
		enviarMensagemFrame("CurvarDireita: <" + angulo + ">, <" + raio +">");
		if (r != null) {
			r.CurvarDireita(raio, angulo);
		}
		else {
			System.out.printf("CurvarDireita: <%d>, <%d>\n", angulo, raio);
		}
	}
	
	public void Parar(boolean agora) {
		enviarMensagemFrame("Parar: <" + agora + ">");
		if (r != null) {
			r.Parar(agora);
		}
		else {
			System.out.print("Parar robot\n");
		}
	}
	
	public void CloseEV3() {
		if (r != null) {
			r.CloseEV3();
		}else{
			System.out.println("Fechar robot\n");}
	}
}