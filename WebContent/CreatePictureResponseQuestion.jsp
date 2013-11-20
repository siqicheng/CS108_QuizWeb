<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Picture-Response Question</title>

</head>
<body>
<h1>Add a new Picture-Response question</h1>



<form action="CreatePictureResponseServlet" method="post">
Your Picture URL: <br>
<textarea name="url" rows="1" cols="80"></textarea><br>
Your question:<br>
<textarea name="question" rows="1" cols="80"></textarea><br>
Your answers (use ; as delimiters if more than one legal answer): <br>
<textarea name="answers" rows="2" cols="80"></textarea><br>
<input type="submit" value="Add"/>
</form>

<form action="CreateQuiz.jsp">
<input type="submit" value="Back">
</form>

</body>
</html>