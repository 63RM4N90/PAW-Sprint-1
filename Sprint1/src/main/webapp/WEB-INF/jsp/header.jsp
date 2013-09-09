<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="css/style.css" />
	</head>
	<body>
		<div style="overflow: auto;">
			<h1 style="float: left; margin-top: 0;">Register user</h1>
			<c:if test="${not empty user}">
				<span style="float: right;"><c:out value="${user.username}" /> [<a href="logout">Cerrar sesi&oacute;n</a>]</span>
			</c:if>
		</div>