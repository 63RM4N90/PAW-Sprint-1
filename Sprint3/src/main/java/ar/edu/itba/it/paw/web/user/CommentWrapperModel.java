package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.common.CommentWrapper;

@SuppressWarnings("serial")
public abstract class CommentWrapperModel extends LoadableDetachableModel<List<CommentWrapper>> {

	@SpringBean
	private UserRepo users;
	
	public CommentWrapperModel() {
		super();
		Injector.get().inject(this);
	}

	@Override
	protected List<CommentWrapper> load() {
		 List<Comment> comments = transformableLoad();
		return transformComments(comments, getRelatedUser());
	}
	
	protected User getRelatedUser() {
		return SocialCthulhuSession.get().getUser();
	}
	
	private List<CommentWrapper> transformComments(List<Comment> commentList, User u) {
		List<CommentWrapper> result = new ArrayList<CommentWrapper>();
		for (Comment c : commentList) {
			result.add(new CommentWrapper(c,
					getProcessedComment(c.getComment()), c.favouritedBy(u)));
		}
		return result;
	}

	private String getProcessedComment(String comment) {
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
				String searchHTML = "<a href='../hashtag/" + search + "'>"
						+ result + "</a>";
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
				String userHTML = "";
				if (users.getUser(search) != null) {
					userHTML = "<a href='../profile/" + search + "'>" + result
							+ "</a>";
				} else {
					userHTML = "@" + search;
				}
				ans += word.replace(result, userHTML) + " ";
			} else {
				ans += word + " ";
			}
		}
		return ans;
	}
	
	protected abstract List<Comment> transformableLoad();

}
