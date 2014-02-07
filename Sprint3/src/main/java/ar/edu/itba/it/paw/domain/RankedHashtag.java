package ar.edu.itba.it.paw.domain;

public class RankedHashtag extends PersistentEntity{

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
	
	public Integer getId(){
		return hashtag.getId();
	}
}
