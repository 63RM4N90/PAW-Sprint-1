<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<h2>
		<c:out value="${user.username}" />'s favourites
	</h2>

	<br>
	<ul style="padding: 0;">
		<c:set var="row" value="0" />
		<c:forEach items="${comments}" var="comment">
			<li
				<c:set var="row" value="${row + 1}" />>
				<div class="comment">
					<p>${comment.comment}</p>
					<div class="comment-reference">
						<i>Created by: </i><a href="${commentOwnerURL}">${comment.author.username}</a> | 
						<i><fmt:formatDate value="${comment.date}" pattern="dd-MM-yyyy HH:mm" /></i>
					</div>
				</div>
			</li>
		</c:forEach>
	</ul>
<%@ include file="../footer.jsp"%>