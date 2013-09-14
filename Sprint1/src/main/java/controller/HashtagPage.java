package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Hashtag;
import network.HashtagDAO;
//import services.CommentService;
import services.HashtagService;
//import services.UserService;

@SuppressWarnings("serial")
public class HashtagPage extends HttpServlet{

	//private CommentService commentService = CommentService.getInstance();
	//private UserService userService = UserService.getInstance();
	private HashtagService hashtagService = HashtagService.getInstance();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String hashtagStr = req.getParameter("tag");
		Hashtag hashtag = hashtagService.getHashtag(hashtagStr);
		//req.setAttribute("user", hashtag.getAuthor());
		req.setAttribute("tag",hashtag);
		req.setAttribute("comments", hashtagService.getComments(hashtagStr));
		req.getRequestDispatcher("/WEB-INF/jsp/tag.jsp").forward(req, resp);
		
		System.out.println(hashtag.getHashtag());
	}
	
}
