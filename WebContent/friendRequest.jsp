<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*,java.text.SimpleDateFormat,java.sql.*,quiz_model.*,database_connection.*,user.*,java.awt.*,javax.swing.*,java.awt.event.*,java.io.*,javax.swing.border.TitledBorder;" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String friend = request.getParameter("sender");
	if (friend == null)
		friend = (String)request.getSession().getAttribute("sender");
	String sender = (String)request.getSession().getAttribute("sender");
%>


<title><%=friend%>'s Friend Requests</title>
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
					<form method="post" id="search-form" action="FriendSearchServlet" >
						<input type="text" id="search-text" name="name" placeholder="Search for friends..." />
						<% out.println("<input type = \"hidden\" name = \"sender\" value =" + "\""+sender + "\"/>"); %>
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
		<h2><%=friend%>, someone wants to make friends with you!</h2>
		
		
		<%
					ArrayList<Friend_Request> friendRequests = FriendManager.getFriendRequests(friend);
					int numRequest = friendRequests.size();
					for (Friend_Request friendRequest : friendRequests) {
						String user = friendRequest.getUserName();
	
						out.println("<b>"+friendRequest.getUserName() + " requested :" + "</b><br>");
						out.println("<input id = \"mail-input\" readonly value = \"" + friendRequest.getMessage() +"\">");
						
						//form1 accept request
						String formline1 = "<form method = \"POST\" action = \"friendAcceptServlet\">";
						String senderline1 = "<input type = \"hidden\" name = \"user\" value = \"" + user + "\">";
						String receiverline1 = "<input type = \"hidden\" name = \"friend\" value = \""+ friend + "\">";
						String confirmButton = "<input id = \"red-button\" type = \"submit\" value = \"Confirm\" onclick=\"this.disabled=true;this.form.submit();\">";
						String endForm1 = "</form>";
						out.println(formline1);
						out.println(senderline1);
						out.println(receiverline1);
						out.println(confirmButton);
						out.println(endForm1);
	
						//form2 delete request
						String formline2 = "<form method = \"POST\" action = \"friendRejectServlet\">";
						String senderline2 = "<input type = \"hidden\" name = \"user\" value = \"" + user + "\">";
						String receiverline2 = "<input type = \"hidden\" name = \"friend\" value = \""+ friend + "\">";
						String rejectButton = "<input id = \"green-button\" type = \"submit\" value = \"Not Now\" onclick=\"this.disabled=true;this.form.submit();\">";
						String endForm2 = "</form>";
						out.println(formline2);
						out.println(senderline2);
						out.println(receiverline2);
						out.println(rejectButton);
						out.println(endForm2);
					}
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