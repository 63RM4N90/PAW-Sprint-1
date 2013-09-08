<%@ include file="header.jsp" %>

<h2>Registration</h2>
<form method="POST" action="login">
	<div class="form-field">
		<label for="name">Nombre:</label>
		<input type="text" name="name"/>
	</div>
	<div class="form-field">
		<label for="surname">Apellido:</label>
		<input type="text" name="surname"/>
	</div>
	<div class="form-field">
		<label for="username">Usuario:</label>
		<input type="text" name="username"/>
	</div>
	<div class="form-field">
		<label for="password">Password:</label>
		<input type="password" name="password"/>
	</div>
	<div class="form-field">
		<label for="description">Description:</label>
		<input type="text" name="description"/>
	</div>
	<div class="form-buttons">
		<input type="submit" name="submit" value="Submit" />
	</div>
</form>

<%@ include file="footer.jsp" %>