<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<body>
		<div>
			<c:if test="${isLogged}">
				<span><c:out value="${userSession.username}" /> [<a href="logout">Close session</a>]</span>
			</c:if>
		</div>
		<form method="POST" action="search">
			<div class="search-bar">
				<label for="search">Search:</label>
				<input type="text" name="search" />
			</div>
			<div class="search-button">
				<input type="submit" name="submit" value="Search" />
			</div>
		</form>
