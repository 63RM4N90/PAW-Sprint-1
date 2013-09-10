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
<div class="notRegistered"><a href="register">Not a member yet? Register!</a></div>

<%@ include file="footer.jsp" %>