package administration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.AccountManager;
import user.AdministratorAccount;

/**
 * Servlet implementation class PromoteAccountServlet
 */
@WebServlet("/PromoteAccountServlet")
public class PromoteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PromoteAccountServlet() {
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
		String promoteId = request.getParameter("name");
		String adminUser = (String)request.getSession().getAttribute("sender");
		AdministratorAccount admin = new AdministratorAccount(adminUser);
		if(!admin.getUserType().equals("s")){
			response.sendRedirect("CreateAccount_welcome.jsp");
			return;
		}
		if(AccountManager.hasAccount(promoteId)) {
			String status = request.getParameter("status");
			admin.setStatus(promoteId, status);
		} 
		response.sendRedirect("admin.jsp");

	}

}
