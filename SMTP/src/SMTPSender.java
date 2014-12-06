import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class SMTPSender {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket = new Socket("smtp.gmail.com", 25);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

		Scanner sc = new Scanner(System.in);
		System.out.println("Connected!");
		
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			out.println(line);
			String command = in.readLine();
			System.out.println(command);
		}
		
		sc.close();
		in.close();
		out.close();
		socket.close();
	}
}
