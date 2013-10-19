package dao;

import java.util.List;

import model.User;

public interface UserDAO extends GenericDAO<User>{
	
	public User authenticate(String username, String password);

	public User getUser(String username);

	public List<User> getUsersWithName(String name);
}
