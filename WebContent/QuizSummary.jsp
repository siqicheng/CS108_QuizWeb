<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.sql.*, quiz_model.*, database_connection.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	int quizId = Integer.parseInt(request.getParameter("quizId"));
	DBConnection con = (DBConnection) request.getSession().getAttribute("dbcon");
	if (con == null) {
		request.getSession().setAttribute("dbcon", new DBConnection());
		con = (DBConnection) request.getSession().getAttribute("dbcon");
		System.out.println("hello");
	}
	Quiz quiz = new Quiz(quizId, con);
	request.getSession().setAttribute("Quiz", quiz);
	request.getSession().setAttribute("startTime", new java.util.Date().getTime());
%>
<!--<%
	String id = request.getParameter("quizId");
	System.out.println(id);
	DBConnection dbcon = (DBConnection)request.getSession().getAttribute("dbcon");
	if(dbcon == null){
		dbcon = new DBConnection();
		request.getSession().setAttribute("dbcon", dbcon);
	}
	
	Statement stmt = (Statement)request.getSession().getAttribute("db_connection");
	if(stmt == null){
		stmt = dbcon.getStatement();
		request.getSession().setAttribute("db_connection", stmt);
	}
	
	String name = dbcon.getQuizName(id);
%>
-->
<title><%=quiz.getName()%></title>
</head>
<body>
This is quiz <%=quiz.getName()%>
<form action="QuizSummaryPageServlet" method="post">
<input type="submit" value="GoRockQuiz">
</form>
</body>
</html>