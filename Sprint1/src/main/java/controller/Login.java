package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import services.UserService;

@SuppressWarnings("serial")
public class Login extends HttpServlet {

	private UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
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
}
