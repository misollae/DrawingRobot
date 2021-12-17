import java.io.BufferedReader;
import java.io.OutputStream;

public class GravarFormas extends Thread{
	private FrameGravarFormas GUIGravarFormas;
	private String[] strings;
	private OutputStream output;
	private String name;
	private String line;
	private RobotDesenhador robot;
	private BufferedReader br;
	
	public GravarFormas(RobotDesenhador robot) {
		this.GUIGravarFormas = new FrameGravarFormas(this);
		this.robot = robot;
	}
	
}
