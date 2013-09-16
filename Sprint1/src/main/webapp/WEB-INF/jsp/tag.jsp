<%@ include file="header.jsp"%>

<h2>
	#<c:out value="${tag.hashtag}" />
</h2>
<h5>
	<c:out value="${tag.author.username}" />
</h5>
<p>
	<fmt:formatDate value="${tag.date}" pattern="dd-MM-yyyy HH:mm" />
</p>

<ul style="padding: 0;">
	<c:set var="row" value="0" />
	<c:forEach items="${comments}" var="comment">
		<li
			<c:set var="row" value="${row + 1}" />>
			<p>${comment.comment}</p> <a
			href="<c:url value="profile"><c:param name="user" value="${comment.author.username}" /></c:url>">${comment.author.username}</a>
			<br>
			<p><fmt:formatDate value="${comment.date}" pattern="dd-MM-yyyy HH:mm" /></p>
		</li>
	</c:forEach>
</ul>



<%@ include file="footer.jsp"%>