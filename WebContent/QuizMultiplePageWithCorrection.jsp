<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="database_connection.*,quiz_model.*,quiz_web.*,java.sql.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	Quiz quiz = (Quiz) request.getSession().getAttribute("Quiz");
	List<Question> questions = quiz.getQuestions();
	String questionNumStr = request.getParameter("question");
	int questionNum;
	if (questionNumStr == null) questionNum = 0;
	else questionNum = Integer.parseInt(questionNumStr) + 1;	
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz - <%=quiz.getName()%></title>
</head>
<body>
<h1><%=quiz.getName() %></h1>
<form action="QuizMultiplePageWithCorrectionAfterSubmission.jsp" method="POST">
<%	 
	//out.print("<form action=\"QuizMultiplePageServlet\" method=\"post\">");
	Question question = questions.get(questionNum);
	out.print(question.getHTMLwithQuestion(questionNum));
	String input = "<input type=\"hidden\" name=\"question\" value=\""+ Integer.toString(questionNum)+"\">";
	out.print(input);
	//System.out.println(input);
	/*if (questionNum == 0) {
		out.print("<input type=\"submit\" name=\"action\" value=\"Next\">");
	} else if (questionNum == questions.size()-1) {
		out.print("<input type=\"submit\" name=\"action\" value=\"Back\">");
		out.print("<input type=\"submit\" value=\"Submit\">");
	} else {
		out.print("<input type=\"submit\" name=\"action\" value=\"Back\">");
		out.print("<input type=\"submit\" name=\"action\" value=\"Next\">");
	}*/
	String fakeButton;
	if (questionNum == questions.size() -1 ) {
		fakeButton = "See result";
	} else fakeButton = "Next";
%>
<input type="submit" value="Submit">
<input type="submit" value="<%=fakeButton%>" disabled>
</form>
</body>
</html>