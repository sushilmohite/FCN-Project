/**
 * File: SMTPReceiver.java
 * 
 * @author Kedarnath Calangutkar, Sushil Mohite, Shivangi
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class listens for new connections
 * @author Kedarnath Calangutkar, Sushil Mohite, Shivangi
 *
 */
public class SMTPReceiver extends Thread {

	private final static int PORT = 25;
	
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			
			while(true) {
				new HandleRequest(ss.accept()).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A new object of this class is created for every connection established
	 * @author Kedarnath Calangutkar, Sushil Mohite, Shivangi
	 *
	 */
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
			
			String mailFrom = null;
			String mailTo = null;
			String body = "";
			String subject = null;
			String date = null;
			String subjectString = "Subject: ";
			String fromString = "From: ";
			String toString = "To: ";
			String dateString = "Date: ";
			
			try {
				out.println(220);
				String temp = in.readLine();
				if(temp.startsWith("AUTH PLAIN")) {
					out.println("Accepted");
					in.readLine();
				}
				
				out.println(250);
				in.readLine();
				
				out.println(250);
				in.readLine();
				
				out.println(250);
				if (in.readLine().equalsIgnoreCase("data")) {
					out.println(354);
					String input;
					while(!(input = in.readLine()).equals(".")) {
						if (input.startsWith(fromString)) {
							input.replaceAll("<", "");
							input.replaceAll(">", "");
						}
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
				if ((mailFrom != null) && (mailTo != null) && (subject != null) && (date != null)) {
					break;
				}
			}
			
			sc.close();
			
			// extracting username from email id
			mailTo = mailTo.substring(toString.length(), mailTo.indexOf('@'));
			
			if(mailFrom.contains(ClientUtil.OUR_DOMAIN)) {
				mailFrom = mailFrom.substring(fromString.length());
			} else {
				mailFrom = mailFrom.substring(mailFrom.indexOf('<') + 1, mailFrom.indexOf('>'));
			}
			subject = subject.substring(subjectString.length());
			date = date.substring(dateString.length());
			
			if (DBCommunicator.isUser(mailTo)) {
				Email email = new Email(mailFrom, mailTo, subject, date, body);
				DBCommunicator.saveEmail(email);
			}
		}
	}
}
