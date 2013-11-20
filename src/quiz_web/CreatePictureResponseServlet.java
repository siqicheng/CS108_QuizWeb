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

import quiz_model.PictureResponseQuestion;
import quiz_model.Question;
import quiz_model.QuestionResponseQuestion;

/**
 * Servlet implementation class CreatePictureResponseServlet
 */
@WebServlet("/CreatePictureResponseServlet")
public class CreatePictureResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePictureResponseServlet() {
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
		
		String url = request.getParameter("url");
		String question = request.getParameter("question");
		String answers = request.getParameter("answers");
		ArrayList<String> answerList = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(answers, ";");
		 while (st.hasMoreTokens()) {
		     answerList.add(st.nextToken());
		 }
		 
		 PictureResponseQuestion newQ = new PictureResponseQuestion(url, question, answerList);
		 questions.add(newQ);
		 
		 RequestDispatcher dispatch = request.getRequestDispatcher("CreateQuiz.jsp");
		 dispatch.forward(request, response);
	}

}
