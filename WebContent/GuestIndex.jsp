<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import = "java.util.*, java.util.List, java.text.SimpleDateFormat, java.sql.*, quiz_model.*, database_connection.*, user.*, java.awt.*, javax.swing.*, java.awt.event.*, java.io.*, javax.swing.border.TitledBorder;" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	//TODO 
	// check if username is in db

	String Username = request.getParameter("name");

	
	String sender = (String)request.getSession().getAttribute("sender");
	if(sender == null || sender.equals("null")) { /* From login page */
		sender = new String(Username);
		request.getSession().setAttribute("sender", sender);
	}

	
	if(Username == null || Username.equals("null")) Username = sender;
	
	//if(Username == "" && sender == ""){
		//Username = "guest";
		//sender = "guest";
	//}
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
						<a href="GuestIndex.jsp" id="item-text">Home</a>
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
			<h1>Welcome <%=Username%></h1>

			<h2>Announcements</h2>
		
			<h2>Popular Quiz</h2>
			<ul>
					<%
						Statement stmt = (Statement) request.getSession().getAttribute("db_connection");
						if(stmt == null) {
					    	stmt = (new DBConnection()).getStatement();
					    	request.getSession().setAttribute("db_connection", stmt);
						}
					
						ResultSet rs = stmt.executeQuery("SELECT QuizName, Quiz_Id, Number FROM (SELECT Quiz_Id, COUNT(*) as Number FROM quiz_take_history GROUP BY Quiz_Id) AS tmp, QI WHERE tmp.Quiz_Id = QI.QuizID ORDER BY Number DESC;");
						int counter_1 = 0;
						while (rs.next() && counter_1 < 15) {
							String name = rs.getString("QuizName");
							String id = Integer.toString(rs.getInt("Quiz_Id"));
							String line = "<li><a href=\"QuizSummary.jsp?quizId=" + id + "&user_name=" + sender + "\">"
									+ name + "</a></li>";
							out.println(line);
							++counter_1;
						}
					%>
				</ul>
			<h2>Latest Quiz</h2>
				<ul>
					<%
						rs = stmt.executeQuery("SELECT QuizID, QuizName FROM QI order by CreateTime DESC;");
						int counter_0 = 0;
						while (rs.next() && counter_0 < 15) {
							String name = rs.getString("QuizName");
							String id = Integer.toString(rs.getInt("QuizID"));
							String line = "<li><a href=\"QuizSummary.jsp?quizId=" + id + "&user_name=" + sender + "\">"
									+ name + "</a></li>";
							out.println(line);
							++counter_0;
						}
					%>
				</ul>
			

		
				<form action="CategorySearchResult.jsp" method="POST">
				<p>Search by Category
				<select name="category" onchange="this.form.submit()">
				<option value="All" selected>All</option>
				<%
					Category category = new Category();
				
					for(int i = 0; i < category.options.length; ++i){
						out.print("<option value=\"" + category.options[i] + "\">" + category.options[i] + "</option>");
					}
				%>
				</select>
				<br>
			</form>
		
			<form action="TagSearchResult.jsp" method="POST">
				<p>Search by Tag
				<input type="text" name="tag" placeholder = "tags..."/>
				<input type="submit" value="search"/>
				<br>
			</form>
		
			<form action = "FriendSearchServlet" method = "post">
			<input type = "text" name = "name" placeholder = "Search for friends"/>
			<% //<input type = "hidden" name = "sender" value = Username/> %>
			<% out.println("<input type = \"hidden\" name = \"sender\" value =" + "\""+sender + "\"/>"); %>
			<input type = "submit" value = "Search"/>
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
