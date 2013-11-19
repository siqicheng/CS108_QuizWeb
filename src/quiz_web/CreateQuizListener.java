package quiz_web;

import java.util.ArrayList;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import quiz_model.*;


/**
 * Application Lifecycle Listener implementation class CreateQuizListener
 *
 */
@WebListener
public class CreateQuizListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public CreateQuizListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {
    	HttpSession session = arg0.getSession();
    	ArrayList<Question> createdQuestions = new ArrayList<Question>();
    	session.setAttribute("createdQuestions", createdQuestions);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
