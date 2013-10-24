package ar.edu.itba.it.paw.services;

import java.util.List;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Hashtag;
import ar.edu.itba.it.paw.model.User;

public interface CommentService {
	
	public List<Hashtag> getHashtagList(String comment, User author);
	
	public void save(Comment comment);
	
	public List<Comment> getComments(User user);
	
	public List<Comment> getComments(String hashtag);	

}

