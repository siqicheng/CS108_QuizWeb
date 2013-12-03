<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*,java.text.SimpleDateFormat,java.sql.*,quiz_model.*,database_connection.*,user.*,java.awt.*,javax.swing.*,java.awt.event.*,java.io.*,javax.swing.border.TitledBorder;" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	String friend = request.getParameter("sender");
%>


<title><%=friend%>'s Friend Requests</title>
</head>
<body>
	<h1><%=friend%>, someone wants to make friends with you!</h1>
	
	
	<%
				ArrayList<Friend_Request> friendRequests = FriendManager.getFriendRequests(friend);
				int numRequest = friendRequests.size();
				for (Friend_Request friendRequest : friendRequests) {
					String user = friendRequest.getUserName();
					
					out.println("##########################################");
					out.println("<br>");
					out.println(friendRequest.getUserName() + "has requested to be your friend");
					out.println("<br>");
					out.println(friendRequest.getMessage());
					out.println("<br>");
					out.println(friendRequest.getSentDate());
					out.println("<br>");
					//form1 accept request
					String formline1 = "<form method = \"POST\" action = \"friendAcceptServlet\">";
					String senderline1 = "<input type = \"hidden\" name = \"user\" value = \"" + user + "\">";
					String receiverline1 = "<input type = \"hidden\" name = \"friend\" value = \""+ friend + "\">";
					String confirmButton = "<input type = \"submit\" value = \"Confirm\" onclick=\"this.disabled=true;this.form.submit();\">";
					String endForm1 = "</form>";
					out.println(formline1);
					out.println(senderline1);
					out.println(receiverline1);
					out.println(confirmButton);
					out.println(endForm1);

					//form2 delete request
					String formline2 = "<form method = \"POST\" action = \"friendRejectServlet\">";
					String senderline2 = "<input type = \"hidden\" name = \"user\" value = \"" + user + "\">";
					String receiverline2 = "<input type = \"hidden\" name = \"friend\" value = \""+ friend + "\">";
					String rejectButton = "<input type = \"submit\" value = \"Not Now\" onclick=\"this.disabled=true;this.form.submit();\">";
					String endForm2 = "</form>";
					out.println(formline2);
					out.println(senderline2);
					out.println(receiverline2);
					out.println(rejectButton);
					out.println(endForm2);
				}
	%>
	
	
	
	
	
	
	
	<form action = "CreateAccount_welcome.jsp" method = "post">
	<%
		out.println("<input type = \"hidden\" name = \"sender\" value ="
				+ "\"" + friend + "\"/>");
	%>
	<%
		out.println("<input type = \"hidden\" name = \"name\" value ="
				+ "\"" + friend + "\"/>");
	%>
	<input type = "submit" value = "My Homepage"/>
	</form>


</body>
</html>