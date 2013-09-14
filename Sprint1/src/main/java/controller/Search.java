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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String search = req.getParameter("search");
		List<User> users = userService.getUsersWithName(search);
		req.setAttribute("search", search);
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/jsp/results.jsp").forward(req, resp);
	}
}
