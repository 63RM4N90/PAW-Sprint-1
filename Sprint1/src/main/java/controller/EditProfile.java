package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import services.UserService;

@SuppressWarnings("serial")
public class EditProfile extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		User userSession = (User) session.getAttribute("user");			
		req.setAttribute("userSession", userSession);
		setDefaults(req,userSession);
		req.getRequestDispatcher("/WEB-INF/jsp/editProfile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		int pwdlength = req.getParameter("password").length();
		User userSession = (User) req.getSession().getAttribute("user");
		UserService userService = UserService.getInstance();
		req.setAttribute("user", userSession.getName());
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
					userSession.getUsername(),
					req.getParameter("description"),
					req.getParameter("password"),
					null,
					req.getParameter("secretQuestion"),
					req.getParameter("secretAnswer"));
			user.setId(userSession.getId());
			userService.save(user);
			req.getSession().setAttribute("user", user);
			resp.sendRedirect("profile?user=" + user.getUsername());
		}
	}

	private void fillInputs(HttpServletRequest req) {
		req.setAttribute("name", req.getParameter("name"));
		req.setAttribute("surname", req.getParameter("surname"));
		req.setAttribute("password", req.getParameter("password"));
		req.setAttribute("confirm", req.getParameter("confirm"));
		req.setAttribute("description", req.getParameter("description"));
		req.setAttribute("secretQuestion", req.getParameter("secretQuestion"));
		req.setAttribute("secretAnswer", req.getParameter("secretAnswer"));
	}
	
	private void setDefaults(HttpServletRequest req, User original) {
		req.setAttribute("name", original.getName());
		req.setAttribute("surname", original.getSurname());
		req.setAttribute("password", original.getPassword());
		req.setAttribute("confirm", original.getPassword());
		req.setAttribute("description", original.getDescription());
		req.setAttribute("secretQuestion", original.getSecretQuestion());
		req.setAttribute("secretAnswer", original.getSecretAnswer());
	}
}
