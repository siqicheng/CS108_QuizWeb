<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Fill-in-the-Blank question</title>
</head>
<body>
<h1>Add a new Fill-in-the-Blank question</h1>
<p>Your question should have two parts, with a blank left to be filled by the quiz taker</p>
<form action="CreateFillInBlankServlet" method="post">
First part of your question:<br>
<textarea name="part1" rows="4" cols="80"></textarea><br>
Second part of your question:<br>
<textarea name="part2" rows="4" cols="80"></textarea><br>
Your answers (use ; as delimiters if more than one legal answer): <br>
<textarea name="answers" rows="4" cols="80"></textarea><br>
<input type="submit" value="Add"/>
</form>

<form action="CreateQuiz.jsp">
<input type="submit" value="Back">
</form>

</body>
</html>