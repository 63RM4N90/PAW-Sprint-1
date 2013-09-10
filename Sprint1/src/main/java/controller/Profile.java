package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import services.CommentService;
import services.UserService;

@SuppressWarnings("serial")
public class Profile extends HttpServlet {
	
	private CommentService commentService = CommentService.getInstance();
	private UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("user");
		User user = userService.getUsuer(username);
		req.setAttribute("username", username);
		req.setAttribute("comments", commentService.getComments(user));
		req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String comment = req.getParameter("comment");
	}
}
