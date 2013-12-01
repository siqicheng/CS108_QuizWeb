<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.sql.*, quiz_model.*, database_connection.*, java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String Username = request.getParameter("name");
	if (Username.length() < 1)
		Username = "Guest";
%>



<title><%=Username%></title>
</head>
<body>
	<h1><%=Username%></h1>
	<h2>Announcenments</h2>
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
					String line = "<li><a href=\"Quiz-summary.jsp?id=" + id + "\">"
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
					String line = "<li><a href=\"Quiz-summary.jsp?id=" + id + "\">"
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
					String line = "<li>Finished quiz <a href=\"Quiz-summary.jsp?id=" + id + "\">"
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
					String line = "<li><a href=\"Quiz-summary.jsp?id=" + id + "\">"
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
</body>
</html>