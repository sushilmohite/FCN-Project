import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class SMTPCommunicator extends Thread {

	private final static int PORT = 1055;
	private ArrayBlockingQueue<Email> emailQueue;
	
	public SMTPCommunicator(ArrayBlockingQueue<Email> emailQueue) {
		this.emailQueue = emailQueue;
	}
	
	public void run() {
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
				
				Email email = (Email) in.readObject();
				emailQueue.add(email);
				emailQueue.notify();
					
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
