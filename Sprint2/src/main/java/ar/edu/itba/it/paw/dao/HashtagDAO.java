package ar.edu.itba.it.paw.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Hashtag;

public interface HashtagDAO {
	public Hashtag getHashtag(String hashtag);

	public void save(Hashtag hashtag);

	void saveWithComment(Hashtag hashtag, int commentId);

	public List<Comment> getComments(String hashtag);

	public TreeMap<Integer, ArrayList<Hashtag>> rankedHashtags(Date from,
			Date to);
}
