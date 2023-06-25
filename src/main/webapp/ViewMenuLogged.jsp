<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>

<div class="w3-bar">

	<a class="w3-bar-item w3-button" href="MainController"> <i class="fa fa-home" aria-hidden="true"></i> </a> 
	<a class="menu w3-bar-item w3-button w3-hide-small" id="GetOwnTimeline" href=#> MyPosts </a>
	<a class="menu w3-bar-item w3-button w3-hide-small" id="GetFollowedUsers" href=#> Buddies </a>
	<a class="menu w3-bar-item w3-button w3-hide-small w3-right" id="LogoutController" href=#> <i class="fa fa-sign-out"></i> </a>
	
	<a class="menu w3-bar-item w3-button w3-hide-small" id="LogoutController" href=#>Logout</a>

	<a href="javascript:void(0)" class="w3-bar-item w3-button w3-right w3-hide-large w3-hide-medium" onclick="stack()">&#9776;</a>
</div>

<div id="stack" class="w3-bar-block w3-theme-d2 w3-hide w3-hide-large w3-hide-medium">
	<a class="menu w3-bar-item w3-button" id="GetOwnTimeline" href=#> MyPosts </a>
	<a class="menu w3-bar-item w3-button" id="GetFollowedUsers" href=#> Buddies </a>
	<a class="menu w3-bar-item w3-button" id="LogoutController" href=#> Logout </a>
</div>
