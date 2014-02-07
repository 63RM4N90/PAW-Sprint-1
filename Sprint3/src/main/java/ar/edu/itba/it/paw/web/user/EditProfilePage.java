package ar.edu.itba.it.paw.web.user;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.SecuredPage;

public class EditProfilePage extends SecuredPage {

	private static final long serialVersionUID = 1L;

	private static final int MAX_STRING_LENGTH = 32;
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 16;
	private static final int MAX_DESCRIPTION_LENGTH = 140;

	private transient String name;
	private transient String surname;
	private transient String password;
	private transient String confirmPassword;
	private transient String description;
	private transient String selected;
	private transient User user;
	private transient final List<String> TYPES = Arrays.asList(new String[] {
			"Private", "Public" });
	@SpringBean
	private UserRepo users;

	public EditProfilePage() {
		String username = new SocialCthulhuSession(getRequest()).getUsername();
		user = users.getUser(username);
		name = user.getName();
		surname = user.getSurname();
		password = user.getPassword();
		confirmPassword = user.getPassword();
		description = user.getDescription();
		selected = user.isPrivate() ? "Private" : "Public";
		add(new FeedbackPanel("errorPanel"));
		Form<EditProfilePage> form = new Form<EditProfilePage>(
				"editProfileForm", new CompoundPropertyModel<EditProfilePage>(
						this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				if (validateInput()) {
					user.setName(name);
					user.setSurname(surname);
					user.setPassword(password);
					user.setDescription(description);
					if (selected.equals("Private")) {
						user.makePrivate();
					} else {
						user.makePublic();
					}
				}
			}

			private boolean validateInput() {
				return checkInputLength()
						&& password.length() > MIN_PASSWORD_LENGTH
						&& password.length() < MAX_PASSWORD_LENGTH
						&& checkPasswordConfirm();
			}

			private boolean checkPasswordConfirm() {
				if (!password.equals(confirmPassword)) {
					error(getString("password_nonmatch"));
					return false;
				}
				return true;
			}

			private boolean checkInputLength() {
				if (name.length() > MAX_STRING_LENGTH || name.length() == 0) {
					error(getString("name_length"));
					return false;
				}
				if (surname.length() > MAX_STRING_LENGTH
						|| surname.length() == 0) {
					error(getString("surname_length"));
					return false;
				}
				if (password.length() < MIN_PASSWORD_LENGTH
						|| password.length() > MAX_PASSWORD_LENGTH) {
					error(getString("password_length"));
					return false;
				}
				if (confirmPassword.length() < MIN_PASSWORD_LENGTH
						|| confirmPassword.length() > MAX_PASSWORD_LENGTH) {
					error(getString("confirmPassword_length"));
					return false;
				}
				if (description.length() > MAX_DESCRIPTION_LENGTH
						|| description.length() == 0) {
					error(getString("name_length"));
					return false;
				}
				return true;
			}
		};

		form.add(new TextField<String>("name").setRequired(true));
		form.add(new TextField<String>("surname").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new PasswordTextField("confirmPassword"));
		form.add(new TextField<String>("description").setRequired(true));
		form.add(new RadioChoice<String>("privateUser",
				new PropertyModel<String>(this, "selected"), TYPES));
		form.add(new Button("submit", new ResourceModel("Submit")));
		add(form);
	}
}
