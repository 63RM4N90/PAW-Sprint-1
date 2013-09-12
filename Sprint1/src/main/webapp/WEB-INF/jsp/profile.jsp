<%@ include file="header.jsp" %>
<img src="/images/<c:out value="${user.username}" />.jpg" alt="user_image">
<h2><c:out value="${user.username}" /></h2>
<h5><c:out value="${user.name}" /></h5>
<h5><c:out value="${user.surname}" /></h5>
<p><c:out value="${user.description}" /></p>

<form method="POST" action="profile">
	<div class="form-field">
		<label for="comment">Comment:</label>
		<input type="text" name="comment" />
	</div>
	<div class="form-buttons">
		<input type="submit" name="submit" value="Post" />
	</div>
</form>
<ul style="padding:0;">
	<c:set var="row" value="0" />
	<c:forEach items="${comments}" var="comment">
		<li class="<c:if test="${row % 2 == 0}">odd</c:if><c:if test="${row % 2 != 0}">even</c:if><c:set var="row" value="${row + 1}" />" style="list-style-type: none; padding: 10px;">
			<span style="font-size: 15px;">
				<c:out value="${comment.comment}" />
			</span>
			<a href="">
				<c:out value="${comment.author.username}" />
			</a>
			<br />
		</li>
	</c:forEach>
</ul>



<%@ include file="footer.jsp" %>