<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="database_connection.*,quiz_model.*,quiz_web.*,java.sql.*,java.util.*, java.text.SimpleDateFormat"%>
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
			ArrayList<String> ansList = q.fetchAnswer(request, i);
			int curScore = q.getScore(ansList);
			totScore += curScore;
			out.print(q.getHTMLwithQuestionResult(i, ansList, curScore));
		}
		out.println("Score: " + totScore + "<br><br>"); 
		long startTime = (Long) session.getAttribute("startTime");
        long timeElapsed = new java.util.Date().getTime() - startTime;
		out.println("Elapsed time: "+ timeElapsed +"s<br><br>"); //need to calculate elapesed time
		
		/* Insert into quiz history table */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start_str = format.format(new Timestamp(startTime));
		String end_str = format.format(new java.util.Date().getTime());
		String sender = (String)request.getSession().getAttribute("sender");
		String score = Integer.toString(totScore);
		String id = Integer.toString(quiz.getId());
		String sql = "INSERT INTO quiz_take_history VALUES(\"" + sender + "\"," + id + "," + score + ",\"" + start_str + "\",\"" + end_str + "\");";
		DBConnection con = (DBConnection) request.getSession().getAttribute("dbcon");
		con.getStatement().executeUpdate(sql);
		
		/* Update Achievement table */
		con.updateAchievementTable(score, sender, id);
		
		/*INSERT INTO quiz_take_history VALUES ("shrink_du", 0, 200, "2013-12-01 05:00:07", "2013-12-01 05:30:07");*/
		
	%>
</table>

<form action="CreateAccount_welcome.jsp" method="POST">
<input type="submit" value="Homepage">
</form>

<br>
<br>

</body>
</html>