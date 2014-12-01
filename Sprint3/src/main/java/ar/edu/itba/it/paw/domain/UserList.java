package ar.edu.itba.it.paw.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class UserList extends PersistentEntity {

	@ManyToOne
	private User _owner;
	@Column(nullable = false)
	private String _name;
	@ManyToMany(mappedBy = "lists")
	private Set<User> _users = new HashSet<User>();
	
	public UserList() {
	}
	
	public UserList(User owner, String name) {
		_owner = owner;
		_name = name;
	}
	
	public Set<Comment> comments() {
		Set<Comment> comments = new HashSet<Comment>();
		for (User user : _users) {
			comments.addAll(user.getComments());
		}
		return comments;
	}
	
	public String name() {
		return _name;
	}
	
	public User owner() {
		return _owner;
	}
	
	public void addUser(User user) {
		user.addBelongingList(this);
	}
	
	public void removeUser(User user) {
		_users.remove(user);
	}
	
	public void changeName(String name) {
		_name = name;
	}

	public void removeAllUsers() {
		for (User user : _users) {
			user.removeFromBelongingList(this);
		}
	}
}
