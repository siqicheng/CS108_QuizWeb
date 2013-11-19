<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Multiple-Choice question</title>
</head>
<body>
<h1>Add a new Multiple-Choice question</h1>
<p>Your question should have multiple choices, with only one of them is a legal answer</p>

<form action="CreateMultipleChoiceServlet" method="post">
Question: <br>
<textarea name="question" rows="3" cols="80"></textarea><br>
Correct answer:<br>
<textarea name="answer" rows="1" cols="50"></textarea><br>
Other choices: <br>
<%
String cn = request.getParameter("choiceNumber");
int choiceNumber;
if(cn == null) choiceNumber = 2;
else choiceNumber = Integer.parseInt(cn);

for (int i = 0; i < choiceNumber; ++i){
	String html = "choice" + i + ": ";
	html += "<textarea name = \"" + "choice" + i + "\" rows=\"1\" cols=\"50\"></textarea><br>";
	out.print(html);
}
%>
<input type="hidden" name="choiceNumber" value="<%=choiceNumber%>">
<input type="submit" value="Add"/>
</form>

<form action="CreateMultipleChoiceQuestion.jsp">
<input type="hidden" name="choiceNumber" value="<%=choiceNumber+1%>">
<input type="submit" value="More choices"/>
</form>

<form action="CreateQuiz.jsp">
<input type="submit" value="Back">
</form>

</body>
</html>