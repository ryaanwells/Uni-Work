import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ReflectorThread implements Runnable{
	
	private PrintWriter out;
	private BufferedReader in;
	
	private String inputLine;
	
	public ReflectorThread(Socket socket) throws IOException{
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	@Override
	public void run() {
		while (true){
			try {
				inputLine = in.readLine();
				System.out.println("READ");
			} catch (IOException e){
				System.err.println("ERROR in thread: IOError when receiving. Closing thread");
				return;
			}
			
			if (inputLine != null){
				System.out.println(inputLine);
				out.println(inputLine);
			}
			else {
				return;
			}
			
		}
	}

}
