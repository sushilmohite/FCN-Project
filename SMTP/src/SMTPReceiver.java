import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SMTPReceiver {

	private final static int PORT = 25;
	
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
			
			String mailFrom = "";
			String mailTo = "";
			String body = "";
			String subject = "";
			String date = "";
			String content = "";
			String boundary = "";
			String contentString = "Content-Type: ";
			String subjectString = "Subject: ";
			String fromString = "From: ";
			String toString = "To: ";
			String dateString = "Date: ";
			
			try {
				out.println(220);
				in.readLine();
				
				out.println(250);
				in.readLine();
				
				out.println(250);
				in.readLine();
				
				out.println(250);
				if (in.readLine().equalsIgnoreCase("data")) {
					out.println(354);
					String input;
					while(!(input = in.readLine()).equals(".")) {
						body += input + "\n";
					}
					out.println(250);
				}
				
				if (in.readLine().equalsIgnoreCase("quit")) {
					out.println(221);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			Scanner sc = new Scanner(body);
			while(sc.hasNextLine()) {
				String input = sc.nextLine();
				if (input.startsWith(fromString)) {
					mailFrom = input;
				}
				if (input.startsWith(toString)) {
					mailTo = input;
				}
				if (input.startsWith(subjectString)) {
					subject = input;
				}
				if (input.startsWith(dateString)) {
					date = input;
				}
				if (input.startsWith(contentString)) {
					int n = input.indexOf("boundary");
					boundary = input.substring(n + 9);
				}
				if (boundary != "") {
					if (input.startsWith("--" + boundary)) {
						sc.nextLine();
						while (!(input = sc.nextLine()).startsWith("--" + boundary)) {
							content += input + "\n";
						}
						break;
					}
				}
			}
			
			sc.close();
			
			System.out.println(mailFrom);
			System.out.println(mailTo);
			System.out.println(subject);
			System.out.println(date);
			System.out.println(content);
		}
	}
}
