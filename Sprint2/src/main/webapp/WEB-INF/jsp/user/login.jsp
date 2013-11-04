<%@ include file="../header.jsp" %>
<div class="login-container">
	<div class="login-column column-1">	
		<h2>Login</h2>
		<div class="error"><c:out value="${error}" /></div>
		<form method="POST" action="login" class="form-horizontal">
			<div class="control-group">
				<label class="control-label" for="username">User:</label>
				<div class="controls">
					<input type="text" name="username" value="<c:out value="${username}" />" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="password">Password:</label>
				<div class="controls">
					<input type="password" name="password"/>
				</div>
			</div>
			<div class="control-group">
				<input class="btn-inverse pull-right cthulhu-button" type="submit" name="submit" value="Login" />
				<a class="pull-right" href="recoverPassword">forgotten password?</a>
			</div>	
		</form>
	</div>
	<div class="login-column column-2">
		<h2>Welcome to Social Cthulhu!</h2>
		<p>
			A place were you can become a Cthulhu follower and show your love <br>
			to our beloved god by posting comments in your own personal space. <br>
			Wanna join us? <br>
		</p>
		<div class="notRegistered">
			<a href="registration">
				<button class="btn-inverse cthulhu-button">
					Register
				</button>
			</a>
		</div>
	</div>

</div>
<div class="about-us">
	<p>
		Special Project Done By<br>
		Florencia Besteiro - German Romarion - Gabriel Zanzotti
	</p>
</div>
<%@ include file="../footer.jsp" %>