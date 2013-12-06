<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "user.*, java.sql.*, quiz_model.*, database_connection.*, java.util.Date, java.text.SimpleDateFormat, java.util.Calendar" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search by tag</title>
</head>
<%
	
	String userName = (String)request.getSession().getAttribute("sender");
	String tag = request.getParameter("tag");
	tag = tag.trim();
	DBConnection con = (DBConnection) request.getSession().getAttribute("dbcon");
	if (con == null) {
		request.getSession().setAttribute("dbcon", new DBConnection());
		con = (DBConnection) request.getSession().getAttribute("dbcon");
	}
%>
<body>
<%
	String query = "SELECT QuizID, QuizName from QI WHERE QuizTag like '%" + tag + "%';";
	ResultSet rs = con.getStatement().executeQuery(query);
	if(!rs.isBeforeFirst()) out.println("There is no quiz with this Tag!");
	else {
		out.println("<ul>");
		while(rs.next()){
			String id = rs.getString("QuizID");
			String name = rs.getString("QuizName");
			String line = "<li><a href=\"QuizSummary.jsp?quizId=" + id + "&user_name=" + userName + "\">"
			+ name + "</a></li>";
			out.println(line);
		}
		out.println("</ul>");
	}
%>
<form action="CreateAccount_welcome.jsp" method="POST">
<input type="submit" value="Homepage">
</form>

</body>
</html>