package quiz_model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

public abstract class Question {
	
	public final int perScore = 100;

	protected int id; /* Display id within a quiz, not necessarily indicate the stored id in database */
	protected int score = perScore;
	
	public Question(){}
	
	public Question(int id){
		this.id = id;
	}
	
	public Question(int id, int score){
		this.id = id;
		this.score = score;
	}
	
	public int getId() {
		return id;
	}
	
	public int getScore() {
		return score;
	}
	
	public abstract int getAnswerNum();
	public abstract String getHTML(int questionNum);	/* Return the HTML format of the question */
	public abstract String getHTMLwithAnswer(int questionNum);	/* Return the HTML format of the question with answer*/
	public abstract String getHTMLwithQuestion(int questionNum);	/* Return the HTML format of the question to be answered */
	public abstract String getHTMLwithQuestionResult(int questionNum, ArrayList<String> userAns, int curScore);
	public abstract ArrayList<String> fetchAnswer(HttpServletRequest request, int questionNum);
	public abstract int getScore(ArrayList<String> ans);
	public abstract String getType(); /* Return the type of the question */
	public void setId(int id){
		this.id = id;
	}
	//public abstract int evaluateAnswer(); /* Evaluate the answer and return the score earned */ 
	public abstract String insertSql(int id, String user);
	
}
