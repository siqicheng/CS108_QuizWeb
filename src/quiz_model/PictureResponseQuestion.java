package quiz_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PictureResponseQuestion extends Question {

	String url;	/* of picture */
	String question; 
	ArrayList<String> answers;
	
	
	public PictureResponseQuestion(String url, String question, ArrayList<String> answers){
		this.url = url;
		this.question = question;
		this.answers = new ArrayList<String>(answers);
	}
	
	public PictureResponseQuestion(int id, Statement stmt) {
		super(id);
		try {
			String ansStr = "";
			ResultSet rs = stmt.executeQuery("SELECT * FROM PR WHERE QuestionID = \"" + id + "\"");
			rs.next();
			question = rs.getString("Question");
			url = rs.getString("Url");
			ansStr = rs.getString("Answer");
			String[] answerList = ansStr.split("#blank#");
			for (String answer: answerList) {
				answers.add(answer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getHTML(int questionNum) {
		String html_question = "<b>Question " + Integer.toString(this.id) + ": </b><br>" ;
		html_question += "<img src=\"" + this.url + "\"/><br>"; 
		html_question += this.question + "<br>";
		return html_question;

	}

	@Override
	public String getHTMLwithAnswer(int questionNum) {
		String html = this.getHTML(questionNum);
		html += "<b>Answers:</b> ";
		for(String answer : this.answers){
			html += answer;
			html += "/";
		}
		html = html.substring(0, html.length() - 1);
		html += "<br>";
		return html;
	}

	@Override
	public String getType() {
		return "PictureResponse";
	}

	@Override
	public String insertSql(int id, String user) {
		String sql = "INSERT INTO PR VALUES(";
		sql += Integer.toString(id) + ","; /* ID */
		sql += "\"" + user + "\","; /* User name */
		
		/* Question */
		sql += "\"" + question + "\",";  
		
		/* Answer */
		sql += "\"";
		for(String answer : answers){
			sql += "#" + answer + "#";
		}
		sql += "\",";
		
		/* URL */
		sql += "\"" + url + "\","; 
		
		sql += Integer.toString(5) + ","; /* Score, to be changed */
		sql += "\"" + "#NULL#" + "\","; /* Tag, to be changed */
		sql += "0);"; /* Time */

		return sql;	

	}

	@Override
	public String getHTMLwithQuestion(int questionNum) {
		String html_question = this.getHTML(questionNum);
		String html_answer = "<b>Your answers (use ; as delimiters if more than one legal answer):</b><br>";
		html_answer += "<b><textarea name=\"answers\" rows=\"1\" cols=\"30\"></textarea></b><br>";
		return html_question + html_answer;
	}
}
