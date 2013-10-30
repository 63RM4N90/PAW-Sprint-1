package ar.edu.itba.it.paw.web;

import ar.edu.itba.it.paw.domain.Comment;

public class CommentWrapper implements Comparable<CommentWrapper> {
	private Comment comment;
	private String transformedComment;

	public CommentWrapper(Comment comment, String transformedComment) {
		this.comment = comment;
		this.transformedComment = transformedComment;
	}

	public Comment getComment() {
		return comment;
	}

	public String getTransformedComment() {
		return transformedComment;
	}

	@Override
	public int compareTo(CommentWrapper o) {
		return comment.compareTo(o.getComment());
	}
}
