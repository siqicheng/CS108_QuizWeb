<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, java.text.SimpleDateFormat, java.sql.*, quiz_model.*, database_connection.*, user.*, java.awt.*, javax.swing.*, java.awt.event.*, java.io.*, javax.swing.border.TitledBorder;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String sender = request.getParameter("sender");
	String receiver = request.getParameter("receiver");
	String msg = request.getParameter("message");
	String date = request.getParameter("date");
%>

<title>Message from <%=sender %></title>
</head>
<body>
<h1>Message from <%=sender %></h1>
<p><%=msg %></p>

	<form action = "writeMail.jsp" method = "post">
	<%out.println("<input type = \"hidden\" name = \"sender\" value =" + "\"" + receiver + "\"/>");%>
	<%out.println("<input type = \"hidden\" name = \"receiver\" value =" + "\"" + sender + "\"/>");%>
	<input type = "submit" value = "Reply"/>
	</form>
	
	<form action = "deleteMailServlet" method = "post">
	<%out.println("<input type = \"hidden\" name = \"sender\" value =" + "\"" + sender + "\"/>");%>
	<%out.println("<input type = \"hidden\" name = \"receiver\" value =" + "\"" + receiver + "\"/>");%>
	<%out.println("<input type = \"hidden\" name = \"date\" value =" + "\"" + date + "\"/>");%>
	<input type = "submit" value = "Delete Message"/>
	</form>
	

	<form action = "mailSystem.jsp" method = "post">
	<%out.println("<input type = \"hidden\" name = \"sender\" value =" + "\"" + sender + "\"/>");%>
	<input type = "submit" value = "Go Back to Mailbox"/>
	</form>

</body>
</html>