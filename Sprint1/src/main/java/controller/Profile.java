package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Comment;
import model.Hashtag;
import model.User;
import services.CommentService;
import services.HashtagService;
import services.UserService;

@SuppressWarnings("serial")
public class Profile extends AbstractController {
	private CommentService commentService = CommentService.getInstance();
	private UserService userService = UserService.getInstance();
	private HashtagService hashtagService = HashtagService.getInstance();
	private static final int MAX_COMMENT_LENGTH = 140;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("user");
		HttpSession session = req.getSession(false);
		User userSession = (User) session.getAttribute("user");

		List<Hashtag> top10;

		if (req.getParameter("period") == null) {
			top10 = hashtagService.topHashtags(30);
		} else {
			top10 = hashtagService.topHashtags(Integer.valueOf(req
					.getParameter("period")));
		}

		if (username == null) {
			if (userSession != null) {
				String period = req.getParameter("period");
				if (period == null) {
					period = "30";
				}
				resp.sendRedirect("profile?user=" + userSession.getUsername()
						+ "&period=" + period);
			} else {
				req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req,
						resp);
			}
		} else {
			User profile = userService.getUser(username);
			if (profile != null) {
				if (userSession != null) {
					if (profile.getUsername().equals(userSession.getUsername())) {
						req.setAttribute("isOwner", true);
					}
				}
				req.setAttribute("previous", "profile");
				req.setAttribute("ranking", top10);
				req.setAttribute("user", profile);
				req.setAttribute("isEmptyPicture", profile.getPicture() == null);
				List<Comment> comments = commentService.getComments(profile);
				for (Comment comment : comments) {
					comment.setComment(getProcessedComment(comment.getComment()));
				}
				req.setAttribute("comments", comments);
			} else {
				resp.sendRedirect("profile");
				return;
			}
			req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req,
					resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String aux = req.getParameter("comment");
		HttpSession session = req.getSession();
		List<Hashtag> top10;

		if (req.getParameter("period") == null) {
			top10 = hashtagService.topHashtags(30);
		} else {
			top10 = hashtagService.topHashtags(Integer.valueOf(req
					.getParameter("period")));
		}

		req.setAttribute("previous", "profile");
		req.setAttribute("ranking", top10);
		User user = (User) session.getAttribute("user");
		if (aux.length() > 0 && aux.length() < MAX_COMMENT_LENGTH) {
			Comment comment = new Comment(user, new Date(), aux,
					commentService.getHashtagList(aux, user));
			commentService.save(comment);
		}
		resp.sendRedirect("profile?user=" + user.getUsername());
	}
}
