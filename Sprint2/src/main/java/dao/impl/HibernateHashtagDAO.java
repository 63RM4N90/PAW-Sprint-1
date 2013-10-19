package dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import model.Comment;
import model.Hashtag;
import dao.HashtagDAO;

public class HibernateHashtagDAO extends HibernateGenericDAO<Hashtag> implements HashtagDAO {

	@Autowired
	public HibernateHashtagDAO(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public Hashtag getHashtag(String hashtag) {
		Session session = getSession();
		Query query = session.createQuery("from Hashtag where hashtag = ?");
		query.setParameter(0, hashtag);
		List<Hashtag> result = (List<Hashtag>)query.list();
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public void save(Hashtag hashtag) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Comment> getComments(String hashtag) {
		HibernateCommentDAO HCDAO = new HibernateCommentDAO((SessionFactory) getSession());
		return HCDAO.getComments(hashtag);		
	}

	@Override
	public TreeMap<Integer, ArrayList<Hashtag>> rankedHashtags(Date from,
			Date to) {
		
		TreeMap<Integer, ArrayList<Hashtag>> rank = new TreeMap<Integer, ArrayList<Hashtag>>();
		
		//TODO
		
		
		return null;
	}

}
