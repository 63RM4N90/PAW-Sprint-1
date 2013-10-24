package ar.edu.itba.it.paw.services;

import java.util.List;

import ar.edu.itba.it.paw.domain.User;

public interface UserService {

	public boolean userExists(String username);
	
	public void save(User user);
	
	public User authenticate(String username, String password);
	
	public User getUser(String username);
	
	public List<User> getUsersWithName(String name);
	
}
