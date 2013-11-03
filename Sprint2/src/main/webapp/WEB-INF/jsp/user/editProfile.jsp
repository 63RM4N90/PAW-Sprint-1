<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<h2>Edit Profile</h2>
	<div class="error">
		<c:out value="${usernameError}" />
	</div>
	<div class="error">
		<c:out value="${passwordError}" />
	</div>
	<div class="error">
		<c:out value="${generalError}" />
	</div>
	<form:form method="POST" action="editProfile" commandName="editUserForm" class="form-horizontal registration-form">
		<%-- 		enctype="multipart/form-data"> --%>
		<form:errors path="*" />
		<form:hidden path="id" />
		<div class="control-group">
			<label class="control-label registration-label" for="name">Name:</label>
			<div class="controls registration-field">
				<form:input type="text" name="name" path="name" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="surname">Surname:</label>
			<div class="controls registration-field">
				<form:input type="text" name="surname" path="surname" />
			</div>		
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="password">Password:</label>
			<div class="controls registration-field">
				<form:input type="password" name="password" path="password" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="confirm">Confirm password:</label>
			<div class="controls registration-field">
				<form:input type="password" name="confirm" path="confirmPassword" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="description">Description:</label>
			<div class="controls registration-field">
				<form:input type="text" name="description" path="description" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="description">Privacy:</label>
			<div class="controls registration-field">
				<form:radiobutton name="privacy" path="privacy" value="T" />Private
				<form:radiobutton name="privacy" path="privacy" value="F" />Public
			</div>
		</div>
		<div class="form-buttons">
			<input type="submit" class="btn-inverse pull-right cthulhu-button" name="submit" value="Save" />
		</div>
	</form:form>
</div>
<%@ include file="../footer.jsp"%>
