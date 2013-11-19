package quiz_web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import quiz_model.*;
/**
 * Servlet implementation class CreateQuestionResponseServlet
 */
@WebServlet("/CreateQuestionResponseServlet")
public class CreateQuestionResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionResponseServlet() {
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
		
		String question = request.getParameter("question");
		String answers = request.getParameter("answers");
		ArrayList<String> answerList = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(answers, ";");
		 while (st.hasMoreTokens()) {
		     answerList.add(st.nextToken());
		 }
		 
		 QuestionResponseQuestion newQ = new QuestionResponseQuestion(question, answerList);
		 questions.add(newQ);
		 
		 RequestDispatcher dispatch = request.getRequestDispatcher("CreateQuiz.jsp");
		 dispatch.forward(request, response);
	}


}
