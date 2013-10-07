package ar.edu.itba.it.paw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.services.UserService;

@SuppressWarnings("serial")
public class Picture extends HttpServlet {
	
	private UserService userService = UserService.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		User user = userService.getUser(username);
		if (user != null) {
			if (user.getPicture() != null) {
				resp.getOutputStream().write(user.getPicture());
			}
		}
	}
}
