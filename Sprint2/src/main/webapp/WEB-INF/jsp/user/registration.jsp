<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<h2>Registration</h2>

	<form:form method="POST" action="registration" commandName="userForm"
		enctype="multipart/form-data">
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="name">Name:</label>
				<form:input type="text" path="name" />
			</div>
			<div class="error">
				<form:errors path="name" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="surname">Surname:</label>
				<form:input type="text" path="surname" />
			</div>
			<div class="error">
				<form:errors path="surname" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="username">User:</label>
				<form:input type="text" path="username" />
			</div>
			<div class="error">
				<form:errors path="username" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="password">Password:</label>
				<form:input type="password" path="password" />
			</div>
			<div class="error">
				<form:errors path="password" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="confirmPassword">Confirm password:</label>
				<form:input type="password" path="confirmPassword" />
			</div>
			<div class="error">
				<form:errors path="confirmPassword" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="description">Description:</label>
				<form:input type="text" path="description" />
			</div>
			<div class="error">
				<form:errors path="description" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="secretQuestion">Secret question:</label>
				<form:input type="text" path="secretQuestion" />
			</div>
			<div class="error">
				<form:errors path="secretQuestion" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="secretAnswer">Secret answer:</label>
				<form:input type="text" path="secretAnswer" />
			</div>
			<div class="error">
				<form:errors path="secretAnswer" />
			</div>
		</div>
		<div class="registration-form-field">
			<label for="picture">Picture:</label>
			<form:input type="file"  name="picture" path="picture" />
		</div>
		<div class="registration-form-buttons">
			<input type="submit" class="button" name="submit" value="Submit" />
		</div>
	</form:form>
	<div class="alreadyRegistered">
		<a href="login">Already registered? Login!</a>
	</div>
</div>
<%@ include file="../footer.jsp"%>