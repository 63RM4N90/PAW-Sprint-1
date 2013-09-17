
<div class="top container-fluid">
	<h2>
		TOP 10 Hashtags
	</h2>
	
	<span>
		Of the last
		
		<form action="">
			<input type="radio" name="days" value="1">day
			<input type="radio" name="days" value="7">week
			<input type="radio" name="days" value="30">month
		</form>
	</span>


	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${ranking}" var="hashtag">
			<li <c:set var="row" value="${row + 1}" />>
				<p>#${hashtag.hashtag}
				<a href="<c:url value="profile"><c:param name="user" value="${hashtag.author.username}" /></c:url>">${hashtag.author.username}</a>
				<fmt:formatDate value="${hashtag.date}" pattern="dd-MM-yyyy HH:mm" /></p>
			</li>
		</c:forEach>
	</ul>

</div>