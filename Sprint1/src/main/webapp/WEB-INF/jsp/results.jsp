<%@ include file="header.jsp"%>

<h2>
	Results for <c:out value="${search}" />
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
				<c:out value="${user.name}" />
			</span>
			<span>
				<c:out value="${user.surname}" />
			</span>
		</li>
	</c:forEach>
</ul>
<a href="profile">My profile</a>

<%@ include file="footer.jsp"%>