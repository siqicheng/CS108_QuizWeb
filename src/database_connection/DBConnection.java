package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public void updateAchievementTable_Taken(String score, String sender, String id){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(new Timestamp(System.currentTimeMillis()));
		/* step 1: I am the Greatest */
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
		
		if(insert){	// To Achievement_History
			
			String sql = "INSERT INTO Achievement_History VALUES('" + sender + "', 'I am the Greatest', '" + time + "');"; 
			//String sql = "INSERT INTO Partial_Achievements VALUES(\"" + sender + "\",\"I am the Greatest\");";
			try {
				if(statement.isClosed()) generateConnection();
				statement.executeUpdate(sql);
			} catch (SQLException e) {}		
		}
		
		/* step 2: Quiz Machine */
		query = "SELECT COUNT(*) AS NUM FROM quiz_take_history WHERE User_Name = '" + sender + "' GROUP BY User_Name";
		int takenNumber = 0;
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			takenNumber = rs.getInt("NUM");
		} catch (SQLException e) {}
		if (takenNumber == 10) {
			String sql = "INSERT INTO Achievement_History VALUES('" + sender + "', 'Quiz Machine', '" + time + "');";
			try {
				if(statement.isClosed()) generateConnection();
				statement.executeUpdate(sql);
			} catch (SQLException e) {}	
		}
	}
	
	public double averageRate(String quizId){
		String query = "SELECT SUM(Rate) AS TOTAL, COUNT(*) AS NUM FROM Rates_Table WHERE QuizID=" + quizId + " GROUP BY QuizID";
		double result = -1;
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			if(!rs.isBeforeFirst()) return result;
			else {
				rs.next();
				int total = rs.getInt("TOTAL");
				int num = rs.getInt("NUM");
				return (double)total/num;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<String> getReviews(String quizId){
		List<String> results = new ArrayList<String>();
		String query = "SELECT UserID, Rate, Comment FROM Rates_Table WHERE QuizID=" + quizId + " ORDER BY Time DESC;";
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			if(!rs.isBeforeFirst()) {
				results.add("No review yet.");
			} else {
				while(rs.next()){
					String name = rs.getString("UserID");
					String name_str = "<a href=\"CreateAccount_welcome.jsp?name=" + name +"\">" + name + "</a>";
					
					int rate = rs.getInt("Rate");
					String rate_str;
					if(rate < 0) rate_str = "no rate";
					else rate_str = "rate: " + Integer.toString(rate) + "/5";
					
					String comment = rs.getString("Comment");
					
					String whole = name_str + " " + rate_str + "	" + comment + "<br>";
					results.add(whole);
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	public List<String> getAnnouncement(){
		List<String> announcements = new ArrayList<String>();
		String query = "SELECT Time, content FROM announceTable ORDER BY Time DESC;";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			while(rs.next() && announcements.size() < 11){
				Date time = rs.getTimestamp("Time");
				String time_str = format.format(time);
				String content = rs.getString("content");
				announcements.add(time_str + "#" + content);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return announcements;
	}
	
	public void updateAchievement_CreateQuiz(String sender){
		/* Get current created number */
		String query = "SELECT COUNT(*) AS NUM FROM QI WHERE CreaterId = '" + sender + "' GROUP BY CreaterId";
		int quizNumber = 0;
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			quizNumber = rs.getInt("NUM");
		} catch (SQLException e) {}
		
		
		/* Update achievement */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(new Timestamp(System.currentTimeMillis()));
		
		if(quizNumber == 1 ) {
			query = "INSERT INTO Achievement_History VALUES('" + sender + "', 'Amateur Author', '" + time + "');"; 
			try {
				statement.executeUpdate(query);
			} catch (SQLException e) {}
		}
		
		if(quizNumber == 5 ) {
			query = "INSERT INTO Achievement_History VALUES('" + sender + "', 'Prolific Author', '" + time + "');"; 
			try {
				statement.executeUpdate(query);
			} catch (SQLException e) {}
		}
		
		if(quizNumber == 10 ) {
			query = "INSERT INTO Achievement_History VALUES('" + sender + "', 'Prodigious Author', '" + time + "');"; 
			try {
				statement.executeUpdate(query);
			} catch (SQLException e) {}
		}
	}
	
	public String getQuizDescription(String quizId){
		String query = "SELECT QuizDescription FROM QI WHERE QuizID=" + quizId + ";";
		try {
			if(connection.isClosed()) generateConnection();
			ResultSet rs = statement.executeQuery(query);
			rs.next();
			String description = rs.getString("QuizDescription");
			return description;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

