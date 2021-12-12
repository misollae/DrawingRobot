import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class FrameDesenharQuadrado extends JFrame {
	private DesenharQuadrado compDesenhador;
	private int lado;
	private JTextField textField;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameDesenharQuadrado window = new FrameDesenharQuadrado();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrameDesenharQuadrado() {
		initialize();
	}
	
	public void setComportamento(DesenharQuadrado compDesenhador) {
		this.compDesenhador = compDesenhador;
	}

	private void myHandleDistancia() {
		try {
			this.lado = Integer.parseInt(this.textField.getText());			
			System.out.printf("Novo Lado Quadrado: %d\n", lado);
			write("Novo Lado: " + lado);
			this.compDesenhador.setLado(lado);
		} catch (Exception e) {
		}	
	}
	
	private void  myHandleWindowClosing(){
		System.out.print("Main window is closing...\n");
		this.setVisible(false);
		this.dispose();
	}
	
	private void  myHandleWindowClosed(){
		System.out.print("Main window is closed...\n");
	}
	
	public void write(String s) {
		textPane.setText(textPane.getText() + s + "\n");
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Frame Quadrado");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				myHandleWindowClosing();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				myHandleWindowClosed();
			}
		});
		setBounds(100, 100, 347, 222);
		getContentPane().setLayout(null);
		
		this.lado = 10;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 70, 275, 95);
		getContentPane().add(scrollPane);
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JLabel lblNewLabel_1 = new JLabel("Lado:");
		lblNewLabel_1.setBounds(22, 73, 74, 13);
		getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setText(Integer.toString(lado));
		textField.setColumns(10);
		textField.setBounds(56, 23, 96, 19);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myHandleDistancia();
			}
		});
		getContentPane().add(textField);
		
		JLabel lblNewLabel_1_1 = new JLabel("Lado:");
		lblNewLabel_1_1.setBounds(22, 26, 54, 13);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel = new JLabel("Updates:");
		lblNewLabel.setBounds(22, 55, 74, 13);
		getContentPane().add(lblNewLabel);
	}
}