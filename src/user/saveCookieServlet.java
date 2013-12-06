package user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


/**
 * Servlet implementation class saveCookieServlet
 */
@WebServlet("/saveCookieServlet")
public class saveCookieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public saveCookieServlet() {
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
		response.setContentType("text/html");
		String usrname = request.getParameter("cookiename");
		String savecookie = request.getParameter("savecookie");
		request.setAttribute("name", usrname);
		request.setAttribute("sender", usrname);
		
		String homepage = "CreateAccount_welcome.jsp";
		if (!"on".equals(savecookie)){
			request.setAttribute("once", "one");
	        RequestDispatcher rd = request.getRequestDispatcher(homepage);
	        rd.forward(request,response);
		}else{
		
			Cookie[] cookies = request.getCookies();
			boolean foundCookie = false;
			if(cookies != null){
				for(int i = 0; i < cookies.length; i++) { 
					Cookie cookie1 = cookies[i];
					if (cookie1.getName().equals("username")) {
						foundCookie = true;
						cookie1.setValue(usrname);
						cookie1.setMaxAge(30*24*60*60);
					}
				}  
			}
			if (!foundCookie) {
				Cookie cookie1 = new Cookie("username",usrname);
				cookie1.setMaxAge(30*24*60*60);
				response.addCookie(cookie1); 
			}

			request.setAttribute("once", "one");
			RequestDispatcher rd = request.getRequestDispatcher(homepage);
			rd.forward(request,response);
		}
	}

}
