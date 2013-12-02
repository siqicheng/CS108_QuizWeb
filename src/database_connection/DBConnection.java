package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
		try {
			if(statement.isClosed() || connection.isClosed()) generateConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public Statement getStatement(){
		try {
			if(statement.isClosed() || connection.isClosed()) generateConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}
	
	public String getQuizName(String quizId){
		String query = "SELECT QuizName FROM QI WHERE QuizID=" + quizId + ";";
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			String quizName = rs.getString("QuizName");
			return quizName;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
 
	public String getQuizCreator(String quizId){
		String query = "SELECT CreaterId FROM QI WHERE QuizID=" + quizId + ";";
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			String creator = rs.getString("CreaterId");
			return creator;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getQuizTimes(String quizId){
		String query = "select COUNT(*) AS Freq FROM quiz_take_history WHERE Quiz_Id = "+ quizId + " GROUP BY Quiz_Id;";
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			int freq = rs.getInt("Freq");
			return freq;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}

