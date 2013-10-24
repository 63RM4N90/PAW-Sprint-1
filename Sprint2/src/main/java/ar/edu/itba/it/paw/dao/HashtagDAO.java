package ar.edu.itba.it.paw.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Hashtag;

public interface HashtagDAO extends GenericDAO<Hashtag>{

	public Hashtag getHashtag(String hashtag);

	public List<Comment> getComments(String hashtag);

	public TreeMap<Integer, ArrayList<Hashtag>> rankedHashtags(Date from,
			Date to);
}
