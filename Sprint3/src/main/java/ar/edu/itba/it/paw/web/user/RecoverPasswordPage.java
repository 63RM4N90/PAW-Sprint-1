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

public class RecoverPasswordPage extends BasePage {
	private static final long serialVersionUID = 1L;
	private transient String username;
	private transient String secretAnswer;
	@SpringBean
	private UserRepo users;
	
	public RecoverPasswordPage(String user, String secretQuestion) {
		add(new Label("success", " "));
		add(new FeedbackPanel("errorPanel"));
		username = user;
		Form<RecoverPasswordPage> form = new Form<RecoverPasswordPage>(
				"recoverPasswordForm",
				new CompoundPropertyModel<RecoverPasswordPage>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				User user = users.getUser(username);
				if (secretAnswer.equals(user.getSecretAnswer())) {
					BasePage responsePage = new ChangePasswordPage(username);
					setResponsePage(responsePage);
				} else {
					error(getString("secretAnswer_mismatch"));
				}
			}
		};
		add(new Link<Void>("back") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				ForgotPasswordPage responsePage = new ForgotPasswordPage();
				setResponsePage(responsePage);
			}
		});
		form.add(new Label("secretQuestion", secretQuestion));
		form.add(new TextField<String>("secretAnswer").setRequired(true));
		add(form);
	}
}
