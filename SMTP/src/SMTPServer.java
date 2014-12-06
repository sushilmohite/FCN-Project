import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class SMTPServer {

	static String data = "From: Sushil Mohite <spm6749@cs.rit.edu>\r\nDate: Sun, 30 Nov 2014 00:38:50 +0000\r\nSubject: Haha\r\nTo: \"Kedarnath Calangutkar\" <krc9698@cs.rit.edu>\r\n\r\nContent-Type: text/plain; charset=UTF-8\r\n\r\nHello Kedarnath\r\n\r\nHow are you?\r\n\r\nRegards,\r\nSushil Mohite\r\n\r\n";
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket = new Socket("mailhost.cs.rit.edu", 25);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		String initialID = in.readLine();
		System.out.println(initialID);
		System.out.println("HELO " + InetAddress.getLocalHost().getHostName());
		out.println("HELO " + InetAddress.getLocalHost().getHostName());
		String welcome = in.readLine();
		System.out.println(welcome);
		
		System.out.println("MAIL From:<" + "spm6749@cs.rit.edu" + ">");
		out.println("MAIL From:<" + "spm6749@cs.rit.edu" + ">");
		String senderOK = in.readLine();
		System.out.println(senderOK);
		System.out.println("RCPT TO:<" + "krc9698@cs.rit.edu" + ">");
		out.println("RCPT TO:<" + "krc9698@cs.rit.edu" + ">");
		String recipientOK = in.readLine();
		System.out.println(recipientOK);
		System.out.println("DATA");
		out.println("DATA");
		String datainput = in.readLine();
		System.out.println(datainput);
		//String line;
		/*while ((line = msg.readLine()) != null) {
			out.println(line);
		}*/
		System.out.println(data);
		out.println(data);
		System.out.println(".");
		out.println(".");
		String acceptedOK = in.readLine();
		System.out.println(acceptedOK);
		System.out.println("QUIT");
		out.println("QUIT");
	}

}
