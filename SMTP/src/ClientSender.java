import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.bind.DatatypeConverter;

public class ClientSender {

	private String username;
	private String password;
	
	public ClientSender(String username, String password) {
	
		this.username = username;
		this.password = password;
	}
	
	public boolean sendEmail(Email email) {
		
		if (username.contains(ClientUtil.OUR_DOMAIN) && !email.getTo().contains(ClientUtil.OUR_DOMAIN)) {
			return false;
		}

		SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket = null;
		Scanner scanner = null;
		PrintWriter out = null;
		try {
			if(email.getTo().contains(ClientUtil.OUR_DOMAIN)) {
				socket = (SSLSocket) factory.createSocket(ClientUtil.OUR_SMTP_HOST_NAME, ClientUtil.OUR_SMTP_PORT);
			} else if(email.getTo().contains(ClientUtil.GMAIL_DOMAIN)) {
				socket = (SSLSocket) factory.createSocket(ClientUtil.GMAIL_SMTP_HOST_NAME, ClientUtil.GMAIL_SMTP_PORT);
			}
			scanner = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		String message = "\000" + username + "\000" + password;
		byte[] auth = message.getBytes();
		String encoded = DatatypeConverter.printBase64Binary(auth);
		out.println("AUTH PLAIN " + encoded);
		scanner.nextLine();
		
		out.println("HELO");
		scanner.nextLine();
		
		out.println("mail from:<" + email.getFrom() + ">");
		scanner.nextLine();
		
		out.println("rcpt to:<" + email.getTo() + ">");
		scanner.nextLine();
		
		out.println("data");
		scanner.nextLine();
		
		String body = "";
		body += "From: " + email.getFrom() + "\n";
		body += "To: " + email.getTo() + "\n";
		body += "Subject: " + email.getSubject() + "\n";
		body += "Date: " + new Date().toString() + "\n";
		body += "Content-Type: " + "boundary=1234" + "\n";
		body += "--1234" + "\n";
		body += email.getHTMLContent() + "\n";
		body += "--1234" + "\n";
		
		Scanner scanContent = new Scanner(body);
		while(scanContent.hasNextLine()) {
			out.println(scanContent.nextLine());
		}
		scanContent.close();
		out.println();
		out.println(".");
		scanner.nextLine();
		
		out.println("quit");
		
		scanner.close();
		out.close();
		
		return true;
	}
}
