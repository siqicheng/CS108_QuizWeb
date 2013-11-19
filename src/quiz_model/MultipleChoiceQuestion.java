package quiz_model;

import java.util.ArrayList;

import org.json.JSONObject;
import java.util.Random;

public class MultipleChoiceQuestion extends Question{

	String question;
	ArrayList<String> choices; /* Choices other than the correct answer */
	String answer; 
	
	public MultipleChoiceQuestion(String question, ArrayList<String> choices, String answer){
		this.question = question;
		this.choices = new ArrayList<String>(choices);
		this.answer = answer;
	}
	
	@Override
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("question", question);
		for(int i = 0; i < choices.size(); ++i){
			json.put("choice" + i, choices.get(i));
		}
		
		json.put("answer", answer);
		
		return json;
	}

	@Override
	public String getHTML() {
		String html_question = "<b>Question " + Integer.toString(this.id) + ": </b>" + this.question + "<br>";
		Random rnd = new Random();
		double prob = 1 / choices.size();
		boolean answerNotShown = true;
		for (int i = 0; i < choices.size(); ++i){
			if(rnd.nextDouble() < prob && answerNotShown) {
				html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + this.answer + "\"> " + this.answer + "</p>";
				answerNotShown = false;
			}
			html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + choices.get(i) + "\"> " + choices.get(i) + "</p>";
		}		

		if(answerNotShown) html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + this.answer + "\"> " + this.answer + "</p>";		
		return html_question;
	}

	@Override
	public String getHTMLwithAnswer() {
		String html_question = "<b>Question " + Integer.toString(this.id) + ": </b>" + this.question + "<br>";
		Random rnd = new Random();
		double prob = (double)1 / choices.size();
		boolean answerNotShown = true;
		for (int i = 0; i < choices.size(); ++i){
			if(rnd.nextDouble() < prob && answerNotShown) {
				html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + this.answer + "\"> " + this.answer + "</p>";
				answerNotShown = false;
			}
			html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + choices.get(i) + "\"> " + choices.get(i) + "</p>";
		}
		
		if(answerNotShown) html_question += "<p><input type=\"radio\" name=\"choice\" value=\"" + this.answer + "\"> " + this.answer + "</p>";
		
		String html_answer = "<b>Answers: </b>" + this.answer;
		html_answer += "<br>";
		return html_question + html_answer;
	}

	@Override
	public String getType() {
		return "MultipleChoice";
	}
	

}
