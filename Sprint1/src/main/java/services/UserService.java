package services;

import java.util.List;

import model.User;
import network.UserDAO;

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
		return userDao.getUsersWithName(name);
	}
}
