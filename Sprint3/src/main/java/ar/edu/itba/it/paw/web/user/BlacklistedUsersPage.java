package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;

@SuppressWarnings("serial")
public class BlacklistedUsersPage extends BasePage {
	@SpringBean
	private UserRepo _users;

	public BlacklistedUsersPage() {
		add(new Label("blacklisted_users_title",
				getString("blacklisted_users_title")));
		IModel<List<User>> userModel = new LoadableDetachableModel<List<User>>() {

			@Override
			protected List<User> load() {
				List<User> users = new ArrayList<User>();
				users.addAll(SocialCthulhuSession.get().getUser()
						.getBlacklistedUsers());
				return users;
			}
		};
		add(new UsersPanel("blacklisted_users", userModel));
	}
}
