package quiz_model;

import java.util.ArrayList;

public class PictureResponseQuestion extends Question {

	String url;	/* of picture */
	String question; 
	ArrayList<String> answers;
	
	
	public PictureResponseQuestion(String url, String question, ArrayList<String> answers){
		this.url = url;
		this.question = question;
		this.answers = new ArrayList<String>(answers);
	}
	
	@Override
	public String getHTML() {
		String html_question = "<b>Question " + Integer.toString(this.id) + ": </b><br>" ;
		html_question += "<img src=\"" + this.url + "\"/><br>"; 
		html_question += this.question + "<br>";
		return html_question;

	}

	@Override
	public String getHTMLwithAnswer() {
		String html = this.getHTML();
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
}
