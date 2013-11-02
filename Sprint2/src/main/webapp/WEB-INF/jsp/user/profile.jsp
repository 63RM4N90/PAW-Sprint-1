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
				<img src="../image?username=<c:out value="${user.username}" />"
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
			
			<h6><a href="../../user/follows?user=${user.username}&type=Followers">Followers: ${followers}</a></h6>
			<h6><a href="../../user/follows?user=${user.username}&type=Following">Following: ${following}</a></h6>
			<h6><a href="../../user/favourites?user=${user.username}">Favourites</a></h6>
			<c:if test="${isOwner}">
				<p>
					Notifications:
					<c:out value="${notifications}" />
				</p>
				<a href="../notifications">Notifications</a>
			</c:if>
		</div>
		<c:if test="${isOwner}">
			<form method="GET" action="../editProfile">
				<button class="button">Edit</button>
			</form>
		</c:if>
	</div>
	<c:if test="${not empty username}">
		<c:if test="${not isOwner}">

			<c:if test="${not isFollowing}">
				<button onclick="location.href='../../user/follow?user=${user.username}'"> Follow </button>
			</c:if>
			<c:if test="${isFollowing}">
				<button onclick="location.href='../../user/unfollow?user=${user.username}'"> Unfollow </button>
			</c:if>
		</c:if>
	</c:if>

	<div class="comments-column profile-column">
		<c:if test="${isOwner}">
			<div class="commentError">
				<c:out value="${commentError}" />
			</div>
			<div class="comment-creator-area">
				<form method="POST" action="../profile">
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
						<p>${comment.transformedComment}</p>
						<div class="comment-reference">
							
							<c:if test="${not empty username}">
								<c:if test="${comment.favouritee}">
									<a href="../../user/unfavourite?user=${user.username}&id=${comment.comment.id}&url=user/profile/${user.username}">remove favourite</a>
								</c:if>
								<c:if test="${not comment.favouritee}">
									<a href="../../user/favourite?user=${user.username}&id=${comment.comment.id}&url=user/profile/${user.username}">add favourite</a>
								</c:if>
								<c:if test="${not isOwner}">
									<a href="../../user/recuthulu?user=${user.username}&id=${comment.comment.id}&url=user/profile/${user.username}">Recuthulu</a>
								</c:if>
								<c:if test="${comment.recuthulu}">
									<i>Recuthuled from: </i>
									<a href="../../user/profile/${comment.comment.originalAuthor.username}">${comment.comment.originalAuthor.username}</a>
								</c:if>
								<c:if test="${not comment.recuthulu}">
									<i>Created by: </i>
									<a href="../../user/profile/${comment.comment.originalAuthor.username}">${comment.comment.originalAuthor.username}</a>
								</c:if>
							</c:if>

							| <i><fmt:formatDate value="${comment.comment.date}"
										pattern="dd-MM-yyyy HH:mm" /></i> |
							<c:if test="${isOwner}">
									<a href="../user/delete/${comment.comment.id}">Delete</a>								
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