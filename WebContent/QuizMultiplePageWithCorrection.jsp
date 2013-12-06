<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="database_connection.*,quiz_model.*,quiz_web.*,java.sql.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String sender = (String)request.getSession().getAttribute("sender");
	Quiz quiz = (Quiz) request.getSession().getAttribute("Quiz");
	List<Question> questions = quiz.getQuestions();
	String questionNumStr = request.getParameter("question");
	int questionNum;
	if (questionNumStr == null) questionNum = 0;
	else questionNum = Integer.parseInt(questionNumStr) + 1;	
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz - <%=quiz.getName()%></title>
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
		<h1><%=quiz.getName() %></h1>
		<form action="QuizMultiplePageWithCorrectionAfterSubmission.jsp" method="POST">
		<%	 
			//out.print("<form action=\"QuizMultiplePageServlet\" method=\"post\">");
			Question question = questions.get(questionNum);
			out.print(question.getHTMLwithQuestion(questionNum));
			String input = "<input type=\"hidden\" name=\"question\" value=\""+ Integer.toString(questionNum)+"\">";
			out.print(input);
			//System.out.println(input);
			/*if (questionNum == 0) {
				out.print("<input type=\"submit\" name=\"action\" value=\"Next\">");
			} else if (questionNum == questions.size()-1) {
				out.print("<input type=\"submit\" name=\"action\" value=\"Back\">");
				out.print("<input type=\"submit\" value=\"Submit\">");
			} else {
				out.print("<input type=\"submit\" name=\"action\" value=\"Back\">");
				out.print("<input type=\"submit\" name=\"action\" value=\"Next\">");
			}*/
			String fakeButton;
			if (questionNum == questions.size() -1 ) {
				fakeButton = "See result";
			} else fakeButton = "Next";
		%>
		<input type="submit" value="Submit" id = "red-button">
		<input type="submit" value="<%=fakeButton%>" disabled id = "gray-button" >
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