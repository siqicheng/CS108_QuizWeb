<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String webName = "IQuizYOU";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to <%=webName%></title>
<link rel="shortcut icon" href="pic/favicon.ico" /> 
<link rel="stylesheet" href="CSS/login_style.css" type="text/css">

</head>
<script type="text/javascript" src="checkcookies.js"></script>
<script type="text/javascript">
        window.onload = checkCookie();
</script>
<body>
	<div id="title-bar">
  		<div class="wrapper">
  			<div class="login">
    			<form id="lzform" name="lzform" method="post" action="LoginServlet">
			        <fieldset>
			            <legend>login</legend>
			            
			            <input type="hidden" value="index_nav" name="source">
			            
			            <div class="item item-account">
				            <input type="text" name="name" id="form_email" value="" class="inp" placeholder="username" tabindex="1">
				            <div class="opt">
				            	<label for="form_remember">
				            		<input name="remember" type="checkbox" id="form_remember" tabindex="4">
				            		remember me
				            	</label>
				            </div>
				        </div>
				        
				        <div class="item item-passwd">
				                <input type="password" name="password" id="form_password" class="inp" placeholder="password" tabindex="2">
				        </div>

			            <div class="item-submit">
			                <input type="submit" value="Login" class="login-submit" tabindex="4">
			                <input type="submit" value="Guest" class="guest-submit" tabindex="4" onclick="form.action='GuestIndex.jsp';">
			            </div>
			        </fieldset>
    			</form>
			</div>
  
		    <div class="reg">    
		        <a href="CreateAccount_new_account.jsp" class="register-buttom"> Register</a>
		        <div class="ad-info">
		        	<b>More challenge, more knowledge</b>
		        </div>
		    </div>
		    
	  	</div>  	
	</div>

	
	
	<div class="wrapper">
		<img src="pic/IQuizYOU.gif" width="950" height="475">
		<div id="ft">
			<span class="fleft">
			  <a> Copyright � 2013 <%=webName%> , all rights reserved</a><br>
			  <a> Quick Links</a><br> 
			  <a href="http://www.stanford.edu/">Stanford University</a><br>
			  <a href="http://www.stanford.edu/class/cs108/">CS 108 -- Object Oriented System Design</a><br>
			</span>
		
			<span class="fright">
			    <a>About us</a>
			    � <a href="http://www.linkedin.com/pub/siqi-cheng/64/696/250">Siqi</a>
			    � <a href="http://www.linkedin.com/pub/wenxiao-du/58/ab8/778">Wenxiao</a>
			    � <a href="http://www.linkedin.com/pub/haoran-li/52/46b/a0">Haoran</a>
			    � <a href="http://www.linkedin.com/pub/hao-zhang/36/b80/a35">Hao</a>
			</span>
  		</div>
	</div>
	
</body>
</html>