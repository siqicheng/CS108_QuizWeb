<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import = "java.util.*,java.util.List,java.text.SimpleDateFormat,java.sql.*,quiz_model.*,database_connection.*,user.*,java.awt.*,javax.swing.*,java.awt.event.*,java.io.*,javax.swing.border.TitledBorder;" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	//TODO 
	// check if username is in db

	String Username = request.getParameter("name");

	String sender = (String) request.getSession()
			.getAttribute("sender");
	if (sender == null || sender.equals("null")) { /* From login page */
		sender = new String(Username);
		request.getSession().setAttribute("sender", sender);
	}

	if (Username == null || Username.equals("null"))
		Username = sender;
%>

<%
	String saveCookie = request.getParameter("remember");
%>
	<form id="ini" action = "saveCookieServlet" method = "post">
	<%
		out.println("<input type = \"hidden\" name = \"cookiename\" value ="
				+ "\"" + sender + "\"/>");
	%>
	<%
		out.println("<input type = \"hidden\" name = \"savecookie\" value ="
				+ "\"" + saveCookie + "\"/>");
	%>
	</form>
	
<%

	String onetime2 = (String)request.getAttribute("once");
	boolean jump2 = (onetime2 == null || "null".equals(onetime2));
	if (jump2 && Username.equals(sender)){
		out.println("<script type=\"text/javascript\">");
		out.println("window.onload=function(){document.getElementById('ini').submit();}");
		out.println("</script>");
	}
%>



<title><%=Username%></title>
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
						<%
							String index = new String();
							if (sender.equals(""))
								index = "GuestIndex.jsp";
							else
								index = "CreateAccount_welcome.jsp";
						%>
						<a href="<%=index%>" id="item-text">Home</a>
					</li>
					<li id="items">
						<%
							String createQ = new String();
							if (!sender.equals(""))
								out.println("<a href=\"CreateQuiz.jsp\" id=\"item-text\">CreateQuiz</a>");
						%>
						<!--<a href="CreateQuiz.jsp" id="item-text">CreateQuiz</a>-->
					</li>
					<li id="items">
						<%
							String friendG = new String();
							if (!sender.equals(""))
								out.println("<a href=\"http://www.google.com\" id=\"item-text\">Friends</a>");
						%>
<!--						<a href="http://www.google.com" id="item-text">Friends</a>-->
					</li>
					<li id="items">
						<%
							String mailG = new String();
							if (!sender.equals(""))
								out.println("<a href=\"mailSystem.jsp\" id=\"item-text\">Mailbox</a>");
						%>
<!--						<a href="mailSystem.jsp" id="item-text">Mailbox</a>-->
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
			<h1>Welcome <%=Username%></h1>
	<%

		if(Username.equals(sender) && !sender.equals("")){
			//set and unset privacy
			String privacy = FriendManager.getPrivacy(sender);
			if("true".equals(privacy)){
				// unset privacy
				out.println("<form action = \"privacyServlet\" method = \"post\">");
				out.println("<input type = \"hidden\" name = \"user\" value =" + "\""+sender + "\"/>");
				out.println("<input type = \"hidden\" name = \"privacy\" value =" + "\"false\"/>");

				out.println("<input id=\"red-button\" type = \"submit\" value = \"Public to All\"/>");
				out.println("</form>");
			} else {
				//set privacy
				out.println("<form action = \"privacyServlet\" method = \"post\">");
				out.println("<input type = \"hidden\" name = \"user\" value =" + "\""+sender + "\"/>");
				out.println("<input type = \"hidden\" name = \"privacy\" value =" + "\"true\"/>");
				out.println("<input id=\"yellow-button\" type = \"submit\" value = \"Anonymous to Non Friends\"/>");
				out.println("</form>");
			}
		}
		if (Username.equals(sender)){
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
		}
	%>
			<h2>Announcements</h2>
		
			<h2>Popular Quiz</h2>
			<ul>
					<%
						Statement stmt = (Statement) request.getSession().getAttribute(
								"db_connection");
						if (stmt == null) {
							stmt = (new DBConnection()).getStatement();
							request.getSession().setAttribute("db_connection", stmt);
						}

						ResultSet rs = stmt
								.executeQuery("SELECT QuizName, Quiz_Id, Number FROM (SELECT Quiz_Id, COUNT(*) as Number FROM quiz_take_history GROUP BY Quiz_Id) AS tmp, QI WHERE tmp.Quiz_Id = QI.QuizID ORDER BY Number DESC;");
						int counter_1 = 0;
						while (rs.next() && counter_1 < 15) {
							String name = rs.getString("QuizName");
							String id = Integer.toString(rs.getInt("Quiz_Id"));
							String line = "<li><a href=\"QuizSummary.jsp?quizId=" + id
									+ "&user_name=" + sender + "\">" + name + "</a></li>";
							out.println(line);
							++counter_1;
						}
					%>
				</ul>
			<h2>Latest Quiz</h2>
				<ul>
					<%
						rs = stmt
								.executeQuery("SELECT QuizID, QuizName FROM QI order by CreateTime DESC;");
						int counter_0 = 0;
						while (rs.next() && counter_0 < 15) {
							String name = rs.getString("QuizName");
							String id = Integer.toString(rs.getInt("QuizID"));
							String line = "<li><a href=\"QuizSummary.jsp?quizId=" + id
									+ "&user_name=" + sender + "\">" + name + "</a></li>";
							out.println(line);
							++counter_0;
						}
					%>
				</ul>
			<h2>Recent Activities</h2>
				<ul>
					<%
						String query = "SELECT QuizID, QuizName, Score, End_Time FROM QI, quiz_take_history WHERE QI.QuizID = quiz_take_history.Quiz_Id AND User_Name = \""
								+ Username + "\" order by Start_Time DESC;";
						rs = stmt.executeQuery(query);
						int counter = 0;
						while (rs.next() && counter < 15) {
							String name = rs.getString("QuizName");
							String id = Integer.toString(rs.getInt("QuizID"));
							String score = Integer.toString(rs.getInt("Score"));
							String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm")
									.format(rs.getTimestamp("End_Time"));
							String line = "<li>Finished quiz <a href=\"QuizSummary.jsp?quizId="
									+ id
									+ "&user_name="
									+ sender
									+ "\">"
									+ name
									+ "</a> at "
									+ endTime
									+ " and earned "
									+ score
									+ " points</li>";
							out.println(line);
							++counter;
						}
					%>
				</ul>
				<%
					query = "SELECT QuizID, QuizName FROM QI WHERE CreaterId = '"
							+ Username + "' ORDER BY CreateTime DESC;";
					rs = stmt.executeQuery(query);
					if (rs.isBeforeFirst()) { /* rs not empty */
						out.println("<h2>Created Quiz</h2>");
						out.println("<ul>");
						int counter_2 = 0;
						while (rs.next() && counter < 15) {
							String name = rs.getString("QuizName");
							String id = Integer.toString(rs.getInt("QuizID"));
							String line = "<li><a href=\"QuizSummary.jsp?quizId=" + id
									+ "&user_name=" + sender + "\">" + name
									+ "</a></li>";
							out.println(line);
							++counter_2;
						}
						out.println("</ul>");
					}
				%>
			<h2>Achievements</h2>
			<ul>
			<%
				DBConnection con = (DBConnection) request.getSession()
						.getAttribute("dbcon");
				if (con == null) {
					request.getSession().setAttribute("dbcon", new DBConnection());
					con = (DBConnection) request.getSession().getAttribute("dbcon");
					//System.out.println("hello");
				}
				List<String> achievements = con.getAchievements(sender);
				for (int i = 0; i < achievements.size(); ++i) {
					out.print("<li>" + achievements.get(i) + "</li>");
				}
			%>
			</ul>
			<h2>Messages</h2>
		<%
			if (MailManager.hasNewMessage(sender))
				out.println("You have NEW messages!<br>");
			if (MailManager.hasNewChallenge(sender))
				out.println("You have NEW challenges!<br>");
			if (Username.equals(sender)) {
				ArrayList<Friend_Request> friendRequests = new ArrayList<Friend_Request>();
				friendRequests = FriendManager.getFriendRequests(sender);
				if (friendRequests.size() != 0) {
					out.println("You have NEW friend requests!<br>");
				}
			}
		%>
			<%
				if (!sender.equals(""))
					out.println("<h2>Friends Activities</h2>");
			%>
		
			<form action="CategorySearchResult.jsp" method="POST">
				<p>Search by Category
				<select name="category" id = "yellow-button" onchange="this.form.submit()">
				<option value="All" selected>All</option>
				<%
					Category category = new Category();

					for (int i = 0; i < category.options.length; ++i) {
						out.print("<option value=\"" + category.options[i] + "\">"
								+ category.options[i] + "</option>");
					}
				%>
				</select>
				<br>
			</form>
		
		
		<%
			boolean showButton = false;
			
			if (sender != null && !Username.equals(sender)){
				if(FriendManager.isFriend(Username, sender)){
					//remove a friend
					
					String formline = "<form method = \"POST\" action = \"rmFriendServlet\">";
					String userline = "<input type = \"hidden\" name = \"username\" value = \"" + Username + "\">";
					String friendline = "<input type = \"hidden\" name = \"friendname\" value = \"" + sender + "\">";
					String rmButton = "<input type = \"submit\" value = \"Remove Friend\" name = \"rmbutton\" onclick=\"this.disabled=true;this.form.submit();\">";
					String endForm = "</form>";
					out.println(formline);
					out.println(userline);
					out.println(friendline);
					out.println(rmButton);
					out.println(endForm);
					
					//String rmButton = "<button onclick=\"rmfriend('" + Username + "','" + sender + "');this.disabled=true;\">Remove Friend</button>";
					//out.println(rmButton);
					
				} else if (!FriendManager.isDuplicateFriendRequest(sender, Username)){
					
					//send a friend request
					String formline = "<form method = \"POST\" action = \"friendRequestServlet\">";
					String senderline = "<input type = \"hidden\" name = \"sender\" value = \"" + sender + "\">";
					String receiverline = "<input type = \"hidden\" name = \"receiver\" value = \"" + Username + "\">";
					String msgline = "<textarea name = \"msg\" rows = \"3\" cols = \"25\">Mesages to sent </textarea >";
					String requestButton = "<input type = \"submit\" value = \"Add Friend\" name = \"addbutton\" onclick=\"this.disabled=true;this.form.submit();\">";
					String endForm = "</form>";
					out.println(formline);
					out.println(senderline);
					out.println(receiverline);
					out.println(msgline);
					out.println(requestButton);
					out.println(endForm);
				} else {
					// request sent button
					String sentButton = "<button type=\"button\" disabled>Friend Request Sent</button>";
					out.println(sentButton);
				}
			}
		%>
		
		<%
					if (Username.equals(sender)) {
						request.getSession().setAttribute("QuizCreator", sender);
					}
				%>
		
		<script>
		function rmfriend(name1, name2){
			FriendManager.rmFriendship(name1, name2);
			alert("Welcome ");
		}
		
		</script>
		
		<form action="AdminAvailableServlet" method="post">
			<p>
				<input type="hidden" name="name" value="<%=Username%>">
				<button type="submit" id="red-button">Administrator</button>
			</p>
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
