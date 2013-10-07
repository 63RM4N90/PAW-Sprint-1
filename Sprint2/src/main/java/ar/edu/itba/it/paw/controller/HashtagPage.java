package ar.edu.itba.it.paw.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.Hashtag;
import ar.edu.itba.it.paw.services.CommentService;
import ar.edu.itba.it.paw.services.HashtagService;

@SuppressWarnings("serial")
public class HashtagPage extends AbstractController {

	// private CommentService commentService = CommentService.getInstance();
	// private UserService userService = UserService.getInstance();
	private HashtagService hashtagService = HashtagService.getInstance();
	private CommentService commentService = CommentService.getInstance();

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
