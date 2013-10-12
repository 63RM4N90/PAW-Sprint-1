<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<h2>Registration</h2>

	<form:form method="POST" action="registration" commandName="userForm">
	
		<div class="error"><form:errors path="*" /></div>
	
		<form:hidden path="user" />
	
		<div class="registration-form-field">
			<label for="name">Name:</label>
			<form:input type="text" path="name" />
			<div class="error">
				<form:errors path="name" />
			</div>
		</div>
		<div class="registration-form-field">
			<label for="surname">Surname:</label>
			<form:input type="text" path="surname" />
			<div class="error">
				<form:errors path="surname" />
			</div>
		</div>
		<div class="registration-form-field">
			<label for="username">User:</label>
			<form:input type="text" path="username" />
			<div class="error">
				<form:errors path="username" />
			</div>
		</div>
		<div class="registration-form-field">
			<label for="password">Password:</label>
			<form:input type="password" path="password" />
			<div class="error">
				<form:errors path="password" />
			</div>
		</div>
		<div class="registration-form-field">
			<label for="confirmPassword">Confirm password:</label>
			<form:input type="password" path="confirmPassword" />
			<div class="error">
				<form:errors path="confirmPassword" />
			</div>
		</div>
		<div class="registration-form-field">
			<label for="description">Description:</label>
			<form:input type="text" path="description" />
			<div class="error">
				<form:errors path="description" />
			</div>
		</div>
		<div class="registration-form-field">
			<label for="secretQuestion">Secret question:</label>
			<form:input type="text" path="secretQuestion" />
			<div class="error">
				<form:errors path="secretQuestion" />
			</div>
		</div>
		<div class="registration-form-field">
			<label for="secretAnswer">Secret answer:</label>
			<form:input type="text" path="secretAnswer" />
			<div class="error">
				<form:errors path="secretAnswer" />
			</div>
		</div>
		<!-- 		<div class="registration-form-field"> -->
		<!-- 			<label for="picture">Picture:</label> -->
		<%-- 			<form:input type="file" path="picture" accept="image/"/> --%>
		<!-- 		</div> -->
		<div class="registration-form-buttons">
			<input type="submit" class="button" name="submit" value="Submit" />
		</div>
	</form:form>
	<div class="alreadyRegistered">
		<a href="login">Already registered? Login!</a>
	</div>
</div>
<%@ include file="../footer.jsp"%>