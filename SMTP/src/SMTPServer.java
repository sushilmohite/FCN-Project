/**
 * File: SMTPServer.java
 * 
 * @author Kedarnath Calangutkar, Sushil Mohite, Shivangi
 */

public class SMTPServer {
	
	/**
	 * Main method
	 */
	public static void main(String[] args) {
		new SMTPReceiver().start();
		new IMAPCommunicator().start();		
	}
}
