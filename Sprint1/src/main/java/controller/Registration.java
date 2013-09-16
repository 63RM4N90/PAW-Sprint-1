package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import services.UserService;

@SuppressWarnings("serial")
public class Registration extends HttpServlet {

	private UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		//DiskFileUpload du = new DiskFileUpload();
		
		String username = req.getParameter("username");
		if (userService.userExists(username)) {
			System.out.println("1");
			req.setAttribute("usernameError", "User already exists!");
			fillInputs(req);
			req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(
					req, resp);
		} else {
			int pwdlength = req.getParameter("password").length();
			if (pwdlength < 8 || pwdlength > 16) {
				req.setAttribute("passwordError","Password must contain between 8 and 16 characters!");
				fillInputs(req);
				req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
			}
			if (!req.getParameter("password").equals(req.getParameter("confirm"))) {
				req.setAttribute("passwordError","Password confirmation doesn't match the password field!");
				fillInputs(req);
				req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
			} else {
				User user = new User(req.getParameter("name"),
						req.getParameter("surname"),
						req.getParameter("username"),
						req.getParameter("description"),
						req.getParameter("password"), null,
						req.getParameter("secretQuestion"),
						req.getParameter("secretAnswer"));
				userService.save(user);
				user = userService.getUsuer(username);
				req.getSession().setAttribute("user", user);
				resp.sendRedirect("profile?user=" + user.getUsername());
			}
		}
	}

	private void fillInputs(HttpServletRequest req) {
		req.setAttribute("name", req.getParameter("name"));
		req.setAttribute("surname", req.getParameter("surname"));
		req.setAttribute("description", req.getParameter("description"));
		req.setAttribute("username", req.getParameter("username"));
		req.setAttribute("secretQuestion", req.getParameter("secretQuestion"));
		req.setAttribute("secretAnswer", req.getParameter("secretAnswer"));
	}
}
