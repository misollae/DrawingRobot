import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;

public class FrameRobotDesenhador {

	private JFrame frmFrameRobot;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameRobotDesenhador window = new FrameRobotDesenhador();
					window.frmFrameRobot.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrameRobotDesenhador() {
		initialize();
	}
	
	public void openFrame() {
		frmFrameRobot.setVisible(true);
	}
	
	public void write(String s) {
		textPane.setText(textPane.getText() + s + "\n");
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFrameRobot = new JFrame();
		frmFrameRobot.setTitle("Frame Robot");
		frmFrameRobot.setBounds(100, 100, 350, 226);
		frmFrameRobot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFrameRobot.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 42, 305, 124);
		frmFrameRobot.getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		JLabel lblNewLabel = new JLabel("Comandos Executados:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(90, 10, 167, 35);
		frmFrameRobot.getContentPane().add(lblNewLabel);
	}

}