import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class SMTPReceiver extends Thread {
	
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(25);
		Socket socket = ss.accept();
		System.out.println("Connected!");

		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
		Scanner sc = new Scanner(System.in);
		
		String lastCommand = "";
		while(sc.hasNext()) {
			String line = sc.nextLine();
			out.println(line);
			String command = in.readLine();
			System.out.println(lastCommand);
			if(lastCommand.equals("DATA")){
				while(!command.equals(".")) {
					command = in.readLine();
					System.out.println(command);
				}
			}
			lastCommand = command;
		}
		
		sc.close();
		ss.close();
	}
}
