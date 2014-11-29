import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class SMTPServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("smtp.gmail.com", 25);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		String initialID = in.readLine();
		System.out.println(initialID);
		System.out.println("HELO " + InetAddress.getLocalHost().getHostName());
		out.println("HELO " + InetAddress.getLocalHost().getHostName());
		String welcome = in.readLine();
		System.out.println(welcome);
		
		out.println("STARTTLS");
		System.out.println("STARTTLS");
		
		System.out.println("MAIL From:<" + "fussion21@gmail.com" + ">");
		out.println("MAIL From:<" + "fussion21@gmail.com" + ">");
		String senderOK = in.readLine();
		System.out.println(senderOK);
		System.out.println("RCPT TO:<" + "fussion21@gmail.com" + ">");
		out.println("RCPT TO:<" + "fussion21@gmail.com" + ">");
		String recipientOK = in.readLine();
		System.out.println(recipientOK);
		System.out.println("DATA");
		out.println("DATA");
		//String line;
		/*while ((line = msg.readLine()) != null) {
			out.println(line);
		}*/
		out.println("Hi Kedar");
		System.out.println("Hi Kedar");
		System.out.println(".");
		out.println(".");
		String acceptedOK = in.readLine();
		System.out.println(acceptedOK);
		System.out.println("QUIT");
		out.println("QUIT");
	}

}
