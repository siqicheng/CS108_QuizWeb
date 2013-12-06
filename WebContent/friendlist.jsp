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
</head>
<body>
<h1><%=sender %>'s friends</h1>
	<%
		HashSet<String> friendlist = FriendManager.getFriends(sender);
		for (String friend : friendlist){
			out.println("<a href=\"CreateAccount_welcome.jsp?name=" + friend + "&sender=" + sender + "\">" + friend + "</a>");
		}
	%>
</body>
</html>