
<%@ include file="header.jsp"%>
<img src="image?username=<c:out value="${user.username}" />"
	alt="user_picture" />

<span><h2>
		<c:out value="${user.username}" />
	</h2></span>
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
<c:if test="${isOwner}">
	<form method="GET" action="editProfile">
		<button>Edit</button>
	</form>
</c:if>
<c:if test="${isOwner}">
	<div class="commentError">
		<c:out value="${commentError}" />
	</div>
	<form method="POST" action="profile">
		<div class="form-field">
			<label for="comment">Comment:</label> <input type="text"
				name="comment" maxlength="140" />
		</div>
		<div class="form-buttons">
			<input type="submit" name="submit" value="Post" />
		</div>
	</form>
</c:if>
<ul style="padding: 0;">
	<c:set var="row" value="0" />
	<c:forEach items="${comments}" var="comment">
		<li <c:set var="row" value="${row + 1}" />>
			<p>${comment.comment}</p> <a
			href="<c:url value="profile"><c:param name="user" value="${comment.author.username}" /></c:url>">${comment.author.username}</a>
			<br>
			<p>
				<fmt:formatDate value="${comment.date}" pattern="dd-MM-yyyy HH:mm" />
			</p>
		</li>
	</c:forEach>
</ul>

<%@ include file="footer.jsp"%>