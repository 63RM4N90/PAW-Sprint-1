<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<h2>Registration</h2>

	<form:form method="POST" action="registration" commandName="userForm">
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="name">Name:</label>
				<form:input type="text" path="name" value='<c:out value="${name}"/>' maxlength=32 />
			</div>
			<div class="error">
				<form:errors path="name" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="surname">Surname:</label>
				<form:input type="text" path="surname" value="<c:out value="${surname}"/>" maxlength=32 />
			</div>
			<div class="error">
				<form:errors path="surname" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="username">User:</label>
				<form:input type="text" path="username" value="<c:out value="${username}"/>" maxlength=32 />
			</div>
			<div class="error">
				<form:errors path="username" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="password">Password:</label>
				<form:input type="password" path="password" value="<c:out value="${password}"/>" maxlength=16 />
			</div>
			<div class="error">
				<form:errors path="password" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="confirmPassword">Confirm password:</label>
				<form:input type="password" path="confirmPassword" value="<c:out value="${confirm}"/>" maxlength=16 />
			</div>
			<div class="error">
				<form:errors path="confirmPassword" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="description">Description:</label>
				<form:input type="text" path="description" value="<c:out value="${description}"/>" maxlength=140 />
			</div>
			<div class="error">
				<form:errors path="description" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="secretQuestion">Secret question:</label>
				<form:input type="text" path="secretQuestion" value="<c:out value="${secretQuestion}"/>" maxlength=64 />
			</div>
			<div class="error">
				<form:errors path="secretQuestion" />
			</div>
		</div>
		<div class="field-wrapper">
			<div class="registration-form-field">
				<label for="secretAnswer">Secret answer:</label>
				<form:input type="text" path="secretAnswer" value="<c:out value="${secretAnswer}"/>" maxlength=64 />
			</div>
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