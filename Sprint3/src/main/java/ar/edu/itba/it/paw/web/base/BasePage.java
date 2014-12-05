package ar.edu.itba.it.paw.web.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;

import ar.edu.itba.it.paw.domain.PublicityRepo;
import ar.edu.itba.it.paw.domain.RenderedPublicity;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SessionProvider;
import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.common.SearchPage;

public class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;
	private transient String searchText;
	private StringBuilder values = new StringBuilder();
	@SpringBean
	private UserRepo users;
	@SpringBean
	private PublicityRepo publicities;
	private transient RenderedPublicity rendered_publicity;

	@SuppressWarnings("serial")
	public BasePage() {
		rendered_publicity = publicities.fetch_any_rendered_publicity();
		add(new Image("separator1", SocialCthulhuApp.SEPARTOR));
		add(new Link<Void>("home") {

			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}

		}.add(new Image("home_image", SocialCthulhuApp.HOME)));
		Link<Void> publicity_link = new Link<Void>("publicity_link") {

			@Override
			public void onClick() {
				setResponsePage(new RedirectPage(
						rendered_publicity.get_redirection_url()));
			}

		};
		if (rendered_publicity != null)
			publicity_link.add(new Image("publicity", rendered_publicity
					.fetch_resource_reference())
					.setVisible(rendered_publicity != null));
		add(publicity_link.setVisible(rendered_publicity != null));
		Form<BasePage> form = new Form<BasePage>("searchForm") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				SearchPage responsePage = new SearchPage(searchText);
				setResponsePage(responsePage);
			}
		};

		final IModel<String> model = new IModel<String>() {
			@Override
			public String getObject() {
				return searchText;
			}

			@Override
			public void setObject(String object) {
				searchText = object;

				values.append("\n");
				values.append(searchText);
			}

			@Override
			public void detach() {
			}
		};

		form.add(new AutoCompleteTextField<String>("searchField", model) {
			@Override
			protected Iterator<String> getChoices(String input) {
				if (Strings.isEmpty(input)) {
					List<String> emptyList = Collections.emptyList();
					return emptyList.iterator();
				}

				List<String> choices = new ArrayList<String>(10);

				List<User> usernames = users.getAll();

				for (final User u : usernames) {
					final String username = u.getUsername();

					if (username.toUpperCase().startsWith(input.toUpperCase())) {
						choices.add(username);
						if (choices.size() == 10) {
							break;
						}
					}
				}

				return choices.iterator();
			}
		});

		form.add(new Button("searchButton"));
		add(form);
		add(new Image("socialCthulhuTitle", SocialCthulhuApp.TITLE));

		/* session related info */

		Link<Void> logout = new Link<Void>("logout") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				User user = SocialCthulhuSession.get().getUser();
				user.resetToken();
				((SocialCthulhuApp) SocialCthulhuApp.get()).getCookieService()
						.removeCookieIfPresent(getRequest(), getResponse(),
								SessionProvider.REMEMBERME_USER);
				((SocialCthulhuApp) SocialCthulhuApp.get()).getCookieService()
						.removeCookieIfPresent(getRequest(), getResponse(),
								SessionProvider.REMEMBERME_TOKEN);
				((SocialCthulhuSession) getSession()).signOut();
				setResponsePage(getApplication().getHomePage());
			}
		};
		add(logout);
		Image separator2 = new Image("separator2", SocialCthulhuApp.SEPARTOR);
		add(separator2);
		Label user = new Label("user", SocialCthulhuSession.get().getUsername());
		add(user);
		if (!SocialCthulhuSession.get().isSignedIn()) {
			logout.setVisible(false);
			separator2.setVisible(false);
			user.setVisible(false);
		}
	}
}
