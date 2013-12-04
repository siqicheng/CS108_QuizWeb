<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="database_connection.*,quiz_model.*,quiz_web.*,java.sql.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
	String questionNumStr = request.getParameter("question");
	int questionNum;
	if (questionNumStr == null) {
		questionNum = 0;
	} else {
		String actionStr = request.getParameter("action");
		if (actionStr == null) {
			questionNum = Integer.parseInt(questionNumStr);
		}
		if (actionStr.matches("Back")) {
			questionNum = Integer.parseInt(questionNumStr)-1;
		} else {
			// next
			questionNum = Integer.parseInt(questionNumStr)+1;
		}
	}
	request.getSession().setAttribute("question",questionNum);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz - <%=quiz.getName()%></title>
</head>
<body>
<h1><%=quiz.getName() %></h1>
<%	 
	out.print("<form action=\"QuizMultiplePageServlet\" method=\"post\">");
	Question question = questions.get(questionNum);
	out.print(question.getHTMLwithQuestion(questionNum));
	out.print("<input type=\"hidden\" name=\"question\" value=\""+ Integer.toString(questionNum)+"\">");
	if (questionNum == 0) {
		out.print("<input type=\"submit\" name=\"action\" value=\"Next\">");
	} else if (questionNum == questions.size()-1) {
		out.print("<input type=\"submit\" name=\"action\" value=\"Back\">");
		out.print("<input type=\"submit\" value=\"Submit\">");
	} else {
		out.print("<input type=\"submit\" name=\"action\" value=\"Back\">");
		out.print("<input type=\"submit\" name=\"action\" value=\"Next\">");
	}
	out.print("</form>");
%>
</body>
</html>