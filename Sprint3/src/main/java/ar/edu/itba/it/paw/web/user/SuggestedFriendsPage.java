package ar.edu.itba.it.paw.web.user;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
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

	@SuppressWarnings("serial")
	public SuggestedFriendsPage() throws InvalidPropertiesFormatException,
			IOException {

		IModel<List<User>> usersModel = new LoadableDetachableModel<List<User>>() {

			@Override
			protected List<User> load() {
				User user = users.getUser(SocialCthulhuSession.get()
						.getUsername());
				return users.getSuggestedFriends(user);
			}
		};

		add(new UsersPanel("users-panel", usersModel));
		add(new Label("suggested_friends_title",
				getString("suggested_friends_title")));
	}
}
