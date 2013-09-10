<%@ include file="header.jsp" %>

<h2>Welcome <c:out value="${username}" />!</h2>
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
			<a href="<c:url value="comment"><c:param name="id" value="${comment.author}" /></c:url>">
			</a>
			<br />
		</li>
	</c:forEach>
</ul>



<%@ include file="footer.jsp" %>