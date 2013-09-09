<%@ include file="header.jsp" %>

<h2>Login</h2>
<div class="error"><c:out value="${error}" /></div>
<form method="POST" action="login">
	<div class="form-field">
		<label for="username">Usuario:</label>
		<input type="text" name="username" value="<c:out value="${username}" />" />
	</div>
	<div class="form-field">
		<label for="password">Contrase&ntilde;a:</label>
		<input type="password" name="password"/>
	</div>
	<div class="form-buttons">
		<input type="submit" name="submit" value="Ingresar" />
	</div>
</form>

<%@ include file="footer.jsp" %>