
<div class="top container-fluid">
	<h2>
		TOP 10 Hashtags
	</h2>
	
	<span>
		Of the last
		
		<form action="">
			<a href="<c:url value="${previous}"><c:param name="period" value="1" /></c:url>">day |</a>
			<a href="<c:url value="${previous}"><c:param name="period" value="7" /></c:url>">week |</a>
			<a href="<c:url value="${previous}"><c:param name="period" value="30" /></c:url>">month</a>
		</form>
	</span>

<c:if test="${!isempty}">
	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${ranking}" var="hashtag">
			<li <c:set var="row" value="${row + 1}" />>
				<p>
				<a href="<c:url value="hashtag"><c:param name="tag" value="${hashtag.hashtag}" /></c:url>">#${hashtag.hashtag}</a>
				<a href="<c:url value="profile"><c:param name="user" value="${hashtag.author.username}" /></c:url>">${hashtag.author.username}</a>
				<fmt:formatDate value="${hashtag.date}" pattern="dd-MM-yyyy HH:mm" /></p>
			</li>
		</c:forEach>
	</ul>
</c:if>

<c:if test="${isempty}">
	<p>No hay hashtags</p>
</c:if>

</div>