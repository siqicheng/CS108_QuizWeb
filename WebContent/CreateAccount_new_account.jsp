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
</head>
<body>
	<h1>Create New Account</h1>

	<p>Please enter username and password.</p><!--
onsubmit="return validateForm(this);"
	--><form action="AccountCreationServlet" method="post" >
	    	User Name: <input type="text" name="name"/> <br/>
	    	Password: <input type="text" name="password"/> <br/>
	    	Confirm Password: <input type="password" name="confirm"> <br/>
	    	Gender:
	    	<input type="radio" name="gender" value="male" checked=""> Male
			<input type="radio" name="gender" value="female"> Female <br/>
			Age:<input type="text" name="age"><br/>
			Email:<input type="text" name="email">
    	<input type="submit" value="login"/>
	</form>
	
	
</body>
</html>
