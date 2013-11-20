package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {

	protected static String account = MyDBInfo.MYSQL_USERNAME; 
	protected static String password = MyDBInfo.MYSQL_PASSWORD; 
	protected static String server = MyDBInfo.MYSQL_DATABASE_SERVER; 
	protected static String database = MyDBInfo.MYSQL_DATABASE_NAME;
	
	private static Connection connection;
	private static Statement statement;
	
	public DBConnection() {
		generateConnection();
	}

	private static void generateConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			connection = DriverManager.getConnection ("jdbc:mysql://" + server, account, password);
			statement = connection.createStatement();
			statement.executeQuery("USE " + database);
		} 
		catch (SQLException e) {
	        e.printStackTrace();
	    }
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConnection() {
        try {
        	connection.close();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }
	
	public Connection getConnection(){
		return connection;
	}
	
	public Statement getStatement(){
		return statement;
	}
	
 
}

