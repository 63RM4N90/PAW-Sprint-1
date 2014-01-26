package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;

public class ForgotPasswordPanel extends Panel {
	private static final long serialVersionUID = 1L;
	private String userToRecover;
	private UserRepo users;
	
	public ForgotPasswordPanel(String id) {
		super(id);
		Form<ForgotPasswordPanel> form = new Form<ForgotPasswordPanel>(
				"registrationForm",
				new CompoundPropertyModel<ForgotPasswordPanel>(this)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				User user = users.getUser(userToRecover);
				if (user != null) {
					System.out.println("SUCCESS = user = " + user.getName());
				} else {
					System.out.println("ERROR - USER NULL");
				}
			}
		};
		form.add(new TextField<String>("userToRecover").setRequired(true));
	}
}
