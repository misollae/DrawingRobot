import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;

import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;

public class FrameServidor {	
	private RobotDesenhador robot;

	private String RobotNome;
	private int RobotDistancia;
	private int RobotRaio;
	private int RobotAngulo;
	private boolean RobotEstadoLigacao;
	
	private JFrame frame;
	private JTextField TextFieldNome;
	private JTextField TextFieldRaio;
	private JTextField TextFieldDistancia;
	private JTextField TextFieldAngulo;
	private JTextPane textPane;
	private JButton BotaoEsquerda;
	private JButton BotaoDireita;
	private JButton BotaoAtras;
	private JButton BotaoFrente;
	private JButton BotaoParar;
	private JToggleButton OpenCloseToggle;


	/**
	 * Launch the application.
	 */
	private void myInitialize() {
	}
	
	public void openFrame() {
		this.frame.setVisible(true);
	}
	
	public void setRobot(RobotDesenhador robot) {
		this.robot = robot;
	}
	
	public boolean robotIsOpen() {
		return RobotEstadoLigacao;
	}
	
	public void write(String s) {
		textPane.setText(textPane.getText() + s + "\n");
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}
	
	private void myHandleRaio() {
		try {
			this.RobotRaio = Integer.parseInt(this.TextFieldRaio.getText());
			System.out.printf("Novo raio: %d\n", this.RobotRaio);	
			textPane.setText(textPane.getText() + "Novo raio: " + this.TextFieldRaio.getText() + "\n");
		} catch (Exception e) {
			System.out.print("Raio Inválido. \n");
			textPane.setText(textPane.getText() + "Raio Inválido." + "\n");
		}		
	}
	
	private void myHandleDistancia() {
		try {
			this.RobotDistancia = Integer.parseInt(this.TextFieldDistancia.getText());
			System.out.printf("Nova distância: %d\n", this.RobotDistancia);
			textPane.setText(textPane.getText() + "Nova Distância: " + this.TextFieldDistancia.getText() + "\n");
			
		} catch (Exception e) {
			System.out.print("Distância Inválida \n");
			textPane.setText(textPane.getText() + "Distância Inválida." + "\n");
		}	
	}
	
	private void myHandleAngulo() {
		try {
			this.RobotAngulo = Integer.parseInt(this.TextFieldAngulo.getText());
			System.out.printf("Novo ângulo: %d\n", this.RobotAngulo);
			textPane.setText(textPane.getText() + "Novo Ângulo: " + this.TextFieldAngulo.getText() + "\n");
		} catch (Exception e) {
			System.out.print("Ângulo Inválido \n");
			textPane.setText(textPane.getText() + "Ângulo Inválido." + "\n");
		}	
	}
	
	private void myHandleOpenClose() {
		this.RobotNome = this.TextFieldNome.getText();
		
		if (!this.RobotEstadoLigacao) {
			if (robot.OpenEV3(this.RobotNome)) {
				this.RobotEstadoLigacao = true;
				System.out.printf("Nome do robot = %s\n", this.RobotNome);	
				textPane.setText(textPane.getText() + "Nome do robot = " + this.RobotNome + "\n");

				BotaoParar.setEnabled(true);
				BotaoEsquerda.setEnabled(true);
				BotaoFrente.setEnabled(true);
				BotaoAtras.setEnabled(true);
				BotaoDireita.setEnabled(true);
				TextFieldDistancia.setEnabled(true);
				TextFieldRaio.setEnabled(true);
				TextFieldAngulo.setEnabled(true);
			}
		}
		else {
			this.RobotEstadoLigacao = false;
			robot.Close();
			
			BotaoParar.setEnabled(false);
			BotaoEsquerda.setEnabled(false);
			BotaoFrente.setEnabled(false);
			BotaoAtras.setEnabled(false);
			BotaoDireita.setEnabled(false);
			TextFieldDistancia.setEnabled(false);
			TextFieldRaio.setEnabled(false);
			TextFieldAngulo.setEnabled(false);
		}
		OpenCloseToggle.setSelected(this.RobotEstadoLigacao);
		if(RobotEstadoLigacao) OpenCloseToggle.setText("Close Robot"); else OpenCloseToggle.setText("Open Robot");
	}

	private void  myHandleWindowClosing(){
		robot.Parar(true);
		robot.Close();
		System.out.print("Main window is closing...\n");
		this.frame.setVisible(false);
		this.frame.dispose();
	}
	
	private void  myHandleWindowClosed(){
		System.out.print("Main window is closed...\n");
		System.out.print(0);
	}
	
	
	/**
	 * Create the application.
	 */
	public FrameServidor() {
		initialize();
		frame.setTitle("Frame Servidor");
		
		this.RobotNome = "EVC";
		this.RobotDistancia = 20;
		this.RobotAngulo = 90;
		this.RobotRaio = 0;
		
		this.RobotEstadoLigacao = false;
		this.TextFieldNome.setText(this.RobotNome);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 222, 438, 166);
		frame.getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);

		myInitialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				myHandleWindowClosing();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				myHandleWindowClosed();
			}
		});
		
		frame.setResizable(false);
		frame.setBounds(100, 100, 500, 453);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(21, 10, 46, 26);
		frame.getContentPane().add(lblNome);
		
		TextFieldNome = new JTextField();
		TextFieldNome.setBounds(94, 14, 96, 19);
		frame.getContentPane().add(TextFieldNome);
		TextFieldNome.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Raio");
		lblNewLabel.setBounds(21, 46, 45, 13);
		frame.getContentPane().add(lblNewLabel);
		
		TextFieldRaio = new JTextField();
		TextFieldRaio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myHandleRaio();
			}
		});
		
		TextFieldRaio.setBounds(94, 43, 96, 19);
		frame.getContentPane().add(TextFieldRaio);
		TextFieldRaio.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Dist\u00E2ncia");
		lblNewLabel_1.setBounds(21, 75, 74, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		TextFieldDistancia = new JTextField();
		TextFieldDistancia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myHandleDistancia();
			}
		});
		
		TextFieldDistancia.setBounds(94, 72, 96, 19);
		frame.getContentPane().add(TextFieldDistancia);
		TextFieldDistancia.setColumns(10);
		
		TextFieldAngulo = new JTextField();
		TextFieldAngulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myHandleAngulo();
			}
		});
		
		TextFieldAngulo.setBounds(363, 72, 96, 19);
		frame.getContentPane().add(TextFieldAngulo);
		TextFieldAngulo.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("\u00C2ngulo");
		lblNewLabel_2.setBounds(308, 75, 45, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		BotaoFrente = new JButton("Frente");
		BotaoFrente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robot.Reta(RobotDistancia);
				robot.Parar(false);
				textPane.setText(textPane.getText() + "Andar para a Frente: " + RobotDistancia + "\n");
			}
		});
		BotaoFrente.setBounds(204, 113, 85, 21);
		frame.getContentPane().add(BotaoFrente);
		
		BotaoParar = new JButton("Parado");
		BotaoParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robot.Parar(false);
			}
		});
		BotaoParar.setBounds(204, 144, 85, 21);
		frame.getContentPane().add(BotaoParar);
		
		OpenCloseToggle = new JToggleButton("Open Robot");
		OpenCloseToggle.setBounds(308, 13, 150, 21);
		OpenCloseToggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myHandleOpenClose();
			}
		});
		frame.getContentPane().add(OpenCloseToggle);
		
		BotaoAtras = new JButton("Tr\u00E1s");
		BotaoAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robot.Reta(-RobotDistancia);
				robot.Parar(false);
				textPane.setText(textPane.getText() + "Andar para Trás: " + RobotDistancia + "\n");
			}
		});
		BotaoAtras.setBounds(204, 175, 85, 21);
		frame.getContentPane().add(BotaoAtras);
		
		BotaoDireita = new JButton("Direita");
		BotaoDireita.setBounds(299, 144, 96, 21);
		BotaoDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robot.CurvarDireita(RobotAngulo, RobotRaio);
				robot.Parar(false);
				textPane.setText(textPane.getText() + "Curvar à Direita: (" + RobotAngulo + ", " + RobotRaio + ")\n");
			}
		});
		frame.getContentPane().add(BotaoDireita);
		
		BotaoEsquerda = new JButton("Esquerda");
		BotaoEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robot.CurvarEsquerda(RobotAngulo, RobotRaio);
				robot.Parar(false);
				textPane.setText(textPane.getText() + "Curvar à Esquerda: (" + RobotAngulo + ", " + RobotRaio + ")\n");
			}
		});
		BotaoEsquerda.setBounds(94, 144, 100, 21);
		frame.getContentPane().add(BotaoEsquerda);
		
		JLabel lblNewLabel_3 = new JLabel("Mensagens");
		lblNewLabel_3.setBounds(22, 206, 73, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		
		
		BotaoAtras.setEnabled(false);
		BotaoFrente.setEnabled(false);
		BotaoDireita.setEnabled(false);
		BotaoEsquerda.setEnabled(false);
		BotaoParar.setEnabled(false);
		TextFieldAngulo.setEnabled(false);
		TextFieldRaio.setEnabled(false);
		TextFieldDistancia.setEnabled(false);
	}
}