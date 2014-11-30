package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;

public class UsersPanel extends Panel {

	private static final long serialVersionUID = -7991551927050985707L;

	public UsersPanel(String id, IModel<List<User>> users) {
		super(id);
		Label noSuggestedFriends = new Label("no_users", getString("no_users"));
		noSuggestedFriends.setVisible(users.getObject() == null
				|| users.getObject().isEmpty());
		add(noSuggestedFriends);
		add(new PropertyListView<User>("user", users) {

			private static final long serialVersionUID = -6956993400492972641L;

			@Override
			protected void populateItem(ListItem<User> item) {
				Link<User> usernameLink = new Link<User>("usernameLink",
						item.getModel()) {
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
			}
		});
	}
}
