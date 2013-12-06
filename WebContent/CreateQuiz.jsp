<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, quiz_model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String sender = (String)request.getSession().getAttribute("sender");
%>
<title>Create a new quiz</title>
<link rel="shortcut icon" href="pic/favicon.ico" /> 
<link rel="stylesheet" href="CSS/home_style.css" type="text/css">
<!-- COLLECTED CSS -->
</head>
<body>
	<div id="title-bar">
		<div class="wrapper">
			<div class="logo">
				<a href="login.jsp"><img src="pic/logo.jpg" width="" height="44"></a>
			</div>
			
			<div id="function-item">
				<ul id="function-list">
					<li id="items">
						<a href="CreateAccount_welcome.jsp" id="item-text">Home</a>
					</li>
					<li id="items">
						<a href="CreateQuiz.jsp" id="item-text">CreateQuiz</a>
					</li>
					<li id="items">
						<a href="http://www.google.com" id="item-text">Friends</a>
					</li>
					<li id="items">
						<a href="mailSystem.jsp" id="item-text">Mailbox</a>
					</li>
				</ul>
			</div>

			<div id="search-bar-board">	 
				<div id="search-bar" >
					<form method="GET" id="search-form" action="http://www.google.com" >
						<input type="text" id="search-text" name="q" placeholder="searching..." />
	
						<button type="submit" class="magnify-button" id="search-buttom">
							<i  id="search-buttom-glass"></i>
						</button>
					</form> 
				</div>
			</div>

			<div id ="title-bar-text"> 
				<b> <%=sender%> </b>
			</div>	
		</div>
	</div>




<div class="wrapper">
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

<h2>Step One</h2>
<h3>Select a question type to add: </h3>
<form action = "CreateQuestionTypeServlet" method="post">
<p><input type="radio" name="type" value="QuestionResponseQuestion" checked> <b>Question-Response</b></p>
<p><input type="radio" name="type" value="FillInBlankQuestion"> <b>Fill in the Blank</b></p>
<p><input type="radio" name="type" value="MultipleChoiceQuestion"> <b>Multiple Choice</b></p>
<p><input type="radio" name="type" value="PictureResponseQuestion"> <b>Picture Response</b></p>
<input type="submit" value="Select" id = "red-button">
</form>

<h2>Step Two</h2>
<form action = "CreateQuizSubmissionServlet" method="post">
<p><b>Give quiz a name:</b><br>
<input name="quizName" id = "basic-input" placeholder="Quiz Name"></textarea><br></p>
<p><b>Give quiz a description:</b><br>
<input name="quizDescription" id = "basic-input" placeholder="Quiz Description"></textarea><br></p>
<p><b>Tags:</b><br>
<input name="tags" id = "basic-input-long" placeholder="Use # as delimiter e.g. ironman#marvel"></textarea><br></p>
<p><b>Category:</b> 
<select name="category" id ="yellow-button">
	<option value="All" selected>All</option>
	<%
		Category category = new Category();
	
		for(int i = 0; i < category.options.length; ++i){
			out.print("<option value=\"" + category.options[i] + "\">" + category.options[i] + "</option>");
		}
	%>
</select>

<h2>Step Three</h2>
<p><b>Practice Questions</b> <input type="checkbox" name="practice" value="yes"></p>
<p><b>Random Questions</b> <input type="checkbox" name="random" value="yes"></p>
<p><b>Page Setting:</b> 
<input type="radio" name="page_setting" value="one"> <b>One Page</b>
<input type="radio" name="page_setting" value="multiple"> <b>Multiple Pages</b></p>
<p><b>Immediate Correction</b> <input type="checkbox" name="immediate" value="yes"></p>
<input type="submit" name = "action" value="Submit" id ="red-button" >
<input type="submit" name = "action" value="Cancel" id ="green-button">
</form>
</div>

	<div class="wrapper">
		<div id="ft">
			<span class="fleft">
			  <a> Copyright © 2013 <%="IQuizYOU"%> , all rights reserved</a><br>
			  <a> Quick Links</a><br> 
			  <a href="http://www.stanford.edu/">Stanford University</a><br>
			  <a href="http://www.stanford.edu/class/cs108/">CS 108 -- Object Oriented System Design</a><br>
			</span>
		
			<span class="fright">
			    <a>About us</a>
			    · <a href="http://www.linkedin.com/pub/siqi-cheng/64/696/250">Siqi</a>
			    · <a href="http://www.linkedin.com/pub/wenxiao-du/58/ab8/778">Wenxiao</a>
			    · <a href="http://www.linkedin.com/pub/haoran-li/52/46b/a0">Haoran</a>
			    · <a href="http://www.linkedin.com/pub/hao-zhang/36/b80/a35">Hao</a>
			</span>
  		</div>
	</div>


</body>
</html>