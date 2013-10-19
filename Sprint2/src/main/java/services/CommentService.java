package services;

import java.util.List;

import model.Comment;
import model.Hashtag;
import model.User;

public interface CommentService {
	
	public List<Hashtag> getHashtagList(String comment, User author);
	
	public void save(Comment comment);
	
	public List<Comment> getComments(User user);
	
	public List<Comment> getComments(String hashtag);	

}
