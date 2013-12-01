package quiz_model;

public abstract class Question {

	protected int id; /* Display id within a quiz, not necessarily indicate the stored id in database */
	protected int score = 100;
	
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
	
	public abstract String getHTML(int questionNum);	/* Return the HTML format of the question */
	public abstract String getHTMLwithAnswer(int questionNum);	/* Return the HTML format of the question with answer*/
	public abstract String getHTMLwithQuestion(int questionNum);	/* Return the HTML format of the question to be answered */
	public abstract String getType(); /* Return the type of the question */
	public void setId(int id){
		this.id = id;
	}
	//public abstract int evaluateAnswer(); /* Evaluate the answer and return the score earned */ 
	public abstract String insertSql(int id, String user);
	
}
