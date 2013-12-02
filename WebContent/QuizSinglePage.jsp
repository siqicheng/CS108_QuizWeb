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
	if (quiz.isRandom()) {
		Collections.shuffle(questions);
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz - <%=quiz.getName()%></title>
</head>
<body>
<form action="QuizResultPage.jsp" method="post">
<%
	for (int i = 0; i < questions.size(); i++) {
		Question question = questions.get(i);
		out.print(question.getHTMLwithQuestion(i));
	}
%> <input type="hidden" name="quizId" value="<%=quiz.getId()%>">
   <input type="submit" value="Submit">
</form>
</body>
</html>