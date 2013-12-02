package quiz_model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import database_connection.DBConnection;

public class Quiz {
	int id;
	String name;
	String createrId;
	Timestamp createTime;
	String tag;
	String category;
	boolean canPractice;
	boolean isRandom;
	boolean isOnePage;
	boolean isImmediateCorrection;
	ArrayList<Question> questions;
	int totalScore;

	public Quiz(int id, String name, String createrId, Timestamp createTime, String tag, 
			String category, boolean canPractice, boolean isRandom, boolean isOnePage, boolean isImmediateCorrection,
			ArrayList<Question> questions) {
		this.id = id;
		this.name = name;
		this.createrId = createrId;
		this.createTime = new Timestamp(createTime.getTime());
		this.tag = tag;
		this.category = category;
		this.canPractice = canPractice;
		this.isRandom = isRandom;
		this.isOnePage = isOnePage;
		this.isImmediateCorrection = isImmediateCorrection;
		this.questions = questions;
	}
	
	private Question getQuestion(int QuestionID, int QuestionType, Statement stmt) {
		Question result;
		if (QuestionType == 1) {
			result = new QuestionResponseQuestion(QuestionID, stmt);
		} else if (QuestionType == 2) {
			result = new FillInBlankQuestion(QuestionID, stmt);
		} else if (QuestionType == 3) {
			result = new MultipleChoiceQuestion(QuestionID, stmt);
		} else if (QuestionType == 4) {
			result = new PictureResponseQuestion(QuestionID, stmt);
		} else {
			result = null;
		}
		return result;
	}

	public Quiz(int id, DBConnection con) {
		this.id = id;
		String quizName = "";
		String createrId = "";
		Timestamp createTime = new Timestamp(0);
		String tag = "";
		String category = "";
		//int totalScore = 0;
		boolean canPractice = false;
		boolean isRandom = false;
		boolean isOnePage = false;
		boolean isImmediateCorrection = false;
		ArrayList<Question> questionList = new ArrayList<Question>();

		try {
			Statement stmt = con.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM QI WHERE QuizID = \"" + id + "\"");
			rs.next();
			quizName = rs.getString("QuizName");
			createrId = rs.getString("CreaterId");
			createTime = rs.getTimestamp("CreateTime");
			tag = rs.getString("QuizTag");
			category = rs.getString("QuizCategory");
			canPractice = rs.getBoolean("canPractice");
			isRandom = rs.getBoolean("isRandom");
			isOnePage = rs.getBoolean("isOnePage");
			isImmediateCorrection = rs.getBoolean("isImmediateCorrection");

			int score = 0;
			Statement stmt2 = (new DBConnection()).getStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM QQ WHERE QuizID = \"" + id + "\"");
			while (rs2.next()) {
				int questionType = rs2.getInt("questionType");
				int questionId = rs2.getInt("questionId");
				Question question = getQuestion(questionId, questionType, stmt);
				if (question != null)
					score += question.getScore();
				questionList.add(question);
			}
			totalScore = score;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.name = quizName;
		this.createrId = createrId;
		this.createTime = new Timestamp(createTime.getTime());
		this.tag = tag;
		this.category = category;
		this.canPractice = canPractice;
		this.isRandom = isRandom;
		this.isOnePage = isOnePage;
		this.isImmediateCorrection = isImmediateCorrection;
		this.questions = questionList;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isRandom() {
		return isRandom;
	}
	
	public boolean isOnePage() {
		return isOnePage;
	}
	
	public boolean isImmediateCorrection() {
		return isImmediateCorrection;
	}
	
	public boolean canPractice() {
		return canPractice;
	}
	public String insertQISql(){
		String sql = "INSERT INTO QI VALUES(";
		// (0, "FirstQuizEver", "Siqi", "2013-11-20 04:21:07", "#CommonSense##CS#","CommonSense",false,false,true,false),
		sql += Integer.toString(id) + ","; /* ID */		
		sql += "\"" + name + "\","; /* quiz name */
		sql += "\"" + createrId + "\",";  /* Creater Id */
		sql += "\"" + createTime + "\","; 
		sql += "\"" + tag + "\","; 
		sql += "\"" + category + "\","; 
		sql += Boolean.toString(canPractice) + ","; 
		sql += Boolean.toString(isRandom) + ","; 
		sql += Boolean.toString(isOnePage) + ","; 
		sql += Boolean.toString(isImmediateCorrection) + ");"; 
		return sql;	
	}

	public String insertQQSql(int questionId, String questionType){

		String sql = "INSERT INTO QQ VALUES(";
		sql += Integer.toString(id) + ","; /* ID */	
		sql += Integer.toString(questionId) + ","; /* ID */	
		sql += questionType + ");"; /* ID */	

		return sql;	
	}
}
