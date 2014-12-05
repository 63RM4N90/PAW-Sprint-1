package ar.edu.itba.it.paw.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.web.user.Bag;
import ar.edu.itba.it.paw.web.user.MapBag;

@Repository
public class HibernateUserRepo extends AbstractHibernateRepo implements
		UserRepo {

	private HashtagRepo hashtags;
	private static final int SUGGESTED_FRIEND_AMOUNT = 3;

	@Autowired
	public HibernateUserRepo(SessionFactory sessionFactory,
			HashtagRepo hashtagRepo) {
		super(sessionFactory);
		this.hashtags = hashtagRepo;
	}

	@Override
	public User authenticate(String username, String password) {
		List<User> result = find(
				" from User where username = ? and password = ?", username,
				password);
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public User getUser(String username) {
		if (username == null)
			return null;
		List<User> result = find(" from User where username = ?", username);
		return result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public List<User> getUsersWithName(String name) {
		return find(" from User where username like '%" + name
				+ "%' or (name like '%" + name + "%') or (surname like '%"
				+ name + "%')");
	}

	@Override
	public void registerUser(User user) {
		super.save(user);
	}

	@Override
	public User getUser(int id) {
		return super.get(User.class, id);
	}

	@Override
	public List<User> getAll() {
		List<User> result = find(" from User ");
		return result;
	}

	@Override
	public List<User> blcklistedUsers(User user) {
		return null;
	}

	@Override
	public List<User> getSuggestedFriends(User user) {
		Properties prop = new Properties();
		String property_file_name = "users.properties";
		List<User> following = user.getFollowing();
		InputStream is = getClass().getClassLoader().getResourceAsStream(property_file_name);
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bag<User> bag = new MapBag<User>();
		int n = Integer.parseInt(prop.getProperty("friends_in_common_amount"));
		for (User each : following)
			bag.add(each.getFollowers());

		List<User> aux = new ArrayList<User>();
		List<User> ans = new ArrayList<User>();

		bag.getNOrGreaterMatching(n, aux);

		int usrs_left = SUGGESTED_FRIEND_AMOUNT - randomize(aux, ans, user);
		if (usrs_left != 0)
			ans.addAll(hashtags.mostFollowed(usrs_left));

		return ans;
	}

	private int randomize(List<User> aux, List<User> ans, User user) {
		Random randomGenerator = new Random();
		while (ans.size() < SUGGESTED_FRIEND_AMOUNT && aux.size() > 0) {
			int rand = randomGenerator.nextInt(aux.size());
			User auxUser = aux.get(rand);
			if (!user.getUsername().equals(auxUser.getUsername()))
				ans.add(auxUser);

			aux.remove(rand);
		}
		return ans.size();
	}
}
