<%@ include file="header.jsp"%>
<div class="vertical-container">
	<h2>
		Results for "<c:out value="${search}" />"
	</h2>
	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${users}" var="user">
			<li
				<c:set var="row" value="${row + 1}" />>
				<a
				href="<c:url value="profile"><c:param name="user" value="${user.username}" /></c:url>">${user.username}</a>
				<br />
				<span>
					<c:out value="${user.surname}" />
				</span>
				<span>
					<c:out value="${user.name}" />
				</span>
				<span>
					Registered on <c:out value="${user.registrationDate}" />
				</span>
			</li>
		</c:forEach>
	</ul>
<%@ include file="footer.jsp"%>