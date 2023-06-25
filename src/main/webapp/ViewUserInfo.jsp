<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
  <title>Profile Page</title>
</head>
<body>
  <div class="w3-container w3-card w3-round w3-white w3-section w3-center">
    <h4>My Profile</h4>
    <p><img src="imgs/avatar3.png" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>
    <hr>
    <p class="w3-left-align"> <i class="fa fa-id-card fa-fw w3-margin-right"></i> ${user.name} </p>
    <p class="w3-left-align"> <i class="fa fa-id-badge fa-fw w3-margin-right"></i> ${user.mail} </p>
    <button type="button" class="editUser w3-row w3-button w3-green w3-section"><i class="fa fa-user-plus"></i> &nbsp;Edit</button>

    <!-- añadido para salir-->
    <a class="menu w3-bar-item w3-button" id="LogoutController" href=#> Logout </a>
  </div>
  <br>

  <!-- Mostraremos el tipo de usuario elegido anteriormente -->
  <p id="selectedOption"></p>

  <script>
    // Obtén el valor de la opción seleccionada de la URL
    var urlParams = new URLSearchParams(window.location.search);
    var selectedOption = urlParams.get('option');

    // Actualiza el contenido del elemento con el valor seleccionado
    document.getElementById("selectedOption").textContent = selectedOption;
  </script>
</body>
</html>