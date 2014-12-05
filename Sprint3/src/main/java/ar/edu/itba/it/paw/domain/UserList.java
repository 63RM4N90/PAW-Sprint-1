package ar.edu.itba.it.paw.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

	public List<Comment> comments() {
		List<Comment> comments = new ArrayList<Comment>();
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

	public Set<User> users() {
		return _users;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserList other = (UserList) obj;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		if (_owner == null) {
			if (other._owner != null)
				return false;
		} else if (!_owner.equals(other._owner))
			return false;
		return true;
	}
}
