package ar.edu.itba.it.paw.web.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.HashtagRepo;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.SecuredPage;

public class SuggestedFriendsPage extends SecuredPage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private UserRepo users;
	@SpringBean
	private HashtagRepo hashtags;
	private static final int SUGGESTED_FRIEND_AMOUNT = 3;

	public SuggestedFriendsPage() throws InvalidPropertiesFormatException,
			IOException {

		User user = users.getUser(SocialCthulhuSession.get().getUsername());
		List<User> userFriends = new ArrayList<User>();
		try {
			userFriends = getSuggestedFriends(user);
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		add(new UsersPanel("users-panel", userFriends));
		add(new Label("suggested_friends_title",
				getString("suggested_friends_title")));
	}

	private List<User> getSuggestedFriends(User user)
			throws InvalidPropertiesFormatException, IOException {
		Set<User> following = user.getFollowing();
		Bag<User> bag = new MapBag<User>();
		int n = getNValue();
		for (User each : following) {
			bag.add(each.getFollowers());
		}
		List<User> aux = new ArrayList<User>();
		List<User> ans = new ArrayList<User>();

		bag.getNOrGreaterMatching(n, aux);

		int usrs_left = SUGGESTED_FRIEND_AMOUNT - randomize(aux, ans, user);
		if (usrs_left != 0) {
			ans.addAll(hashtags.mostFollowed(usrs_left));
		}

		return ans;
	}

	private int randomize(List<User> aux, List<User> ans, User user) {
		Random randomGenerator = new Random();
		int i = ans.size();
		while (i < SUGGESTED_FRIEND_AMOUNT && aux.size() > 0) {
			int rand = randomGenerator.nextInt(aux.size());
			User auxUser = aux.get(rand);
			if (!user.getUsername().equals(auxUser.getUsername())) {
				ans.add(auxUser);
			}
			aux.remove(rand);
			i++;
		}
		return ans.size();
	}

	private int getNValue() throws InvalidPropertiesFormatException,
			IOException {
		File file = new File("src/main/resources/parameters.xml");
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.loadFromXML(fileInput);
		fileInput.close();

		int commonFollowers = Integer.parseInt(properties
				.getProperty("common-followers"));
		return commonFollowers;
	}
}
