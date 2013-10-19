package services;

import java.util.List;

import model.User;

public interface UserService {
	
	public boolean userExists(String username);
	
	public void save(User user);
	
	public User authenticate(String username, String password);
	
	public User getUser(String username);
	
	public List<User> getUsersWithName(String name);
	
}
