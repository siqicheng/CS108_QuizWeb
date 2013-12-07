package quiz_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

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
			String[] answerList = ansStr.split("%");
			for (String answer: answerList) {
				answers.add(answer);
			}
//			for (int i = 0; i < answers.size(); i++) {
//				System.out.println(answers.get(i));
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getHTML(int questionNum) {
		String html_question = "<h2>Question " + Integer.toString(questionNum) + ": </h2>" + this.part1 + " __________ " + this.part2 + "<br>";
		return html_question;
	}

	@Override
	public String getHTMLwithAnswer(int questionNum) {
		String html_question = "<h2>Question " + Integer.toString(questionNum) + ": </h2>" + this.part1 + " __________ " + this.part2 + "<br>";
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
			sql += answer + "%";
		}
		sql = sql.substring(0, sql.length()-1);
		sql += "\",";
		
		sql += Integer.toString(score) + ","; /* Score, to be changed */
		sql += "\"" + "#NULL#" + "\","; /* Tag, to be changed */
		sql += "0);"; /* Time */

		return sql;	
	}

	@Override
	public String getHTMLwithQuestion(int questionNum) {
		String html_question = "<h2>Question " + Integer.toString(questionNum) + ": </h2><br>" + this.part1+"</br>";
		html_question += "<b><textarea name=\"answer"+ Integer.toString(questionNum) +"\" rows=\"1\" cols=\"30\"></textarea></b>";
		html_question += this.part2 + "<br>";
		return html_question;
	}

	@Override
	public ArrayList<String> fetchAnswer(HttpServletRequest request, int questionNum) {
		// TODO Auto-generated method stub
		ArrayList<String> ansList = new ArrayList<String> ();
		String ans = request.getParameter("answer" + Integer.toString(questionNum));
        if (ans == null) {
        	//System.out.println("answer" + Integer.toString(questionNum));
        	ans = (String)request.getSession().getAttribute("answer" + Integer.toString(questionNum)) ;
        	//System.out.println("hello");
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
