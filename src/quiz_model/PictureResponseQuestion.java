package quiz_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

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
		this.answers = new ArrayList<String>();
		try {
			String ansStr = "";
			ResultSet rs = stmt.executeQuery("SELECT * FROM PR WHERE QuestionID = \"" + id + "\"");
			rs.next();
			question = rs.getString("Question");
			url = rs.getString("Url");
			ansStr = rs.getString("Answer");
			String[] answerList = ansStr.split("%");
			for (String answer: answerList) {
				answers.add(answer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getHTML(int questionNum) {
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b><br>" ;
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
			sql += answer + "%";
		}
		sql = sql.substring(0, sql.length()-1);
		sql += "\",";
		
		/* URL */
		sql += "\"" + url + "\","; 
		
		sql += Integer.toString(score) + ","; /* Score, to be changed */
		sql += "\"" + "#NULL#" + "\","; /* Tag, to be changed */
		sql += "0);"; /* Time */

		return sql;	

	}

	@Override
	public String getHTMLwithQuestion(int questionNum) {
		String html_question = this.getHTML(questionNum);
		String html_answer = "<b><textarea name=\"answer"+ Integer.toString(questionNum) +"\" rows=\"1\" cols=\"30\"></textarea></b><br>";
		return html_question + html_answer;
	}

	@Override
	public ArrayList<String> fetchAnswer(HttpServletRequest request, int questionNum) {
		// TODO Auto-generated method stub
		ArrayList<String> ansList = new ArrayList<String> ();
		String ans = request.getParameter("answer" + Integer.toString(questionNum));
        if (ans == null) {
        	System.out.println("answer" + Integer.toString(questionNum));
        	ans = (String)request.getSession().getAttribute("answer" + Integer.toString(questionNum)) ;
        	System.out.println("hello");
        	if (ans == null || ans.equals("null")) {
                ans = "";
        	}
        }
        ansList.add(ans);
        return ansList;
	}

	@Override
	public int getScore(ArrayList<String> ansList) {
		// TODO Auto-generated method stub
		String ans = ansList.get(0);
		for (String s: answers) {
			if (s.trim().toLowerCase().matches(ans.trim().toLowerCase())) {
				return score;
			}
		}
		return 0;
	}

	@Override
	public String getHTMLwithQuestionResult(int questionNum, ArrayList<String> userAnsList,
			int curScore) {
		// TODO Auto-generated method stub
		String userAns = userAnsList.get(0);
		String html_question = getHTML(questionNum);
		String html_user_answer = "<b>Your answer:</b> " + userAns + "</br><br>";
		String ansStr = "";
		for (int i = 0; i < answers.size()-1; i++) {
			ansStr += (answers.get(i) + "/");
		}
		ansStr += answers.get(answers.size()-1);
		String html_correct_answer = "<b>Correct answer:</b> " + ansStr + "</br><br>";
		String html_correct = "";
		if (score == curScore) {
			html_correct += "<b>You are correct!</b>" + "</br><br>";
		} else {
			html_correct += "<b>You are wrong!</b>" + "</br><br>";
		}
		
		return html_question + html_user_answer + html_correct_answer + html_correct;
	}

	@Override
	public int getAnswerNum() {
		// TODO Auto-generated method stub
		return 1;
	}
}
