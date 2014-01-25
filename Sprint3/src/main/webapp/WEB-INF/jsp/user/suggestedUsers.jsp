<%@ include file="../header.jsp"%>
<div class="vertical-container">
	<h2>
		Suggested users for you:
	</h2>
	<br>
	<ul style="padding: 0;">
		<c:set var="row" value="0" />
		<c:forEach items="${suggestedUsers}" var="cuthuler">
			<li <c:set var="row" value="${row + 1}" />>
				<div class="comment">
					<div class="comment-reference">
						<i>Cuthuler: </i> <a
							href="../../user/profile/${cuthuler.username}">${cuthuler.username}</a>
					</div>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>
<%@ include file="../footer.jsp"%>