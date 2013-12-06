<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String sender = (String)request.getSession().getAttribute("sender");
%>
<title>New Multiple-Choice Multiple-Answer question</title>
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
		<h1>Add a new Multiple-Choice Multiple-Answer question</h1>
		<p>Your question should have multiple choices, there can be multiple legal answers as specified by you</p>
		
		<form action="CreateMultipleChoiceMultipleAnswerServlet" method="post">
		<b>Question: </b><br>
		<textarea name="question" rows="3" cols="80" id = "big-input"></textarea><br>
		<b>Correct answers:</b><br>
		<% 
		String an = request.getParameter("createAnswerNumber");
		int answerNumber;
		if (an == null) answerNumber = 1;
		else answerNumber = Integer.parseInt(an);
		
		for (int i = 0; i < answerNumber; ++i){
			String html = "answer" + i + ": ";
			html += "<textarea name = \"" + "createAnswer" + i + "\" rows=\"1\" cols=\"50\" id = \"big-input\"></textarea><br>";
			out.print("<b>" + html + "</b>");
		}
		%>
		<b>Other choices: </b><br>
		<%
		
		String cn = request.getParameter("choiceNumber");
		int choiceNumber;
		
		if(cn == null) choiceNumber = 2;
		else choiceNumber = Integer.parseInt(cn);
		
		for (int i = 0; i < choiceNumber; ++i){
			String html = "choice" + i + ": ";
			html += "<textarea name = \"" + "choice" + i + "\" rows=\"1\" cols=\"50\" id = \"big-input\"></textarea><br>";
			out.print("<b>" + html + "</b>");
		}
		%>
		<input type="hidden" name="createAnswerNumber" value="<%=answerNumber%>">
		<input type="hidden" name="choiceNumber" value="<%=choiceNumber%>">
		<input type="submit" value="Add" id = "red-button"/>
		</form>
		
		<form action="CreateMultipleChoiceMultipleAnswerQuestion.jsp">
		<input type="hidden" name="createAnswerNumber" value="<%=answerNumber+1%>">
		<input type="submit" value="More legal answers" id = "yellow-button"/>
		</form>
		
		<form action="CreateMultipleChoiceMultipleAnswerQuestion.jsp">
		<input type="hidden" name="choiceNumber" value="<%=choiceNumber+1%>">
		<input type="submit" value="More choices" id = "yellow-button"/>
		</form>
		
		<form action="CreateQuiz.jsp">
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