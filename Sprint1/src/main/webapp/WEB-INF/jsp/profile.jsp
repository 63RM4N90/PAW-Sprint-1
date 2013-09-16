<%@ include file="header.jsp"%>
<c:if test="${empty user.picture}">
	<img src="img/a.jpg" alt="user_picture"/>
</c:if>
<c:if test="${not empty user.picture}">
	<img src="img/<c:out value="${user.picture.path}" />"
		alt="user_picture"/>
</c:if>

<span><h2><c:out value="${user.username}" /></h2></span>
<span><%@ include file="top10.jsp"%></span>

<h5>
	<c:out value="${user.name}" />
</h5>
<h5>
	<c:out value="${user.surname}" />
</h5>
<p>
	<c:out value="${user.description}" />
</p>

<form method="POST" action="profile">
	<div class="form-field">
		<label for="comment">Comment:</label> <input type="text"
			name="comment" />
	</div>
	<div class="form-buttons">
		<input type="submit" name="submit" value="Post" />
	</div>
</form>
<ul style="padding: 0;">
	<c:set var="row" value="0" />
	<c:forEach items="${comments}" var="comment">
		<li <c:set var="row" value="${row + 1}" />>
			<p>${comment.comment}</p> <a
			href="<c:url value="profile"><c:param name="user" value="${comment.author.username}" /></c:url>">${comment.author.username}</a>
			<br />
			<span>
				<c:out value="${comment.date}" />
			</span>
		</li>
	</c:forEach>
</ul>

<%@ include file="footer.jsp"%>