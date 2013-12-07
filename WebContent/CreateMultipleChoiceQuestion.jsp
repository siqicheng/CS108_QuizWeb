<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String sender = (String)request.getSession().getAttribute("sender");
%>
<title>New Multiple-Choice question</title>
</head>
<link rel="shortcut icon" href="pic/favicon.ico" /> 
<link rel="stylesheet" href="CSS/home_style.css" type="text/css">
<!-- COLLECTED CSS -->
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
						<a href="friendlist.jsp?sender=<%=sender%>" id="item-text">Friends</a>
					</li>
					<li id="items">
						<a href="mailSystem.jsp" id="item-text">Mailbox</a>
					</li>
				</ul>
			</div>

			<div id="search-bar-board">	 
				<div id="search-bar" >
					<form method="POST" id="search-form" action="TagSearchResult.jsp" >
						<input type="text" id="search-text" name="tag" placeholder="Searching tags..." />
	
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
		<h2>Add a new Multiple-Choice question</h2>
		<p><b>Your question should have multiple choices, with only one of them is a legal answer</b></p>
		
		<form action="CreateMultipleChoiceServlet" method="post">
		<h2>Question: </h2>
		<textarea name="question" rows="3" cols="80" id = "big-input"></textarea><br>
		<h2>Correct answer:</h2>
		<textarea name="answer" rows="1" cols="50" id = "big-input"></textarea><br>
		<h2>Other choices: </h2>
		<%
		String cn = request.getParameter("choiceNumber");
		int choiceNumber;
		if(cn == null) choiceNumber = 2;
		else choiceNumber = Integer.parseInt(cn);
		
		for (int i = 0; i < choiceNumber; ++i){
			String html = "choice" + i + ": &nbsp&nbsp&nbsp";
			html += "<textarea name = \"" + "choice" + i + "\" rows=\"1\" cols=\"50\" id = \"big-input\"></textarea><br>";
			out.print("<b>" + html + "</b>");
		}
		%>
		<input type="hidden" name="choiceNumber" value="<%=choiceNumber%>">
		
		</form>
		
		<form action="CreateMultipleChoiceQuestion.jsp" id = "question-submit">
		<input type="hidden" name="choiceNumber" value="<%=choiceNumber+1%>">
		<input type="submit" value="More choice" id = "yellow-button"/>
		</form>
		
		<form action="CreateQuiz.jsp">
		<input type="submit" value="Add" id = "red-button" form = "question-submit"/>
		<input type="submit" value="Back" id = "green-button">
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