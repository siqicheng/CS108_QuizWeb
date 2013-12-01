<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.sql.*, quiz_model.*, database_connection.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String Username = request.getParameter("name");
	if (Username.length() < 1)
		Username = "Guest";
%>



<title>Welcome <%=Username%></title>
</head>
<body>
	<h1>Welcome <%=Username%></h1>
	<h2>Announcenments</h2>
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
</body>
</html>