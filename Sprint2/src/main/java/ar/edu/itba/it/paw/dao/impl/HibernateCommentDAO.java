package ar.edu.itba.it.paw.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.dao.CommentDAO;
import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.User;
@Repository
public class HibernateCommentDAO extends HibernateGenericDAO<Comment> implements CommentDAO {

	public HibernateCommentDAO() {
	}
	
	@Override
	public List<Comment> getComments(String hashtag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> getComments(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Comment comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeComment(int commentId) {
		// TODO Auto-generated method stub
		
	}

}
