package quiz_model;

public abstract class Question {

	protected int id; /* Display id within a quiz, not necessarily indicate the stored id in database */
	
	public Question(){}
	
	public Question(int id){
		this.id = id;
	}
	
	public abstract String getHTML();	/* Return the HTML format of the question */
	public abstract String getHTMLwithAnswer();	/* Return the HTML format of the question with answer*/
	public abstract String getType(); /* Return the type of the question */
	public void setId(int id){
		this.id = id;
	}
	//public abstract int evaluateAnswer(); /* Evaluate the answer and return the score earned */ 
	public abstract String insertSql(int id, String user);
	
}
