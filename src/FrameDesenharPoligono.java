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
public class FrameDesenharPoligono extends JFrame {
	private DesenharPoligono compDesenhador;
	private int lado;
	private int numLados;
	private JTextField textField;
	private JTextPane textPane;
	private JTextField textField2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameDesenharPoligono window = new FrameDesenharPoligono();
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
	public FrameDesenharPoligono() {
		initialize();
	}
	
	public void setComportamento(DesenharPoligono compDesenhador) {
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
	
	private void myHandleNumLados() {
		try {
			if (Integer.parseInt(this.textField2.getText()) >= 3) this.numLados = Integer.parseInt(this.textField2.getText());			
			System.out.printf("Novo Número de Lados Poligono: %d\n", numLados);
			
			write("Novo Número de Lados: " + numLados);
			this.compDesenhador.setNumLados(numLados);
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
		setTitle("Frame Poligono");
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
		this.numLados = 5;
		
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
		textField.setBounds(60, 23, 74, 19);
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
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Num Lados:");
		lblNewLabel_1_1_1.setBounds(148, 26, 74, 13);
		getContentPane().add(lblNewLabel_1_1_1);
		
		textField2 = new JTextField();
		textField2.setText("5");
		textField2.setColumns(10);
		textField2.setBounds(223, 23, 74, 19);
		textField2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myHandleNumLados();
			}
		});
		getContentPane().add(textField2);
	}
}