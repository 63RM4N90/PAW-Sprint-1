package services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import database.UserDAO;

import model.User;

public class UserService {

	private UserDAO userDao;

	private static UserService instance;

	public static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}

	private UserService() {
		userDao = UserDAO.getInstance();
	}

	public boolean userExists(String username) {
		return userDao.getUser(username) != null;
	}

	public void save(User user) {
		userDao.save(user);
	}

	public User authenticate(String username, String password) {
		return userDao.authenticate(username, password);
	}

	public User getUser(String username) {
		return userDao.getUser(username);
	}
	
	public List<User> getUsersWithName(String name) {
		List<User> result = userDao.getUsersWithName(name);
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
