package user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class rmFriendServlet
 */
@WebServlet("/rmFriendServlet")
public class rmFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public rmFriendServlet() {
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
		String username = request.getParameter("username");
		String friendname = request.getParameter("friendname");
		if (FriendManager.isFriend(username, friendname)){
			FriendManager.rmFriendship(username, friendname);
		}
		
		
		request.getSession().setAttribute("name", username);
		request.getSession().setAttribute("sender", friendname);
		
        RequestDispatcher rd = request.getRequestDispatcher("CreateAccount_welcome.jsp");
        rd.forward(request,response);
	}

}
