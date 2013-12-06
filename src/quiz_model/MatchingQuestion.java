package quiz_model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public class MatchingQuestion extends Question {

	public MatchingQuestion(int id, Statement stmt) {
		super(id);
	}
	
	@Override
	public int getAnswerNum() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHTML(int questionNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHTMLwithAnswer(int questionNum) {
		// TODO Auto-generated method stub
		return null;
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
		return null;
	}

}
