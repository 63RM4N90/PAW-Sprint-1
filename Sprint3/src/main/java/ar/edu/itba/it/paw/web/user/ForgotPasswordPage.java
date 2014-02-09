package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.common.LoginPage;

public class ForgotPasswordPage extends BasePage {

	private static final long serialVersionUID = 1L;
	private transient String userToRecover;
	@SpringBean
	private UserRepo users;

	public ForgotPasswordPage() {
		add(new FeedbackPanel("errorPanel"));
		Form<ForgotPasswordPage> form = new Form<ForgotPasswordPage>(
				"forgotPasswordForm",
				new CompoundPropertyModel<ForgotPasswordPage>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				User user = users.getUser(userToRecover);
				if (user != null) {
					BasePage responsePage = new RecoverPasswordPage(
							user.getUsername(), user.getSecretQuestion());
					setResponsePage(responsePage);
				} else {
					error(getString("username_nonexisting"));
				}
			}
		};
		form.add(new TextField<String>("userToRecover").setRequired(true));
		add(form);
		add(new Label("success", " "));
		add(new Link<Void>("back") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				LoginPage responsePage = new LoginPage();
				setResponsePage(responsePage);
			}
		});
	}
}
