package quiz_web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quiz_model.Question;
import quiz_model.Quiz;

/**
 * Servlet implementation class QuizSummaryPageServlet
 */
@WebServlet("/QuizSummaryPageServlet")
public class QuizSummaryPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizSummaryPageServlet() {
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
		// TODO Auto-generated method stub
		request.getSession().setAttribute("startTime", new java.util.Date().getTime());
		Quiz quiz = (Quiz) request.getSession().getAttribute("Quiz");
		List<Question> questions = quiz.getQuestions();
		if (quiz.isRandom()) {
			Collections.shuffle(questions);
		}
		if (quiz.isOnePage()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("QuizSinglePage.jsp");
			dispatcher.forward(request, response);
		} else if (!quiz.isImmediateCorrection()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("QuizMultiplePage.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("QuizMultiplePageWithCorrection.jsp");
			dispatcher.forward(request, response);
		}
	}

}
