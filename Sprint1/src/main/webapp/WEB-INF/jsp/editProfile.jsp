<%@ include file="header.jsp" %>

<h2>EditProfile</h2>
<div class="error"><c:out value="${usernameError}" /></div>
<div class="error"><c:out value="${passwordError}" /></div>
<form method="POST" action="editProfile">
	
	<div class="form-field">
		<label for="surname">ChangeProfilePicture:</label>
		<button><img src="sessionUser.image" alt="user image"></buton>
	</div>
	<div class="form-field">
		<label for="name">Name:</label>
		<input type="text" name="name" value="<c:out value="${sessionUser.name}" />" />
	</div>
	<div class="form-field">
		<label for="surname">Surname:</label>
		<input type="text" name="surname" value="<c:out value="${sessionUser.surname}" />"/>
	</div>
	<div class="form-field">
		<label for="password">Password:</label>
		<input type="password" name="password" value="<c:out value="${sessionUser.password}" />"/>
	</div>
	<div class="form-field">
		<label for="confirm">Confirm password:</label>
		<input type="password" name="confirm" value="<c:out value="${sessionUser.password}" />"/>
	</div>
	<div class="form-field">
		<label for="description">Description:</label>
		<input type="text" name="description" value="<c:out value="${sessionUser.description}" />"/>
	</div>
	<div class="form-field">
		<label for="secretQuestion">Secret question:</label>
		<input type="text" name="secretQuestion" value="<c:out value="${sessionUser.secretQuestion}" />"/>
	</div>
	<div class="form-field">
		<label for="secretAnswer">Secret answer:</label>
		<input type="text" name="secretAnswer" value="<c:out value="${sessionUser.secretAnswer}" />"/>
	</div>
	<div class="form-buttons">
		<input type="submit" name="submit" value="Save" />
	</div>
</form>

<%@ include file="footer.jsp" %>