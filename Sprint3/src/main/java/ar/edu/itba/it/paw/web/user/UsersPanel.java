package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;

public class UsersPanel extends Panel {

	private static final long serialVersionUID = -7991551927050985707L;
	private transient List<User> usersToShow;

	public UsersPanel(String id, List<User> users) {
		super(id);
		usersToShow = users;
		Label noSuggestedFriends = new Label("no_users",
				getString("no_users"));
		noSuggestedFriends.setVisible(usersToShow == null
				|| usersToShow.isEmpty());
		add(noSuggestedFriends);
		add(new RefreshingView<User>("user") {

			private static final long serialVersionUID = -6956993400492972641L;

			@Override
			protected Iterator<IModel<User>> getItemModels() {
				List<IModel<User>> result = new ArrayList<IModel<User>>();
				for (User u : usersToShow) {
					result.add(new EntityModel<User>(User.class, u));
				}
				return result.iterator();
			}

			@Override
			protected void populateItem(Item<User> item) {
				Link<User> usernameLink = new Link<User>("usernameLink",
						item.getModel()) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(getModelObject()
								.getId()));
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
			}
		});
	}
}
