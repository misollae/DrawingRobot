import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class FrameEspacarFormas extends JFrame {
	private EspacarFormasGeometricas compDesenhador;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameEspacarFormas window = new FrameEspacarFormas();
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
	public FrameEspacarFormas() {
		initialize();
	}
	
	public void setComportamento(EspacarFormasGeometricas compDesenhador) {
		this.compDesenhador = compDesenhador;
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
		setTitle("Frame Espaçar");
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
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 30, 280, 130);
		getContentPane().add(scrollPane);
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JLabel lblNewLabel = new JLabel("Updates:");
		lblNewLabel.setBounds(22, 15, 74, 13);
		getContentPane().add(lblNewLabel);
	}
}