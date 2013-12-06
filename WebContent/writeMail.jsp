<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, java.text.SimpleDateFormat, java.sql.*, quiz_model.*, database_connection.*, user.*, java.awt.*, javax.swing.*, java.awt.event.*, java.io.*, javax.swing.border.TitledBorder;" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String sender = request.getParameter("sender");
	String receiver = request.getParameter("receiver");
%>

<title>Composing a mail...</title>
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
	//send a mail
	String formline = "<form method = \"POST\" action = \"sendMailServlet\">";
	String senderline = "<input type = \"hidden\" name = \"sender\" value = \"" + sender + "\">";
	String receiverline = "";
	if (receiver == null)
		receiverline = "<div><input id = \"basic-input-long\" type = \"text\" name = \"receiver\" placeholder = \"Receiver\">";
	else
		receiverline = "<div><input id = \"basic-input\" type = \"text\" name = \"receiver\" value = \"" + receiver + "\">";
	String msgline = "<textarea id = \"big-input\" placeholder = \"Mails to send\" name = \"msg\" rows = \"10\" cols = \"100\" ></textarea></div>";
	String requestButton = "<input id = \"red-button\" type = \"submit\" value = \"Send\" name = \"addbutton\" onclick=\"this.disabled=true;this.form.submit();\">";
	String endForm = "</form>";
	out.println(formline);
	out.println(senderline);
	out.println(receiverline);
	out.println("<br>");
	out.println(msgline);
	out.println(requestButton);
	out.println(endForm);
	%>
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