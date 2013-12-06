package xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import quiz_model.FillInBlankQuestion;
import quiz_model.MultipleChoiceQuestion;
import quiz_model.PictureResponseQuestion;
import quiz_model.Question;
import quiz_model.QuestionResponseQuestion;
import quiz_model.Quiz;

public class Xmlparser {

	String name;
	String description;
	String category;
	boolean canPractice;
	boolean isRandom;
	boolean isOnePage;
	boolean isImmediateCorrection;
	ArrayList<Question> questions;

	public Xmlparser(String xml){
		System.out.println(xml);
		Document doc = parseXmlFile(xml);
		Element elements = doc.getDocumentElement();
		
		/*If no such attribute exists, then false */
		isRandom = Boolean.parseBoolean(elements.getAttribute("random"));
		isOnePage = Boolean.parseBoolean(elements.getAttribute("one-page"));
		canPractice = Boolean.parseBoolean(elements.getAttribute("practice-mode"));
		isImmediateCorrection = Boolean.parseBoolean(elements.getAttribute("immediate-correction"));
		
		name = getStringFromTag(elements, "title");
		category = getStringFromTag(elements, "category");
		description = getStringFromTag(elements, "description");
		
		questions = new ArrayList<Question>();
		getQuestions(elements);
	}
	
	public String getName() {
		return name;
	}


	public String getDescription() {
		return description;
	}


	public String getCategory() {
		return category;
	}


	public boolean isCanPractice() {
		return canPractice;
	}


	public boolean isRandom() {
		return isRandom;
	}


	public boolean isOnePage() {
		return isOnePage;
	}


	public boolean isImmediateCorrection() {
		return isImmediateCorrection;
	}


	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	/* Populate questions list from elements*/
	private void getQuestions(Element elements){
        NodeList nl = elements.getElementsByTagName("question");
        if (nl != null && nl.getLength() > 0) {
                for (int i = 0; i < nl.getLength(); i++) {
                        // get the question element
                        Element el = (Element) nl.item(i);
                        // get the Employee object
                        Question question = questionFromElement(el);
                        // add it to list
                        questions.add(question);
                }
        }
	}
	
	private static Question questionFromElement(Element element){
		String type = element.getAttribute("type");
		
		if(type.equals("question-response")) {
			String question = getStringFromTag(element, "query");
			//System.out.print(question);
			ArrayList<String> answers = getListFromTag(element, "answer");
			//System.out.print(answers);
			return (new QuestionResponseQuestion(question, answers));
		}
		
		if(type.equals("fill-in-blank")) {
			String pre = getStringFromTag(element, "pre");
			//System.out.print(pre);
			String post = getStringFromTag(element, "post");
			//System.out.print(post);
			ArrayList<String> answers = getListFromTag(element, "answer");
			//System.out.print(answers);
			return (new FillInBlankQuestion(pre, post, answers));
		}
		
		if(type.equals("multiple-choice")) {
			String question = getStringFromTag(element, "query");
			ArrayList<String> options = getMultiChoiceOptions(element);		
			String answer =  getMultiChoiceAnswer(element);
			return (new MultipleChoiceQuestion(question, options, answer));
		}
		
		if(type.equals("picture-response")) {
			String question = getStringFromTag(element, "query");
			String url = getStringFromTag(element, "image-location");
			ArrayList<String> answers = getListFromTag(element, "answer");
			return (new PictureResponseQuestion(url, question, answers));
		}
		
		return null;
	}
	
	private static ArrayList<String> getListFromTag(Element element, String tagName){
        ArrayList<String> answers = new ArrayList<String>();
        NodeList nl = element.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
        	for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                answers.add(el.getFirstChild().getNodeValue());
        }
        }     	
        return answers;
	}
	
	private static ArrayList<String> getMultiChoiceOptions(Element element){
        ArrayList<String> answers = new ArrayList<String>();
        NodeList nl = element.getElementsByTagName("option");
        if (nl != null && nl.getLength() > 0) {
        	for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                if(!el.getAttribute("answer").equals("answer")) answers.add(el.getFirstChild().getNodeValue());
        	}
        }     	
        return answers;
	}
	
	private static String getMultiChoiceAnswer(Element element){
        NodeList nl = element.getElementsByTagName("option");
        if (nl != null && nl.getLength() > 0) {
        	for (int i = 0; i < nl.getLength(); i++) {
                Element el = (Element) nl.item(i);
                if(el.getAttribute("answer").equals("answer")) return el.getFirstChild().getNodeValue();
        	}
        }     	
        return null;
	}
	/*public static Quiz quizFromXml(String xml){
		Document doc = parseXmlFile(xml);
		Element elements = doc.getDocumentElement();
		
		/*If no such attribute exists, then false
		boolean isRandom = Boolean.parseBoolean(elements.getAttribute("random"));
		boolean isOnePage = Boolean.parseBoolean(elements.getAttribute("one-page"));
		boolean canPractice = Boolean.parseBoolean(elements.getAttribute("practice-mode"));
		boolean isImmCorrection = Boolean.parseBoolean(elements.getAttribute("immediate-correction"));
		
		String title = getStringFromTag(elements, "title");
		String category = getStringFromTag(elements, "category");
		String description = getStringFromTag(elements, "description");
		
		return null;
	}*/
	
	/* 
	 * Return a document object from xml file 
	 */
    private static Document parseXmlFile(String xmlFile) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document dom = null;
        try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                dom = db.parse(xmlFile);

        } catch (ParserConfigurationException pce) {
                pce.printStackTrace();
        } catch (SAXException se) {
                se.printStackTrace();
        } catch (IOException ioe) {
                ioe.printStackTrace();
        }
        
        return dom;
    }
    
    private static String getStringFromTag(Element element, String tagName) {
        String text = "";
        NodeList nl = element.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) text = nl.item(0).getFirstChild().getNodeValue();
        return text;
    }

    public static void main(String[] args) {
    	new Xmlparser("src/xml_examples/bunny.xml");
        /*MyQuiz quiz = getQuizFromXml("src/xml/bunny.xml");
        quiz.saveToDatabase();
        exportQuizToXml(quiz, "testXml.xml");*/
    }
	
}


