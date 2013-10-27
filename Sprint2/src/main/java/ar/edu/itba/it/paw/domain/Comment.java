package ar.edu.itba.it.paw.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Comment extends PersistentEntity implements Comparable<Comment> {

	@ManyToOne
	private User author;
	
	@Column(nullable = false)
	private Date date;
	@Column(nullable = false)
	private String comment;
	
	@ManyToMany	
	@JoinColumn(name = "com_id")
	private Set<Hashtag> hashtags;
	@OneToMany
	private Set<User> references;

	public Comment() {
	}

	public Comment(User author, Date date, String comment,
			Set<Hashtag> hashtags, Set<User> references) {
		this.author = author;
		this.date = date;
		this.comment = comment;
		this.hashtags = hashtags;
		this.references = references;
	}

	public User getAuthor() {
		return author;
	}

	public Date getDate() {
		return date;
	}

	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}

	public Set<Hashtag> getHashtags() {
		return hashtags;
	}

	public Set<User> getReferences() {
		return references;
	}

	public int compareTo(Comment o) {
		return date.compareTo(o.getDate());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}
}
