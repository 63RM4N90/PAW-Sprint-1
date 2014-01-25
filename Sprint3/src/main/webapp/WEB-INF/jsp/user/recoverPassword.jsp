<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<div class="recover-container">
		<h2>Recovering Password</h2>
		<div class="error">
			<c:out value="${error}" />
		</div>
		<div class="success">
			<c:out value="${success}" />
		</div>
		<c:if test="${!userSelected}">
			<form method="GET" action="recoverPassword">
				<div class="control-group">
					<label class="control-label" for="userToRecover"> Insert your Username: </label> 
					<div class="controls">
						<input type="text" name="userToRecover" />
					</div>
				</div>
				<div class="form-buttons recover">
					<input type="submit" class="button user-register-button"
						name="submit" value="submit username" />
				</div>
			</form>
		</c:if>
		<c:if test="${userSelected && !passwordRecovered}">
			<form class="form-horizontal" method="POST" action="recoverPassword">
				<div class="control-group">
					<label class="control-label"><c:out value="${user.secretQuestion}" /></label> 
					<div class="controls">
						<input type="text" name="secretAnswer" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">New Password:</label> 
					<div class="controls">
						<input type="password" name="password" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">Confirm New Password:</label>
					<div class="controls">
					 	<input type="password" name="confirm" />
					 </div>
				</div>
				<input type="hidden" name="userToRecover"
					value="<c:out value="${user.username}"/>" />
				<c:if test="${!passwordRecovered}">
					<div class="control-group">
						<input type="submit" class="button" name="submit"
							value="submit answer" />
					</div>
				</c:if>
			</form>
		</c:if>
		<a href="login"><button class="button back-button recover-button">Go
				back</button></a>
	</div>
</div>
<%@ include file="../footer.jsp"%>