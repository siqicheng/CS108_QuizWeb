package quiz_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import database_connection.DBConnection;

public class ActivityManger {
	
	static public List<Activity>  getActivities(String sender){
		List<Activity> activities = new ArrayList<Activity>();
		DBConnection con = new DBConnection();
		Statement st = con.getStatement();
		
		/* step 1: create activities */
		/* [type, sender, id, name] */
		String query = "SELECT QuizID, QuizName, CreateTime FROM QI WHERE CreaterId='" + sender + "' ORDER BY CreateTime DESC;";
		try {
			ResultSet rs = st.executeQuery(query);
			int counter = 0;
			while(rs.next() && counter < 10){
				counter++;
				Timestamp time = rs.getTimestamp("CreateTime");
				String id = Integer.toString(rs.getInt("QuizID"));
				String name = rs.getString("QuizName");
				List<String> tmp = new ArrayList<String>();
				tmp.add("create");
				tmp.add(sender);
				tmp.add(id);
				tmp.add(name);
				Activity a = new Activity(tmp, time);
				activities.add(a);
			}
		} catch (SQLException e) {}
		
		
		/* step 2: take activities */
		/* [type, sender, ID, name] */
		query = "SELECT QuizID, QuizName, End_Time FROM QI, quiz_take_history WHERE QI.QuizID = quiz_take_history.Quiz_Id AND User_Name='" + sender + "' ORDER BY End_Time DESC;";
		try {
			ResultSet rs = st.executeQuery(query);
			int counter = 0;
			while(rs.next() && counter < 10){
				counter++;
				Timestamp time = rs.getTimestamp("End_Time");
				String id = Integer.toString(rs.getInt("QuizID"));
				String name = rs.getString("QuizName");
				List<String> tmp = new ArrayList<String>();
				tmp.add("take");
				tmp.add(sender);
				tmp.add(id);
				tmp.add(name);
				Activity a = new Activity(tmp, time);
				activities.add(a);
			}
		} catch (SQLException e) {}
		
		/*step 3: achievements */
		/* [type, sender, achievement] */
		//query = "SELECT QuizID, QuizName, End_Time FROM QI, quiz_take_history WHERE QI.QuizID = quiz_take_history.Quiz_Id AND User_Name='" + sender + "' ORDER BY End_Time DESC;";
		query = "SELECT * FROM Achievement_History WHERE User_Name='" + sender + "' ORDER BY Time DESC;";
		try {
			ResultSet rs = st.executeQuery(query);
			int counter = 0;
			while(rs.next() && counter < 10){
				counter++;
				Timestamp time = rs.getTimestamp("Time");
				String achievement = rs.getString("Achievement");
				List<String> tmp = new ArrayList<String>();
				tmp.add("achieve");
				tmp.add(sender);
				tmp.add(achievement);
				Activity a = new Activity(tmp, time);
				activities.add(a);
			}
		} catch (SQLException e) {}
		
		con.closeConnection();
		return activities;
	}
	

    public static void main(String[] args) {
    	List<Activity> as = ActivityManger.getActivities("sqcheng");
    	//System.out.println(as);
        /*MyQuiz quiz = getQuizFromXml("src/xml/bunny.xml");
        quiz.saveToDatabase();
        exportQuizToXml(quiz, "testXml.xml");*/
    }
}
