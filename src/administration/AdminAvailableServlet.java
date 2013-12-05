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
 * Servlet implementation class AdminAvailableServlet
 */
@WebServlet("/AdminAvailableServlet")
public class AdminAvailableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAvailableServlet() {
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
		AdministratorAccount admin = new  AdministratorAccount(request.getParameter("name"));
		if(!admin.getUserType().equals("s")){
			RequestDispatcher rd = request.getRequestDispatcher("CreateAccount_welcome.jsp");
            rd.forward(request,response);
		}else{
			RequestDispatcher rd = request.getRequestDispatcher("admin.jsp");
            rd.forward(request,response);
		}
		
	}

}
