<%@ include file="../header.jsp"%>
<div class="container">
	<div class="error-container">
		<h2>Notifications</h2>
		<div class="form-field">
			<c:forEach items="${notifications}" var="notification">
				<li <c:set var="row" value="${row + 1}" />>
					<div class="comment">
						<p>${notification.notification}</p>
						<div class="comment-reference">
							<i> By: </i>
							<a href="../../user/profile/${notification.notificator.username}">${notification.notificator.username}</a>
							| <i><fmt:formatDate value="${notification.date}"
										pattern="dd-MM-yyyy HH:mm" /></i> |
						</div>
					</div>
				</li>
			</c:forEach>
		</div>
	</div>
</div>

<%@ include file="../footer.jsp"%>