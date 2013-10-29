<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<div class="info-column profile-column">
		<div class="user-info">
			<c:if test="${isEmptyPicture}">
				<img
					src="${pageContext.request.contextPath}/img/default_picture.png"
					alt="user_picture" />
			</c:if>
			<c:if test="${!isEmptyPicture}">
				<img src="image?username=<c:out value="${user.username}" />"
					alt="user_picture" />
			</c:if>
			<h3>
				<c:out value="${user.username}" />
			</h3>
			<h6>Name:</h6>
			<h5>
				<c:out value="${user.name}" />
			</h5>
			<h6>Surname:</h6>
			<h5>
				<c:out value="${user.surname}" />
			</h5>
			<h6>Description:</h6>
			<p>
				<c:out value="${user.description}" />
			</p>
			<h6>Visits:</h6>
			<p>
				<c:out value="${user.visits}" />
			</p>
		</div>
		<c:if test="${isOwner}">
			<form method="GET" action="editProfile">
				<button class="button">Edit</button>
			</form>
		</c:if>
	</div>
	<div class="comments-column profile-column">
		<c:if test="${isOwner}">
			<div class="commentError">
				<c:out value="${commentError}" />
			</div>
			<div class="comment-creator-area">
				<form method="POST" action="profile">
					<div class="form-area">
						<textarea class="comment-area" rows="5" cols="40"
							placeholder="you have 140 characters to write down your sins..."
							name="comment"></textarea>
					</div>
					<div class="form-buttons">
						<input type="submit" class="button profile-button" name="submit"
							value="Post" />
					</div>
				</form>
			</div>
		</c:if>
		<ul>
			<c:set var="row" value="0" />
			<c:forEach items="${comments}" var="comment">
				<li <c:set var="row" value="${row + 1}" />>
					<div class="comment">
						<p>${comment.comment}</p>
						<div class="comment-reference">
							<i>Created by: </i><a
								href="<c:url value="profile"><c:param name="user" value="${comment.author.username}" /></c:url>">${comment.author.username}</a>
							| <i><fmt:formatDate value="${comment.date}"
									pattern="dd-MM-yyyy HH:mm" /></i> |
							<c:if test="${isOwner}">
								<a
									href="<c:url value="profile"><c:param name="commentid" value="${comment.id}" /><c:param name="user" value="${comment.author.username}" /></c:url>">Delete</a>
							</c:if>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="tag-column profile-column">
		<%@ include file="../top10.jsp"%>
	</div>
</div>
<%@ include file="../footer.jsp"%>