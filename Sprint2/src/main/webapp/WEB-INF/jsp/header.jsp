<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<link rel="stylesheet" type="text/css" href="../../styles/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="../../styles/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="../../styles/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css"
	href="../../styles/bootstrap-responsive.min.css" />
<link rel="stylesheet" type="text/css" href="../../styles/custom.css" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="../../styles/style.css" />
	</head>
	<body>
		<div class="top-bar">
			<div class="top-bar-element title">
				<img src="../../img/title.png" alt="Social Cthulhu"/>
			</div>
			<div class="top-bar-element search-bar">
				<form method="POST" action="search">
						<input type="text"  placeholder="Find Soul..." name="search" />
						<input type="submit" class="button" name="submit" value="Search" />
				</form>
			</div>
			<div class="top-bar-element session">
				<div class="home-link">
					<img class="separator" src="../../img/topbar_separator.png" alt=""/>
					<a href="/Sprint2"><img class="home" src="../../img/home.png" alt="home"/></a>
				</div>
				<c:if test="${isLogged}">
					<img class="separator" src="../../img/topbar_separator.png" alt=""/>
					<div class="user-session">
						<p><c:out value="${userSession.username}" /></p>
						<br>
						<a href="logout">Log out</a>
					</div>
				</c:if>
			</div>
		</div>