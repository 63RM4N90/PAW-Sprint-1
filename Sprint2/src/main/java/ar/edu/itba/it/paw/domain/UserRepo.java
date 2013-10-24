package ar.edu.itba.it.paw.domain;

import java.util.List;

public interface UserRepo extends HibernateRepo {
	
	public User authenticate(String username, String password);

	public User getUser(String username);
	
	public List<User> getUsersWithName(String name);
}
