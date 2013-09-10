<%@ include file="header.jsp" %>

<h2>Registration</h2>
<div class="error"><c:out value="${usernameError}" /></div>
<div class="error"><c:out value="${passwordError}" /></div>
<form method="POST" action="register">
	<div class="form-field">
		<label for="name">Nombre:</label>
		<input type="text" name="name" value="<c:out value="${name}" />" />
	</div>
	<div class="form-field">
		<label for="surname">Apellido:</label>
		<input type="text" name="surname" value="<c:out value="${surname}" />"/>
	</div>
	<div class="form-field">
		<label for="username">Usuario:</label>
		<input type="text" name="username" value="<c:out value="${username}" />"/>
	</div>
	<div class="form-field">
		<label for="password">Password:</label>
		<input type="password" name="password" value="<c:out value="${password}" />"/>
	</div>
	<div class="form-field">
		<label for="description">Description:</label>
		<input type="text" name="description" value="<c:out value="${description}" />"/>
	</div>
	<div class="form-field">
		<label for="secretQuestion">Secret question:</label>
		<input type="text" name="secretQuestion" value="<c:out value="${secretQuestion}" />"/>
	</div>
	<div class="form-field">
		<label for="secretAnswer">Secret answer:</label>
		<input type="text" name="secretAnswer" value="<c:out value="${secretAnswer}" />"/>
	</div>
	<div class="form-buttons">
		<input type="submit" name="submit" value="Submit" />
	</div>
</form>

<%@ include file="footer.jsp" %>