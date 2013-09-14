package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Comment;
import model.User;
import services.CommentService;
import services.UserService;

@SuppressWarnings("serial")
public class Profile extends HttpServlet {

	private CommentService commentService = CommentService.getInstance();
	private UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("user");
		HttpSession session = req.getSession(false);
		User profile = userService.getUsuer(username);
		User userSession = (User) session.getAttribute("user");			
		if(userSession!=null) {
			if(profile.getUsername().equals(userSession.getUsername())){
				req.setAttribute("isOwner",true);
			}
		}
		req.setAttribute("user", profile);
		req.setAttribute("userSession", userSession);
		req.setAttribute("comments", commentService.getComments(profile));
		req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		String aux = req.getParameter("comment");
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (aux.length() > 0) {
			String commentString = commentService.getProcessedComment(aux);
			Comment comment = new Comment(user, new Date(), commentString,
					commentService.getHashtagList(commentString, user));
			commentService.save(comment);
		}
		resp.sendRedirect("profile?user=" + user.getUsername());
	}
}
