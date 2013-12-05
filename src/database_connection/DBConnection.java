package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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
	
	public String getQuizName(String quizId) {
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
 
	public String getQuizCreator(String quizId) {
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
	
	public int getAverageScore(String quizId){
		int freq = getQuizTimes(quizId);
		String query = "SELECT SUM(Score) AS Total_Score FROM quiz_take_history where quiz_id = " + quizId + ";";
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			int total = rs.getInt("Total_Score");
			return total/freq;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getAverageTime(String quizId){
		int freq = getQuizTimes(quizId);
		String query = "SELECT SUM(TIMESTAMPDIFF(SECOND, START_TIME, END_TIME)) AS Total_Time FROM quiz_take_history where quiz_id = " + quizId + ";";
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			int total = rs.getInt("Total_Time");
			return total/freq;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<String> getAchievements(String userName){
		List<String> achievements = new ArrayList<String>();
		
		/* Get created quiz number */
		String query = "SELECT COUNT(*) AS NUM FROM QI WHERE CreaterId = '" + userName + "' GROUP BY CreaterId";
		int quizNumber = 0;
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			quizNumber = rs.getInt("NUM");
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		
		if(quizNumber > 0) achievements.add("Ameateur Author");
		if(quizNumber > 4) achievements.add("Prolific Author");
		if(quizNumber > 9) achievements.add("Prodigious Author");
		
		/* Get taken quiz number */
		query = "SELECT COUNT(*) AS NUM FROM quiz_take_history WHERE User_Name = '" + userName + "' GROUP BY User_Name";
		int takenNumber = 0;
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			takenNumber = rs.getInt("NUM");
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		if (takenNumber > 9) achievements.add("Quiz Machine");
		
		/* I am the Greatest */
		query = "SELECT * FROM Partial_Achievements WHERE User_Name = '" +  userName +"' AND Achievement_Type = 'I am the Greatest';";
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			if(rs.isBeforeFirst()) achievements.add("I am the Greatest");;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/* Practice Makes Perfect */
		query = "SELECT * FROM Partial_Achievements WHERE User_Name = '" +  userName +"' AND Achievement_Type = 'Practice Makes Perfect';";
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			if(rs.isBeforeFirst()) achievements.add("Practice Makes Perfect");;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return achievements;
	}
	
	public void updateAchievementTable(String score, String sender, String id){
		String query = "SELECT Score FROM quiz_take_history WHERE Quiz_Id=" + id + " ORDER BY Score DESC;";
		boolean insert = false;
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			if(!rs.isBeforeFirst()) insert = true;
			else {
				rs.next();
				int top = rs.getInt("Score");
				if (Integer.parseInt(score) >= top) insert = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(insert){
			String sql = "INSERT INTO Partial_Achievements VALUES(\"" + sender + "\",\"I am the Greatest\");";
			try {
				if(statement.isClosed()) generateConnection();
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}

