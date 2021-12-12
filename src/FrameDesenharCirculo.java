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
public class FrameDesenharCirculo extends JFrame {
	private DesenharCirculo compDesenhador;
	private int raio;
	private JTextField textField;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameDesenharCirculo window = new FrameDesenharCirculo();
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
	public FrameDesenharCirculo() {
		initialize();
	}
	
	public void setComportamento(DesenharCirculo compDesenhador) {
		this.compDesenhador = compDesenhador;
	}
	
	private void myHandleRaio() {
		try {
			this.raio = Integer.parseInt(this.textField.getText());		
			this.compDesenhador.setRaio(raio);
			System.out.println("Novo Raio Círculo: " + raio);
			write("Novo Raio: " + raio);
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
		setTitle("Frame Círculo");
		setResizable(false);
		setBounds(100, 100, 337, 223);
		getContentPane().setLayout(null);
		
		
		this.raio = 10;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 70, 275, 95);
		getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		
		textField = new JTextField();
		textField.setText(Integer.toString(raio));
		textField.setColumns(10);
		textField.setBounds(56, 23, 96, 19);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myHandleRaio();
			}
		});
		getContentPane().add(textField);
		
		JLabel lblNewLabel_1_1 = new JLabel("Raio:");
		lblNewLabel_1_1.setBounds(22, 26, 54, 13);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel = new JLabel("Updates:");
		lblNewLabel.setBounds(22, 55, 74, 13);
		getContentPane().add(lblNewLabel);
	}
}