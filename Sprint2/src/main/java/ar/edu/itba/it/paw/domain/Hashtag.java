package ar.edu.itba.it.paw.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Hashtag extends PersistentEntity {

	private String hashtag;

	@OneToOne
	private User author;
	@Column(nullable = false)
	private Date date;

	@ManyToMany(mappedBy = "hashtags")
	private Set<Comment> comments;
	private static final int MAX_HASHTAG_SIZE = 139;

	public Hashtag() {
	}

	public Hashtag(String hashtag, User author, Date date) {
		if (hashtag.length() > MAX_HASHTAG_SIZE) {
			throw new IllegalArgumentException();
		}
		this.hashtag = hashtag;
		if (author == null || date == null) {
			throw new IllegalArgumentException();
		}
		this.author = author;
		this.date = date;
	}

	public String getHashtag() {
		return hashtag;
	}

	public User getAuthor() {
		return author;
	}

	public Date getDate() {
		return date;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashtag == null) ? 0 : hashtag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hashtag other = (Hashtag) obj;
		if (hashtag == null) {
			if (other.hashtag != null)
				return false;
		} else if (!hashtag.equals(other.hashtag))
			return false;
		return true;
	}
}
