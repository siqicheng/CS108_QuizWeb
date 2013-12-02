package quiz_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FillInBlankQuestion extends Question{

	private String part1;
	private String part2;
	private ArrayList<String> answers;
	
	public FillInBlankQuestion(String part1, String part2, ArrayList<String> answers){
		super();
		this.part1 = part1;
		this.part2 = part2;
		this.answers = new ArrayList<String>(answers);
	}
	
	public FillInBlankQuestion(int id, Statement stmt) {
		super(id);
		this.answers = new ArrayList<String>();
		try {
			String ansStr = "";
			String questionStr = "";
			ResultSet rs = stmt.executeQuery("SELECT * FROM FB WHERE QuestionID = \"" + id + "\"");
			rs.next();
			questionStr = rs.getString("Question");
			String[] questionList = questionStr.split("#blank#");
			part1 = questionList[0];
			part2 = questionList[1];
			//StringTokenizer str1 = new StringTokenizer(questionStr, "#blank#");
			//part1 = str1.nextToken();
			//part2 = str1.nextToken();
			ansStr = rs.getString("Answer");
			String[] answerList = ansStr.split("#blank#");
			for (String answer: answerList) {
				answers.add(answer);
			}
			for (int i = 0; i < answers.size(); i++) {
				System.out.println(answers.get(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getHTML(int questionNum) {
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b>" + this.part1 + " __________ " + this.part2 + "<br>";
		return html_question;
	}

	@Override
	public String getHTMLwithAnswer(int questionNum) {
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b>" + this.part1 + " __________ " + this.part2 + "<br>";
		String html_answer = "<b>Answers: </b>";
		for(String answer : this.answers){
			html_answer += answer;
			html_answer += "/";
		}
		html_answer = html_answer.substring(0, html_answer.length()  - 1);
		html_answer += "<br>";
		return html_question + html_answer;
	}

	@Override
	public String getType() {
		return "FillInBlank";
	}

	@Override
	public String insertSql(int id, String user) {
		String sql = "INSERT INTO FB VALUES(";
		sql += Integer.toString(id) + ","; /* ID */
		sql += "\"" + user + "\","; /* User name */
		
		/* Question */
		sql += "\"" + part1 + " #blank# " + part2 + "\",";  
		
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
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b><br>" + this.part1+"</br>";
		html_question += "<b><textarea name=\"answers\" rows=\"1\" cols=\"30\"></textarea></b>";
		html_question += this.part2 + "<br>";
		return html_question;
	}
}
