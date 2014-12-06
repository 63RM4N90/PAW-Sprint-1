package ar.edu.itba.it.paw.web.user;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;

public class UsersPanel extends Panel {

	private static final long serialVersionUID = -7991551927050985707L;
	@SpringBean
	private UserRepo userss;

	@SuppressWarnings("serial")
	public UsersPanel(String id, IModel<List<User>> users) {
		super(id);
		Label noSuggestedFriends = new Label("no_users", getString("no_users"));
		noSuggestedFriends.setVisible(users.getObject() == null
				|| users.getObject().isEmpty());
		add(noSuggestedFriends);

		add(new PropertyListView<User>("user", users) {

			@Override
			protected void populateItem(ListItem<User> item) {
				User logged_in_user = userss.getUser(SocialCthulhuSession.get()
						.getUsername());
				boolean is_same_user = logged_in_user != null
						&& item.getModelObject().getUsername()
								.equals(logged_in_user.getUsername());
				int followers_amount = Integer
						.parseInt(getString("followers_amount"));
				IModel<User> user_model = new EntityModel<User>(User.class,
						item.getModelObject());
				item.add(new Image("popular", SocialCthulhuApp.POPULAR_ICON)
						.setVisible(item.getModelObject().getFollowers().size() > followers_amount));
				Link<User> usernameLink = new Link<User>("usernameLink",
						user_model) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(
								new PageParameters().set("username",
										getModelObject().getUsername())));
					}
				};
				usernameLink
						.add(new Label("username", new PropertyModel<String>(
								item.getModel(), "username")));
				item.add(usernameLink);
				item.add(new Label("name", new PropertyModel<String>(item
						.getModel(), "name")));
				item.add(new Label("surname", new PropertyModel<String>(item
						.getModel(), "surname")));
				PrettyTime p = new PrettyTime();
				item.add(new Label("registrationDate", p.format(item
						.getModelObject().getRegistrationDate())));
				boolean has_blacklisted_user = logged_in_user != null
						&& logged_in_user.hasBlacklistedUser(item
								.getModelObject());
				Link<User> blacklistLink = new Link<User>("unblacklist",
						user_model) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						User logged_user = SocialCthulhuSession.get().getUser();
						if (logged_user.getBlacklistedUsers().contains(
								getModelObject())) {
							logged_user.removeBlacklistedUser(getModelObject());
						} else {
							logged_user.addBlacklistedUser(getModelObject());
						}
					}
				};
				blacklistLink.add(new Label("unblacklist_text",
						getString("unblacklist_text"))
						.setVisible(has_blacklisted_user && !is_same_user
								&& logged_in_user != null));
				blacklistLink.add(new Label("blacklist_text",
						getString("blacklist_text"))
						.setVisible(!has_blacklisted_user && !is_same_user
								&& logged_in_user != null));
				item.add(blacklistLink);
			}
		});
	}
}
