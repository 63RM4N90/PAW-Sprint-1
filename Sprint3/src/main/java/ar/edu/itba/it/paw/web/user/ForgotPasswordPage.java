package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.basic.Label;

import ar.edu.itba.it.paw.web.base.BasePage;

public class ForgotPasswordPage extends BasePage {

	private static final long serialVersionUID = 1L;
	
	public ForgotPasswordPage() {
		add(new ForgotPasswordPanel("forgotPasswordPanel"));
		RecoverPasswordPanel rpp = new RecoverPasswordPanel("recoverPasswordPanel");
		add(rpp);
		rpp.setVisible(false);
		add(new Label("error", " "));
		add(new Label("success", " "));
	}
}
