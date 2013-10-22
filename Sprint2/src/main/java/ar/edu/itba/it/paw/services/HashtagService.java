package ar.edu.itba.it.paw.services;

import java.util.LinkedList;

import ar.edu.itba.it.paw.model.Hashtag;
import ar.edu.itba.it.paw.model.RankedHashtag;

public interface HashtagService {

	public void save(Hashtag hashtag);
	
	public Hashtag getHashtag(String hashtag);
	
	public LinkedList<RankedHashtag> topHashtags(int days);
	
}
