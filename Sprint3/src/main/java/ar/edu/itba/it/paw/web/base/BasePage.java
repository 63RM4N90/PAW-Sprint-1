package ar.edu.itba.it.paw.web.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuApp;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.common.SearchPage;

public class BasePage extends WebPage {
	
	private static final long serialVersionUID = 1L;
	private String searchText;
	private StringBuilder values = new StringBuilder();
	@SpringBean
	private UserRepo users;
	
	@SuppressWarnings("serial")
	public BasePage() {
		add(new Image("separator1", SocialCthulhuApp.SEPARTOR));
		add(new Link<Void>("home") {

			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
			
		}.add(new Image("home_image", SocialCthulhuApp.HOME)));
		Form<BasePage> form = new Form<BasePage>("searchForm") {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				SearchPage responsePage = new SearchPage(searchText);
				setResponsePage(responsePage);
			}
		};
		
		final IModel<String> model = new IModel<String>()
		        {
		            private String value = null;

		            @Override
		            public String getObject()
		            {
		                return value;
		            }

		            @Override
		            public void setObject(String object)
		            {
		                value = object;

		                values.append("\n");
		                values.append(value);
		            }

		            @Override
		            public void detach()
		            {
		            }
		        };
		
		form.add(new AutoCompleteTextField<String>("searchField", model)
		        {
		            @Override
		            protected Iterator<String> getChoices(String input)
		            {
		                if (Strings.isEmpty(input))
		                {
		                    List<String> emptyList = Collections.emptyList();
		                    return emptyList.iterator();
		                }

		                List<String> choices = new ArrayList<String>(10);

		                List<User> usernames = users.getAll();

		                for (final User u : usernames)
		                {
		                    final String username = u.getUsername();

		                    if (username.toUpperCase().startsWith(input.toUpperCase()))
		                    {
		                        choices.add(username);
		                        if (choices.size() == 10)
		                        {
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

		/*session related info*/
		
		SocialCthulhuSession session = (SocialCthulhuSession) getSession();
		Link<Void> logout = new Link<Void>("logout") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onClick() {
				((SocialCthulhuSession) getSession()).signOut();
				setResponsePage(getApplication().getHomePage());
			}
		};
		add(logout);
		Image separator2 = new Image("separator2", SocialCthulhuApp.SEPARTOR); 
		add(separator2);
		Label user = new Label("user", session.getUsername());
		add(user);
		if(!session.isSignedIn()) {
			logout.setVisible(false);
			separator2.setVisible(false);
			user.setVisible(false);
		}
	}
	
	protected String getProcessedComment(String comment) {
		// Search for URLs
		String aux = comment;
		if (aux != null && aux.contains("http:")) {
			int indexOfHttp = aux.indexOf("http:");
			int endPoint = (aux.indexOf(' ', indexOfHttp) != -1) ? aux.indexOf(
					' ', indexOfHttp) : aux.length();
			String url = aux.substring(indexOfHttp, endPoint);
			String targetUrlHtml = "<a href='" + url + "' target='_blank'>"
					+ url + "</a>";
			aux = aux.replace(url, targetUrlHtml);
		}

		// Search for Hashtags
		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		String[] words = aux.split(" ");
		String ans = "";
		String result = "";

		for (String word : words) {
			Matcher matcher = pattern.matcher(word);
			if (matcher.find()) {
				result = matcher.group();
				result = result.replace(" ", "");
				String search = result.replace("#", "");
				String searchHTML = "<a href='../../hashtag/detail?tag="
						+ search + "'>" + result + "</a>";
				ans += word.replace(result, searchHTML) + " ";
			} else {
				ans += word + " ";
			}
		}

		// Search for Users
		patternStr = "@([A-Za-z0-9_]+)";
		pattern = Pattern.compile(patternStr);
		words = ans.split(" ");
		ans = "";
		for (String word : words) {
			Matcher matcher = pattern.matcher(word);
			if (matcher.find()) {
				result = matcher.group();
				result = result.replace(" ", "");
				String search = result.replace("@", "");
				String userHTML = "<a href='./" + search + "'>" + result
						+ "</a>";
				ans += word.replace(result, userHTML) + " ";
			} else {
				ans += word + " ";
			}
		}
		return ans;
	}
}
