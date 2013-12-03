<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*,java.text.SimpleDateFormat,java.sql.*,quiz_model.*,database_connection.*,user.*,java.awt.*,javax.swing.*,java.awt.event.*,java.io.*,javax.swing.border.TitledBorder;" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String sender = (String)request.getSession().getAttribute("sender");
	if (sender == null)
		sender = request.getParameter("sender");
		
%>
<title><%=sender%>'s Mailbox</title>
</head>
<body>

	<form action = "writeMail.jsp" method = "post">
	<% out.println("<input type = \"hidden\" name = \"sender\" value =" + "\""+sender + "\"/>");%>
	<input type = "submit" value = "Compose"/>
	</form>

	<h1>Received Emails: </h1>

	<table border="1" class = "fixed">
	    <col width="100px" />
    	<col width="500px" />
    	<col width="200px" />
	<%
		ArrayList<Message> messages = MailManager.getMessages(sender);
		for (Message msg : messages){
			out.println("<tr>");
			
			out.println("<td>" + msg.getsender() + "</td>");
			String message = msg.getMessage().length() < 100 ? msg.getMessage() : msg.getMessage().substring(0,100);
			//String form = "<input type = \"submit\" name = \"msg\" value = \"" + message + "\">";
			String link = "<a href=\"readMail.jsp?sender=" + msg.getsender() + "&receiver=" + msg.getreceiver() + "&message=" + msg.getMessage() + "&date=" + msg.getSentDate()  + "\">" + message;
			out.println("<td>" + link + "</td>");
			out.println("<td>" + msg.getSentDate() + "</td>");
			
			out.println("</tr>");
		}
	
	%>
	</table>


	<form action = "CreateAccount_welcome.jsp" method = "post">
	<%out.println("<input type = \"hidden\" name = \"sender\" value =" + "\"" + sender + "\"/>");%>
	<%out.println("<input type = \"hidden\" name = \"name\" value =" + "\"" + sender + "\"/>");%>
	<input type = "submit" value = "My Homepage"/>
	</form>

</body>
</html>