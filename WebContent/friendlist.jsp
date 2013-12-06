<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, java.util.List, java.text.SimpleDateFormat, java.sql.*, quiz_model.*, database_connection.*, user.*, java.awt.*, javax.swing.*, java.awt.event.*, java.io.*, javax.swing.border.TitledBorder;" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
String sender = (String)request.getSession().getAttribute("sender");
if(sender == null || sender.equals("null")) { /* From login page */
	request.getSession().setAttribute("sender", sender);
}

%>
<title><%=sender %>'s friends</title>
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
		<h1><%=sender %>'s friends</h1>
			<%
					ArrayList<Friend_Request> friendRequests = new ArrayList<Friend_Request>();
					friendRequests = FriendManager.getFriendRequests(sender);
					if (friendRequests.size()!=0){
						// sender has friend requests
						String formline = "<form method = \"POST\" action = \"friendRequest.jsp\">";
						String userline = "<input type = \"hidden\" name = \"sender\" value = \"" + sender + "\">";
						//String receiverline = "<input type = \"hidden\" name = \"receiver\" value = \"" + Username + "\">";
						String requestsButton = "<input id=\"green-button\" type = \"submit\" value = \"Friend Requests\" onclick=\"this.disabled=true;this.form.submit();\">";
						String endForm = "</form>";
						out.println(formline);
						out.println(userline);
						//out.println(receiverline);
						out.println(requestsButton);
						out.println(endForm); 
					}
					
				HashSet<String> friendlist = FriendManager.getFriends(sender);
				for (String friend : friendlist){
					out.println("<a href=\"CreateAccount_welcome.jsp?name=" + friend + "&sender=" + sender + "\">" + friend + "</a><br>");
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