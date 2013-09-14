package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import network.HashtagDAO;
import services.UserService;

@SuppressWarnings("serial")
public class Search extends HttpServlet {

	HashtagDAO hashtagDao = HashtagDAO.getInstance();
	UserService userService = UserService.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String search = req.getParameter("search");
		if (search.charAt(0) == '#') {
			String hashtag = new String(search.toCharArray(), 1, search.length()-1);
			
		} else {
			User user = userService.getUsuer(search);
			if (user != null) {
				resp.sendRedirect("profile?user=" + user.getUsername());
			}
		}
	}
}
