package quiz_model;

import java.util.ArrayList;

import org.json.JSONObject;

public class QuestionResponseQuestion extends Question{
	
	private String question;
	private ArrayList<String> answers;
	
	public QuestionResponseQuestion(int id, String question, ArrayList<String> answers){
		super(id);
		this.question = question;
		this.answers = new ArrayList<String>(answers);
	}

	public QuestionResponseQuestion(int id, JSONObject json){
		super(id);
		this.question = json.optString("question");
		this.answers = new ArrayList<String>();
		for(int i = 0; i < json.length() - 1; ++i){
			answers.add(json.optString("answer" + i));
		}
	}
	
	public QuestionResponseQuestion(String question, ArrayList<String> answers){
		super();
		this.question = question;
		this.answers = new ArrayList<String>(answers);
	}

	public QuestionResponseQuestion(JSONObject json){
		super();
		this.question = json.optString("question");
		this.answers = new ArrayList<String>();
		for(int i = 0; i < json.length() - 1; ++i){
			answers.add(json.optString("answer" + i));
		}
	}
	
	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("question", question);
		for(int i = 0; i < answers.size(); ++i){
			json.put("answer" + i, answers.get(i));
		}
		return json;
	}

	@Override
	public String getHTML() {
		String html_question = "<b>Question " + Integer.toString(this.id) + ": </b>" + this.question + "<br>";
		return html_question;
	}
	
	
	public static void main(String[] args) {
		for(int i = 0; i < 10; ++i){
			System.out.println("answer"+i);
		}
	}

	@Override
	public String getType() {
		return "QuestionResponse";
	}

	@Override
	public String getHTMLwithAnswer() {
		String html_question = "<b>Question " + Integer.toString(this.id) + ": </b>" + this.question + "<br>";
		String html_answer = "<b>Answers: </b>";
		for(String answer : this.answers){
			html_answer += answer;
			html_answer += "/";
		}
		html_answer += "<br>";
		return html_question + html_answer;
	}
	
}
