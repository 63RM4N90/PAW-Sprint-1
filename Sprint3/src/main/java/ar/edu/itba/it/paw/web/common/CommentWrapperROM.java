package ar.edu.itba.it.paw.web.common;

import org.apache.wicket.model.IModel;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.User;

public class CommentWrapperROM implements IModel<CommentWrapper> {

	private static final long serialVersionUID = 5312924914928923754L;
	private IModel<Comment> comment;
	private String transformedComment;
	private User userSession;

	public CommentWrapperROM(IModel<Comment> comment,
			String transformedComment, User userSession) {
		this.comment = comment;
		this.transformedComment = transformedComment;
		this.userSession = userSession;
	}

	@Override
	public void detach() {
		comment.detach();
	}

	@Override
	public CommentWrapper getObject() {
		return new CommentWrapper(comment.getObject(), transformedComment,
				userSession);
	}

	@Override
	public void setObject(CommentWrapper object) {
	}
}
