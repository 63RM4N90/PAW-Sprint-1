<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page isELIgnored="false"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/style.css" />
</head>
<body>
	<div class="top-bar">
		<div class="top-bar-element title">
			<img src="${pageContext.request.contextPath}/img/title.png" alt="Social Cthulhu" />
		</div>
		<div class="top-bar-element search-bar">
			<form method="POST" action="/Sprint2/bin/search/results">
				<input type="text" placeholder="Find Soul..." name="search" /> <input
					type="submit" class="btn-inverse search-button" name="submit" value="Search" />
			</form>
		</div>
		<a href="${pageContext.request.contextPath}/bin/hashtag/top10"><img class="top-hashtags" src="${pageContext.request.contextPath}/img/t10h.png" alt=""/></a>
		<div class="top-bar-element session">
			<div class="home-link">
				<img class="separator"
					src="${pageContext.request.contextPath}/img/topbar_separator.png"
					alt="" /> <a href="${pageContext.request.contextPath}/bin/user/home"><img class="home"
					src="${pageContext.request.contextPath}/img/home.png" alt="home" /></a>
			</div>
			<c:if test="${not empty username}">
				<img class="separator"
					src="${pageContext.request.contextPath}/img/topbar_separator.png"
					alt="" />
				<div class="user-session">
					<p>
						<c:out value="${username}" />
					</p>
					<br> <a href="${pageContext.request.contextPath}/bin/user/logout">Log out</a>
				</div>
			</c:if>
		</div>
	</div>
