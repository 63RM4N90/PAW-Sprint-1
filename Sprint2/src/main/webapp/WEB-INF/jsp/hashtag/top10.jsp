<%@ include file="../header.jsp" %>
<div class="top container-fluid">
	<h3>
		TOP 10 Hashtags
	</h3>
	
	<a href="../hashtag/top10?period=1">1 day |</a>
	<a href="../hashtag/top10?period=7">1 week |</a>
	<a href="../hashtag/top10?period=30">1 month</a>
	

<c:if test="${!isempty}">
	<ul>
		<c:set var="row" value="0" />
		<c:forEach items="${ranking}" var="hashtag">
			<li <c:set var="row" value="${row + 1}" />>
				<a href="<c:url value="/bin/hashtag/detail"><c:param name="tag" value="${hashtag.hashtag.hashtag}" /></c:url>">#${hashtag.hashtag.hashtag}</a>
				<div class="tag-info">
					<a href="<c:url value="profile"><c:param name="user" value="${hashtag.hashtag.author.username}" /></c:url>">${hashtag.hashtag.author.username}</a>
					<fmt:formatDate value="${hashtag.hashtag.date}" pattern="dd-MM-yyyy" />
					tagged <c:out value="${hashtag.rank}" /> times
				</div>
			</li>
		</c:forEach>
	</ul>
</c:if>

<c:if test="${isempty}">
	<p>There are no hashtags</p>
</c:if>

</div>
<%@ include file="../footer.jsp" %>