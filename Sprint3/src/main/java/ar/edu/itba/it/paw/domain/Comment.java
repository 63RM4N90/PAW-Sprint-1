package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends PersistentEntity implements Comparable<Comment> {

	@ManyToOne
	private User author;
	@ManyToOne
	private User originalAuthor;

	@Column(nullable = false)
	private Date date;
	@Column(nullable = false)
	private String comment;
	@ManyToMany
	@JoinColumn(name = "com_id")
	private Set<Hashtag> hashtags;
	@ManyToMany
	private List<User> references;

	@ManyToMany(mappedBy = "favourites")
	private List<User> favouritees = new ArrayList<User>();

	private static final int MAX_COMMENT_LENGTH = 140;

	protected Comment() {
	}

	public Comment(User author, Date date, String comment,
			Set<Hashtag> hashtags, List<User> references, User originalauthor) {
		this.author = author;
		this.date = date;
		if (comment.length() > MAX_COMMENT_LENGTH) {
			throw new IllegalArgumentException();
		}
		this.comment = comment;
		if (author == null || originalauthor == null) {
			throw new IllegalArgumentException();
		}
		this.hashtags = hashtags;
		this.originalAuthor = originalauthor;
		this.references = references;
	}

	public User getAuthor() {
		return author;
	}

	public Date getDate() {
		return date;
	}

	public boolean favouritedBy(User user) {
		return favouritees.contains(user);
	}

	public String getComment() {
		return comment;
	}

	public User getOriginalAuthor() {
		return originalAuthor;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Set<Hashtag> getHashtags() {
		return hashtags;
	}

	public List<User> getReferences() {
		return references;
	}

	public boolean isRecuthulu() {
		return !author.equals(originalAuthor);
	}

	@Override
	public int compareTo(Comment o) {
		return -date.compareTo(o.getDate());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((originalAuthor == null) ? 0 : originalAuthor.hashCode());
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
		Comment other = (Comment) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (originalAuthor == null) {
			if (other.originalAuthor != null)
				return false;
		} else if (!originalAuthor.equals(other.originalAuthor))
			return false;
		return true;
	}
}
