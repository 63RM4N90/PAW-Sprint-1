package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserList;
import ar.edu.itba.it.paw.domain.UserListRepo;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;

@SuppressWarnings("serial")
public class UserListForm extends BasePage {

	private transient String name;
	@SpringBean
	private UserListRepo userLists;
	@SpringBean
	private UserRepo users;

	public UserListForm() {
		add(new FeedbackPanel("errorPanel"));
		Form<UserListForm> form = new Form<UserListForm>("user_list_form",
				new CompoundPropertyModel<UserListForm>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				User logged_in_user = SocialCthulhuSession.get().getUser();
				UserList new_user_list = new UserList(logged_in_user, name);
				if (!logged_in_user.userlists().contains(new_user_list))
					userLists.createUserList(new_user_list);
				setResponsePage(UserListsPage.class);
			}
		};
		form.add(new TextField<String>("name").setRequired(true));
		form.add(new Button("create_user_list", new ResourceModel("create_user_list")));
		add(form);
	}
}
