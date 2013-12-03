<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String sender = request.getParameter("sender");
%>
<title><%=sender%>'s Mailbox</title>
</head>
<body>

<form action = "writeMail.jsp" method = "post">
<% out.println("<input type = \"hidden\" name = \"sender\" value =" + "\""+sender + "\"/>");%>
<input type = "submit" value = "Compose"/>
</form>




<%


%>

</body>
</html>