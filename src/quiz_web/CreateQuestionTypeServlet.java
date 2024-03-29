package quiz_web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateQuestionTypeServlet
 */
@WebServlet("/CreateQuestionTypeServlet")
public class CreateQuestionTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateQuestionTypeServlet() {
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
		String type = request.getParameter("type");
		RequestDispatcher dispatch;
		if(type.equals("QuestionResponseQuestion")) {
			dispatch = request.getRequestDispatcher("CreateQuestionResponseQuestion.jsp");
		} else if (type.equals("FillInBlankQuestion")){
			dispatch = request.getRequestDispatcher("CreateFillInBlankQuestion.jsp");
		} else if (type.equals("MultipleChoiceQuestion")){
			dispatch = request.getRequestDispatcher("CreateMultipleChoiceQuestion.jsp");
		} else if (type.equals("PictureResponseQuestion")){
			dispatch = request.getRequestDispatcher("CreatePictureResponseQuestion.jsp");
		} else if (type.equals("QuestionResponseQuestionMultiAnswer")){
			dispatch = request.getRequestDispatcher("CreateQuestionResponseMultiAnswerQuestion.jsp");
		} else if (type.equals("MultipleChoiceMultipleAnswerQuestion")){
			dispatch = request.getRequestDispatcher("CreateMultipleChoiceMultipleAnswerQuestion.jsp");
		} else if (type.equals("MatchingQuestion")){
			dispatch = request.getRequestDispatcher("CreateMatchingQuestion.jsp");
		} else dispatch = null;
		dispatch.forward(request, response);
	}

}
