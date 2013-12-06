<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="user.*"%>
<%@ page import="java.sql.ResultSet"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String sender = (String)request.getSession().getAttribute("sender");
	AdministratorAccount admin = new AdministratorAccount(sender);
	AccountManager.connect();
	AccountManager.statement.executeQuery("select * from quiz_take_history;");
	ResultSet rs =  AccountManager.statement.executeQuery("SELECT FOUND_ROWS();");
	rs.next();
	int numOfHistoryTaken = rs.getInt("FOUND_ROWS()");
	AccountManager.statement.executeQuery("select * from userTable;");
	rs = AccountManager.statement.executeQuery("SELECT FOUND_ROWS();");
	rs.next();
	int numOfUser = rs.getInt("FOUND_ROWS()");	
	AccountManager.close();
%>

<title>Administrator - <%=sender%></title>
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
		<h2>Delete account</h2>
		<form action="DeleteAccountServlet" method="post">
			<p>
				<input type="text" name="name" id = "basic-input" placeholder="User name">
				<button type="submit"  id="red-button">Delete</button>
			</p>
		</form>
		<h2>Promote account</h2>
		<form action="PromoteAccountServlet" method="post">
			<p>
				<input type="text" name="name" id = "basic-input" placeholder="User name">
				<select name="status" id ="green-button">
						<option value="u"  >Normal User</option>
						<option value="s">Administrator</option>
					</select>
					<button type="submit"  id="green-button">Change</button>
			</p>
		</form>
		<h2>Create announcement</h2>
		<form action="NewAnnounceServlet" method="post">
			<div>
				<textarea rows="10"; cols="40"; name="content" id = "big-input" placeholder="Write new announcement"></textarea>
			</div>
			<button type="submit"  id="red-button">Post</button>
		</form>
		
		<h2>Manage Quiz</h2>
		<form action="ManageQuizServlet" method="post">
			<p>
				<input type="text" name="quiz" id = "basic-input" placeholder="QuizID">
				<select name="operation" id ="yellow-button">
					<option value="1">Clear taken history</option>
					<option value="2">Delete quiz</option>
				</select>
				<button type="submit"  id="yellow-button">Submit</button>

			</p>
		</form>
		
		<h2>Num of users</h2>
		<b><%=numOfUser%></b>
		<h2>Num of quiz taken</h2>
		<b><%=numOfHistoryTaken%></b>
		
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