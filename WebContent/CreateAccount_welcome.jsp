<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import = "java.util.*, java.text.SimpleDateFormat, java.sql.*, quiz_model.*, database_connection.*, user.*, java.awt.*, javax.swing.*, java.awt.event.*, java.io.*, javax.swing.border.TitledBorder;" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	//TODO 
	// check if username is in db
	String Username = request.getParameter("name");
	if (Username == null)
		Username = (String)request.getSession().getAttribute("name");
	if (Username.length() < 1)
		Username = "Guest";
	String sender = request.getParameter("sender");

	if (sender == null || sender.isEmpty())
		sender = (String)request.getSession().getAttribute("sender");
	if (sender == null || sender.isEmpty())	
		sender = Username;
	//System.out.println("Sender: "+ sender);

%>



<title><%=Username%></title>
</head>
<body>

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
	<h2>Recent Activities</h2>
		<ul>
			<%
				String query = "SELECT QuizID, QuizName, Score, End_Time FROM QI, quiz_take_history WHERE QI.QuizID = quiz_take_history.Quiz_Id AND User_Name = \"" + Username + "\" order by Start_Time DESC;";
				rs = stmt.executeQuery(query);
				int counter = 0;
				while (rs.next() && counter < 15) {
					String name = rs.getString("QuizName");
					String id = Integer.toString(rs.getInt("QuizID"));
					String score = Integer.toString(rs.getInt("Score"));
					String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rs.getTimestamp("End_Time"));
					String line = "<li>Finished quiz <a href=\"QuizSummary.jsp?quizId=" + id + "&user_name=" + sender + "\">"
							+ name + "</a> at "+ endTime + " and earned " + score +  " points</li>";
					out.println(line);
					++counter;
				}
			%>
		</ul>
		<%
			query = "SELECT QuizID, QuizName FROM QI WHERE CreaterId = '"+ Username + "' ORDER BY CreateTime DESC;";
			rs = stmt.executeQuery(query);
			if (rs.isBeforeFirst()){ /* rs not empty */
				out.println("<h2>Created Quiz</h2>");
				out.println("<ul>");
				int counter_2 = 0;
				while (rs.next() && counter < 15) {
					String name = rs.getString("QuizName");
					String id = Integer.toString(rs.getInt("QuizID"));
					String line = "<li><a href=\"QuizSummary.jsp?quizId=" + id + "&user_name=" + sender + "\">"
					+ name + "</a></li>";
					out.println(line);
					++counter_2;
				}
				out.println("</ul>");
			}
		%>
	<h2>Achievements</h2>
	<h2>Messages</h2>
	<h2>Friends Activities</h2>

	<form action = "CreateAccount_welcome.jsp" method = "post">
	<input type = "text" name = "name" placeholder = "Search for friends"/>
	<% //<input type = "hidden" name = "sender" value = Username/> %>
	<% out.println("<input type = \"hidden\" name = \"sender\" value =" + "\""+sender + "\"/>"); %>
	<input type = "submit" value = "Search"/>
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
	
	// you have friend request button
	if (Username.equals(sender)){
		ArrayList<Friend_Request> friendRequests = new ArrayList<Friend_Request>();
		friendRequests = FriendManager.getFriendRequests(sender);
		if (friendRequests.size()!=0){
			// sender has friend requests
			String formline = "<form method = \"POST\" action = \"friendRequest.jsp\">";
			String userline = "<input type = \"hidden\" name = \"sender\" value = \"" + sender + "\">";
			//String receiverline = "<input type = \"hidden\" name = \"receiver\" value = \"" + Username + "\">";
			String requestsButton = "<input type = \"submit\" value = \"Friend Requests\" onclick=\"this.disabled=true;this.form.submit();\">";
			String endForm = "</form>";
			out.println(formline);
			out.println(userline);
			//out.println(receiverline);
			out.println(requestsButton);
			out.println(endForm); 
		}
	}
	
%>

	<form action = "mailSystem.jsp" method = "post">
	<% out.println("<input type = \"hidden\" name = \"sender\" value =" + "\""+sender + "\"/>");%>
	<input type = "submit" value = "Mailbox"/>
	</form>
<% 
	if (Username.equals(sender)){
		request.getSession().setAttribute("QuizCreator",sender);
		out.println("<h2>Create Quiz</h2>");
		out.println("<form action = \"CreateQuiz.jsp\" method = \"post\">");
		//out.println("<input type = \"hidden\" name = \"sender\" value =" + "\""+sender + "\"/>");
		out.println("<input type = \"submit\" value = \"Create Quiz\"/>");
		out.println("</form>");
	}
%>

<script>
function rmfriend(name1, name2){
	FriendManager.rmFriendship(name1, name2);
	alert("Welcome ");
}

</script>

</body>
</html>
