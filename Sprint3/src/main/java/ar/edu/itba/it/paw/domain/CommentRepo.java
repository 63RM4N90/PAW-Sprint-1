package ar.edu.itba.it.paw.domain;

import java.util.List;
import java.util.Set;

public interface CommentRepo {

	public List<Comment> getAll();

	public Set<Hashtag> getHashtagList(String comment, User author);

	public Set<User> getReferences(String comment, User loggedUser);

	public void delete(Comment comment);
	
	public Comment getComment(int id);
	
	public void addComment(Comment comment);
}
