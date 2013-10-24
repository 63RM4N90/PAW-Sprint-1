package ar.edu.itba.it.paw.domain;

import java.util.List;

public interface CommentRepo {

	public List<Comment> getComments(User user);
	
	public List<Comment> getAll();
	
	public Comment getComment(int id);
	
	public List<Hashtag> getHashtagList(Comment comment);
	
	public List<Comment> getComments(String hashtag);
}
