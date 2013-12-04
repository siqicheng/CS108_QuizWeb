package quiz_web;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
		String quizCreator = (String)request.getSession().getAttribute("QuizCreator");
		String action = request.getParameter("action");
		if(action.equals("Submit")){
			/* Insert question */
			int quizId = 0;
			try {
				for(Question question : questions){
					//System.out.println(question.insertSql(0, "Wenxiao"));
					String attr = question.getType()+"Num";
					String query = "SELECT Number FROM WS where Name = '"+attr+"';";
					ResultSet rs = stmt.executeQuery(query);
					// might be 0
					//System.out.println(rs.toString());
					rs.next();
					int questionId = rs.getInt("Number");
					question.setId(questionId);
					stmt.executeUpdate(question.insertSql(questionId, quizCreator));
					
					String query_2 = "SELECT Number FROM WS where Name = 'QuizNum'";
					ResultSet rs_2 = stmt.executeQuery(query_2);
					// might be 0
					rs_2.next();
					quizId = rs_2.getInt("Number");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			boolean canPractice;
			boolean isRandom;
			boolean isOnePage;
			boolean isImmediateCorrection;
			String quizName = request.getParameter("quizName");
			String quizDescription = request.getParameter("quizDescription");
			if (quizName == null) {
				quizName = "";
			}
			if (quizDescription == null) {
				quizDescription = "";
			}
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
			Quiz quiz = new Quiz(quizId, quizName, quizDescription, quizCreator, new Timestamp(System.currentTimeMillis()), "tag", 
					"category", canPractice, isRandom, isOnePage, isImmediateCorrection, questions);
			try {
				stmt.executeUpdate(quiz.insertQISql());
				for(Question question : questions) {
					System.out.println(quiz.insertQQSql(question.getId(), question.getType()));
					stmt.executeUpdate(quiz.insertQQSql(question.getId(), question.getType()));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			;

			/* Clear the createdQuesions list */
			questions.clear();
			request.setAttribute("createdQuestions", questions);
		} 
		RequestDispatcher dispatch = request.getRequestDispatcher("CreateAccount_welcome.jsp");
		dispatch.forward(request, response);
	}

}
