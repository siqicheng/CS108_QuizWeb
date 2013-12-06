package quiz_web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database_connection.DBConnection;

import quiz_model.Quiz;

/**
 * Servlet implementation class QuizFeedBackServlet
 */
@WebServlet("/QuizFeedBackServlet")
public class QuizFeedBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizFeedBackServlet() {
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
		Quiz quiz = (Quiz) request.getSession().getAttribute("Quiz");
		String quiz_id = Integer.toString(quiz.getId());
		String user_name = (String)request.getSession().getAttribute("sender");
		String score_str = request.getParameter("rate");
		String comment = request.getParameter("review");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(new java.util.Date().getTime());
		
		String sql = "INSERT INTO Rates_Table VALUES(" + quiz_id + ", " + score_str + ", '" + comment + "', '" + user_name + "', '" + time + "');";		
		
		//System.out.println(sql);
		DBConnection con = new DBConnection();
			try {
				con.getStatement().executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("QuizSummary.jsp");
		dispatch.forward(request, response);
	}

}
