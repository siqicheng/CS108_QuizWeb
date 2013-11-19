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

import quiz_model.FillInBlankQuestion;
import quiz_model.MultipleChoiceQuestion;
import quiz_model.Question;

/**
 * Servlet implementation class CreateMultipleChoiceServlet
 */
@WebServlet("/CreateMultipleChoiceServlet")
public class CreateMultipleChoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMultipleChoiceServlet() {
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
		String answer = request.getParameter("answer");
		ArrayList<String> choiceList = new ArrayList<String>();
		String cn = request.getParameter("choiceNumber");
		int choiceNumber = Integer.parseInt(cn);
		for(int i = 0; i < choiceNumber; ++i){
			choiceList.add(request.getParameter("choice" + i));
		}
		
		MultipleChoiceQuestion newQ = new MultipleChoiceQuestion(question, choiceList, answer);
		questions.add(newQ);
		 
		RequestDispatcher dispatch = request.getRequestDispatcher("CreateQuiz.jsp");
		dispatch.forward(request, response);
	}

}
