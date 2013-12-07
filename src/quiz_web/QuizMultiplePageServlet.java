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
		//		String questionNumStr = request.getParameter("question");
		//		int questionNum;
		//		if (questionNumStr == null) {
		//			questionNum = 0;
		//		} else {
		//			String actionStr = request.getParameter("action");
		//			if (actionStr == null) {
		//				questionNum = Integer.parseInt(questionNumStr);
		//			}
		//			if (actionStr.matches("Back")) {
		//				questionNum = Integer.parseInt(questionNumStr)-1;
		//			} else {
		//				// next
		//				questionNum = Integer.parseInt(questionNumStr)+1;
		//				String ans = request.getParameter("answer" + Integer.toString(questionNum-1));
		//				if (ans != null) {
		//					request.getSession().setAttribute("answer"+Integer.toString(questionNum-1), ans);
		//				}
		//			}
		//		}
		//		request.getSession().setAttribute("question",questionNum);
		int questionNum = (Integer)request.getSession().getAttribute("question");
		Question q = questions.get(questionNum);
		int answerNum = q.getAnswerNum();
		if (q.getType().matches("QuestionResponseMultiAnswer")) {
			for (int i = 0; i < answerNum; i++) {
				String ansStr = request.getParameter("answer"+Integer.toString(questionNum)+"_"+Integer.toString(i));
				request.getSession().setAttribute("answer"+Integer.toString(questionNum)+"_"+Integer.toString(i), ansStr);
			}
		} else {
			String[] ans = request.getParameterValues("answer" + Integer.toString(questionNum));
			if (ans != null) {
				if (ans.length == 1) {
					System.out.println(questionNum);
					System.out.println("answer"+Integer.toString(questionNum));
					request.getSession().setAttribute("answer"+Integer.toString(questionNum), ans[0]);
				} else {
					for (int i = 0; i < ans.length; i++) {
						System.out.println("answer"+Integer.toString(questionNum)+"_"+Integer.toString(i));
						System.out.println(ans[i]);
						request.getSession().setAttribute("answer"+Integer.toString(questionNum)+"_"+Integer.toString(i), ans[i]);
					}
					request.getSession().setAttribute("anserNum_"+Integer.toString(questionNum),ans.length);
				}
			}
		}
		if (questionNum == questions.size()-1) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("QuizResultPage.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("QuizMultiplePage.jsp");
			dispatcher.forward(request, response);
		}
	}

}
