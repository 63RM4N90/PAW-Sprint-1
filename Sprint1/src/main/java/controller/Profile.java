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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("user");
		List<Hashtag> top10 = hashtagService.TopHashtags(7);
		HttpSession session = req.getSession(false);
		User profile = userService.getUsuer(username);
		if (profile != null) {
			User userSession = (User) session.getAttribute("user");
			if (userSession != null) {
				if (profile.getUsername().equals(userSession.getUsername())) {
					req.setAttribute("isOwner", true);
				}
			}
			req.setAttribute("user", profile);
			req.setAttribute("userSession", userSession);
			req.setAttribute("ranking", top10);
			List<Comment> comments = commentService.getComments(profile);
			for (Comment comment : comments) {
				comment.setComment(getProcessedComment(comment.getComment()));
			}
			req.setAttribute("comments", comments);
		}
		req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String aux = req.getParameter("comment");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (aux.length() > 0) {
			Comment comment = new Comment(user, new Date(), aux,
					commentService.getHashtagList(aux, user));
			commentService.save(comment);
		}
		resp.sendRedirect("profile?user=" + user.getUsername());
	}
}
