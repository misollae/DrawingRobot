import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Semaphore;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class FrameTP2 {
	private BufferCircular buffer;
	private ClienteDoRobot cliente;
	private DesenharQuadrado desenhaQua;
	private DesenharCirculo  desenhaCir;
	private DesenharPoligono desenhaPol;
	private EspacarFormasGeometricas espaca;
	private ServidorRobot servidor; 
	
	private Semaphore semaforo;
	
	private Estado estadoAtual;
	private enum Estado { ESPERA, DESENHA, PARA };
	
	private Comportamento ultiComportamento;
	private Comportamento proxComportamento;

	private JFrame frmTp;

	/**
	 * Create the application.
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	public FrameTP2() throws InvocationTargetException, InterruptedException {
		EventQueue.invokeAndWait(new Runnable() {
			public void run() {
				try {
					initialize();
					frmTp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		estadoAtual = Estado.ESPERA;
		semaforo    = new Semaphore(1);
		this.buffer  = new BufferCircular();
	}
	
	/**
	 * Launch the application.
	 */
	public void run() {
		for (;;) {
			switch (estadoAtual) {
			case ESPERA:
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
				
			case DESENHA:	
				espacarFormas();
				proxComportamento.desenha();
				ultiComportamento = proxComportamento;
				semaforo.release();
				estadoAtual = Estado.PARA;
				break;
				
			case PARA:
				estadoAtual = Estado.ESPERA;
				break;
			}
		}
	}

	public void setUpCliente() {
		this.cliente = new ClienteDoRobot(buffer);
		this.desenhaQua = new DesenharQuadrado();
		this.desenhaPol = new DesenharPoligono();
		this.desenhaCir = new DesenharCirculo();
		this.espaca     = new EspacarFormasGeometricas(); 
		desenhaQua.setCliente(cliente);
		desenhaPol.setCliente(cliente);
		desenhaCir.setCliente(cliente);
		espaca.setCliente(cliente);

		this.ultiComportamento = null;
		this.proxComportamento = null;
	}
	
	public void setUpServidor() throws InvocationTargetException, InterruptedException {
		this.servidor = new ServidorRobot();
		servidor.setBuffer(buffer);
		servidor.start();
	}
	
	public void openFramesCliente() {
		desenhaQua.openFrame();
		desenhaPol.openFrame();
		desenhaCir.openFrame();
		espaca.openFrame();
	}
	
	public void openFramesServidor() {
		servidor.openFrame();
	}
	
	public void espacarFormas() {
		// Sincronização -> com Semáforos ou Monitores
		if (ultiComportamento == null) {
			return;
		}
		
		// NÃO ANDA
		if ((ultiComportamento instanceof DesenharQuadrado && proxComportamento instanceof DesenharQuadrado) ||
			(ultiComportamento instanceof DesenharQuadrado && proxComportamento instanceof DesenharPoligono && ((DesenharPoligono)proxComportamento).getNumLados() == 3) ||
			(ultiComportamento instanceof DesenharPoligono && ((DesenharPoligono)ultiComportamento).getUltNumLados() == 3 && proxComportamento instanceof DesenharQuadrado)) {
			return;
		}
		
		// Anda o diametro do próximo circulo
		if ((ultiComportamento instanceof DesenharQuadrado && proxComportamento instanceof DesenharCirculo) ||
			(ultiComportamento instanceof DesenharPoligono && ((DesenharPoligono)ultiComportamento).getUltNumLados() == 3 && proxComportamento instanceof DesenharCirculo)) {
			espaca.setDistancia(((DesenharCirculo) proxComportamento).getRaio() * 2);
		}
		
		// Anda o raio do circulo anterior
		if ((ultiComportamento instanceof DesenharCirculo && proxComportamento instanceof DesenharQuadrado) ||
			(ultiComportamento instanceof DesenharCirculo && proxComportamento instanceof DesenharPoligono && ((DesenharPoligono)proxComportamento).getNumLados() == 3)) {
			espaca.setDistancia(((DesenharCirculo) ultiComportamento).getUltimoRaio());
		}
		
		// Anda o raio do circulo anterior + o raio do próximo circulo
		if (ultiComportamento instanceof DesenharCirculo && proxComportamento instanceof DesenharCirculo) {
			espaca.setDistancia(((DesenharCirculo) ultiComportamento).getUltimoRaio() + ((DesenharCirculo) proxComportamento).getRaio());
		}
		
		// Anda o raio do circulo anterior + (o raio da circunferência sobreescrita no poligono - metade do seu lado)
		if (ultiComportamento instanceof DesenharCirculo && proxComportamento instanceof DesenharPoligono && ((DesenharPoligono)proxComportamento).getNumLados() > 3) {
			espaca.setDistancia((int) (Math.ceil(((DesenharCirculo) ultiComportamento).getUltimoRaio() + ((DesenharPoligono) proxComportamento).getCircleRadius() - (((DesenharPoligono) proxComportamento).getLado()/2))));
		}
		
		// Anda o raio do próximo circulo + (o raio da circunferência sobreescrita no poligono anterior - metade do seu lado)
		if (ultiComportamento instanceof DesenharPoligono && ((DesenharPoligono)ultiComportamento).getUltNumLados() > 3 && proxComportamento instanceof DesenharCirculo) {
			espaca.setDistancia((int) (Math.ceil(((DesenharCirculo) proxComportamento).getRaio() + ((DesenharPoligono) ultiComportamento).getUltCircleRadius() - (((DesenharPoligono) ultiComportamento).getUltLado()/2))));
		}
		
		// Anda o raio da circunferência sobreescrita no poligono anterior - metade do seu lado
		if (ultiComportamento instanceof DesenharQuadrado && proxComportamento instanceof DesenharPoligono && ((DesenharPoligono)proxComportamento).getNumLados() > 3) {
			espaca.setDistancia((int) (Math.ceil(((DesenharPoligono) proxComportamento).getCircleRadius() - (((DesenharPoligono) proxComportamento).getLado()/2))));
		}
		
		// Anda o raio da circunferência sobreescrita no poligono anterior - metade do seu lado
		if (ultiComportamento instanceof DesenharPoligono && ((DesenharPoligono)ultiComportamento).getUltNumLados() > 3 && proxComportamento instanceof DesenharQuadrado) {
			espaca.setDistancia((int) (Math.ceil(((DesenharPoligono) ultiComportamento).getUltCircleRadius() - (((DesenharPoligono) ultiComportamento).getUltLado()/2))));
		}
		
		espaca.desenha();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTp = new JFrame();
		frmTp.setResizable(false);
		frmTp.setTitle("TP2");
		frmTp.getContentPane().setLayout(null);
		this.ultiComportamento = null;
		
		JButton quadradoButton = new JButton("Desenhar Quadrado");
		quadradoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					semaforo.acquire();
					proxComportamento = desenhaQua;
					estadoAtual       = Estado.DESENHA;
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		quadradoButton.setBounds(25, 117, 186, 34);
		frmTp.getContentPane().add(quadradoButton);
		
		JButton circuloButton = new JButton("Desenhar Círculo");
		circuloButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					semaforo.acquire();
					proxComportamento = desenhaCir;
					estadoAtual       = Estado.DESENHA;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
		});
		circuloButton.setBounds(25, 162, 186, 34);
		frmTp.getContentPane().add(circuloButton);
		
		JButton poligonoButton = new JButton("Desenhar Pol\u00EDgono");
		poligonoButton.setEnabled(false);
		poligonoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					semaforo.acquire();
					proxComportamento = desenhaPol;
					estadoAtual       = Estado.DESENHA;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
		});
		poligonoButton.setBounds(25, 208, 186, 34);
		frmTp.getContentPane().add(poligonoButton);
		
		JButton abrirFrames = new JButton("Cliente");
		abrirFrames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFramesCliente();
			}
		});
		abrirFrames.setBounds(25, 77, 91, 27);
		frmTp.getContentPane().add(abrirFrames);
		
		JButton setUp = new JButton("Cliente");
		setUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setUpCliente(); 
				abrirFrames.setEnabled(true);
				circuloButton.setEnabled(true);
				poligonoButton.setEnabled(true);
				quadradoButton.setEnabled(true);
				setUp.setEnabled(false);
				}
		});
		setUp.setBounds(26, 26, 91, 27);
		frmTp.getContentPane().add(setUp);
		frmTp.setBounds(100, 100, 256, 311);
		frmTp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		abrirFrames.setEnabled(false);
		circuloButton.setEnabled(false);
		quadradoButton.setEnabled(false);
		
		
		JLabel lblNewLabel = new JLabel("Set Up:");
		lblNewLabel.setBounds(102, 8, 45, 13);
		frmTp.getContentPane().add(lblNewLabel);
		
		JButton abrirFrames_1 = new JButton("Servidor");
		abrirFrames_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFramesServidor();
			}
		});
		abrirFrames_1.setEnabled(false);
		abrirFrames_1.setBounds(120, 77, 91, 27);
		frmTp.getContentPane().add(abrirFrames_1);
		
		JButton setUp_1 = new JButton("Servidor");
		setUp_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setUpServidor();
					abrirFrames_1.setEnabled(true);
					setUp_1.setEnabled(false);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		setUp_1.setBounds(121, 26, 91, 27);
		frmTp.getContentPane().add(setUp_1);
		
		JLabel lblOpenFrame = new JLabel("Abrir Frames:");
		lblOpenFrame.setBounds(87, 60, 77, 13);
		frmTp.getContentPane().add(lblOpenFrame);
		
	}	
	
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		FrameTP2 tp2 = new FrameTP2();
		tp2.run();
	}
}