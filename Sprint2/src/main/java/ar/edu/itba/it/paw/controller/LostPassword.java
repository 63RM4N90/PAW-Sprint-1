package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.services.UserService;

@SuppressWarnings("serial")
public class LostPassword extends HttpServlet {
	private UserService userService = UserService.getInstance();;
	private static final int MAX_PASSWORD_LENGTH = 16;
	private static final int MIN_PASSWORD_LENGTH = 8;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userName = req.getParameter("userToRecover");
		User user = null;
		if (userName != null) {
			if (userName != "") {
				user = userService.getUser(userName);				
			} else {
				user = new User("", "", "", "", "", null, "", "", null);
			}
		} else {
			userName = "";
			req.setAttribute("userToRecover", userName);
			user = new User("", "", "", "", "", null, "", "", null);
		}
		if (userName != "" && user.getUsername() == "") {
			req.setAttribute("error", "User does not exist");
			req.setAttribute("userSelected", false);
		} else if (user.getUsername() != "") {
			req.setAttribute("success",
					"Recovering password for " + user.getUsername());
			req.setAttribute("user", user);
			req.setAttribute("userSelected", true);
		} else {
			req.setAttribute("userSelected", false);
		}
		req.getRequestDispatcher("/WEB-INF/jsp/lostPassword.jsp").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String secretAnswerSubmited = req.getParameter("secretAnswer");
		String newPassword = req.getParameter("password");
		String newPasswordConfirm = req.getParameter("confirm");
		String userToRecover = req.getParameter("userToRecover");
		User u = userService.getUser(userToRecover);
		req.setAttribute("userSelected", true);
		if (secretAnswerSubmited != null) {
			boolean matches = u.getSecretAnswer().equals(secretAnswerSubmited);
			boolean passwordMatches = false;
			if (newPassword != null && newPasswordConfirm != null) {
				passwordMatches = newPassword.equals(newPasswordConfirm);
			}
			if (!matches) {
				req.setAttribute("error", "Wrong secret Answer");
				req.setAttribute("success",
						"Recovering password for " + u.getUsername());
				req.setAttribute("user", u);
			} else {
				if (passwordMatches
						&& newPassword.length() <= MAX_PASSWORD_LENGTH
						&& newPassword.length() >= MIN_PASSWORD_LENGTH) {
					req.setAttribute("passwordRecovered", true);
					req.setAttribute("success",
							"Your password was changed successfully!");
					u.setPassword(newPassword);
					userService.save(u);
				} else {
					req.setAttribute("error",
							"Passwords dont match or have less than 8 characters or more than 16.");
					req.setAttribute("success",
							"Recovering password for " + u.getUsername());
					req.setAttribute("user", u);
				}
			}
		}
		req.getRequestDispatcher("/WEB-INF/jsp/lostPassword.jsp").forward(req,
				resp);
	}
}
