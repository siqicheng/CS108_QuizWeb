<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">

function validateForm() {

	var error = 0;
	defaultError = document.getElementById('item-error');
	if (defaultError) {
		defaultError.style.display = 'none';
	}
	
	/*	Check user name
	 * 	1.	Not null
	 *	2.	At least 5 digits
	 *	3.	Do not have ", / ' " \ % & [space]"   
	 */
	var account = document.forms[0].name;
	if (account) {
		var accountName = account.value;
		if (accountName == '') {
			displayError(account, 'Please provide a username');
			error ++;
		} else if (accountName.length < 5){
			displayError(account, 'Username should be at least 5 digits');
			error ++;
		} else if (accountName.indexOf(",") != -1 ||accountName.indexOf("/") != -1 ||accountName.indexOf("'") != -1 ||
			accountName.indexOf("\"")!= -1 ||accountName.indexOf("\\")!= -1 ||accountName.indexOf("%") != -1 ||
			accountName.indexOf("&") != -1 ||accountName.indexOf(" ") != -1){
			displayError(account, 'Please input valid username');
			error ++;	
		} else {
		    clearError(account);
		}
	}

	/*	Check password
	 * 	1.	Not null
	 *	2.	At least 5 digits 
	 */
	var passwd = document.forms[0].password;
	if (passwd) {
		if (passwd.value == '') {
		     displayError(passwd, 'Please provide a password');
		     error ++;
		} else if (passwd.value.length < 5){
		     displayError(passwd, 'Password should be at least 5 digits');
		     error ++;
		} else {
		     clearError(passwd);
		}
	}
	/*	Check confirm password
	 * 	1.	Equals password
	 */
	var confirm = document.forms[0].confirm;
	if (confirm) {
		if (confirm.value != passwd.value) {
		     displayError(confirm, 'Password not match');
		     error ++;
		} else {
		     clearError(confirm);
		}
	}
	
	/*	Check age between 3 ~ 150
	 * 	1.	age not null
	 *	2.	age are int
	 *	3.	age make sense.
	 *
	 */
	var age = document.forms[0].age;

	if (age.value == '') {
	     displayError(age, 'Please provide your age');
	     error ++;
	} else if (parseInt(age.value)<3){
		displayError(age, 'Baby user should be at least 3 years old');
		error ++;		
	} else if (parseInt(age.value)>150){
		displayError(age, 'Are you really over 150 years old');
		error ++;
	} else if (isAgeInt(age,error)){
	} else {
		clearError(age);
	}
		
	/*	Check email
	 * 	1.	Not null
	 *	2.	has @
	 *	3.	has .
	 */
	 
	var email = document.forms[0].email;
	if (email){
		var emailAddr = email.value;
		if (emailAddr == "") {
		     displayError(email, "Please provide your email");
		     error ++;
		} else {
			clearError(email);
		}
	}
	var isValid = true;
	if (error > 0) isValid = false;
	
	return isValid;
}

	
function isAgeInt(age,error){
	for (var i=0; i<age.value.length; i++){
	    if (age.value.charAt(i) < "0" || age.value.charAt(i) > "9" ){
	    	displayError(age, 'Age should be integer');
	    	age.focus( );
	    	age.select( );
		    error = true;
		    break;
	    }
	}
}

function displayError(el, msg) {
	var err = document.getElementById(el.name + '_err');
	if (!err) {
		err = document.createElement('span');
		err.id = el.name + '_err';
		err.className = 'error-tip';
		el.parentNode.appendChild(err);
	}
	err.style.display = 'inline';
	err.innerHTML = msg;
}
	
function clearError(el) {
	var err = document.getElementById(el.name + '_err');
	if (err) {
  		err.style.display = 'none';
	}
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
      

		<form id="lzform" name="lzform" method="post" action="AccountCreationServlet" OnSubmit="return validateForm()">
		    
		    <div id="item-error" style="display: none;">
		        <p class="error">password not match!</p>
		    </div>
		    <div class="item">
		        <label>Username</label>
		        <input id="email" name="name" type="text" class="basic-input" placeholder="username" maxlength="60" tabindex="1">
		        <br><span id="name_err" class="error-tip" style="display: none;"></span>
		    </div>
		    <div class="item">
		        <label>Password</label>
		        <input id="password" name="password" type="password" class="basic-input" placeholder="password" maxlength="20" tabindex="2">
		        <br><span id="password_err" class="error-tip" style="display: none;"></span>
		    </div>
		    <div class="item">
		    	<label>Confirm</label>
		    	<input type="password" name="confirm" type="password" class="basic-input" placeholder="confirm password" maxlength="20" tabindex="3">
		    	<br><span id="confirm_err" class="error-tip" style="display: none;"></span>
		    </div>
		    <div class="item">
		    	<label>Email</label>
		    	<input type="text" name="email" class="basic-input" placeholder="email" maxlength="60" tabindex="5">
		    	<br><span id="email_err" class="error-tip" style="display: none;"></span>
		    </div>
		    
		    <div class="item">
		    	<label>Age</label>
		    	<input type="text" name="age" class="basic-input" placeholder="age [3-150]" maxlength="20" tabindex="4">
		    	<br><span id="age_err" class="error-tip" style="display: none;"></span>
		    </div>

		    <div class="item">
			    <label>Gender</label>
		    	<input type="radio" name="gender" value="male" checked="" tabindex="6"> Male
				<input type="radio" name="gender" value="female" tabindex="7"> Female
		    </div>
		    <div class="item">
		    	<label>&nbsp;</label>
	        	<input type="submit" value="Submit" name="submit" class="btn-submit" tabindex="8">
	        	<input type="reset" value="Reset" name="reset" class="gtn-submit" tabindex="9">
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
		
			<li><img src="pic/Jokes1.jpg" width="480" height="540"></li>
		
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
