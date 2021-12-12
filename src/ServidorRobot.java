import java.lang.reflect.InvocationTargetException;
import java.time.Clock;
import java.util.ArrayList;

import javax.swing.SwingUtilities;


public class ServidorRobot extends Thread{
	private FrameServidor frameServidor;
	private BufferCircular buffer;
	private RobotDesenhador robot;
	private RobotLegoEV3 ev3 = new RobotLegoEV3(); // Robot verdadeiro
	private ArrayList<Mensagem> mensagensRecebidas = new ArrayList<Mensagem>();
	private boolean aExecutar;
	private Clock clock = Clock.systemDefaultZone();
	private long tempoDeEspera;
	private long inicioDaEspera;

	public ServidorRobot() throws InvocationTargetException, InterruptedException {
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {	ServidorRobot.this.frameServidor = new FrameServidor();	}
		});
		} else {
			this.frameServidor = new FrameServidor();
		}
		
		this.robot = new RobotDesenhador(null); // Null para simulaçao, EV3 para verdadeiro
		this.aExecutar = false;
		this.frameServidor.setRobot(robot);
		this.tempoDeEspera  = 0;
		this.inicioDaEspera = 0;
	}
	
	public void setBuffer(BufferCircular buffer) {
		this.buffer = buffer;
	}
	
	public void openFrame() {
		frameServidor.openFrame();
		robot.openFrame();
	}

	public void run() {
		for (;;) {
			try {
				if (buffer != null) {
					sleep(10);
					Mensagem msgRecebida = buffer.get();
					// Vai adicionando as mensagens recebidas a um array, se o canal estiver aberto
					if (msgRecebida != null) {
						frameServidor.write("Recebi: " + msgRecebida);
						mensagensRecebidas.add(msgRecebida);
					}
				}
				// Sempre que termina a execução de outra instrução, enquanto ainda estiver ligado e tiver mais no Array...
				if (!aExecutar && !mensagensRecebidas.isEmpty() && frameServidor.robotIsOpen()) {
					// Vai buscar a mensagem recebida há mais tempo
					Mensagem proxMensagem = mensagensRecebidas.get(0);
					frameServidor.write("Mandar Executar: " + proxMensagem.toString());
					switch (proxMensagem.getTipo()) {
					// E, consoante o seu tipo, executa-a chamando o método correspondente do Robot.
						case Mensagem.typeReta:
							MensagemReta msgReta = (MensagemReta) proxMensagem;
							this.robot.Reta(msgReta.getDistancia());
							aExecutar      = true;
							// Calculamos, em milisegundos, o tempo que vai demorar a executar cada operação (Vrobot = 30cm/s)
							tempoDeEspera  = (long) (Math.ceil(msgReta.getDistancia() / 30.0f) * 1000.0f);
							// E marcamos o inicio da espera
							inicioDaEspera = clock.millis();
							break;
		
						case Mensagem.typeCurvaDireita:
							MensagemCurvarDireita msgDireita = (MensagemCurvarDireita) proxMensagem;
							this.robot.CurvarDireita(msgDireita.getAngulo(), msgDireita.getRaio());
							aExecutar      = true;
							tempoDeEspera  = (long) ((((Math.toRadians(msgDireita.getAngulo()) * (msgDireita.getRaio() + 4.5f)) / 30.0f) * 1000.0f) + 620.0);
							inicioDaEspera = clock.millis();
							break;
		
						case Mensagem.typeCurvaEsquerda:
							MensagemCurvarEsquerda msgEsquerda = (MensagemCurvarEsquerda) proxMensagem;
							this.robot.CurvarEsquerda(msgEsquerda.getAngulo(), msgEsquerda.getRaio());
							aExecutar      = true;
							tempoDeEspera  = (long) (((Math.toRadians(msgEsquerda.getAngulo()) * (msgEsquerda.getRaio() + 4.0f)) / 30.0f) * 1000.0f);
							inicioDaEspera = clock.millis();
							break;
							
						case Mensagem.typePara:
							MensagemParar msgPara = (MensagemParar) proxMensagem;
							this.robot.Parar(msgPara.getBoolean());
							aExecutar      = true;
							tempoDeEspera  = (long) 500.0f;
							inicioDaEspera = clock.millis();
							break;
					}
				} else if (aExecutar){
					// Comparando esse tempo ao tempo atual, assim que ultrapassou o tempo previsto pode executar a próxima instrução
					if(clock.millis() - inicioDaEspera >= tempoDeEspera) {
						aExecutar = false; 
						mensagensRecebidas.remove(0);
						inicioDaEspera = 0;
						tempoDeEspera  = 0;
					}
				}

			} catch (Exception e) {
			}
		}
	}
}
