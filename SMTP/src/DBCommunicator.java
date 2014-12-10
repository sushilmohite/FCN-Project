import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

public class DBCommunicator {
	
	// Database connectivity parameters
	public static String dbServerIP = "krc9698.wireless.rit.edu";
	public static String schema = "fcn";
	public static String dbuser = "kedar";
	public static String dbpass = "kedar";
	public static String driver = "com.mysql.jdbc.Driver";
	public static String connectionURL = "jdbc:mysql://" + dbServerIP + "/" + schema;
	
	public static boolean saveEmail(Email email) {
		boolean sent = false;
		
		try {
			Class.forName(driver);
		
			//Getting a connection to the database. Change the URL parameters
			Connection connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
			//Creating a statement object
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO fcn.emails (from, to, subject, timestamp, content) VALUES (?, ?, ?, ?, ?)");
			
			//preparedStatement = connection.prepareStatement(insertTableSQL);
			//preparedStatement.setInt(1, appId);
			preparedStatement.setString(1, email.getFrom());
			preparedStatement.setString(2, email.getTo());
			preparedStatement.setString(3, email.getSubject());
			preparedStatement.setString(4, email.getTimestamp());
			preparedStatement.setString(5, email.getHTMLContent());
			preparedStatement.executeUpdate();
			//Executing the query and getting the result set
			//ResultSet rs = statement.executeQuery("INSERT INTO fcn.emails (from, to, subject, timestamp, content) VALUES(" + email.getFrom() + ", " + email.getTo() + ", " + email.getSubject() + ", " + email.getTimestamp() + ", " + email.getHTMLContent() + ")");
			//Iterating the resultset and get the appId
			//if(rs.next()) {
				//sent = true;
			//}
			
			//rs.close();
			preparedStatement.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sent;
	}
	
	public static Email[] fetchEmails(String username) {
		
		Email[] email = new Email[0];
		
		try {
			Class.forName(driver);
		
			//Getting a connection to the database. Change the URL parameters
			Connection connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
			//Creating a statement object
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM " + "fcn.emails" + " WHERE username = '" + username + "'");
			if (rs.next()) {
				email = new Email[rs.getInt("COUNT(*)")];
			}
			else {
				return email;
			}
			
			//Executing the query and getting the result set
			rs = statement.executeQuery("SELECT * FROM " + "fcn.emails" + " WHERE username = '" + username + "'");
			//Iterating the resultset and get the appId
			for (int i = 0; i < email.length; i++) {
				if (rs.next()) {
					email[i] = new Email(rs.getInt("id"), rs.getString("from"), rs.getString("to"), rs.getString("subject"), rs.getString("timestamp"), rs.getString("content"), rs.getInt("seen"));
				}
			}
			
			rs.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return email;
	}
	
	public static boolean isAuthenticated(String username, String password) {
		String storedPassword = null;
		try {
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("SELECT * FROM " + "fcn.user_info" + " WHERE username = '" + username + "'");
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
	}
	
	public static boolean isUser(String userName) {
		boolean exists = false;
		
		try {
			Class.forName(driver);
		
			//Getting a connection to the database. Change the URL parameters
			Connection connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
			//Creating a statement object
			Statement statement = connection.createStatement();
			//Executing the query and getting the result set
			ResultSet rs = statement.executeQuery("SELECT * FROM " + "fcn.user_info" + " WHERE username = '" + userName + "'");
			//Iterating the resultset and get the appId
			if(rs.next()) {
				exists = true;
			}
			
			rs.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}
}
