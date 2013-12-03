package quiz_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class MultipleChoiceQuestion extends Question{

	String question;
	ArrayList<String> choices; /* Choices other than the correct answer */
	String answer; 
	Boolean correct;
	
	public MultipleChoiceQuestion(String question, ArrayList<String> choices, String answer){
		this.question = question;
		this.choices = new ArrayList<String>(choices);
		this.answer = answer;
		this.correct = false;
	}
	
	public MultipleChoiceQuestion(int id, Statement stmt) {
		super(id);
		this.choices = new ArrayList<String>();
		try {
			String choiceStr = "";
			ResultSet rs = stmt.executeQuery("SELECT * FROM MC WHERE QuestionID = \"" + id + "\"");
			rs.next();
			question = rs.getString("Question");
			answer = rs.getString("Answer");
			choiceStr = rs.getString("Choices");
			String [] choicesList = choiceStr.split("#");
			for (String choice: choicesList) {
				//System.out.println(choice);
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
				html_question += "<p><input type=\"radio\" name=\"answer"+ Integer.toString(questionNum) +"\" value=\"" + this.answer + "\"> " + this.answer + "</p>";
				answerNotShown = false;
			}
			html_question += "<p><input type=\"radio\" name=\"answer"+ Integer.toString(questionNum) +"\" value=\"" + choices.get(i) + "\"> " + choices.get(i) + "</p>";
		}
		
		if(answerNotShown) html_question += "<p><input type=\"radio\" name=\"answer"+ Integer.toString(questionNum) +"\" value=\"" + this.answer + "\"> " + this.answer + "</p>";
		return html_question;
	}

	@Override
	public String fetchAnswer(HttpServletRequest request, int questionNum) {
		// TODO Auto-generated method stub
		String ans = request.getParameter("answer" + Integer.toString(questionNum));
        if (ans == null) {
        	ans = (String)request.getSession().getAttribute("answer" + Integer.toString(questionNum)) ;
        	if (ans == null) {
                ans = "";
        	}
        }
        return ans;
	}

	@Override
	public int getScore(String ans) {
		// TODO Auto-generated method stub
		if (ans.matches(answer)) {
			return score;
		} else {
			return 0;
		}
	}

	@Override
	public String getHTMLwithQuestionResult(int questionNum, String userAns,
			int curScore) {
		// TODO Auto-generated method stub
		String html_question = getHTML(questionNum);
		String html_user_answer = "<b>Your answer:</b> " + userAns + "</br><br>";
		String html_correct_answer = "<b>Correct answer:</b> " + answer + "</br><br>";
		String html_correct = "";
		if (score == curScore) {
			html_correct += "<b>You are correct!</b>" + "</br><br>";
		} else {
			html_correct += "<b>You are wrong!</b>" + "</br><br>";
		}
		
		return html_question + html_user_answer + html_correct_answer + html_correct;
	}
	
}
