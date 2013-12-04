package user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class sendMailServlet
 */
@WebServlet("/sendMailServlet")
public class sendMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendMailServlet() {
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
		String sender = request.getParameter("sender");
		String receiver = request.getParameter("receiver");
		String msg = request.getParameter("msg");
		
		String id = request.getParameter("quizId");
		if (id.length() == 0){
			MailManager.sendMessage(sender, receiver, msg);
			request.getSession().setAttribute("sender", sender);

			RequestDispatcher rd = request.getRequestDispatcher("mailSystem.jsp");
			rd.forward(request,response);
        }else{
        	String link = "<a href=\"QuizSummary.jsp?user_name=" + receiver + "&quizId=" + id + "\">Take this challenge</a>";
        	
			MailManager.sendMessage(sender, receiver, link);
			request.getSession().setAttribute("sender", sender);
			request.getSession().setAttribute("quizId", id);
			
			RequestDispatcher rd = request.getRequestDispatcher("QuizSummary.jsp");
			rd.forward(request,response);
        }
	}

}
