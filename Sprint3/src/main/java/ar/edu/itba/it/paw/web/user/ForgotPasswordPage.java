package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import ar.edu.itba.it.paw.web.base.BasePage;

public class ForgotPasswordPage extends BasePage {

	private static final long serialVersionUID = 1L;
	
	public ForgotPasswordPage() {
		add(new FeedbackPanel("errorPanel"));
		add(new ForgotPasswordPanel("forgotPasswordPanel"));
		add(new Label("success", " "));
	}
}
