<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
String Username = request.getParameter("name");


String sender = (String)request.getSession().getAttribute("sender");
if(sender == null || sender.equals("null")) { /* From login page */
	sender = new String(Username);
	request.getSession().setAttribute("sender", sender);
}


if(Username == null || Username.equals("null")) Username = sender;
%>
<title><%=Username %> not found</title>
</head>
<body>
<h1><%=Username %> is not a user in IQuizYou</h1>
</body>
</html>