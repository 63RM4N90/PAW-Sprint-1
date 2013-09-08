package model;

public class Hashtag {
	private String hashtag;
	private User author;

	public Hashtag() {
	}

	public Hashtag(String hashtag, User author) {
		this.hashtag = hashtag;
		this.author = author;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}
