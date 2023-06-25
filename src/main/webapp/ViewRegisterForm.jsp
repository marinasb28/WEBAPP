<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Form</title>
  <link rel="stylesheet" type="text/css" href="css/ViewRegisterForm.css">
</head>

<!-- AQUÍ AÑADIMOS TODAS LAS VARIABLES QUE PUEDEN CONTENER ERRORES -->
<c:if test = "${user.error['username_wrong_format']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> Given username doesn't follow the right format.</p>
</div>
</c:if>

<c:if test = "${user.error['username_exists']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> Given username already exist on our database.</p>
</div>
</c:if>

<c:if test = "${user.error['name']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> Given name doesn't follow the right format. </p>
</div>
</c:if>

<c:if test = "${user.error['surname']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> Given surname doesn't follow the right format. </p>
</div>
</c:if>

<c:if test = "${user.error['phone_wrong_format']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> Given phone doesn't follow the right format.</p>
</div>
</c:if>

<c:if test = "${user.error['phone_exists']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> Given phone already exist on our database.</p>
</div>
</c:if>

<c:if test = "${user.error['mail_wrong_format']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> Given mail doesn't follow the right format.</p>
</div>
</c:if>

<c:if test = "${user.error['mail_exists']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> Given mail already exist on our database.</p>
</div>
</c:if>

<c:if test = "${user.error['pwd_wrong_format']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> The password doesn't follow the right format.</p>
</div>
</c:if>

<c:if test = "${user.error['pwd2_wrong_format']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> The password doesn't follow the right format.</p>
</div>
</c:if>

<c:if test = "${user.error['pwd2_match']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> The password doesn't match.</p>
</div>
</c:if>

<c:if test = "${user.error['age']}">
<div class="w3-panel w3-theme-l4 w3-display-container">
  <span onclick="this.parentElement.style.display='none'"
  class="w3-button w3-large w3-display-topright">&times;</span>
  <h3> Registration error! </h3>
  <p> User must be 13 years or older.</p>
</div>
</c:if>

<!--faltarían errores para about y gender-->

<body style="background-image: url('imgs/bkgrnd.jpg'); background-size: cover; background-position: center; background-repeat: no-repeat;">
  <div class="required-form" id="required-form">
    <h1>CREATE ACCOUNT</h1>
    
    <!-- <a href="LoginController">Already have an account?</a> !-->

    <!-- id="required" en el P3, aquí es required-form-->
    <form action="RegisterController" id="regform" method="POST">
      <p>
      <label for="username" class="w3-text-theme"> Username:</label><br>
      <input type="text" id="username" name="username" placeholder="Username" value="${user.username}" required autocomplete="username" required pattern="^[a-z0-9._]{5,20}$"><br>


      <label for="name" class="w3-text-theme"> Name:</label><br>
      <input type="text" id="name" name="name" placeholder="Name" value="${user.name}" required pattern="^[\p{L}]{1,20}$"><br>

      <label for="surname" class="w3-text-theme"> Surname:</label><br>
      <input type="text" id="surname" name="surname" placeholder="Surname" value="${user.surname}" required pattern="^[\p{L}]{1,20}$"><br>


      <label for="phone" class="w3-text-theme"> Phone number:</label><br>
      <input type="text" id="phome" name="phone" placeholder="Phone number" value="${user.phone}" required pattern="\d{9}$"><br>

      <label for="mail" class="w3-text-theme"> Email address:</label><br>
      <input type="email" id="mail" name="mail" placeholder="Mail" value="${user.mail}" required autocomplete="email" required pattern="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"><br>

      <label for="datebirth" class="w3-text-theme"> Date of birth:</label><br>
      <input type="date" id="datebirth" name="datebirth" placeholder="DD.MM.YYYY" value="${user.datebirth}" required>

      <label for="pwd" class="w3-text-theme"> Password:</label><br>
      <input type="password" id="pwd" name="pwd" placeholder="Password" value="${user.pwd}" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,12}$"><br>
      
      <label for="pwd2" class="w3-text-theme"> Confirm your password:</label><br>
      <input type="password" id="pwd2" name="pwd2" placeholder="Repeat your password" value="${user.pwd2}" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,12}$"><br><br>

      
      <b>What kind of user will you be? Choose one:</b>
      <div class="select">
        <select name="format" id="format">
          <option selected disabled>Choose an option...</option>
          <option value="Administrator">Administrator</option>
          <option value="Common_User">Common user</option>
          <option value="Content_Contributor">Content contributor</option>
          <option value="BB_Account">Business/Brand account</option>
        </select>
      </div>

      <!-- P3 code: siguiente línea hasta "termns of service" -->
      <div id="terms-of-service-container">
        <input type="checkbox" id="terms-of-service" name="terms-of-service" required>
        I agree to all statements in
        <a href="TermsController">Terms of Service</a>.
      </div>
      

      <div style="display: flex; justify-content: center;">
      <button type="submit" style="width: 200px; height: 50px; background: linear-gradient(135deg, pink, blue); color: white; font-size: 18px; border: none; border-radius: 5px; cursor: pointer; margin-top: 20px; margin-left: auto; margin-right: auto;">Submit</button>
      </div>

      <div style="display: flex; justify-content: center; margin-top: 10px;"></div>
      
    </form>
    </div>

<script>
var regform = document.getElementById("regform");
var username = document.getElementById("username");
var name = document.getElementById("name");
var surname = document.getElementById("surname");
var phone = document.getElementById("phone");
var mail = document.getElementById("mail");
var pwd = document.getElementById("pwd");
var pwd2 = document.getElementById("pwd2");

var checkPasswordValidity = function() {
	 if (pwd2.value !== pwd.value ) {
		pwd2.setCustomValidity("Passwords must match!");
	} else {
		pwd2.setCustomValidity("");
	}
}
pwd2.addEventListener("input", checkPasswordValidity, false);

regform.addEventListener("submit", function (event) {
	checkPasswordValidity();
	if (!this.checkValidity()) {
		this.reportValidity();
		event.preventDefault();
	} 
}, false);

</script>