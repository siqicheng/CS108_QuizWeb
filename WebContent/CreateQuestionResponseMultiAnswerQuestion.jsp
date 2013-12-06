<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, quiz_model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Question-Response Multi-Answer question</title>
</head>
<body>
<h1>Add a new Question-Response Multi-Answer question</h1>


<form action="CreateQuestionResponseMultiAnswerServlet" method="post">
Your question:<br>
<textarea name="question" rows="4" cols="80"></textarea><br>
Your answers (use ; as delimiters if more than one legal answer): <br>
<%
String an = request.getParameter("answerNumber");
int answerNumber;
if(an == null) answerNumber = 1;
else answerNumber = Integer.parseInt(an);

for (int i = 0; i < answerNumber; ++i) {
	String html = "answer" + i + ": ";
	html += "<textarea name = \"" + "answer" + i + "\" rows=\"1\" cols=\"50\"></textarea><br>";
	out.print(html);
}
%>
<p>Is Answer Ordered <input type="checkbox" name="isOrdered" value="yes"></p>
<input type="hidden" name="answerNumber" value="<%=answerNumber%>">
<input type="submit" value="Add"/>
</form>
<form action="CreateQuestionResponseMultiAnswerQuestion.jsp">
<input type="hidden" name="answerNumber" value="<%=answerNumber+1%>">
<input type="submit" value="Add Another Answer"/>
</form>
<form action="CreateQuiz.jsp">
<input type="submit" value="Back">
</form>


</body>
</html>