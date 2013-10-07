package ar.edu.itba.it.paw.model;

public class RankedHashtag {

	private Hashtag hashtag;
	private int rank;
	
	public RankedHashtag(Hashtag hashtag, int rank){
		this.hashtag = hashtag;
		this.rank = rank;
	}

	public Hashtag getHashtag() {
		return hashtag;
	}

	public int getRank() {
		return rank;
	}
}
