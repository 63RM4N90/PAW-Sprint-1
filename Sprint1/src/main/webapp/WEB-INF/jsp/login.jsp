<%@ include file="header.jsp" %>

<h2>Login</h2>
<div class="error"><c:out value="${error}" /></div>
<form method="POST" action="login">
	<div class="form-field">
		<label for="username">User:</label>
		<input type="text" name="username" value="<c:out value="${username}" />" />
	</div>
	<div class="form-field">
		<label for="password">Password:</label>
		<input type="password" name="password"/>
	</div>
	<div class="form-buttons">
		<input type="submit" name="submit" value="Login" />
	</div>
</form>
<a href="lostPassword">forgotten password?</a>

<div>
	<h2>Welcome to Social Cthulhu!</h2>
	<p>
		A place were you can become a Cthulhu follower <br>
		and show your love to our beloved god by posting <br>
		comments in your own personal space. Wanna join us? <br>
	</p>
	<div class="notRegistered"><a href="register"><button>Register</button></a></div>
</div>


<%@ include file="footer.jsp" %>