/**
 * ClientUtil.java
 * 
 * @author Kedarnath Calangutkar, Sushil Mohite, Shivangi
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * ClientUtil class assists the Client class with the authentication
 * 
 * @author Kedarnath Calangutkar, Sushil Mohite, Shivangi
 */
public class ClientUtil {
	
	// IMAPCommunicator connectivity parameters
	public static final int GMAIL_IMAP_PORT = 993;
	public static final String GMAIL_IMAP_HOST_NAME = "imap.gmail.com";

	public static final int OUR_IMAP_PORT = 143;
	public static final String OUR_IMAP_HOST_NAME = "krc9698.wireless.rit.edu";
	
	// SMTP Receiver connectivity parameters
	public static final int GMAIL_SMTP_PORT = 465;
	public static final String GMAIL_SMTP_HOST_NAME = "imap.gmail.com";
	
	public static final int OUR_SMTP_PORT = 25;
	public static final String OUR_SMTP_HOST_NAME = "krc9698.wireless.rit.edu";
	
	// Domain names
	public static final String OUR_DOMAIN = "krc9698.wireless.rit.edu";
	public static final String GMAIL_DOMAIN = "gmail.com";
	
	public static boolean isAuthenticated(String username, String password) {
		
			Socket socket = null;
			Scanner scanner = null;
			PrintWriter out = null;
			try {
				if(username.contains(OUR_DOMAIN)) {
					socket = new Socket(OUR_IMAP_HOST_NAME, OUR_IMAP_PORT);
					System.out.println(socket.getLocalPort() + " " + socket.getPort());
				} else if(username.contains(GMAIL_DOMAIN)) {
					SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
					socket = (SSLSocket) factory.createSocket(GMAIL_IMAP_HOST_NAME, GMAIL_IMAP_PORT);
				} else {
					return false;
				}
				scanner = new Scanner(socket.getInputStream());
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
			System.out.println(socket.getLocalPort() + " " + socket.getPort());
			System.out.println("Logging in.");
			System.out.println(scanner.nextLine());
			System.out.println("a1 login " + username + " " + password);
			out.println("a1 login " + username + " " + password);
			
			scanner.nextLine();
			
			
			if(scanner.nextLine().contains("Success")) {
				out.println("a5 logout");
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				out.close();
				scanner.close();
				return true;
			} else {
				System.err.println("Wrong username/password");
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				out.close();
				scanner.close();
				return false;
			}
	}

}
