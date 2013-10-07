package ar.edu.itba.it.paw.dao;

import java.util.List;

import ar.edu.itba.it.paw.model.User;

public interface UserDAO {
	public User authenticate(String username, String password);

	public User getUser(String username);

	public List<User> getUsersWithName(String name);
}
