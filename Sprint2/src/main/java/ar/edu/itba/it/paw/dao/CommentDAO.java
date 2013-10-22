package ar.edu.itba.it.paw.dao;

import java.util.List;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.User;

public interface CommentDAO extends GenericDAO<Comment>{
	
	public List<Comment> getComments(String hashtag);

	public List<Comment> getComments(User user);

	public void removeComment(int commentId);
}
