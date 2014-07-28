package ar.edu.itba.it.paw.web.common;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.PersistentEntity;

public class CommentWrapper extends PersistentEntity implements Comparable<CommentWrapper> {
	
	private Comment comment;
	private String transformedComment;
	private boolean favouritee;

	public CommentWrapper(Comment comment, String transformedComment, boolean favouritee) {
		this.comment = comment;
		this.transformedComment = transformedComment;
		this.favouritee = favouritee;
	}

	public Comment getComment() {
		return comment;
	}

	public String getTransformedComment() {
		return transformedComment;
	}
	
	public boolean isFavouritee(){
		return favouritee;
	}
	
	public boolean isRecuthulu(){
		return comment.isRecuthulu();
	}

	@Override
	public int compareTo(CommentWrapper o) {
		return comment.compareTo(o.getComment());
	}
	
	@Override
	public Integer getId() {
		return comment.getId();
	}
}
