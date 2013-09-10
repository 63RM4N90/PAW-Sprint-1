package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import network.UserDAO;

@SuppressWarnings("serial")
public class Login extends HttpServlet {

	private UserDAO userDao = UserDAO.getInstance();

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
		User user = userDao.authenticate(username, password);
		if (user != null) {
			
		} else {
			req.setAttribute("username", username);
			req.setAttribute("error", "Invalid user or password.");
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req,
					resp);
		}
	}
}
