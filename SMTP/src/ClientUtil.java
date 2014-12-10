import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class ClientUtil {
	
	// Database connectivity parameters
//	public static String dbServerIP = "krc9698.wireless.rit.edu";
	public static String dbServerIP = "localhost";
	public static String schema = "fcn";
	public static String dbuser = "kedar";
	public static String dbpass = "kedar";
	public static String driver = "com.mysql.jdbc.Driver";
	public static String connectionURL = "jdbc:mysql://" + dbServerIP + "/" + schema;
	
	// IMAPCommunicator connectivity parameters
	public static final int IMAP_PORT = 993;
	public static final String GMAIL_IMAP_HOST_NAME = "imap.gmail.com";
	
	// SMTP Receiver connectivity parameters
	public static final int GMAIL_SMTP_PORT = 587;
	public static final String GMAIL_SMTP_HOST_NAME = "smtp.gmail.com";
	
	public static final int OUR_SMTP_PORT = 25;
	public static final String OUR_SMTP_HOST_NAME = "krc9698.wireless.rit.edu";
	
	// Domain names
	public static final String OUR_DOMAIN = "krc9698.wireless.rit.edu";
	public static final String GMAIL_DOMAIN = "gmail.com";
	
	// Tables
	public static final String USER_TABLE = "fcn.user_info";
	
	public static boolean isAuthenticated(String username, String password) {
		if(username.contains(OUR_DOMAIN)) {
			String storedPassword = null;
			try {
				Class.forName(driver);
				Connection connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
				Statement statement = connection.createStatement();
	
				ResultSet rs = statement.executeQuery("SELECT * FROM " + USER_TABLE + " WHERE username = '" + username + "'");
				if(rs.next()) {
					storedPassword = rs.getString("password");
				}
				if(rs.next()) {
					System.err.println("Multiple entries found!");
				}
	
				rs.close();
				statement.close();
				connection.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
			if (password.equals(storedPassword)) {
				return true;
			} else {
				return false;
			}
		} else if(username.contains(GMAIL_DOMAIN)) {
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket socket = null;
			Scanner scanner = null;
			PrintWriter out = null;
			try {
				socket = (SSLSocket) factory.createSocket(GMAIL_IMAP_HOST_NAME, IMAP_PORT);
				scanner = new Scanner(socket.getInputStream());
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
			scanner.nextLine();
			out.println("a1 login " + username + " " + password);
			
			scanner.nextLine();
			if(scanner.nextLine().contains("Success")) {
				out.println("a5 logout");
				
				out.close();
				scanner.close();
				return true;
			} else {
				System.err.println("Wrong username/password");
				
				out.close();
				scanner.close();
				return false;
			}
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		
	}

}
