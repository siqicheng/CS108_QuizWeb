package user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccountCreationServlet
 */
@WebServlet("/AccountCreationServlet")
public class AccountCreationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountCreationServlet() {
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

		if(AccountManager.hasAccount(request.getParameter("name"))){
            RequestDispatcher rd = request.getRequestDispatcher("CreateAccount_new_account.jsp?checkname=inuse");
            rd.forward(request,response);
        }
        else{
        	AccountManager.createNewAccount(request.getParameter("name"),request.getParameter("password"),
        			"u", request.getParameter("gender").charAt(0), request.getParameter("email"));
        	FriendManager.createPrivacy(request.getParameter("name"));
            RequestDispatcher rd = request.getRequestDispatcher("CreateAccount_welcome.jsp");
            rd.forward(request,response);
        }	
	}

}
