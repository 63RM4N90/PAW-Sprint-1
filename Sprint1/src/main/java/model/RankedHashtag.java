package model;

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

	public void setHashtag(Hashtag hashtag) {
		this.hashtag = hashtag;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
