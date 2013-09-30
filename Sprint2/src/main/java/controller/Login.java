package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RankedHashtag;
import model.User;
import services.HashtagService;
import services.UserService;

@SuppressWarnings("serial")
public class Login extends HttpServlet {

	private UserService userService = UserService.getInstance();
	private HashtagService hashtagService = HashtagService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		if (user == null) {
			showTopTenHashtags(req);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp")
			.forward(req, resp);
		} else {
			resp.sendRedirect("profile?user=" + user.getUsername());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		showTopTenHashtags(req);
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = userService.authenticate(username, password);
		if (user != null) {
			req.getSession().setAttribute("user", user);
			resp.sendRedirect("profile?user=" + user.getUsername());
		} else {
			req.setAttribute("username", username);
			req.setAttribute("error", "Invalid user or password.");
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req,
					resp);
		}
	}

	private void showTopTenHashtags(HttpServletRequest req) {
		List<RankedHashtag> top10;

		if (req.getParameter("period") == null) {
			top10 = hashtagService.topHashtags(30);
		} else {
			top10 = hashtagService.topHashtags(Integer.valueOf(req
					.getParameter("period")));
		}

		boolean isempty = top10.size() == 0;
		req.setAttribute("previous", "login");

		req.setAttribute("ranking", top10);
		req.setAttribute("isempty", isempty);
	}
}
