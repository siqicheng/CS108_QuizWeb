package quiz_web;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quiz_model.Question;

/**
 * Servlet implementation class CreateQuizSubmissionServlet
 */
@WebServlet("/CreateQuizSubmissionServlet")
public class CreateQuizSubmissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizSubmissionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Question> questions = (ArrayList<Question>)request.getSession().getAttribute("createdQuestions"); 
		if(questions == null) {
			questions = new ArrayList<Question>();
			request.getSession().setAttribute("createdQuestions", questions);
		}
		
		Statement stmt = (Statement)request.getSession().getAttribute("db_connection");
		
		String action = request.getParameter("action");
		if(action.equals("Submit")){
			/* Insert question */
			for(Question question : questions){
				try {
					//System.out.println(question.insertSql(0, "Wenxiao"));
					stmt.executeUpdate(question.insertSql(0, "Wenxiao"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			/* Insert quiz */
			
			/* Clear the createdQuesions list */
			questions.clear();
			request.setAttribute("createdQuestions", questions);
		} else { /* Forward to homepage */
			/* TO DO */
		}
	}

}
