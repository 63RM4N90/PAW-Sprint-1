package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserList;
import ar.edu.itba.it.paw.domain.UserListRepo;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;

@SuppressWarnings("serial")
public class AddUserToListFormPage extends BasePage {

	private transient String username;
	private StringBuilder values = new StringBuilder();
	@SpringBean
	private UserListRepo userlists;
	@SpringBean
	private UserRepo users;

	public AddUserToListFormPage(final PageParameters params) {

		add(new FeedbackPanel("errorPanel"));

		final IModel<String> model = new IModel<String>() {
			@Override
			public String getObject() {
				return username;
			}

			@Override
			public void setObject(String object) {
				username = object;

				values.append("\n");
				values.append(username);
			}

			@Override
			public void detach() {
			}
		};

		Form<AddUserToListFormPage> form = new Form<AddUserToListFormPage>(
				"add_user_to_userlist_form",
				new CompoundPropertyModel<AddUserToListFormPage>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				if (!username.equals(SocialCthulhuSession.get().getUsername())) {
					User new_user = users.getUser(username);
					UserList user_list = userlists.userList(params.get(
							"userlist").toInt());
					if (new_user != null
							&& !user_list.users().contains(new_user)) {
						user_list.addUser(new_user);
						setResponsePage(new UserListDetailPage(params));
					}
				}
			}
		};
		form.add(new AutoCompleteTextField<String>("username", model) {
			@Override
			protected Iterator<String> getChoices(String input) {
				if (Strings.isEmpty(input)) {
					List<String> emptyList = Collections.emptyList();
					return emptyList.iterator();
				}

				List<String> choices = new ArrayList<String>(10);

				List<User> usernames = users.getAll();

				for (final User u : usernames) {
					final String username = u.getUsername();

					if (username.toUpperCase().startsWith(input.toUpperCase())) {
						choices.add(username);
						if (choices.size() == 10) {
							break;
						}
					}
				}

				return choices.iterator();
			}
		}.setRequired(true));
		form.add(new Button("add_user_to_userlist", new ResourceModel(
				"add_user_to_userlist")));
		add(form);
	}
}
