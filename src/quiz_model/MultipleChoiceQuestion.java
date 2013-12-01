package quiz_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import java.util.Random;

public class MultipleChoiceQuestion extends Question{

	String question;
	ArrayList<String> choices; /* Choices other than the correct answer */
	String answer; 
	
	public MultipleChoiceQuestion(String question, ArrayList<String> choices, String answer){
		this.question = question;
		this.choices = new ArrayList<String>(choices);
		this.answer = answer;
	}
	
	public MultipleChoiceQuestion(int id, Statement stmt) {
		super(id);
		try {
			String choiceStr = "";
			ResultSet rs = stmt.executeQuery("SELECT * FROM MC WHERE QuestionID = \"" + id + "\"");
			rs.next();
			question = rs.getString("Question");
			answer = rs.getString("Answer");
			choiceStr = rs.getString("Choices");
			String [] choicesList = choiceStr.split("#blank#");
			for (String choice: choicesList) {
				choices.add(choice);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getHTML(int questionNum) {
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b>" + this.question + "<br>";
		Random rnd = new Random();
		double prob = 1 / choices.size();
		boolean answerNotShown = true;
		for (int i = 0; i < choices.size(); ++i){
			if(rnd.nextDouble() < prob && answerNotShown) {
				html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + this.answer + "\"> " + this.answer + "</p>";
				answerNotShown = false;
			}
			html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + choices.get(i) + "\"> " + choices.get(i) + "</p>";
		}		

		if(answerNotShown) html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + this.answer + "\"> " + this.answer + "</p>";		
		return html_question;
	}

	@Override
	public String getHTMLwithAnswer(int questionNum) {
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b>" + this.question + "<br>";
		Random rnd = new Random();
		double prob = (double)1 / choices.size();
		boolean answerNotShown = true;
		for (int i = 0; i < choices.size(); ++i){
			if(rnd.nextDouble() < prob && answerNotShown) {
				html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + this.answer + "\"> " + this.answer + "</p>";
				answerNotShown = false;
			}
			html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + choices.get(i) + "\"> " + choices.get(i) + "</p>";
		}
		
		if(answerNotShown) html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + this.answer + "\"> " + this.answer + "</p>";
		
		String html_answer = "<b>Answers: </b>" + this.answer;
		html_answer += "<br>";
		return html_question + html_answer;
	}

	@Override
	public String getType() {
		return "MultipleChoice";
	}
	
	@Override
	public String insertSql(int id, String user) {
		String sql = "INSERT INTO MC VALUES(";
		sql += Integer.toString(id) + ","; /* ID */
		sql += "\"" + user + "\","; /* User name */
		
		/* Question */
		sql += "\"" + question + "\",";  
		
		/* Choices */
		sql += "\"";
		for(String choice : choices){
			sql += "#" + choice + "#";
		}
		sql += "#" + answer + "#";
		sql += "\",";
		
		/* Answer */
		sql += "\"" + answer + "\",";
		
		sql += Integer.toString(5) + ","; /* Score, to be changed */
		sql += "\"" + "#NULL#" + "\","; /* Tag, to be changed */
		sql += "0);"; /* Time */

		return sql;	

	}

	@Override
	public String getHTMLwithQuestion(int questionNum) {
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b><br>" + this.question + "</br><br>";
		Random rnd = new Random();
		double prob = (double)1 / choices.size();
		boolean answerNotShown = true;
		for (int i = 0; i < choices.size(); ++i){
			if(rnd.nextDouble() < prob && answerNotShown) {
				html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + this.answer + "\"> " + this.answer + "</p>";
				answerNotShown = false;
			}
			html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + choices.get(i) + "\"> " + choices.get(i) + "</p>";
		}
		
		if(answerNotShown) html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + this.answer + "\"> " + this.answer + "</p>";
		return html_question;
	}
	
}
