package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.UserList;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;

@SuppressWarnings("serial")
public class UserListsPage extends BasePage {

	@SpringBean
	private UserRepo users;

	public UserListsPage() {
		final List<UserList> userlists = users.getUser(
				SocialCthulhuSession.get().getUsername()).userlists();
		Label no_userlists = new Label("no_userlists",
				getString("no_userlists"));
		add(no_userlists.setVisible(userlists == null || userlists.isEmpty()));

		IModel<List<UserList>> userListModel = new LoadableDetachableModel<List<UserList>>() {

			@Override
			protected List<UserList> load() {
				List<UserList> userList = new ArrayList<UserList>();
				userList.addAll(users.getUser(
						SocialCthulhuSession.get().getUsername()).userlists());
				return userList;
			}
		};
		add(new PropertyListView<UserList>("userlist", userListModel) {

			@Override
			protected void populateItem(ListItem<UserList> item) {
				Link<UserList> userlistsLink = new Link<UserList>(
						"userlistsLink",
						(IModel<UserList>) new EntityModel<UserList>(
								UserList.class, item.getModelObject())) {
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						setResponsePage(new UserListDetailPage(
								new PageParameters().set("userlist",
										getModelObject().getId())));
						return;
					}
				};
				userlistsLink.add(new Label("userlist_name", item
						.getModelObject().name()));
				item.add(userlistsLink);
			}
		});
		add(new BookmarkablePageLink<Void>("create_user_list_link",
				UserListForm.class).add(new Label("create_user_list",
				getString("create_user_list"))));
	}
}
