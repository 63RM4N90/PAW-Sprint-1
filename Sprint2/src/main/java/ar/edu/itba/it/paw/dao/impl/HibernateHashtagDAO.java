package ar.edu.itba.it.paw.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.dao.HashtagDAO;
import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Hashtag;

@Repository
public class HibernateHashtagDAO extends HibernateGenericDAO<Hashtag> implements HashtagDAO {

	@Autowired
	public HibernateHashtagDAO(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public Hashtag getHashtag(String hashtag) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Hashtag where hashtag = ?");
		query.setParameter(0, hashtag);
		List<Hashtag> result = (List<Hashtag>)query.list();
		tx.commit();
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
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("select c.hashtags.hashtag,count(distinct com_id) as rank from Comment c join c.hastags group by c.hashtags.hashtag order by"
				+ " rank");
		
		ScrollableResults results = query.scroll();
		tx.commit();
		
		ArrayList<Hashtag> aux;
		
		while(results.next()){
			
			Object[] row = results.get();
			Hashtag hashtag = (Hashtag) row[0];
			Integer ranking = results.getInteger(1);
			
			if (rank.containsKey(ranking)) {
	                rank.get(ranking).add(hashtag);

	        } else {
	                aux = new ArrayList<Hashtag>();
	                aux.add(hashtag);
	                rank.put(ranking, aux);
	        }
		}		
		
		return rank;
	}

}
