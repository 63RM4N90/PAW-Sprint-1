package dao;

import java.util.List;

import model.User;

public interface UserDAO {
	public User authenticate(String username, String password);

	public User getUser(String username);

	public void save(User user);

	public List<User> getUsersWithName(String name);

	public void removeUser(User user);
}
