package ar.edu.itba.it.paw.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.dao.UserDAO;
import ar.edu.itba.it.paw.model.User;


@Service
public class UserServiceImp implements UserService{

	private UserDAO userDAO;
	
	@Autowired
	public UserServiceImp(UserDAO userDAO){
		this.userDAO = userDAO;
	}
	
	@Override
	public boolean userExists(String username) {
		return userDAO.getUser(username) != null;
	}

	@Override
	public void save(User user) {
		userDAO.store(user);
	}

	@Override
	public User authenticate(String username, String password) {
		return userDAO.authenticate(username, password);
	}

	@Override
	public User getUser(String username) {
		return userDAO.getUser(username);
	}

	@Override
	public List<User> getUsersWithName(String name) {
		List<User> result = userDAO.getUsersWithName(name);
		sortUsers(result);
		return result;
	}
	
	private void sortUsers(List<User> users) {
		Collections.sort(users, new Comparator<User>() {
			public int compare(User o1, User o2) {
				return o1.getSurname().compareTo(o2.getSurname());
			}
		});
	}
	

}
