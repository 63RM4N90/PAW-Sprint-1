package ar.edu.itba.it.paw.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.dao.CommentDAO;
import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.User;


@Repository
public class HibernateCommentDAO extends HibernateGenericDAO<Comment> implements CommentDAO {

	@Autowired
	public HibernateCommentDAO(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<Comment> getComments(String hashtag) {
		Session session = getSession();
		Query query = session.createQuery("from Comment c inner join c.hashtags where c.hashtags = ?");
		query.setParameter(0, hashtag);
		List<Comment> result = (List<Comment>)query.list();
		return result;
	}

	@Override
	public List<Comment> getComments(User user) {
		Session session = getSession();
		Query query = session.createQuery("from Comment where author = ?");
		query.setParameter(0, user.getUsername());
		List<Comment> result = (List<Comment>)query.list();
		return result;
	}


	@Override
	public void removeComment(int commentId) {
		// TODO Auto-generate method stub		
	}


}
