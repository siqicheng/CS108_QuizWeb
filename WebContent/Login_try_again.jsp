<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login IQuizYOU</title>
<link rel="stylesheet" href="CSS/login_try_again.css" type="text/css">
<!-- COLLECTED CSS -->
</head>
<body>


<div class="wrapper">
	<div id="header">
    	<a>Login IQuizYou</a>
  	</div>

	<div id="content">
	  	<h1>Please provide valid input	</h1>
	  	<div class="article">
      

		<form id="lzform" name="lzform" method="post" action="LoginServlet">

		    <div class="item">
		        <label>Username</label>
		        <input id="email" name="name" type="text" class="basic-input" maxlength="60" tabindex="1">
		    </div>
		    <div class="item">
		        <label>Password</label>
		        <input id="password" name="password" type="password" class="basic-input" maxlength="20" tabindex="2">
		    </div>
		    <div class="item">
		        <label>&nbsp;</label>
		        <p class="remember">
		            <input type="checkbox" id="remember" name="remember" tabindex="4">
		            <label for="remember" class="remember">remember me</label>
		        </p>
		    </div>
		    <div class="item">
		    <label>&nbsp;</label>
	        <input type="submit" value="Login" name="login" class="btn-submit" tabindex="5"><br><br><br>
	        <a>Don't have an IQuizYou ID?</a><br><br>
	        <label>&nbsp;</label>
	        <a rel="nofollow" href="CreateAccount_new_account.jsp" class = "lnk-reg" >Register Now</a>
		    </div>
		</form>
		</div>
		
		
		<ul id="side-nav" class="aside">
		
			<li><img src="pic/Jokes2.jpg" width="480" height="540"></li>
		
  		</ul>
	</div>
	
	
	<div class="wrapper">
		<div id="ft">
			<span class="fleft">
				<a> Copyright © 2013 IQuizYOU , all rights reserved</a><br>
				<a> Quick Links</a><br> 
				<a href="http://www.stanford.edu/">Stanford University</a><br>
				<a href="http://www.stanford.edu/class/cs108/">CS 108 -- Object Oriented System Design</a><br>
			</span>
		
			<span class="fright">
			    <a>About us</a>
			    · <a href="http://www.linkedin.com/pub/siqi-cheng/64/696/250">Siqi</a>
			    · <a href="http://www.linkedin.com/pub/wenxiao-du/58/ab8/778">Wenxiao</a>
			    · <a href="http://www.linkedin.com/pub/haoran-li/52/46b/a0">Haoran</a>
			    · <a href="http://www.linkedin.com/pub/hao-zhang/36/b80/a35">Hao</a>
			</span>
  		</div>
	</div>
	
	
</div>


</body>
</html>