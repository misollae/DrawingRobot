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
	private DesenharCirculo desenhaCir;
	private DesenharPoligono desenhaPol;
	private EspacarFormasGeometricas espaca;
	private ServidorRobot servidor;
	private GravarFormas gravador;
	private Semaphore semaforo;
	private JFrame frmTp;

	/**
	 * Create the application.
	 * 
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
		semaforo = new Semaphore(1);
		this.buffer = new BufferCircular();
	}

	public void setUpCliente() {
		this.cliente = new ClienteDoRobot(buffer);
		this.espaca = new EspacarFormasGeometricas();
		this.desenhaQua = new DesenharQuadrado(espaca);
		this.desenhaPol = new DesenharPoligono(espaca);
		this.desenhaCir = new DesenharCirculo(espaca);
		espaca.setCliente(cliente, semaforo);
		desenhaQua.setCliente(cliente, semaforo);
		desenhaPol.setCliente(cliente, semaforo);
		desenhaCir.setCliente(cliente, semaforo);

		desenhaQua.start();
		desenhaPol.start();
		desenhaCir.start();
		espaca.start();
	}

	public void setUpServidor() throws InvocationTargetException, InterruptedException {
		gravador = new GravarFormas();
		this.servidor = new ServidorRobot(gravador);
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

	private void initialize() {
		frmTp = new JFrame();
		frmTp.setResizable(false);
		frmTp.setTitle("TP2");
		frmTp.getContentPane().setLayout(null);

		JButton quadradoButton = new JButton("Desenhar Quadrado");
		quadradoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				espaca.setProximo(desenhaQua);
				espaca.avanca();
				desenhaQua.avanca();

			}
		});
		quadradoButton.setBounds(25, 129, 186, 34);
		frmTp.getContentPane().add(quadradoButton);

		JButton circuloButton = new JButton("Desenhar Círculo");
		circuloButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				espaca.setProximo(desenhaCir);
				espaca.avanca();
				desenhaCir.avanca();
			}
		});
		circuloButton.setBounds(25, 174, 186, 34);
		frmTp.getContentPane().add(circuloButton);

		JButton poligonoButton = new JButton("Desenhar Pol\u00EDgono");
		poligonoButton.setEnabled(false);
		poligonoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				espaca.setProximo(desenhaPol);
				espaca.avanca();
				desenhaPol.avanca();
			}
		});
		poligonoButton.setBounds(25, 220, 186, 34);
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
		frmTp.setBounds(100, 100, 259, 358);
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

		JButton abrirFrames_2 = new JButton("Grava\u00E7\u00E3o");
		abrirFrames_2.setEnabled(false);
		abrirFrames_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gravador.abrirFrame();
			}
		});
		abrirFrames_2.setBounds(69, 264, 107, 27);
		frmTp.getContentPane().add(abrirFrames_2);
		
		JButton setUp_1 = new JButton("Servidor");
		setUp_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					setUpServidor();
					abrirFrames_1.setEnabled(true);
					abrirFrames_2.setEnabled(true);
					setUp_1.setEnabled(false);
					gravador.start();
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
		
		
		JLabel lblExecutarFormas = new JLabel("Executar Formas:");
		lblExecutarFormas.setBounds(75, 112, 113, 13);
		frmTp.getContentPane().add(lblExecutarFormas);

	}

	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		FrameTP2 tp2 = new FrameTP2();
	}
}