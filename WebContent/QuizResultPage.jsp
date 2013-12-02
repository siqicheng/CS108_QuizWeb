<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="database_connection.*,quiz_model.*,quiz_web.*,java.sql.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Result Page</title>
</head>
<%
	//int quizId = Integer.parseInt(request.getParameter("quizId"));
	//DBConnection con = (DBConnection) request.getSession()
		//	.getAttribute("dbcon");
	//if (con == null) {
		//request.getSession().setAttribute("dbcon", new DBConnection());
		//con = (DBConnection) request.getSession().getAttribute("dbcon");
		//System.out.println("hello");
	//}
	//Quiz quiz = new Quiz(quizId, con);
	Quiz quiz = (Quiz) request.getSession().getAttribute("Quiz");
	List<Question> questions = quiz.getQuestions();
%>
<body>
<h1><%=quiz.getName() %></h1>
<table border="1">
	<%
		//out.println("<tr>");
		//out.println("<th> Questions </th>");
		//out.println("<th> Your Answers </th>");
		//out.println("<th> Correct Answers </th>");
		//out.println("</tr>");
		int totScore = 0;
		for (int i = 0; i < questions.size(); i++) {
			Question q = questions.get(i);
			String ans = q.fetchAnswer(request, i);
			int curScore = q.getScore(ans);
			totScore += curScore;
			out.print(q.getHTMLwithQuestionResult(i, ans, curScore));
		}
		out.println("Score: " + totScore + "<br><br>"); 
		long startTime = (Long) session.getAttribute("startTime");
        long timeElapsed = new java.util.Date().getTime() - startTime;
		out.println("Elapsed time: "+ timeElapsed +"s<br><br>"); //need to calculate elapesed time
	%>
</table>
<br>
<br>

</body>
</html>