package ar.edu.itba.it.paw.web.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.EntityModel;
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
	private transient List<User> suggestedFriends;
	private static final int SUGGESTED_FRIEND_AMOUNT = 3;
	
	public SuggestedFriendsPage() throws InvalidPropertiesFormatException, IOException {
		User user = users.getUser(new SocialCthulhuSession(getRequest()).getId());
		suggestedFriends = getSuggestedFriends(user);
		
		add(new RefreshingView<User>("suggested_friend") {
			private static final long serialVersionUID = 1L;
			@Override
			protected Iterator<IModel<User>> getItemModels() {
				List<IModel<User>> result = new ArrayList<IModel<User>>();
				for (User u : suggestedFriends) {
					result.add(new EntityModel<User>(User.class, u));
				}
				return result.iterator();
			}

			@Override
			protected void populateItem(Item<User> item) {
				Link<String> cthuluerLink = new Link<String>("cthulhuer") {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						
					}
					
				};
				cthuluerLink.add(new Label("cthulhuer_username", new PropertyModel<String>(item.getModel(), "username")));
				item.add(cthuluerLink);
				item.add(new Label("suggested_user_title", getString("suggested_user_title")));
			}
		});
		add(new Label("suggested_friends_title", getString("suggested_friends_title")));
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
		
		
		 int usrs_left= SUGGESTED_FRIEND_AMOUNT - randomize(aux,ans,user);		
		 if(usrs_left != 0 ){
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
