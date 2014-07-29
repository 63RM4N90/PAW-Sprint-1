package ar.edu.itba.it.paw.web.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.ocpsoft.prettytime.PrettyTime;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.web.SocialCthulhuSession;
import ar.edu.itba.it.paw.web.common.CommentWrapper;
import ar.edu.itba.it.paw.web.common.CommentWrapperROM;

public class CommentsPanel extends Panel {

	private static final long serialVersionUID = 8914010631219544701L;
	private int userId;
	private boolean favourites;
	@SpringBean
	private UserRepo users;
	@SpringBean
	private CommentRepo comments;

	public CommentsPanel(String id, int user, boolean favs) {
		super(id);
		this.userId = user;
		this.favourites = favs;
		add(new RefreshingView<CommentWrapper>("wrapperComment") {

			private static final long serialVersionUID = -3507194379306183234L;

			@Override
			protected Iterator<IModel<CommentWrapper>> getItemModels() {
				IModel<User> userModel = new EntityModel<User>(User.class,
						userId);
				User commenter = userModel.getObject();
				List<IModel<CommentWrapper>> result = new ArrayList<IModel<CommentWrapper>>();
				List<Comment> commentList = null;
				if (favourites) {
					commentList = new ArrayList<Comment>(
							commenter.getFavourites());
				} else {
					commentList = commenter.getComments();
				}
				List<CommentWrapper> transformedComments = transformComments(
						commentList, commenter);
				for (CommentWrapper c : transformedComments) {
					result.add(new CommentWrapperROM(new EntityModel<Comment>(
							Comment.class, c.getComment()), c
							.getTransformedComment(), commenter));
				}
				return result.iterator();
			}

			@Override
			protected void populateItem(Item<CommentWrapper> item) {

				boolean canShowRecthulhuedFrom = !item
						.getModelObject()
						.getComment()
						.getAuthor()
						.equals(item.getModelObject().getComment()
								.getOriginalAuthor());

				boolean userCanRecthulhu = !SocialCthulhuSession
						.get()
						.getUsername()
						.equals(item.getModelObject().getComment()
								.getAuthor().getUsername());
				System.out.println("CAN RECTHULHU = " + userCanRecthulhu);
				
				boolean alreadyFavourited = users
						.getUser(SocialCthulhuSession.get().getUsername())
						.getFavourites()
						.contains(item.getModelObject().getComment());

				item.add(new MultiLineLabel("transformedComment", item
						.getModelObject().getTransformedComment())
						.setEscapeModelStrings(false));
				Link<CommentWrapper> removeFavouriteLink = new Link<CommentWrapper>(
						"removeFavouriteLink", item.getModel()) {

					private static final long serialVersionUID = -6657885277633930092L;

					@Override
					public void onClick() {
						User user = users.getUser(SocialCthulhuSession.get()
								.getUsername());
						user.removeFavourite(getModelObject().getComment());
					}

				};
				removeFavouriteLink.add(new Label("removeFavourite",
						getString("remove_favourite")));
				removeFavouriteLink.setVisible(alreadyFavourited);
				item.add(removeFavouriteLink);
				Link<CommentWrapper> addFavouriteLink = new Link<CommentWrapper>(
						"addFavouriteLink", item.getModel()) {

					private static final long serialVersionUID = 3039554452059752457L;

					@Override
					public void onClick() {
						CommentWrapper comment = getModelObject();
						users.getUser(SocialCthulhuSession.get().getUsername())
								.addFavourite(comment.getComment());
					}
				};
				addFavouriteLink.add(new Label("addFavourite",
						getString("add_favourite")));
				addFavouriteLink.setVisible(!alreadyFavourited);
				item.add(addFavouriteLink);
				Link<CommentWrapper> recthulhuLink = new Link<CommentWrapper>(
						"recthulhuLink", item.getModel()) {

					private static final long serialVersionUID = -7382250969064495012L;

					@Override
					public void onClick() {
						String comment = getModelObject().getComment()
								.getComment();
						User originalAuthor = getModelObject().getComment()
								.getOriginalAuthor();
						User author = users.getUser(SocialCthulhuSession.get()
								.getUsername());
						Comment rechtulhu = new Comment(author, new Date(),
								comment, comments.getHashtagList(comment,
										author),
								comments.getReferences(comment), originalAuthor);
						if (!author.getComments().contains(rechtulhu))
							comments.addComment(rechtulhu);
					}
				};
				recthulhuLink
						.add(new Label("recthulhu", getString("recthulhu")));
				recthulhuLink.setVisible(userCanRecthulhu);
				item.add(recthulhuLink);
				Link<CommentWrapper> commentUsernameLink = new Link<CommentWrapper>(
						"commentUsernameLink", item.getModel()) {

					private static final long serialVersionUID = 5817420657291774864L;

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(getModelObject()
								.getComment().getAuthor().getId()));
					}
				};
				commentUsernameLink.add(new Label("comment_username", item
						.getModelObject().getComment().getAuthor()
						.getUsername()));
				item.add(commentUsernameLink);
				Label recthuledFrom = new Label("recthuled_from",
						getString("recthuled_from"));
				Link<CommentWrapper> authorUsernameLink = new Link<CommentWrapper>(
						"authorUsernameLink", item.getModel()) {

					private static final long serialVersionUID = -8279779646272929857L;

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(getModelObject()
								.getComment().getOriginalAuthor().getId()));
					}
				};
				authorUsernameLink.setVisible(canShowRecthulhuedFrom);
				recthuledFrom.setVisible(canShowRecthulhuedFrom);
				authorUsernameLink.add(new Label("comment_author", item
						.getModelObject().getComment().getOriginalAuthor()
						.getUsername()));
				item.add(authorUsernameLink);
				item.add(recthuledFrom);
				PrettyTime p = new PrettyTime();
				item.add(new Label("commentDate", p.format(item
						.getModelObject().getComment().getDate())));

				Link<CommentWrapper> deleteCommentLink = new Link<CommentWrapper>(
						"deleteCommentLink", item.getModel()) {

					private static final long serialVersionUID = -6955584208310539151L;

					@Override
					public void onClick() {
						comments.delete(getModelObject().getComment());
					}
				};
				deleteCommentLink.add(new Label("deleteComment",
						getString("delete_comment")));
				item.add(deleteCommentLink);
			}
		});
	}

	private List<CommentWrapper> transformComments(List<Comment> commentList,
			User u) {
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
