package quiz_model;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.sql.*;

import org.json.JSONObject;

public class QuestionResponseQuestion extends Question{
	
	private String question;
	private ArrayList<String> answers;
	
	public QuestionResponseQuestion(int id, String question, ArrayList<String> answers){
		super(id);
		this.question = question;
		this.answers = new ArrayList<String>(answers);
	}

	public QuestionResponseQuestion(int id, JSONObject json){
		super(id);
		this.question = json.optString("question");
		this.answers = new ArrayList<String>();
		for(int i = 0; i < json.length() - 1; ++i){
			answers.add(json.optString("answer" + i));
		}
	}
	
	public QuestionResponseQuestion(int id, Statement stmt) {
		super(id);
		this.answers = new ArrayList<String>();
		try {
			String ansStr = "";
			String query = "SELECT * FROM QR WHERE QuestionID = \"" + id + "\"";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery("SELECT * FROM QR WHERE QuestionID = \"" + id + "\"");
			rs.next();
			question = rs.getString("Question");
			ansStr = rs.getString("Answer");
			String[] answerList = ansStr.split("#blank#");
			for (String answer: answerList) {
				answers.add(answer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public QuestionResponseQuestion(String question, ArrayList<String> answers){
		super();
		this.question = question;
		this.answers = new ArrayList<String>(answers);
	}

	public QuestionResponseQuestion(JSONObject json){
		super();
		this.question = json.optString("question");
		this.answers = new ArrayList<String>();
		for(int i = 0; i < json.length() - 1; ++i){
			answers.add(json.optString("answer" + i));
		}
	}

	@Override
	public String getHTML(int questionNum) {
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b>" + this.question + "<br>";
		return html_question;
	}
	
	public static void main(String[] args) {
		for(int i = 0; i < 10; ++i) {
			System.out.println("answer"+i);
		}
	}

	@Override
	public String getType() {
		return "QuestionResponse";
	}

	@Override
	public String getHTMLwithAnswer(int questionNum) {
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b>" + this.question + "<br>";
		String html_answer = "<b>Answers: </b>";
		for(String answer : this.answers) {
			html_answer += answer;
			html_answer += "/";
		}
		html_answer = html_answer.substring(0, html_answer.length()  - 1);
		html_answer += "<br>";
		return html_question + html_answer;
	}

	@Override
	public String insertSql(int id, String user) {
		String sql = "INSERT INTO QR VALUES(";
		sql += Integer.toString(id) + ","; /* ID */
		sql += "\"" + user + "\","; /* User name */
		sql += "\"" + question + "\",";  /* Question */
		
		/* Answers */
		sql += "\"";
		for(String answer : answers){
			sql += "#" + answer + "#";
		}
		sql += "\",";
		
		sql += Integer.toString(5) + ","; /* Score, to be changed */
		sql += "\"" + "#NULL#" + "\","; /* Tag, to be changed */
		sql += "0);"; /* Time */
		
		return sql;	
	}

	@Override
	public String getHTMLwithQuestion(int questionNum) {
		// TODO Auto-generated method stub
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b><br>" + this.question + "</br><br>";
		String html_answer = "<b><textarea name=\"answers\" rows=\"1\" cols=\"30\"></textarea></b><br>";
		return html_question + html_answer;
	}
}
