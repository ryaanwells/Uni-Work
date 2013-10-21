import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

public class MultiEchoServer {
	@SuppressWarnings("resource")
	public static void main(String args[]) {
		ServerSocket echoSocket = null;
		Socket clientSocket = null;
		ReflectorThread thread = null;

		try {
			echoSocket = new ServerSocket(8765);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: localhost.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for "
					+ "the connection to: localhost.");
			System.exit(1);
		}
		
		System.out.println("Initialized correctly, listening");
		
		while (true){
			try {
				clientSocket = echoSocket.accept();
			} catch (IOException e) {
				System.err.println("ERROR in server: IOException on accept");
			}
			System.out.println("here");
			try {
				thread = new ReflectorThread(clientSocket);
			} catch (IOException e) {
				System.err.println("ERROR in server: IOException on thread create");
			}
			thread.run();
		}
	}
}
