<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, quiz_model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a new quiz</title>
</head>
<body>

<%
ArrayList<Question> questions = (ArrayList<Question>)request.getSession().getAttribute("createdQuestions"); 
if(questions == null) {
	questions = new ArrayList<Question>();
	request.getSession().setAttribute("createdQuestions", questions);
}

for(int i = 0; i < questions.size(); ++i){
	Question question = questions.get(i);
	question.setId(i);
	String html = question.getHTMLwithAnswer();
	out.println(html);
}
%>

<h3>Select a question type to add: </h3>
<form action = "CreateQuestionTypeServlet" method="post">
<p><input type="radio" name="type" value="QuestionResponseQuestion" checked> Question-Response</p>
<p><input type="radio" name="type" value="FillInBlankQuestion"> Fill in the Blank</p>
<p><input type="radio" name="type" value="MultipleChoiceQuestion"> Multiple Choice</p>
<input type="submit" value="Select">
</form>

</body>
</html>