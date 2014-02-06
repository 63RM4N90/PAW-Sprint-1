package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;

@SuppressWarnings("serial")
public abstract class FollowListPanel extends Panel {
	
	private int userId;

	public FollowListPanel(String id, int userId) {
		super(id);
		this.userId = userId;
		add(new RefreshingView<User>("user") {
			@Override
			protected Iterator<IModel<User>> getItemModels() {
				List<IModel<User>> result = new ArrayList<IModel<User>>();
				Set<User> userList = getFollows(FollowListPanel.this.userId);
				for (User u : userList) {
					result.add(new EntityModel<User>(User.class, u));
				}
				return result.iterator();
			}

			@Override
			protected void populateItem(Item<User> item) {
				item.add(new Link<String>("userFollowLink"){

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						
					}
					
				}.add(new Label("userFollowUsername", new PropertyModel<String>(item.getModel(), "username"))));
			}
		});
	}
	
	protected abstract Set<User> getFollows(int userId);

}
