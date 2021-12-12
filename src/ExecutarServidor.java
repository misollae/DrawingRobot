import java.lang.reflect.InvocationTargetException;

public class ExecutarServidor {
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		ServidorRobot servidor = new ServidorRobot();
		servidor.start();
	}
}
