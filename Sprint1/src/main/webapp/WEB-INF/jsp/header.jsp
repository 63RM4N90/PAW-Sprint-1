<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-responsive.css" />
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-responsive.min.css" />
<link rel="stylesheet" type="text/css" href="css/custom.css" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<span> <c:if test="${isLogged}">
			<span><c:out value="${userSession.username}" /> [<a
				href="logout">Close session</a>]</span>
		</c:if>

		<form method="POST" action="search">
			<span> <span class="search-bar"> <label for="search">Search:</label>
					<input type="text" name="search" />
			</span> <span class="search-button"> <input type="submit"
					name="submit" value="Search" />
			</span>
			</span>
		</form>
	</span>