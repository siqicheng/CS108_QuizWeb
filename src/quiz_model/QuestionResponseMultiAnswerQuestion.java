package quiz_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public class QuestionResponseMultiAnswerQuestion extends Question{
	private String question;
	private ArrayList<ArrayList<String>> answers;
	boolean isOrdered;

	//	public QuesionResponseMultiAnswerQuestion(int id, String question, ArrayList<ArrayList<String>> answers){
	//		super(id, answers.size());
	//		this.question = question;
	//		this.answers = new ArrayList<ArrayList<String>>(answers);
	//	}
	//	
	public QuestionResponseMultiAnswerQuestion(int id, Statement stmt) {
		super(id);
		this.answers = new ArrayList<ArrayList<String>>();
		try {
			String ansStr = "";
			String query = "SELECT * FROM MA WHERE QuestionID = \"" + id + "\"";
			System.out.println(query);
			ResultSet rs = stmt.executeQuery("SELECT * FROM MA WHERE QuestionID = \"" + id + "\"");
			rs.next();
			question = rs.getString("Question");
			ansStr = rs.getString("Answer");
			isOrdered = rs.getBoolean("IsOrder");
			String[] multiAnswerList = ansStr.split("#");
			for (String oneAnswerL: multiAnswerList) {
				ArrayList<String> oneAnsL = new ArrayList<String> ();
				String[] oneAns = oneAnswerL.split("%");
				for (String oneAnswer: oneAns) {
					oneAnsL.add(oneAnswer);
				}
				answers.add(oneAnsL);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public QuestionResponseMultiAnswerQuestion(String question, ArrayList<ArrayList<String>> answers, boolean isOrdered){
		super();
		this.score = answers.size()*super.perScore;
		this.question = question;
		this.answers = new ArrayList<ArrayList<String>>(answers);
		this.isOrdered = isOrdered;
	}

	@Override
	public String getHTML(int questionNum) {
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b>" + this.question + "<br>";
		return html_question;
	}

	@Override
	public String getType() {
		return "QuestionResponseMultiAnswer";
	}

	@Override
	public String getHTMLwithAnswer(int questionNum) {
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b>" + this.question + "<br>";
		String html_answer = "<b>Answers: </b>";
		for(ArrayList<String> answerL : this.answers) {
			html_answer += "<br>";
			String answer = "";
			for (String ans: answerL) {
				answer += ans;
				answer += "/";
			}
			answer = answer.substring(0, answer.length()-1);
			html_answer += answer;
			html_answer += "</br>";
		}
		html_answer += "<br>";
		return html_question + html_answer;
	}

	@Override
	public String insertSql(int id, String user) {
		String sql = "INSERT INTO MA VALUES(";
		sql += Integer.toString(id) + ","; /* ID */
		sql += "\"" + user + "\","; /* User name */
		sql += "\"" + question + "\",";  /* Question */

		/* Answers */
		sql += "\"";
		for(ArrayList<String> answerL : answers){
			String ansStr = "";
			for (String ans : answerL) {
				ansStr += ans;
				ansStr += "%";
			}
			ansStr.substring(0, ansStr.length()-1);
			sql += ansStr + "#";
		}
		sql = sql.substring(0, sql.length()-1);
		sql += "\",";
		if (isOrdered) {
			sql += "true,";
		} else {
			sql += "false,";
		}
		sql += Integer.toString(score) + ","; /* Score, to be changed */
		sql += "\"" + "#NULL#" + "\","; /* Tag, to be changed */
		sql += "0);"; /* Time */

		return sql;	
	}

	@Override
	public String getHTMLwithQuestion(int questionNum) {
		// TODO Auto-generated method stub
		String html_question = "<b>Question " + Integer.toString(questionNum) + ": </b><br>" + this.question + "</br><br>";
		String html_answer = "";
		for (int i = 0; i < answers.size(); i++) {
			html_answer += "<b><textarea name=\"answer" + Integer.toString(questionNum) + "_" + Integer.toString(i) +"\" rows=\"1\" cols=\"30\"></textarea></b><br>";
		}
		return html_question + html_answer;
	}

	@Override
	public ArrayList<String> fetchAnswer(HttpServletRequest request, int questionNum) {
		// TODO Auto-generated method stub
		ArrayList<String> ansList = new ArrayList<String> ();
		for (int i = 0; i < answers.size(); i++) {
			String ans = request.getParameter("answer"+Integer.toString(questionNum)+"_"+Integer.toString(i));
			if (ans == null) {
				ans = (String)request.getSession().getAttribute("answer" + Integer.toString(questionNum)+"_"+Integer.toString(i)) ;
				if (ans == null || ans.equals("null")) {
					ans = "";
				}
			}
			ansList.add(ans);
		}

		return ansList;
	}

	boolean isInList(ArrayList<String> l, String s) {
		for (String str:l) {
			if (str.trim().toLowerCase().matches(s.trim().toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getScore(ArrayList<String> ansList) {
		// TODO Auto-generated method stub
		int myScore = 0;
		if (!isOrdered) {
			for (ArrayList<String> ansL: answers) {
				for (String ans: ansL) {
					if (isInList(ansList,ans)) {
						myScore += super.perScore;
						break;
					}
				}
			}
		} else {
			for (int i = 0; i < answers.size(); i++) {
				ArrayList<String> ansL = answers.get(i);
				for (String ans: ansL) {
					if (ans.matches(ansList.get(i))) {
						myScore += super.perScore;
						break;
					}
				}
			}
		}
		return myScore;
	}

	@Override
	public String getHTMLwithQuestionResult(int questionNum, ArrayList<String> userAns, int curScore) {
		// TODO Auto-generated method stub
		String html_question = getHTML(questionNum);
		String html_user_answer = "<b>Your answer:</b><br>";

		for (String ans: userAns) {
			html_user_answer += ans + "</br><br>";
		}
		String ansStr = "";
		for (ArrayList<String> ansList : answers) {
			for (String ans:ansList) {
				ansStr += (ans + "/");
			}
			ansStr = ansStr.substring(0, ansStr.length()-1);
			ansStr += "</br><br>";
		}
		String html_correct_answer = "<b>Correct answer:</b> <br>" + ansStr;
		String html_correct = "";
		if (score == curScore) {
			html_correct += "<b>You are correct!</b>" + "</br><br>";
		} else if (curScore == 0){
			html_correct += "<b>You are wrong!</b>" + "</br><br>";
		} else {
			html_correct += "<b>You are partially correct!</b>" + "</br><br>";
		}

		return html_question + html_user_answer + html_correct_answer + html_correct;
	}

	@Override
	public int getAnswerNum() {
		// TODO Auto-generated method stub
		return answers.size();
	}

}
