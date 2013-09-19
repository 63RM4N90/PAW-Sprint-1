package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.CommentService;

@SuppressWarnings("serial")
public class Commentary extends HttpServlet {

	private CommentService commentService = CommentService.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String param = req.getParameter("commentid");
		String user = req.getParameter("user");
		if (param != null) {
			int id = Integer.parseInt(param);
			commentService.removeComment(id);
		}
		if (user == null) {
			user = "";
		}
		resp.sendRedirect("profile?user=" + user);
	}
}
