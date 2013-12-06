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

import quiz_model.Question;
import quiz_model.*;

/**
 * Servlet implementation class CreateQuestionResponseMultiAnswerServlet
 */
@WebServlet("/CreateQuestionResponseMultiAnswerServlet")
public class CreateQuestionResponseMultiAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionResponseMultiAnswerServlet() {
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
		
		
		ArrayList<Question> questions = (ArrayList<Question>)request.getSession().getAttribute("createdQuestions"); 
		if(questions == null) {
			questions = new ArrayList<Question>();
			request.getSession().setAttribute("createdQuestions", questions);
		}
		
		String question = request.getParameter("question");
		int answerNumber = Integer.parseInt(request.getParameter("answerNumber"));
		boolean isOrdered;  			
		if (request.getParameter("isOrdered") == null) {
			isOrdered = false;
		} else {
			isOrdered = request.getParameter("isOrdered").equals("yes");
		}
		ArrayList<ArrayList<String>> answerList = new ArrayList<ArrayList<String>>();
		
		for (int i =0 ; i < answerNumber; i++) {
			ArrayList<String> ansList = new ArrayList<String> ();
			String answers = request.getParameter("answer"+Integer.toString(i));
			StringTokenizer st = new StringTokenizer(answers, ";");
			while (st.hasMoreTokens()) {
			    ansList.add(st.nextToken());
			}
			answerList.add(ansList);
		}
		
		 
		 QuestionResponseMultiAnswerQuestion newQ = new QuestionResponseMultiAnswerQuestion(question, answerList, isOrdered);
		 questions.add(newQ);
		 
		 RequestDispatcher dispatch = request.getRequestDispatcher("CreateQuiz.jsp");
		 dispatch.forward(request, response);
	}

}
