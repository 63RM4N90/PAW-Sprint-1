package dao;

import java.util.List;

import model.Comment;
import model.User;

public interface CommentDAO extends GenericDAO<Comment>{
	
	public List<Comment> getComments(String hashtag);

	public List<Comment> getComments(User user);

	public void save(Comment comment);

	public void removeComment(int commentId);
}
