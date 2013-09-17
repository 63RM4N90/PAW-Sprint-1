package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import network.UserDAO;

@SuppressWarnings("serial")
public class LostPassword extends HttpServlet {
	private UserDAO userDao = UserDAO.getInstance();;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("userToRecover");
		User user = userDao.getUser(userName);
		
		if(userName != null && user == null) {
			req.setAttribute("error", "User does not exist");
			req.setAttribute("userSelected", false);
		} else if(user != null){
			req.setAttribute("success", "Recovering password for " + user.getUsername());
			req.setAttribute("user", user);
			req.setAttribute("userSelected", true);
		}
		req.getRequestDispatcher("/WEB-INF/jsp/lostPassword.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String secretAnswerSubmited = req.getParameter("secretAnswer");
		String newPassword = req.getParameter("password");
		String newPasswordConfirm = req.getParameter("confirm");
		String userToRecover = req.getParameter("userToRecover");
		User u = userDao.getUser(userToRecover);
		
		req.setAttribute("userSelected", true);
		if(secretAnswerSubmited != null){
			boolean matches = u.getSecretAnswer().equals(secretAnswerSubmited);
			boolean passwordMatches = newPassword.equals(newPasswordConfirm);
			if(!matches) {
				req.setAttribute("error", "Wrong secret Answer");
				req.setAttribute("success", "Recovering password for " + u.getUsername());
				req.setAttribute("user", u);
			} else {
				if(passwordMatches) {
					req.setAttribute("passwordRecovered", true);
					req.setAttribute("success", "Your password was changed successfully!");
					u.setPassword(newPassword);
					userDao.save(u);
				} else {
					req.setAttribute("error", "Passwords dont match");
					req.setAttribute("success", "Recovering password for " + u.getUsername());
					req.setAttribute("user", u);
				}
			}
		}	
		req.getRequestDispatcher("/WEB-INF/jsp/lostPassword.jsp").forward(req,resp);
	}

}
