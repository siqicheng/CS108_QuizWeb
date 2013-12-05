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
	String html = question.getHTMLwithAnswer(i);
	out.println(html);
}
%>

<h3>Select a question type to add: </h3>
<form action = "CreateQuestionTypeServlet" method="post">
<p><input type="radio" name="type" value="QuestionResponseQuestion" checked> Question-Response</p>
<p><input type="radio" name="type" value="FillInBlankQuestion"> Fill in the Blank</p>
<p><input type="radio" name="type" value="MultipleChoiceQuestion"> Multiple Choice</p>
<p><input type="radio" name="type" value="PictureResponseQuestion"> Picture Response</p>
<input type="submit" value="Select">
</form>


<form action = "CreateQuizSubmissionServlet" method="post">
<p>Give quiz a name:<br>
<textarea name="quizName" rows="1" cols="30"></textarea><br></p>
<p>Give quiz a description:<br>
<textarea name="quizDescription" rows="1" cols="30"></textarea><br></p>
<p>Tags: (use # as delimiter e.g. ironman#marvel)<br>
<textarea name="tags" rows="1" cols="40"></textarea><br></p>
<p>Category: 
<select name="category">
	<option value="All" selected>All</option>
	<%
		Category category = new Category();
	
		for(int i = 0; i < category.options.length; ++i){
			out.print("<option value=\"" + category.options[i] + "\">" + category.options[i] + "</option>");
		}
	%>
</select>
<br>

<p>Practice Questions <input type="checkbox" name="practice" value="yes"></p>
<p>Random Questions <input type="checkbox" name="random" value="yes"></p>
<p>Page Setting: 
<input type="radio" name="page_setting" value="one"> One Page
<input type="radio" name="page_setting" value="multiple"> Multiple Pages</p>
<p>Immediate Correction <input type="checkbox" name="immediate" value="yes"></p>
<input type="submit" name = "action" value="Submit">
<input type="submit" name = "action" value="Cancel">
</form>
</body>
</html>