package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

public class RecoverPasswordPanel extends Panel {
	private static final long serialVersionUID = 1L;
	private String secretAnswer;
	private String username;
	@SpringBean
	private UserRepo users;
	
	public RecoverPasswordPanel(String id, String secretQuestion, String user) {
		super(id);
		username = user;
		Form<RecoverPasswordPanel> form = new Form<RecoverPasswordPanel>(
				"recoverPasswordForm",
				new CompoundPropertyModel<RecoverPasswordPanel>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				User user = users.getUser(username);
				if (secretAnswer.equals(user.getSecretAnswer())) {
					BasePage responsePage = new ChangePasswordPage(user.getPassword());
					setResponsePage(responsePage);
				} else {
					System.out.println("ERROR - USER NULL");
				}
			}
		};
		form.add(new Label("secretQuestion", secretQuestion));
		form.add(new TextField<String>("secretAnswer").setRequired(true));
		add(form);
	}
}
