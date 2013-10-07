package ar.edu.itba.it.paw.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.dao.HashtagDAO;
import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Hashtag;
@Repository
public class HibernateHashtagDAO extends HibernateGenericDAO<Hashtag> implements HashtagDAO {

	public HibernateHashtagDAO() {
		//super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public Hashtag getHashtag(String hashtag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Hashtag hashtag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveWithComment(Hashtag hashtag, int commentId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Comment> getComments(String hashtag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeMap<Integer, ArrayList<Hashtag>> rankedHashtags(Date from,
			Date to) {
		// TODO Auto-generated method stub
		return null;
	}

}
