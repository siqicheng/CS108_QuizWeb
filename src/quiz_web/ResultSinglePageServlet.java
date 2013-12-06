package quiz_web;

import java.io.IOException;
import java.util.*;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database_connection.DBConnection;

import quiz_model.Question;
import quiz_model.Quiz;

/**
 * Servlet implementation class ResultSinglePageServlet
 */
@WebServlet("/ResultSinglePageServlet")
public class ResultSinglePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultSinglePageServlet() {
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
		HttpSession session = request.getSession();
        // get quiz info
        int  quizId = Integer.parseInt(request.getParameter("quizId"));
        DBConnection con = (DBConnection) request.getSession().getAttribute("dbcon");
    	if (con == null) {
    		request.getSession().setAttribute("dbcon", new DBConnection());
    		con = (DBConnection) request.getSession().getAttribute("dbcon");
    	}
        Quiz quiz = new Quiz(quizId, con);
        List<Question> questions = quiz.getQuestions();

        int curScore = 0;
        for (int i = 0; i < questions.size(); i++) {
        	Question q = questions.get(i);
        	ArrayList<String> ansList = q.fetchAnswer(request, i);
            curScore += q.getScore(ansList);
        }

        long startTime = (Long) session.getAttribute("startTime");
        long timeElapsed = new Date().getTime() - startTime;
        //String quizId = quiz.saveQuizEvent(userName, timeElapsed, currentScore);

//        boolean newAchieve1 = false;
//        boolean newAchieve2 = false;
//        Account user = new Account(userName);
//        if(quizName != null){
//                user.addQuizTaken(quizName, quizId);
//                if(user.countHistory("t") == 10){
//                        newAchieve1 = true;
//                        user.addAchievement("a4", quizName);
//                }
//
//                if (!user.containsAchievement("a5") && currentScore >= quiz.getBestScore()) {
//                        newAchieve2 = true;
//                        user.addAchievement("a5", quizName);
//                }
//        }
//
//        /*
//         * write html
//         */
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        // print to result page
//        out.println("<!DOCTYPE html>");
//        out.println("<html>");
//        out.println("<head>");
//        out.println("<script type=\"text/javascript\" src=\"challenge-msg.js\"></script>");
//        out.println("<meta charset=\"UTF-8\">");
//        out.println("<title>Quiz Results</title>");
//        out.println("</head>");
//        if(newAchieve1 && newAchieve2){
//                out.println("<body onload=\"javascript:Auto_both()\"");
//        } else if(newAchieve1 && !newAchieve2){
//                out.println("<body onload=\"javascript:Auto_1()\"");
//        } else if(!newAchieve1 && newAchieve2){
//                out.println("<body onload=\"javascript:Auto_2()\"");
//        } else {
//                out.println("<body>");
//        }
//        out.println("<h1>Quiz Results</h1>");
//        out.println("<p>Score: " + quiz.getScore(quizId) + "/"
//                        + quiz.getMaxScore() + "</p>");
//
//        out.println("<p>Time: " + quiz.getTimeElapsed(quizId) + "s </p>");
//
//        //*** add challenge button
//
//        out.println("<input name='' type='button' value='Challenge my friends!'onclick='AddElement()'>");
//        out.println("<form action='ChallengeLetterSent' target='hidFrame' method='post' id='letter'>");
//        out.println("<input type='hidden' name='quizName' value=" + quizName + ">");
//        out.println("<div id='msg'></div>");
//        out.println("</form>");
//        out.println("<iframe name='hidFrame' style='display: none'></iframe>");
//
//        out.println("<p><a href=\"" + quiz.getSummaryPage()
//                        + "\">Go back to summary page.</p>");
//
//        out.println("</body>");
//        out.println("</html>");
//
//
//
//        // remove all the session attributes defined in this servlet
//        session.removeAttribute("quizStartTime");
	}

}
