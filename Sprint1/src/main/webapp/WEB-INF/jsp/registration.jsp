<%@ include file="header.jsp" %>

<h2>Registration</h2>
<div class="error"><c:out value="${usernameError}" /></div>
<div class="error"><c:out value="${passwordError}" /></div>
<div class="error"><c:out value="${generalError}" /></div>
<form method="POST" action="register" enctype="multipart/form-data">
	<div class="form-field">
		<label for="name">Name:</label>
		<input type="text" name="name" value="<c:out value="${name}" />" />
	</div>
	<div class="form-field">
		<label for="surname">Surname:</label>
		<input type="text" name="surname" value="<c:out value="${surname}" />"/>
	</div>
	<div class="form-field">
		<label for="username">User:</label>
		<input type="text" name="username" value="<c:out value="${username}" />"/>
	</div>
	<div class="form-field">
		<label for="password">Password:</label>
		<input type="password" name="password" value="<c:out value="${password}" />"/>
	</div>
	<div class="form-field">
		<label for="confirm">Confirm password:</label>
		<input type="password" name="confirm" value="<c:out value="${confirm}" />"/>
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
	<div class="form-field">
		<label for="picture">Picture:</label>
		<input type="file" name="picture" accept="image/"/>
	</div>
	<div class="form-buttons">
		<input type="submit" name="submit" value="Submit" />
	</div>
</form>
<div class="alreadyRegistered"><a href="login">Already registered? Login!</a></div>

<%@ include file="footer.jsp" %>