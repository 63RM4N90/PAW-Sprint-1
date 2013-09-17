package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

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

		DiskFileUpload fu = new DiskFileUpload();
		try {
			List<FileItem> fileItems = fu.parseRequest(req);
			String name = fileItems.get(0).getString();
			String surname = fileItems.get(1).getString();
			String username = fileItems.get(2).getString();
			String password = fileItems.get(3).getString();
			String confirm = fileItems.get(4).getString();
			String description = fileItems.get(5).getString();
			String secretQuestion = fileItems.get(6).getString();
			String secretAnswer = fileItems.get(7).getString();
			byte[] picture = fileItems.get(8).get();
			if (userService.userExists(username)) {
				req.setAttribute("usernameError", "User already exists!");
				fillInputs(req);
				req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp")
						.forward(req, resp);
			} else {
				int pwdlength = password.length();
				if (pwdlength < 8 || pwdlength > 16) {
					req.setAttribute("passwordError",
							"Password must contain between 8 and 16 characters!");
					fillInputs(req);
					req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp")
							.forward(req, resp);
				}
				if (!password.equals(confirm)) {
					req.setAttribute("passwordError",
							"Password confirmation doesn't match the password field!");
					fillInputs(req);
					req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp")
							.forward(req, resp);
				} else {
					
					User user = new User(name, surname, username, description,
							password, picture, secretQuestion, secretAnswer);
					userService.save(user);
					user = userService.getUsuer(username);
					req.getSession().setAttribute("user", user);
					resp.sendRedirect("profile?user=" + user.getUsername());
				}
			}
		} catch (FileUploadException e) {
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
