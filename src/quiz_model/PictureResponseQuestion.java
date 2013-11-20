package quiz_model;

import java.util.ArrayList;

public class PictureResponseQuestion extends Question {

	String url;	/* of picture */
	String question; 
	ArrayList<String> answers;
	
	
	public PictureResponseQuestion(String url, String question, ArrayList<String> answers){
		this.url = url;
		this.question = question;
		this.answers = new ArrayList<String>(answers);
	}
	
	@Override
	public String getHTML() {
		String html_question = "<b>Question " + Integer.toString(this.id) + ": </b><br>" ;
		html_question += "<img src=\"" + this.url + "\"/><br>"; 
		html_question += this.question + "<br>";
		return html_question;

	}

	@Override
	public String getHTMLwithAnswer() {
		String html = this.getHTML();
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

}
