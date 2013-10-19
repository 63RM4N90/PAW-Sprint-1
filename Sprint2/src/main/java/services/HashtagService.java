package services;

import java.util.LinkedList;

import model.Hashtag;
import model.RankedHashtag;

public interface HashtagService {
	
	public void save(Hashtag hashtag);
	
	public Hashtag getHashtag(String hashtag);
	
	public LinkedList<RankedHashtag> topHashtags(int days);
	
}
