<%@ include file="header.jsp" %>
<<<<<<< HEAD
<div class="vertical-container">
	<h2>EditProfile</h2>
	<div class="error"><c:out value="${usernameError}" /></div>
	<div class="error"><c:out value="${passwordError}" /></div>
	<div class="error"><c:out value="${generalError}" /></div>
	<form method="POST" action="editProfile" enctype="multipart/form-data">
		<div class="registration-form-field">
			<label for="name">Name:</label>
			<input type="text" name="name" value="<c:out value="${name}" />" />
		</div>
		<div class="registration-form-field">
			<label for="surname">Surname:</label>
			<input type="text" name="surname" value="<c:out value="${surname}" />"/>
		</div>
		<div class="registration-form-field">
			<label for="password">Password:</label>
			<input type="password" name="password" value="<c:out value="${password}" />"/>
		</div>
		<div class="registration-form-field">
			<label for="confirm">Confirm password:</label>
			<input type="password" name="confirm" value="<c:out value="${confirm}" />"/>
		</div>
		<div class="registration-form-field">
			<label for="description">Description:</label>
			<input type="text" name="description" value="<c:out value="${description}" />"/>
		</div>
		<div class="registration-form-field">
			<label for="picture">Picture:</label>
			<input type="file" name="picture" accept="image/"/>
		</div>
		<div class="form-buttons">
			<input type="submit" class="button" name="submit" value="Save" />
		</div>
	</form>
<%@ include file="footer.jsp" %>