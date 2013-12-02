package quiz_web;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quiz_model.Question;
import quiz_model.Quiz;

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
					String attr = question.getType()+"Num";
					String query = "SELECT Number FROM WS where Name = '"+attr+"';";
					ResultSet rs = stmt.executeQuery(query);
					// might be 0
					//System.out.println(rs.toString());
					rs.next();
					int questionId = rs.getInt("Number");
					question.setId(questionId);
					stmt.executeUpdate(question.insertSql(questionId, "Wenxiao"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			int quizId = 0;
			try {
				//System.out.println(question.insertSql(0, "Wenxiao"));
				String query = "SELECT Number FROM WS where Name = 'QuizNum'";
				ResultSet rs = stmt.executeQuery(query);
				// might be 0
				rs.next();
				quizId = rs.getInt("Number");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			boolean canPractice;
			boolean isRandom;
			boolean isOnePage;
			boolean isImmediateCorrection;
			if (request.getParameter("practice") == null) {
				canPractice = false;
			} else {
				canPractice = request.getParameter("practice").equals("yes");
			}
			if (request.getParameter("random") == null) {
				isRandom = false;
			} else {
				isRandom= request.getParameter("random").equals("yes");
			}
			if (request.getParameter("page_setting") == null) {
				isOnePage = true;
			} else {
				isOnePage = request.getParameter("page_setting").equals("one");
			}
			if (request.getParameter("immediate") == null) {
				isImmediateCorrection = false;
			} else {
				isImmediateCorrection = request.getParameter("immediate").equals("yes");
			}
			Quiz quiz = new Quiz(quizId, "quiz", "Wenxiao", new Timestamp(System.currentTimeMillis()), "tag", 
					"category", canPractice, isRandom, isOnePage, isImmediateCorrection, questions);
			quiz.insertQISql();
			for(Question question : questions){
				quiz.insertQQSql(question.getId(), question.getType());
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
