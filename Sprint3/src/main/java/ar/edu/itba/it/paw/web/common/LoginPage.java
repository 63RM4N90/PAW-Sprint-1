package ar.edu.itba.it.paw.web.common;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SessionProvider;
import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.user.ForgotPasswordPage;
import ar.edu.itba.it.paw.web.user.ProfilePage;

public class LoginPage extends BasePage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private UserRepo users;

	private String username;
	private String password;
	private boolean rememberMe;

	public LoginPage() {
		if (((SocialCthulhuSession) getSession()).isSignedIn()) {
			SocialCthulhuSession session = SocialCthulhuSession.get();
			setResponsePage(new ProfilePage(new PageParameters().set(
					"username", users.getUser(session.getUsername())
							.getUsername())));
		}
		add(new FeedbackPanel("feedback"));
		Form<LoginPage> form = new Form<LoginPage>("loginForm",
				new CompoundPropertyModel<LoginPage>(this)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				SocialCthulhuSession session = SocialCthulhuSession.get();

				if (session.signIn(username, password, users)) {
					User loggedUser = users.getUser(session.getUsername());
					if (rememberMe) {
						String token = String.valueOf(System.currentTimeMillis());
						loggedUser.setToken(token);
						CookieService cookieService = ((SocialCthulhuApp) SocialCthulhuApp.get()).getCookieService();
						cookieService.saveCookie(getResponse(), SessionProvider.REMEMBERME_USER, username, 30);
						cookieService.saveCookie(getResponse(), SessionProvider.REMEMBERME_TOKEN, token, 30);
					}
					continueToOriginalDestination();
					setResponsePage(new ProfilePage(new PageParameters().set(
							"username", loggedUser.getUsername())));
				} else {
					error(getString("invalidCredentials"));
				}
			}
		};

		form.add(new TextField<String>("username").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new Button("login", new ResourceModel("login")));
		form.add(new CheckBox("rememberMe", new PropertyModel<Boolean>(this,
				"rememberMe")));
		add(form);
		add(new BookmarkablePageLink<Void>("forgotPassword",
				ForgotPasswordPage.class));
		add(new BookmarkablePageLink<Void>("registration",
				RegistrationPage.class));
		add(new TopTenHashtagsPanel("top10hashtags"));
	}
}
