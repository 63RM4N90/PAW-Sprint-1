<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<h2>EditProfile</h2>
	<div class="error">
		<c:out value="${usernameError}" />
	</div>
	<div class="error">
		<c:out value="${passwordError}" />
	</div>
	<div class="error">
		<c:out value="${generalError}" />
	</div>
	<form:form method="POST" action="editProfile" commandName="sarasa"/>
<%-- 		enctype="multipart/form-data"> --%>
		<div class="registration-form-field">
			<label for="name">Name:</label> <form:input type="text" name="name"
				path="name" maxlength=32 />
		</div>
		<div class="registration-form-field">
			<label for="surname">Surname:</label>
			<form:input type="text" name="surname" path="surname" maxlength=32 />
		</div>
		<div class="registration-form-field">
			<label for="password">Password:</label>
			<form:input type="password" name="password" path="password"
				maxlength=16 />
		</div>
		<div class="registration-form-field">
			<label for="confirm">Confirm password:</label>
			<form:input type="password" name="confirm" path="confirm"
				maxlength=16 />
		</div>
		<div class="registration-form-field">
			<label for="description">Description:</label>
			<form:input type="text" name="description" path="description"
				maxlength=140 />
		</div>
		<!-- 		<div class="registration-form-field"> -->
		<!-- 			<label for="picture">Picture:</label> -->
		<!-- 			<input type="file" name="picture" accept="image/"/> -->
		<!-- 		</div> -->
		<div class="form-buttons">
			<input type="submit" class="button" name="submit" value="Save" />
		</div>
	</form:form>
</div>
<%@ include file="../footer.jsp"%>