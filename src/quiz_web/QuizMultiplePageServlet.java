package quiz_web;

import java.io.IOException;
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
 * Servlet implementation class QuizMultiplePageServlet
 */
@WebServlet("/QuizMultiplePageServlet")
public class QuizMultiplePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizMultiplePageServlet() {
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
		Quiz quiz = (Quiz) request.getSession().getAttribute("Quiz");
		List<Question> questions = quiz.getQuestions();
		int questionNum = (Integer) request.getSession().getAttribute("question");
		if (questionNum == questions.size()-1) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("QuizResultPage.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("QuizMultiplePage.jsp");
			dispatcher.forward(request, response);
		}
	}

}
