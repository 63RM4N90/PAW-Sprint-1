package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import model.Comment;
import model.Hashtag;

public interface HashtagDAO extends GenericDAO<Hashtag>{
	public Hashtag getHashtag(String hashtag);

	public void save(Hashtag hashtag);

	public List<Comment> getComments(String hashtag);

	public TreeMap<Integer, ArrayList<Hashtag>> rankedHashtags(Date from,
			Date to);
}
