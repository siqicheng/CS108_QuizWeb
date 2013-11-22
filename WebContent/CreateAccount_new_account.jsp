<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function validateForm(frm) {

	  account = frm.elements['form_email'],
	  passwd = frm.elements['form_password'],
	  defaultError = document.getElementById('item-error');

	  if (defaultError) {
	    defaultError.style.display = 'none';
	  }


	  if (account) {
	    var account_value = trim(account.value);
	    if (account_value === '') {
	      displayError(account, 'Please provide a username');
	      error = 1;
	    } else if (! (/^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(account_value)
	                  || /^1[358](\d){9}$/.test(account_value))){
	      displayError(account, 'Please input valid username');
	      error = 1;
	    } else {
	      clearError(account);
	    }
	  }

	  if (passwd) {
	    if (passwd.value === '') {
	      displayError(passwd, 'Please provide a password');
	      error = 1;
	    } else {
	      passwd && clearError(passwd);
	    }
	  }
	  return !error;
}

</script>
<title>Create Account</title>
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
      

		<form id="lzform" name="lzform" method="post" action="AccountCreationServlet">

		    <div class="item">
		        <label>Username</label>
		        <input id="email" name="name" type="text" class="basic-input" placeholder="username" maxlength="60" tabindex="1">
		    </div>
		    <div class="item">
		        <label>Password</label>
		        <input id="password" name="password" type="password" class="basic-input" placeholder="password" maxlength="20" tabindex="2">
		    </div>
		    <div class="item">
		    	<label>Confirm</label>
		    	<input type="password" name="confirm" type="password" class="basic-input" placeholder="confirm password" maxlength="20" tabindex="3">
		    </div>
		    <div class="item">
			    <label>Gender</label>
		    	<input type="radio" name="gender" value="male" checked=""> Male
				<input type="radio" name="gender" value="female"> Female
		    </div>
		    <div class="item">
		    	<label>Age</label>
		    	<input type="text" name="age" class="basic-input" placeholder="age [10-150]" maxlength="20" tabindex="4">
		    </div>
		    <div class="item">
		    	<label>Email</label>
		    	<input type="text" name="email" class="basic-input" placeholder="email" maxlength="20" tabindex="5">
		    </div>

		    <div class="item">
		    	<label>&nbsp;</label>
	        	<input type="submit" value="Submit" name="submit" class="btn-submit" tabindex="6">
		    </div>
		    <br><br><br><br>
		    <div class="item">
			    <a>Already have an IQuizYou ID?</a><br><br>
		        <label>&nbsp;</label>
		        <a rel="nofollow" href="Login_try_again.jsp" class = "lnk-reg" >Login</a>
	        </div>
		</form>
		</div>
		
		
		<ul id="side-nav" class="aside">
		
			<li><img src="pic/Jokes1.jpg" width="480" height="480"></li>
		
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
