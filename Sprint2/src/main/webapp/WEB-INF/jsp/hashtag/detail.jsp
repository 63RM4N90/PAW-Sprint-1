<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<h2>
		#<c:out value="${tag.hashtag}" />
	</h2>
	<h5>
		Created by: <c:out value="${tag.author.username}" />
	</h5>
	<h5>
		On date: <fmt:formatDate value="${tag.date}" pattern="dd-MM-yyyy HH:mm" />
	</h5>
	<br>
	<ul style="padding: 0;">
		<c:set var="row" value="0" />
		<c:forEach items="${comments}" var="comment">
			<li
				<c:set var="row" value="${row + 1}" />>
				<div class="comment">
					<p>${comment.transformedComment}</p>
					<div class="comment-reference">
						<c:if test="${not empty username}">
								<c:if test="${comment.favouritee}">
									<a href="../user/unfavourite?user=${user.username}&id=${comment.comment.id}&url=hashtag?tag=${tag.hashtag}">remove favourite</a>
								</c:if>
								<c:if test="${not comment.favouritee}">
									<a href="../user/favourite?user=${user.username}&id=${comment.comment.id}&url=hashtag?tag=${tag.hashtag}">add favourite</a>
								</c:if>
								<c:if test="${not isOwner}">
									<a href="../user/recuthulu?user=${user.username}&id=${comment.comment.id}&hashtag?tag=${tag.hashtag}">Recuthulu</a>
								</c:if>
							</c:if>
								<c:if test="${comment.recuthulu}">
									<i>Recuthuled from: </i>
									<a href="../user/profile/${comment.comment.originalAuthor.username}">${comment.comment.originalAuthor.username}</a>
								</c:if>
								<c:if test="${not comment.recuthulu}">
									<i>Created by: </i>
									<a href="../user/profile/${comment.comment.originalAuthor.username}">${comment.comment.originalAuthor.username}</a>
								</c:if>

							| <i><fmt:formatDate value="${comment.comment.date}"
										pattern="dd-MM-yyyy HH:mm" /></i> |
							<c:if test="${isOwner}">
									<a href="../../user/delete/${comment.comment.id}">Delete</a>								
							</c:if>
					</div>
				</div>
			</li>
		</c:forEach>
	</ul>
<%@ include file="../footer.jsp"%>