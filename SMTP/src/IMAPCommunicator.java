/**
 * IMAP Communicator
 * 
 * @author Kedarnath Calangutkar, Sushil Mohite, Shivangi 
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

/**
 * IMAPCommunicator class extracts the emails from the database via DBCommunicator
 * and transfers them to the client using IMAP protocols
 * 
 * @author Kedarnath Calangutkar, Sushil Mohite, Shivangi
 */
public class IMAPCommunicator extends Thread {

	// Port for IMAP
	private final static int PORT = 143;

	public void run() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			
			while(true) {
				Socket s = ss.accept();
				System.out.println(Thread.currentThread().toString() + s.getPort() + " " + s.getLocalPort());
				new HandleRequest(s).start();
				Thread.sleep(100);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class HandleRequest extends Thread {

		private Socket socket;
		private Scanner in;
		private PrintWriter out;
		private Email[] emails = null;

		public HandleRequest(Socket socket) {
			this.socket = socket;
			try {
				in = new Scanner(socket.getInputStream());
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			
			System.out.println(">" + Thread.currentThread().toString() + socket.getPort() + " " + socket.getLocalPort());
			String loginString = "a1 login ";
			System.out.println("* OK ready for requests");
			out.println("* OK ready for requests");
			String login = in.nextLine();
			System.out.println(login);
			if (!login.startsWith(loginString)) {
				out.println("Wrong command");
				out.close();
				return;
			}
			
			login = login.substring(loginString.length());
			String[] userpass = login.split(" ");
			
			String username = userpass[0].substring(0, userpass[0].indexOf('@'));
			if (!DBCommunicator.isAuthenticated(username, userpass[1])) {
				out.println("Wrong authentication");
				return;
			} else {
				out.println("* CAPABILITY SELECT EXAMINE FETCH STORE");
				out.println("a1 OK " + userpass[0] + " authenticated (Success)");
			}
			
			String line = in.nextLine();
			System.out.println("Fetching mails for: " + username);
			if(line.contains("examine")) {
				emails = DBCommunicator.fetchEmails(username);
			} else if(line.contains("select")) {
				emails = DBCommunicator.fetchEmails(username);
				updateSeen();
				return;
			}

			System.out.println(Arrays.toString(emails));
			
			out.println("(* " + emails.length + " EXISTS)");
			out.println("a3 OK Success");
			
			String[] word = in.nextLine().split(" ");
			//"a4 fetch " + start + ":" + end + " BODY[]");
			String[] index = word[2].split(":");
			int start = Integer.parseInt(index[0]);
			int end = Integer.parseInt(index[1]);
			for(int i = start - 1; i < end; i++) {
				//(\\* " + start + " FETCH \\(BODY\\[\\])
				System.out.println("Sending mail " + i);
				System.out.println("* " + (i + 1) + " FETCH (BODY[]");
				out.println("* " + (i + 1) + " FETCH (BODY[]");
				System.out.println(emails[i].getHTMLContent());
				out.println(emails[i].getHTMLContent());
				out.println("");
			}
			System.out.println("a4 OK Success");
			out.println("a4 OK Success");
			
			word = in.nextLine().split(" ");
			index = word[2].split(":");
			start = Integer.parseInt(index[0]);
			end = Integer.parseInt(index[1]);
			for(int i = start - 1; i < end; i++) {
				if(emails[i].isSeen()) {
					out.println("* " + (i + 1) + " \\Seen");
				} else {
					out.println("* " + (i + 1));
				}
			}
			
			line = in.nextLine();
			
			in.close();
			out.close();
			
		}

		private void updateSeen() {
			String line = in.nextLine();
			if(!line.contains("store")) {
				return;
			}
			System.out.println(line);
			String[] words = line.split(" ");
			int id = Integer.parseInt(words[2]);
			DBCommunicator.setEmailAsSeen(emails[id - 1].getId());
			System.out.println("a4 Success");
			out.println("a4 Success");
			in.nextLine();
		}
	}
}
