package quiz_model;

import java.util.ArrayList;

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

	@Override
	public String getHTML() {
		String html_question = "<b>Question " + Integer.toString(this.id) + ": </b>" + this.part1 + " __________ " + this.part2 + "<br>";
		return html_question;
	}

	@Override
	public String getHTMLwithAnswer() {
		String html_question = "<b>Question " + Integer.toString(this.id) + ": </b>" + this.part1 + " __________ " + this.part2 + "<br>";
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
}
