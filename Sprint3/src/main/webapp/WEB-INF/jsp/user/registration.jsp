<div class="vertical-container">
	<h2>Registration</h2>

	<form:form class="form-horizontal registration-form" method="POST" action="registration" commandName="userForm" enctype="multipart/form-data">
		<div class="control-group">
			<label class="control-label registration-label" for="name">
				<wicket:message key="reg_name">Name</wicket:message>:
			</label>
			<div class="controls registration-field">
				<form:input type="text" path="name" />
			</div>
			<div class="error">
				<form:errors path="name" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="surname">
				<wicket:message key="reg_surname">Surname</wicket:message>:
			</label>
			<div class="controls registration-field">
				<form:input type="text" path="surname" />
			</div>
			<div class="error">
				<form:errors path="surname" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="username">
				<wicket:message key="reg_username">Username</wicket:message>:
			</label>
			<div class="controls registration-field">
				<form:input type="text" path="username" />
			</div>
			<div class="error">
				<form:errors path="username" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="password">
				<wicket:message key="reg_password">Password</wicket:message>:
			</label>
			<div class="controls registration-field">
				<form:input type="password" path="password" />
			</div>
			<div class="error">
				<form:errors path="password" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="confirmPassword">
				<wicket:message key="reg_confirm_password">Confirm password</wicket:message>:
			</label>
			<div class="controls registration-field">
				<form:input type="password" path="confirmPassword" />
			</div>
			<div class="error">
				<form:errors path="confirmPassword" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="description">
				<wicket:message key="reg_description">Description</wicket:message>:
			</label>
			<div class="controls registration-field">
				<form:input type="text" path="description" />
			</div>
			<div class="error">
				<form:errors path="description" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="secretQuestion">
				<wicket:message key="reg_secret_question">Secret question</wicket:message>:
			</label>
			<div class="controls registration-field">
				<form:input type="text" path="secretQuestion" />
			</div>
			<div class="error">
				<form:errors path="secretQuestion" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label registration-label" for="secretAnswer">
				<wicket:message key="reg_secret_answer">Secret answer</wicket:message>:
			</label>
			<div class="controls registration-field">
				<form:input type="text" path="secretAnswer" />
			</div>
			<div class="error">
				<form:errors path="secretAnswer" />
			</div>
		</div>
		<div class="registration-form-field">
			<label for="picture"><wicket:message key="reg_secret_answer">
				Picture</wicket:message>:
			</label>
			<form:input type="file" name="picture" path="picture" />
		</div>
		<div class="registration-form-buttons">
			<input type="submit" class="btn-inverse pull-right cthulhu-button" name="submit" value="Submit" />
		</div>
		<div class="alreadyRegistered pull-right">
			<a wicket:id="reg_already_registered">
				<wicket:message key="reg_already_registered_message" >Already registered? Login!</wicket:message>
			</a>
		</div>
	</form:form>
</div>