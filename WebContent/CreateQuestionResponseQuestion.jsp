<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, quiz_model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Question-Response question</title>
</head>
<body>
<h1>Add a new Question-Response question</h1>



<form action="CreateQuestionResponseServlet" method="post">
Your question:<br>
<textarea name="question" rows="4" cols="80"></textarea><br>
Your answers (use ; as delimiters if more than one legal answer): <br>
<textarea name="answers" rows="4" cols="80"></textarea><br>
<input type="submit" value="Add"/>
</form>

<form action="CreateQuiz.jsp">
<input type="submit" value="Back">
</form>


</body>
</html>