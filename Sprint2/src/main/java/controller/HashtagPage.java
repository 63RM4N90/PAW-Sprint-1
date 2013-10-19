package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Comment;
import model.Hashtag;
import services.CommentServiceAUX;
import services.HashtagService;
//import services.CommentService;
//import services.UserService;

@SuppressWarnings("serial")
public class HashtagPage extends AbstractController {

	// private CommentService commentService = CommentService.getInstance();
	// private UserService userService = UserService.getInstance();
	private HashtagService hashtagService = HashtagService.getInstance();
	private CommentServiceAUX commentService = CommentServiceAUX.getInstance();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String hashtagStr = req.getParameter("tag");
		Hashtag hashtag = hashtagService.getHashtag(hashtagStr);
		// req.setAttribute("user", hashtag.getAuthor());
		req.setAttribute("tag", hashtag);
		List<Comment> comments = commentService.getComments(hashtagStr);
		for (Comment comment : comments) {
			comment.setComment(getProcessedComment(comment.getComment()));
		}
		req.setAttribute("comments", comments);
		req.getRequestDispatcher("/WEB-INF/jsp/tag.jsp").forward(req, resp);
	}
}
