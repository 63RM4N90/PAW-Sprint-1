package ar.edu.itba.it.paw.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.dao.HashtagDAO;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Hashtag;

@Repository
public class HibernateHashtagDAO extends HibernateGenericDAO<Hashtag> implements HashtagDAO {

	@Autowired
	public HibernateHashtagDAO(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Hashtag getHashtag(String hashtag) {
		Session session = getSession();
		Query query = session.createQuery("from Hashtag where hashtag = ?");
		query.setParameter(0, hashtag);
		List<Hashtag> result = (List<Hashtag>)query.list();
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public List<Comment> getComments(String hashtag) {
		HibernateCommentDAO HCDAO = new HibernateCommentDAO((SessionFactory)getSession());
		return HCDAO.getComments(hashtag);		
	}

	@Override
	public TreeMap<Integer, ArrayList<Hashtag>> rankedHashtags(Date from,
			Date to) {
		
		TreeMap<Integer, ArrayList<Hashtag>> rank = new TreeMap<Integer, ArrayList<Hashtag>>();
		
		Session session = getSession();
		Query query = session.createQuery("select hashtag,count(id) as rankfrom Comment c inner join c.hastags group by hashtag.hashtag order by"
				+ "rank");
		
		
		
		return null;
	}

}
