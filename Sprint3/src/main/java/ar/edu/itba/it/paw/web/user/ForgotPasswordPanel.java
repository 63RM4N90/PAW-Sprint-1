package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ForgotPasswordPanel extends Panel {
	private static final long serialVersionUID = 1L;
	private String userToRecover;
	@SpringBean
	private UserRepo users;

	public ForgotPasswordPanel(String id) {
		super(id);
		Form<ForgotPasswordPanel> form = new Form<ForgotPasswordPanel>(
				"forgotPasswordForm",
				new CompoundPropertyModel<ForgotPasswordPanel>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				User user = users.getUser(userToRecover);
				if (user != null) {
					BasePage responsePage = new RecoverPasswordPage(
							user.getSecretQuestion(), user.getUsername());
					setResponsePage(responsePage);
				} else {
					error(getString("username_nonexisting"));
				}
			}
		};
		form.add(new TextField<String>("userToRecover").setRequired(true));
		add(form);
	}
}
