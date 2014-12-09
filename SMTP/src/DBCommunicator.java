import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBCommunicator {
	
	// Database connectivity parameters
	public static String dbServerIP = "krc9698.wireless.rit.edu";
	public static String schema = "fcn";
	public static String dbuser = "kedar";
	public static String dbpass = "kedar";
	public static String driver = "com.mysql.jdbc.Driver";
	public static String connectionURL = "jdbc:mysql://" + dbServerIP + "/" + schema;
	
	public static boolean add(Email email) {
		boolean sent = false;
		
		try {
			Class.forName(driver);
		
			//Getting a connection to the database. Change the URL parameters
			Connection connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
			//Creating a statement object
			Statement statement = connection.createStatement();
			//Executing the query and getting the result set
			ResultSet rs = statement.executeQuery("SELECT * FROM " + "fcn.user_info" + " WHERE username = '" + "username" + "'");
			//Iterating the resultset and get the appId
			if(rs.next()) {
				sent = true;
			}
			
			rs.close();
			statement.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sent;
	}

}