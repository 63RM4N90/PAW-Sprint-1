
<div class="top container-fluid">
	<h3>
		TOP 10 Hashtags
	</h3>
	<form action="">
		<a href="<c:url value="${previous}"><c:param name="period" value="1" /></c:url>">1 day |</a>
		<a href="<c:url value="${previous}"><c:param name="period" value="7" /></c:url>">1 week |</a>
		<a href="<c:url value="${previous}"><c:param name="period" value="30" /></c:url>">1 month</a>
	</form>

<c:if test="${!isempty}">
	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${ranking}" var="hashtag">
			<li <c:set var="row" value="${row + 1}" />>
				<a href="<c:url value="/bin/hashtag/detail"><c:param name="tag" value="${hashtag.hashtag.hashtag}" /></c:url>">#${hashtag.hashtag.hashtag}</a>
				<div class="tag-info">
					<a href="<c:url value="profile"><c:param name="user" value="${hashtag.hashtag.author.username}" /></c:url>">${hashtag.hashtag.author.username}</a>
					<fmt:formatDate value="${hashtag.hashtag.date}" pattern="dd-MM-yyyy" />
					AMOUNT:<c:out value="${hashtag.rank}" />
				</div>
			</li>
		</c:forEach>
	</ul>
</c:if>

<c:if test="${isempty}">
	<p>There are no hashtags</p>
</c:if>

</div>