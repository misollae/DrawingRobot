import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class GravarFormas extends Thread {
	private FrameGravarFormas frameGravar;
	private String[] strings;
	private OutputStream output;
	private String name;
	private String line;
	private RobotDesenhador robot;
	private BufferedReader br;

	public enum Estado {
		Espera, Gravar, Parar
	}

	private Estado estado;

	public GravarFormas() {
		this.frameGravar = new FrameGravarFormas(this);
		estado = Estado.Espera;
	}

	public void abrirFrame() {
		frameGravar.setVisible(true);
	}

	public Estado getEstado() {
		return estado;
	}

	public void iniciarGravacao() {
		estado = Estado.Gravar;
	}

	public void pararGravacao() {
		estado = Estado.Espera;
		try {

			File novoFicheiro = new File(frameGravar.getFileName());
			if (novoFicheiro.createNewFile()) {
				System.out.println("File created.");
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (estado != Estado.Parar) {
			switch (estado) {
			case Espera:
				break;
			case Gravar:
				break;
			default:
				break;
			}
		}
	}
}
