package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.UserList;
import ar.edu.itba.it.paw.domain.UserListRepo;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.common.CommentWrapper;

@SuppressWarnings("serial")
public class UserListDetailPage extends BasePage {

	@SpringBean
	private UserListRepo userlists;
	@SpringBean
	private UserRepo users;

	public UserListDetailPage(final PageParameters params) {

		IModel<List<CommentWrapper>> commentModel = new CommentWrapperModel() {
			@Override
			protected List<Comment> transformableLoad() {
				List<Comment> comments = new ArrayList<Comment>();
				UserList ul = userlists
						.userList(params.get("userlist").toInt());
				comments.addAll(ul.comments());
				return comments;
			}
		};

		add(new CommentsPanel("comments_panel", commentModel));
		Link<Void> userlistsLink = new Link<Void>(
				"add_user_to_user_list_link") {

			@Override
			public void onClick() {
				setResponsePage(new AddUserToListFormPage(params));
				return;
			}
		};
		Link<Void> deleteUserListLink = new Link<Void>(
				"delete_user_list_link") {

			@Override
			public void onClick() {
				UserList ul = userlists
						.userList(params.get("userlist").toInt());
				ul.removeAllUsers();
				userlists.deleteUserList(ul);
				setResponsePage(UserListsPage.class);
			}
		};
		userlistsLink.add(new Label("add_user_to_user_list",
				getString("add_user_to_user_list")));
		deleteUserListLink.add(new Label("delete_user_list",
				getString("delete_user_list")));
		add(userlistsLink);
		add(deleteUserListLink);
		add(new Label("title", userlists.userList(
				params.get("userlist").toInt()).name()));
	}
}
