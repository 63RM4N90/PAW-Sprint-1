package ar.edu.itba.it.paw.web.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

public class SearchPage extends BasePage{

	private static final long serialVersionUID = 1L;
	private String search;
	@SpringBean
	private UserRepo users;
	
	public SearchPage(String searchText) {
		super();
		search = searchText;
		add(new Label("search", new PropertyModel<String>(this, "search")));
		add(new RefreshingView<User>("userResult") {
			@Override
			protected Iterator<IModel<User>> getItemModels() {
				List<IModel<User>> result = new ArrayList<IModel<User>>();
				List<User> userList;
				if(search == null) {
					userList = users.getAll();
				} else {
					userList = users.getUsersWithName(search);
				}
				for (User u : userList) {
					result.add(new EntityModel<User>(User.class, u));
				}
				return result.iterator();
			}

			@Override
			protected void populateItem(Item<User> item) {
				Link<String> usernameLink = new Link<String>("usernameLink") {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						
					}
					
				};
				usernameLink.add(new Label("username", new PropertyModel<String>(item.getModel(), "username")));
				item.add(usernameLink);
				item.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
				item.add(new Label("surname", new PropertyModel<String>(item.getModel(), "surname")));
				item.add(new Label("registrationDate", new PropertyModel<String>(item.getModel(), "registrationDate")));
			}
		});
	}
}