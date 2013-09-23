package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import services.UserService;

@SuppressWarnings("serial")
public class Search extends HttpServlet {

	UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/results.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String search = req.getParameter("search");

		if (search == null) {
			search = " ";
		}
		List<User> users = userService.getUsersWithName(search);
		req.setAttribute("users", users);
		req.setAttribute("search", search);
		req.getRequestDispatcher("/WEB-INF/jsp/results.jsp").forward(req, resp);
	}
}
