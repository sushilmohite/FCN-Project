import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class IMAPCommunicator {

	private final static int PORT = 993;

	public IMAPCommunicator() {

	}

	public void start() {
		try {
			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(PORT);
			
//			SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();

//			ServerSocket ss = new ServerSocket(PORT);

			while (true) {
				new HandleRequest((SSLSocket)sslserversocket.accept()).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class HandleRequest extends Thread {

		private SSLSocket socket;
		private BufferedReader in;
		private PrintWriter out;

		public HandleRequest(SSLSocket clientSocket) {
			this.socket = clientSocket;
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.close();
		}
	}
}
