<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.sql.*, quiz_model.*, database_connection.*, user.*, java.awt.*, javax.swing.*, java.awt.event.*, java.io.*, javax.swing.border.TitledBorder;" %>
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
		sender = (String)request.getSession().getAttribute("sender");
	if (sender == null)
		sender = Username;
%>



<title>Welcome <%=Username%></title>
</head>
<body>
	<h1>Welcome <%=Username%></h1>
	<h2>Announcements</h2>
	<h2>Popular Quiz</h2>
	<h2>Latest Quiz</h2>
		<ul>
			<%
				Statement stmt = (Statement) request.getSession().getAttribute("db_connection");
				if(stmt == null) {
			    	stmt = (new DBConnection()).getStatement();
			    	request.getSession().setAttribute("db_connection", stmt);
				}
			
				ResultSet rs = stmt.executeQuery("SELECT QuizID, QuizName FROM QI order by CreateTime DESC;");
				while (rs.next()) {
					String name = rs.getString("QuizName");
					String id = Integer.toString(rs.getInt("QuizID"));
					String line = "<li><a href=\"Quiz-summary.jsp?id=" + id + "\">"
							+ name + "</a></li>";
					out.println(line);
				}
			%>
		</ul>
	<h2>Recent Activities</h2>
	<h2>Achievements</h2>
	<h2>Messages</h2>
	<h2>Friends Activities</h2>
	
	
	
	
	
	
	
	
	
	<form action = "CreateAccount_welcome.jsp" method = "post">
	<input type = "text" name = "name" value = "Search for friends"/>
	<% //<input type = "hidden" name = "sender" value = Username/> %>
	<% out.println("<input type = \"hidden\" name = \"sender\" value =" + "\""+sender + "\"/>"); %>
	<input type = "submit" value = "Search"/>
	</form>



<%
	boolean showButton = false;
	
	if (sender != null && !Username.equals(sender)){
		if(FriendManager.isFriend(Username, sender)){
			//TODO 
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
			
		} else {
			//TODO
			//send a friend request
			
			
			
			String requestButton = "<button type = \"button\">Send Friend Request</button>";
			out.println(requestButton);
		}
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