<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, java.text.SimpleDateFormat, java.sql.*, quiz_model.*, database_connection.*, user.*, java.awt.*, javax.swing.*, java.awt.event.*, java.io.*, javax.swing.border.TitledBorder;" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String sender = request.getParameter("sender");
%>

<title>Composing a mail...</title>
</head>
<body>

	<%
	//send a mail
	String formline = "<form method = \"POST\" action = \"sendMailServlet\">";
	String senderline = "<input type = \"hidden\" name = \"sender\" value = \"" + sender + "\">";
	String receiverline = "<input type = \"text\" name = \"receiver\" placeholder = \"Receiver\">";
	String msgline = "<textarea name = \"msg\" rows = \"10\" cols = \"300\">Mails to sent </textarea >";
	String requestButton = "<input type = \"submit\" value = \"Send\" name = \"addbutton\" onclick=\"this.disabled=true;this.form.submit();\">";
	String endForm = "</form>";
	out.println(formline);
	out.println(senderline);
	out.println(receiverline);
	out.println(msgline);
	out.println(requestButton);
	out.println(endForm);
	%>


	<form action = "mailSystem.jsp" method = "post">
	<%out.println("<input type = \"hidden\" name = \"sender\" value =" + "\"" + sender + "\"/>");%>
	<input type = "submit" value = "Go Back to Mailbox"/>
	</form>
	
</body>
</html>