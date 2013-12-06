package user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FriendSearchServlet
 */
@WebServlet("/FriendSearchServlet")
public class FriendSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendSearchServlet() {
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
		String user = request.getParameter("sender");
		String friend = request.getParameter("name");

		request.setAttribute("sender", user);
		request.setAttribute("name", friend);
		
		if(AccountManager.hasAccount(request.getParameter("name"))){
            RequestDispatcher rd = request.getRequestDispatcher("CreateAccount_welcome.jsp");
            rd.forward(request,response);
        }else{
        	RequestDispatcher rd = request.getRequestDispatcher("NoPerson.jsp");
        	rd.forward(request,response);
        }
	}

}
