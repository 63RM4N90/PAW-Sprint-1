<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<h2>Registration</h2>

	<form:form class="form-horizontal registration-form" method="POST" action="registration" commandName="userForm" enctype="multipart/form-data">
		<div class="control-group">
			<label class="control-label registration-label" for="name">Name:</label>
			<div class="controls registration-field">
				<form:input type="text" path="name" />
			</div>
			<div class="error">
				<form:errors path="name" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="surname">Surname:</label>
			<div class="controls registration-field">
				<form:input type="text" path="surname" />
			</div>
			<div class="error">
				<form:errors path="surname" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="username">User:</label>
			<div class="controls registration-field">
				<form:input type="text" path="username" />
			</div>
			<div class="error">
				<form:errors path="username" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="password">Password:</label>
			<div class="controls registration-field">
				<form:input type="password" path="password" />
			</div>
			<div class="error">
				<form:errors path="password" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="confirmPassword">Confirm password:</label>
			<div class="controls registration-field">
				<form:input type="password" path="confirmPassword" />
			</div>
			<div class="error">
				<form:errors path="confirmPassword" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="description">Description:</label>
			<div class="controls registration-field">
				<form:input type="text" path="description" />
			</div>
			<div class="error">
				<form:errors path="description" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="secretQuestion">Secret question:</label>
			<div class="controls registration-field">
				<form:input type="text" path="secretQuestion" />
			</div>
			<div class="error">
				<form:errors path="secretQuestion" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="secretAnswer">Secret answer:</label>
			<div class="controls registration-field">
				<form:input type="text" path="secretAnswer" />
			</div>
			<div class="error">
				<form:errors path="secretAnswer" />
			</div>
		</div>
		<div class="registration-form-field">
			<label for="picture">Picture:</label>
			<form:input type="file" name="picture" path="picture" />
		</div>
		<div class="registration-form-buttons">
			<input type="submit" class="btn-inverse pull-right cthulhu-button" name="submit" value="Submit" />
		</div>
		<div class="alreadyRegistered pull-right">
			<a href="login">Already registered? Login!</a>
		</div>
	</form:form>
</div>
<%@ include file="../footer.jsp"%>