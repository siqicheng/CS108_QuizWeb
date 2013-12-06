<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="database_connection.*,quiz_model.*,quiz_web.*,java.sql.*,java.util.*, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Result Page</title>
<link rel="shortcut icon" href="pic/favicon.ico" /> 
<link rel="stylesheet" href="CSS/home_style.css" type="text/css">
<!-- COLLECTED CSS -->
</head>
<%
	//int quizId = Integer.parseInt(request.getParameter("quizId"));
	//DBConnection con = (DBConnection) request.getSession()
		//	.getAttribute("dbcon");
	//if (con == null) {
		//request.getSession().setAttribute("dbcon", new DBConnection());
		//con = (DBConnection) request.getSession().getAttribute("dbcon");
		//System.out.println("hello");
	//}
	//Quiz quiz = new Quiz(quizId, con);
	Quiz quiz = (Quiz) request.getSession().getAttribute("Quiz");
	List<Question> questions = quiz.getQuestions();
	String sender = (String)request.getSession().getAttribute("sender");
%>
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
<h2><%=quiz.getName() %></h2>
<table border="1">
	<%
		//out.println("<tr>");
		//out.println("<th> Questions </th>");
		//out.println("<th> Your Answers </th>");
		//out.println("<th> Correct Answers </th>");
		//out.println("</tr>");
		int totScore = 0;
		for (int i = 0; i < questions.size(); i++) {
			Question q = questions.get(i);
			ArrayList<String> ansList = q.fetchAnswer(request, i);
			int curScore = q.getScore(ansList);
			totScore += curScore;
			out.print(q.getHTMLwithQuestionResult(i, ansList, curScore));
		}
		out.println("<b>Score: " + totScore + "<br><br>"); 
		long startTime = (Long) session.getAttribute("startTime");
        long timeElapsed = new java.util.Date().getTime() - startTime;
		out.println("Elapsed time: "+ timeElapsed +"s<br><br></b>"); //need to calculate elapesed time
		
		/* Insert into quiz history table */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start_str = format.format(new Timestamp(startTime));
		String end_str = format.format(new java.util.Date().getTime());
		
		String score = Integer.toString(totScore);
		String id = Integer.toString(quiz.getId());
		String sql = "INSERT INTO quiz_take_history VALUES(\"" + sender + "\"," + id + "," + score + ",\"" + start_str + "\",\"" + end_str + "\");";
		DBConnection con = (DBConnection) request.getSession().getAttribute("dbcon");
		con.getStatement().executeUpdate(sql);
		
		/* Update Achievement table */
		con.updateAchievementTable(score, sender, id);
		
		/*INSERT INTO quiz_take_history VALUES ("shrink_du", 0, 200, "2013-12-01 05:00:07", "2013-12-01 05:30:07");*/
		
	%>
</table>


<form action="QuizFeedBackServlet" method="POST">
<h2>Your feedback: </h2>
<p><b>Rate: </b><br>
<select name="rate" id = "green-button">
	<option value="-1" selected>Rate the quiz</option>
	<option value="1">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
	<option value="4">4</option>
	<option value="5">5</option>
</select>
<br>
<b>Comment:</b><br>
<textarea name="review" rows="3" cols="50" id = "big-input"></textarea>
<input type="hidden" name="quizId" value="<%=Integer.toString(quiz.getId())%>">
<input type="hidden" name="user_name" value="<%=(String)request.getSession().getAttribute("sender")%>">
<input type="submit" value="submit" id = "red-button">
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