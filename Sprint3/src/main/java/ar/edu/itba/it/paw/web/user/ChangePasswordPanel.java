package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.common.LoginPage;

public class ChangePasswordPanel extends Panel {
	
	private static final long serialVersionUID = 1L;
	private String newPassword;
	private String confirmNewPassword;
	private String username;
	@SpringBean
	private UserRepo users;
	
	public ChangePasswordPanel(String id, String user) {
		super(id);
		username = user;
		Form<ChangePasswordPanel> form = new Form<ChangePasswordPanel>(
				"changePasswordForm",
				new CompoundPropertyModel<ChangePasswordPanel>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				User user = users.getUser(username);
				if (newPassword.equals(confirmNewPassword)) {
					user.setPassword(newPassword);
					LoginPage responsePage = new LoginPage();
					setResponsePage(responsePage);
				} else {
					error(getString("password_mismatch"));
				}
			}
		};
		form.add(new Label("newPasswordLabel", "New Password"));
		form.add(new Label("confirmNewPasswordLabel", "Confirm new password"));
		form.add(new PasswordTextField("newPassword").setRequired(true));
		form.add(new PasswordTextField("confirmNewPassword").setRequired(true));
		add(form);
	}
}
