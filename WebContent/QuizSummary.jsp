<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "user.*, java.sql.*, quiz_model.*, database_connection.*, java.util.Date,java.util.List,java.text.SimpleDateFormat, java.util.Calendar, java.text.DecimalFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	int quizId = Integer.parseInt(request.getParameter("quizId"));
	DBConnection con = (DBConnection) request.getSession().getAttribute("dbcon");
	if (con == null) {
		request.getSession().setAttribute("dbcon", new DBConnection());
		con = (DBConnection) request.getSession().getAttribute("dbcon");
		//System.out.println("hello");
	}
	Quiz quiz = new Quiz(quizId, con);
	request.getSession().setAttribute("Quiz", quiz);
	
	
	
	//set challenge read state
	if (request.getParameter("challenge")!= null){
		String receiver = request.getParameter("user_name");
		String date = request.getParameter("date");
		MailManager.setReadState(receiver,date);
	}
	
	
%>
<!--<%
	String id = request.getParameter("quizId");
	//System.out.println(id);
	DBConnection dbcon = (DBConnection)request.getSession().getAttribute("dbcon");
	if(dbcon == null){
		dbcon = new DBConnection();
		request.getSession().setAttribute("dbcon", dbcon);
	}
	
	Statement stmt = (Statement)request.getSession().getAttribute("db_connection");
	if(stmt == null){
		stmt = dbcon.getStatement();
		request.getSession().setAttribute("db_connection", stmt);
	}
	
	String name = dbcon.getQuizName(id);
	String userName = request.getParameter("user_name");
	String sender = (String)request.getSession().getAttribute("sender");
	//System.out.println("USER NAME: " + userName);
%>
-->
<title><%=quiz.getName()%></title>
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
						<a href="<%=index %>" id="item-text">Home</a>
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
							String friendQ = new String();
							if (!sender.equals(""))
								out.println("<a href=\"friendlist.jsp?sender="+sender + "id=\"item-text\">Friends</a>");
								
						%>
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
		<h2><%=quiz.getName()%></h2>
		<h4>
			<% 
				int freq = dbcon.getQuizTimes(id);
				if (freq == 0) out.println("The quiz has never been taken yet. You can be the first taker!");
				else {
					int score = dbcon.getAverageScore(id);
					int time = dbcon.getAverageTime(id);
					String t_str = String.format("%d:%02d:%02d", time/3600, (time%3600)/60, (time%60));
					String output = "The quiz have been taken for "+ Integer.toString(freq) + " times, with average score of " + Integer.toString(score) + ", and the average time of " + t_str + ".";
					out.println(output);
				}
			%>
		</h4>
		<h4>Creator: <a href="CreateAccount_welcome.jsp?name=<%=quiz.getCreator()%>"><%=quiz.getCreator()%></a></h4>
		<h4>Category: <a href="CategorySearchResult.jsp?category=<%=quiz.getCategory()%>"><%=quiz.getCategory()%></a></h4>
		<h4>Tags: <%
			List<String> tags = quiz.getTag();
			boolean first = true;
			for(String tag:tags){
				if(!first) out.print(",");
				out.print("<a href=\"TagSearchResult.jsp?tag="+ tag + "\">" + tag + "</a>");
				first = false;
			}	
			%>
		</h4>
		
		<h4>Average rate: <% 
		double mean =dbcon.averageRate(id);
		if(mean < 0) out.print("no rate yet");
		else out.print((new DecimalFormat("#.##").format(mean))+"/5"); 
		%>
		</h4>
		
		
		<h2>Your historical performance</h2>
		<%	
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String preKey = request.getParameter("pre_key");
			String key = request.getParameter("key");
			if(key == null) key = "End_Time";
			
			String preOrder = request.getParameter("pre_order");
			String order;
			if(preOrder == null || preKey == null) order = "DESC";
			else {
				if(preKey.equals(key)) { /* Toggle the order */
					if(preOrder.equals("DESC")) order = "ASC";
					else order = "DESC";
				} else order = "DESC";
			}
			String sql = "SELECT End_Time, Score, TIMESTAMPDIFF(SECOND, Start_Time, End_Time) as Length FROM quiz_take_history WHERE Quiz_Id ="+ id +" AND User_Name = '" + userName + "' ORDER BY " + key + " " + order;
			Statement s = dbcon.getStatement();
			//System.out.println(sql);
			ResultSet r = s.executeQuery(sql);
			if(!r.isBeforeFirst()) out.println("<h4>You haven't taken this quiz before.</h4>");
			else {
				out.println("<table border=\"1\" class = \"fixed\"><col width=\"180px\" /><col width=\"80px\" /><col width=\"120px\" /><tr>");
				out.println("<td><a href=\"QuizSummary.jsp?user_name=" + userName + "&quizId=" + id + "&pre_order=" + order + "&pre_key=" + key + "&key=End_Time" + "\">When</a></td></td>");
				out.println("<td><a href=\"QuizSummary.jsp?user_name=" + userName + "&quizId=" + id + "&pre_order=" + order + "&pre_key=" + key + "&key=Score" + "\">Score</a></td></td>");
				out.println("<td><a href=\"QuizSummary.jsp?user_name=" + userName + "&quizId=" + id + "&pre_order=" + order + "&pre_key=" + key + "&key=Length" + "\">Time</a></td></td>");
				out.println("</tr>");
				int counter = 0;
				while(r.next() && counter < 10){
					//String u_name = r.getString("User_Name");
					int score = r.getInt("Score");
					int time = r.getInt("Length");
					Date end = r.getTimestamp("End_Time");
					String end_str = format.format(end);
					
					String t_str = String.format("%d:%02d:%02d", time/3600, (time%3600)/60, (time%60));
					out.println("<tr>");
					//out.println("<td><a href=\"CreateAccount_welcome.jsp?name=" + u_name +"\">" + u_name + "</a></td>");
					out.println("<td>" + end_str + "</td>");
					out.println("<td>" + Integer.toString(score) + "</td>");
					out.println("<td>" + t_str + "</td>");
					out.println("</tr>");
					counter++;
				}
				out.println("</table>");
			}
		%>
		
		<h2>Top performers of all time</h2>
		<%
			Statement statement = dbcon.getStatement();
			String query = "SELECT User_Name, Score, TIMESTAMPDIFF(SECOND, Start_Time, End_Time) as Length from quiz_take_history WHERE Quiz_Id = "+ id +" ORDER BY Score DESC, TIMESTAMPDIFF(SECOND, Start_Time, End_Time);";
			ResultSet rs = statement.executeQuery(query);
			if(!rs.isBeforeFirst()) out.println("<h4>No history</h4>");
			else {
				out.println("<table border=\"1\" class = \"fixed\"><col width=\"180px\" /><col width=\"80px\" /><col width=\"120px\" /><tr><td>Name</td><td>Score</td><td>Time</td></tr>");
				int counter = 0;
				while(rs.next() && counter < 10){
					String u_name = rs.getString("User_Name");
					int score = rs.getInt("Score");
					int time = rs.getInt("Length");
					String t_str = String.format("%d:%02d:%02d", time/3600, (time%3600)/60, (time%60));
					out.println("<tr>");
					out.println("<td><a href=\"CreateAccount_welcome.jsp?name=" + u_name + "&sender=" + userName + "\">" + u_name + "</a></td>");
					out.println("<td>" + Integer.toString(score) + "</td>");
					out.println("<td>" + t_str + "</td>");
					out.println("</tr>");
					counter++;
				}
				out.println("</table>");
			}
		%>
		<h2>Top performers of last day</h2>
		<%
			Date now = new Date();
		 	
		 	String now_str = format.format(now);
		 	
		 	Calendar cal = Calendar.getInstance();
		 	cal.setTime(now);
		 	cal.add(Calendar.DATE, -1);
		 	Date oneDayBefore = cal.getTime();
		 	String lastDay = format.format(oneDayBefore);
		 	
		 	query = "SELECT User_Name, Score, TIMESTAMPDIFF(SECOND, Start_Time, End_Time) as Length from quiz_take_history WHERE Quiz_Id = " + id + " and END_TIME > \""+ lastDay + "\" ORDER BY Score DESC, TIMESTAMPDIFF(SECOND, Start_Time, End_Time);";
		 	statement = dbcon.getStatement();
		 	rs = statement.executeQuery(query);
			if(!rs.isBeforeFirst()) out.println("<h4>This quiz hasn't been taken within last 24 hours.</h4>");
			else {
				out.println("<table border=\"1\" class = \"fixed\"><col width=\"180px\" /><col width=\"80px\" /><col width=\"120px\" /><tr><td>Name</td><td>Score</td><td>Time</td></tr>");
				int counter = 0;
				while(rs.next() && counter < 10){
					String u_name = rs.getString("User_Name");
					int score = rs.getInt("Score");
					int time = rs.getInt("Length");
					String t_str = String.format("%d:%02d:%02d", time/3600, (time%3600)/60, (time%60));
					out.println("<tr>");
					out.println("<td><a href=\"CreateAccount_welcome.jsp?name=" + "&sender=" + userName +"\">" + u_name + "</a></td>");
					out.println("<td>" + Integer.toString(score) + "</td>");
					out.println("<td>" + t_str + "</td>");
					out.println("</tr>");
					counter++;
				}
				out.println("</table>");
			}
		%>
		<h2>People just took this quiz</h2>
		<%
			query = "SELECT User_Name, Score, End_Time, TIMESTAMPDIFF(SECOND, Start_Time, End_Time) as Length from quiz_take_history WHERE Quiz_Id = " + id + " ORDER BY End_Time DESC;";
			statement = dbcon.getStatement();
			rs = statement.executeQuery(query);
			if(!rs.isBeforeFirst()) out.println("<h4>No one has taken this quiz yet.</h4>");
			else {
				out.println("<table border=\"1\" class = \"fixed\">");
				out.println("<col width=\"180px\" /><col width=\"80px\" /><col width=\"120px\" /><col width=\"150px\" /><tr><td>Name</td><td>Score</td><td>Time</td><td>When</td></tr>");
				int counter = 0;
				while(rs.next() && counter < 10){
					String u_name = rs.getString("User_Name");
					int score = rs.getInt("Score");
					int time = rs.getInt("Length");
					String t_str = String.format("%d:%02d:%02d", time/3600, (time%3600)/60, (time%60));
					Date end = rs.getTimestamp("End_Time");
					String end_str = format.format(end);
		
					out.println("<tr>");
					out.println("<td><a href=\"CreateAccount_welcome.jsp?name=" + "&sender=" + userName +"\">" + u_name + "</a></td>");
					out.println("<td>" + Integer.toString(score) + "</td>");
					out.println("<td>" + t_str + "</td>");
					out.println("<td>" + end_str + "</td>");
					out.println("</tr>");
					counter++;
				}
				out.println("</table>");
			}
		
		
		%>
		<h2>Reviews</h2>
		<%
			List<String> reviews = dbcon.getReviews(id);
			if(reviews.size() == 0) out.println("No review yet.");
			for(int i = 0; i < reviews.size() && i < 10; ++i) out.println(reviews.get(i));
		%>
		
		<h2>Let's get started!</h2>
		<div>
		<%
			if(!sender.equals("")){
				out.println("<form action=\"QuizSummaryPageServlet\" method=\"post\">");
				out.println("input type=\"submit\" value=\"GoRockQuiz\" id = \"red-button\" >");
				out.println("</form>");
			}else{
				out.println("<h3>Register and rock the quiz</h3>");
			}
		%>
		
<!--		<form action="QuizSummaryPageServlet" method="post">-->
<!--			<input type="submit" value="GoRockQuiz" id = "red-button" >-->
<!--		</form>-->
		
		<form action="sendMailServlet" method="post" id = "email">
			<%
			if (!sender.equals(""))
				out.println("<input id = \"basic-input\" type=\"text\" name=\"receiver\" placeholder=\"Share quiz with your friend\">");
			%>
			<%String address = new String("<a href=\\\"QuizSummary.jsp?user_name=#######&quizId=" + id  + "\\\">Take this challenge</a>");  %>
			<input type="hidden" name= "sender" value="<%=userName%>">
			<input type="hidden" name="quizId" value="<%=quizId%>">
		</form>
		</div>
		<%
			if (!sender.equals(""))
				out.println("<input type=\"submit\" value=\"Share\" id = \"green-button\" form =\"email\" >");
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