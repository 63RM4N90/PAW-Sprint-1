package dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import model.Comment;
import model.Hashtag;

import org.springframework.stereotype.Repository;

import dao.HashtagDAO;
@Repository
public class HibernateHashtagDAO extends HibernateGenericDAO<Hashtag> implements HashtagDAO {

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
