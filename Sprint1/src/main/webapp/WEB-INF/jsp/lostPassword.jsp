<%@ include file="header.jsp" %>

<h2>Recovering Password</h2>
<div class="error"><c:out value="${error}" /></div>
<div class="success"><c:out value="${success}" /></div>
<c:if test="${!userSelected}">
	<form method="GET" action="lostPassword">
			<div class="form-field">
				<label for="userToRecover"> Insert your Username: </label>
				<input type="text" name="userToRecover"/>
			</div>
			<div class="form-buttons">
				<input type="submit" name="submit" value="submit username" />
			</div>
	</form>
</c:if>	
<c:if test="${userSelected && !passwordRecovered}">
	<form method="POST" action="lostPassword">
			<div class="form-field">
				<label for="secretQuestion"><c:out value="${user.secretQuestion}"/></label>
				<input type="text" name="secretAnswer" />
				<input type="hidden" name="userToRecover" value="<c:out value="${user.username}"/>" />
			</div/>
			<c:if test="${!passwordRecovered}">
				<div class="form-buttons">
					<input type="submit" name="submit" value="submit answer" />
				</div>
			</c:if>
	</form>
</c:if>	
<a href="login"><button>Go back</button></a>
<%@ include file="footer.jsp" %>