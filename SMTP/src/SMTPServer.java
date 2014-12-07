
public class SMTPServer {
	
	public static void main(String[] args) {
		new SMTPReceiver();
		new IMAPCommunicator().start();		
	}
}
