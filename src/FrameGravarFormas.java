import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToggleButton;

public class FrameGravarFormas extends JFrame{

	private JTextField textField;
	private File chosenFile;
	private JButton file;
	private GravarFormas gravarFormas;
	private String nomeFicheiro;

	/**
	 * Create the application.
	 */
	public FrameGravarFormas(GravarFormas gravarFormas) {
		this.gravarFormas = gravarFormas;
//		c = new JFileChooser(".");
		initialize();
		nomeFicheiro = "testName";
	}

	private void myHandleNome() {
		try {
			this.nomeFicheiro = this.textField.getText();
			System.out.printf("Novo nome: " + this.nomeFicheiro);			
		} catch (Exception e) {
			System.out.print("Nome Inválido \n");
		}	
	}
	
	public String getFileName() {
		return nomeFicheiro;
	}
	
	public File getChosenFile() {
		return chosenFile;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setVisible(false);
		setTitle("Frame Grava\u00E7\u00E3o");
		setBounds(100, 100, 292, 203);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(25, 53, 128, 19);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myHandleNome();
			}
		});
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nome do Ficheiro:");
		lblNewLabel.setBounds(24, 34, 140, 19);
		getContentPane().add(lblNewLabel);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Iniciar");
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gravarFormas.getEstado().equals(GravarFormas.Estado.Espera)) {
					gravarFormas.iniciarGravacao();
					tglbtnNewToggleButton.setText("Parar");
					return;
				}
				if (gravarFormas.getEstado().equals(GravarFormas.Estado.Gravar)) {
					gravarFormas.pararGravacao();
					tglbtnNewToggleButton.setText("Iniciar");
					return;
				}
			}
		});
		tglbtnNewToggleButton.setBounds(169, 53, 77, 19);
		getContentPane().add(tglbtnNewToggleButton);
		
		file = new JButton("Escolher Ficheiro");
        file.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();

                fc.setCurrentDirectory(Paths.get("C:\\Users\\letic\\OneDrive\\Ambiente de Trabalho\\Code\\FSO\\TP2Fso").toFile());
                int search = fc.showOpenDialog(file);
                if (search == JFileChooser.APPROVE_OPTION) {
                    chosenFile = fc.getSelectedFile();
//                    startCanal.setEnabled(true);
                }
//                else startCanal.setEnabled(false);
            }
        });
        file.setBounds(25, 115, 151, 21);
        getContentPane().add(file);
        
        JButton btnRun = new JButton("Run");
        btnRun.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		gravarFormas.repetirMensagem();;
        	}
        });
        btnRun.setBounds(180, 115, 66, 21);
        getContentPane().add(btnRun);
        
        JLabel lblNewLabel_2 = new JLabel("- Nova Grava\u00E7\u00E3o -");
        lblNewLabel_2.setBounds(95, 15, 150, 13);
        getContentPane().add(lblNewLabel_2);
        
        JLabel lblNewLabel_2_1 = new JLabel("- Importar Grava\u00E7\u00E3o -");
        lblNewLabel_2_1.setBounds(85, 93, 143, 13);
        getContentPane().add(lblNewLabel_2_1);
	}
	
	public static void main(String[] args) {
		new FrameGravarFormas(null);
	}

}
