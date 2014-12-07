import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class IMAPCommunicator {

	private final static int PORT = 993;
	
	public IMAPCommunicator() {}
	
	public void start() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			
			while (true) {
				new HandleRequest(ss.accept()).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class HandleRequest extends Thread {
		
		private Socket clientSocket;
		
		public HandleRequest(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		public void run() {
			try {
				ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				
				String userNameString = (String) in.readObject();
				String password = (String) in.readObject();
				
				// Authenticate
				
				out.println("1");
					
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
