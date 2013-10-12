package ar.edu.itba.it.paw.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.dao.impl.DbUserDAOImpl;
import ar.edu.itba.it.paw.model.User;

@Service
public class UserService {

	private DbUserDAOImpl userDao;

	@Autowired
	public UserService(DbUserDAOImpl userDao) {
		this.userDao = userDao;
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
