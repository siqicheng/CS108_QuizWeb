package administration;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.AdministratorAccount;

/**
 * Servlet implementation class ManageQuizServlet
 */
@WebServlet("/ManageQuizServlet")
public class ManageQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageQuizServlet() {
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
		String quizID = request.getParameter("quiz");
		String adminUser = (String)request.getSession().getAttribute("sender");
		AdministratorAccount admin = new AdministratorAccount(adminUser);
		if(!admin.getUserType().equals("s")){
			response.sendRedirect("CreateAccount_welcome.jsp");
			return;
		}
		if(admin.hasQuiz(quizID)) {
			String op = request.getParameter("operation");
			admin.clearQuizHistory(quizID);
			if(op.equals("2")) admin.deleteQuiz(quizID);
		} 
		
        RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
        rd.forward(request,response);
		
	}

}
