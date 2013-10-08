<%@ include file="../header.jsp" %>
<div class="container">
	<div class="login-column column-1">	
		<h2>Login</h2>
		<div class="error"><c:out value="${error}" /></div>
		<form method="POST" action="login">
			<div class="login-elements">
				<div class="form-field">
					<label for="username">User:</label>
					<input type="text" name="username" value="<c:out value="${username}" />" />
				</div>
				<div class="form-field">
					<label for="password">Password:</label>
					<input type="password" name="password"/>
				</div>
			</div>
			<div class="form-buttons">
				<input class="button" type="submit" name="submit" value="Login" />
			</div>
		</form>
		<a href="lostPassword">forgotten password?</a>
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
				<button class="button">
					Register
				</button>
			</a>
		</div>
	</div>
	<div class="login-column column-3">
		<%@ include file="../top10.jsp"%> 
	</div>
</div>
<div class="about-us">
	<p>
		Special Project Done By<br>
		Florencia Besteiro - German Romarion - Gabriel Zanzotti
	</p>
</div>
<%@ include file="../footer.jsp" %>