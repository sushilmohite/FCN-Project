import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class IMAPCommunicator {

	private final static int PORT = 993;

	public void start() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			
			while(true) {
				new HandleRequest(ss.accept()).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class HandleRequest extends Thread {

		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;

		public HandleRequest(Socket socket) {
			this.socket = socket;
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			
			String loginString = "a1 login ";
			try {
				out.println("someline");
				String login = in.readLine();
				
				if (!login.startsWith(loginString)) {
					out.println("Wrong command");
				}
				
				login = login.substring(loginString.length());
				String[] userpass = login.split(" ");
				
				if (!DBCommunicator.isAuthenticated(userpass[0], userpass[1])) {
					out.println("Wrong authentication");
				}
				
				
				
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
