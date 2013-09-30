<%@ include file="header.jsp" %>
<div class="vertical-container">
	<div class="recover-container">
		<h2>Recovering Password</h2>
		<div class="error"><c:out value="${error}" /></div>
		<div class="success"><c:out value="${success}" /></div>
		<c:if test="${!userSelected}">
			<form method="GET" action="lostPassword">
					<div class="recover-form-field">
						<label for="userToRecover"> Insert your Username: </label>
						<input type="text" name="userToRecover"/>
					</div>
					<div class="form-buttons recover">
						<input type="submit" class="button user-register-button" name="submit" value="submit username" />
					</div>
			</form>
		</c:if>	
		<c:if test="${userSelected && !passwordRecovered}">
			<form method="POST" action="lostPassword">
					<div class="recover-form-field">
						<label><c:out value="${user.secretQuestion}"/></label>
						<input type="text" name="secretAnswer" />
					</div>
					<div class="recover-form-field">
						<label>New Password:</label>
						<input type="password" name="password" />
					</div>
					<div class="recover-form-field">
						<label>Confirm New Password:</label>
						<input type="password" name="confirm" />
					</div>
					<input type="hidden" name="userToRecover" value="<c:out value="${user.username}"/>" />
					<c:if test="${!passwordRecovered}">
						<div class="form-buttons recover">
							<input type="submit" class="button" name="submit" value="submit answer" />
						</div>
					</c:if>
			</form>
		</c:if>	
		<a href="login"><button class="button back-button recover-button">Go back</button></a>
	</div>
<%@ include file="footer.jsp" %>