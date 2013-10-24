package ar.edu.itba.it.paw.domain;

import java.util.List;

public interface CommentRepo extends HibernateRepo {

	public List<Comment> getComments(User user);

	public List<Comment> getAll();

	public Comment getComment(int id);

	public List<Hashtag> getHashtagList(String comment, User author);

	public List<Comment> getComments(String hashtag);

	public List<User> getReferences(String comment);
}
