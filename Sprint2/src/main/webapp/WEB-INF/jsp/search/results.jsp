<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<h2>
		Results for "
		<c:out value="${search}" />
		"
	</h2>
	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${users}" var="user">
			<li class="search-result" <c:set var="row" value="${row + 1}" />><a
				href="../user/profile/${user.username}">${user.username}</a> <br />
				<span> <c:out value="${user.name}" /> </span>
				<span> <c:out value="${user.surname}" /> | </span>
				<span> Registered on <fmt:formatDate value="${user.registrationDate}" pattern="dd-MM-yyyy HH:mm" /> </span>
			</li>
		</c:forEach>
	</ul>
</div>
<%@ include file="../footer.jsp"%>