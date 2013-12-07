package quiz_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class MatchingQuestion extends Question {
	
	String question;
	ArrayList<String> choices; /* Choices other than the correct answer */
	ArrayList<String> answers; 
	
	public MatchingQuestion(String question, ArrayList<String> choices, ArrayList<String> answers){
		this.score = choices.size()*super.perScore;
		this.question = question;
		this.choices = new ArrayList<String>(choices);
		this.answers = new ArrayList<String>(answers);
		//this.correct = false;
	}

	public MatchingQuestion(int id, Statement stmt) {
		super(id);
		this.choices = new ArrayList<String>();
		this.answers = new ArrayList<String>();
		try {
			String choiceStr = "";
			String answerStr = "";
			ResultSet rs = stmt.executeQuery("SELECT * FROM MQ WHERE QuestionID = \"" + id + "\"");
			rs.next();
			question = rs.getString("Question");
			answerStr = rs.getString("Answer");
			choiceStr = rs.getString("Choices");
			score = rs.getInt("Score");
			String [] answersList = answerStr.split("#");
			for (String answerS : answersList) {
				answers.add(answerS);
			}
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
	public int getAnswerNum() {
		// TODO Auto-generated method stub
		return choices.size();
	}

	@Override
	public String getHTML(int questionNum) {
		// TODO Auto-generated method stub
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b>" + this.question + "<br>";
		return html_question;
	}

	@Override
	public String getHTMLwithAnswer(int questionNum) {
		// TODO Auto-generated method stub
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b>" + this.question + "<br>";
		String html_answer = "<b>Answers: </b>";
		for (int i = 0; i < choices.size(); i++) {
			html_answer += choices.get(i) + "----------" + answers.get(i) + "<br>";
		}
		//html_answer = html_answer.substring(0, html_answer.length()  - 1);
		//html_answer += "<br>";
		return html_question + html_answer;
	}

	@Override
	public String getHTMLwithQuestion(int questionNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHTMLwithQuestionResult(int questionNum,
			ArrayList<String> userAns, int curScore) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> fetchAnswer(HttpServletRequest request,
			int questionNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getScore(ArrayList<String> ans) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Matching";
	}

	@Override
	public String insertSql(int id, String user) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO MQ VALUES(";
		sql += Integer.toString(id) + ","; /* ID */
		sql += "\"" + user + "\","; /* User name */
		sql += "\"" + question + "\",";  /* Question */
		
		/* Answers */
		sql += "\"";
		for(String answer : answers){
			sql += answer + "%";
		}
		sql = sql.substring(0, sql.length()-1);
		sql += "\",";
		
		sql += Integer.toString(score) + ","; /* Score, to be changed */
		sql += "\"" + "#NULL#" + "\","; /* Tag, to be changed */
		sql += "0);"; /* Time */
		
		return sql;	
	}

}
