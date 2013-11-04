package ar.edu.itba.it.paw.domain;

import java.util.LinkedList;

public interface HashtagRepo {

	public Hashtag getHashtag(String hashtag);
	
	public LinkedList<RankedHashtag> topHashtags(int days);
	
	public void addHashtag(Hashtag hashtag);
}
