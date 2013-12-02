package quiz_web;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import database_connection.DBConnection;

import quiz_model.*;


/**
 * Application Lifecycle Listener implementation class CreateQuizListener
 *
 */
@WebListener
public class UserListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public UserListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {
    	HttpSession session = arg0.getSession();
    	ArrayList<Question> createdQuestions = new ArrayList<Question>();
    	session.setAttribute("createdQuestions", createdQuestions);
    	DBConnection dbCon = new DBConnection();
    	Statement stmt = dbCon.getStatement();
    	session.setAttribute("db_connection", stmt);
    	session.setAttribute("dbcon", dbCon);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
