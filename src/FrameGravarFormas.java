import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToggleButton;

public class FrameGravarFormas {

	private JFrame frame;
	private JTextField textField;
	private File chosenFile;
	private JButton file;
	private GravarFormas gravarFormas;
	

//	private JButton gravar;
//	private JButton stop;
//	private JFileChooser c;
//	private JButton play;
//	private JButton browse;


	/**
	 * Create the application.
	 */
	public FrameGravarFormas(GravarFormas gravarFormas) {
		this.gravarFormas = gravarFormas;
//		c = new JFileChooser(".");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 369, 185);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(26, 35, 96, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nome Ficheiro");
		lblNewLabel.setBounds(26, 12, 96, 19);
		frame.getContentPane().add(lblNewLabel);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Iniciar Grava\u00E7\u00E3o");
		tglbtnNewToggleButton.setBounds(150, 35, 107, 19);
		frame.getContentPane().add(tglbtnNewToggleButton);
		
		file = new JButton("File");
        file.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
//                fc.setCurrentDirectory(Paths.get("C:\comunicacao\comunicacao.dat").toFile());
                int search = fc.showOpenDialog(file);
                if (search == JFileChooser.APPROVE_OPTION) {
                    chosenFile = fc.getSelectedFile();
//                    startCanal.setEnabled(true);
                }
//                else startCanal.setEnabled(false);
            }
        });
        file.setBounds(26, 64, 66, 21);
        frame.getContentPane().add(file);
	}
}
