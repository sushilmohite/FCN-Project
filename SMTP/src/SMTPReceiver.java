import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SMTPReceiver {

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
			
		}
	}
	
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
