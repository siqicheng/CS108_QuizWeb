package quiz_model;

import java.util.ArrayList;

import org.json.JSONObject;

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
	
	public FillInBlankQuestion(JSONObject json){
		super();
		this.part1 = json.optString("part1");
		this.part2 = json.optString("part2");
		this.answers = new ArrayList<String>();
		for(int i = 0; i < json.length() - 1; ++i){
			answers.add(json.optString("answer" + i));
		}
	}
	
	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("part1", part1);
		json.put("part2", part2);
		for(int i = 0; i < answers.size(); ++i){
			json.put("answer" + i, answers.get(i));
		}
		return json;
	}

	@Override
	public String getHTML() {
		String html_question = "<b>Question " + Integer.toString(this.id) + ": </b>" + this.part1 + " __________ " + this.part2 + "<br>";
		return html_question;
	}

	@Override
	public String getHTMLwithAnswer() {
		String html_question = "<b>Question " + Integer.toString(this.id) + ": </b>" + this.part1 + " __________ " + this.part2 + "<br>";
		String html_answer = "<b>Answers: </b>";
		for(String answer : this.answers){
			html_answer += answer;
			html_answer += "/";
		}
		html_answer += "<br>";
		return html_question + html_answer;
	}

	@Override
	public String getType() {
		return "FillInBlank";
	}

}
