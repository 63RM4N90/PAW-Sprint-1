package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import services.UserService;

@SuppressWarnings("serial")
public class Registration extends FormController {

	private UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		fillInputs("", "", "", "", "", "", req);

		req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req,
				resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		DiskFileUpload fu = new DiskFileUpload();
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> fileItems = fu.parseRequest(req);
			String name = fileItems.get(0).getString();
			String surname = fileItems.get(1).getString();
			String username = fileItems.get(2).getString();
			String password = fileItems.get(3).getString();
			String confirm = fileItems.get(4).getString();
			String description = fileItems.get(5).getString();
			String secretQuestion = fileItems.get(6).getString();
			String secretAnswer = fileItems.get(7).getString();
			FileItem pictureFile = fileItems.get(8);
			byte[] picture;
			if (pictureFile.getName().length() == 0) {
				System.out.println("FOTO ES NULL");
				picture = null;
			} else {
				picture = pictureFile.get();
			}
			if (userService.userExists(username)) {
				req.setAttribute("usernameError", "User already exists!");
				fillInputs(name, surname, username, description,
						secretQuestion, secretAnswer, req);
				req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp")
						.forward(req, resp);
			} else {
				if (super.validate(req, resp, name, surname, password, confirm,
						description)
						&& secretQuestion.length() != 0
						&& secretAnswer.length() != 0) {
					User user = new User(name, surname, username, description,
							password, picture, secretQuestion, secretAnswer,
							new Date());
					userService.save(user);
					user = userService.getUser(username);
					req.getSession().setAttribute("user", user);
					resp.sendRedirect("profile?user=" + user.getUsername());
				} else {
					fillInputs(name, surname, username, description,
							secretQuestion, secretAnswer, req);
					req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp")
							.forward(req, resp);
				}
			}
		} catch (FileUploadException e) {
		}
	}

	private void fillInputs(String name, String surname, String username,
			String description, String secretQuestion, String secretAnswer,
			HttpServletRequest req) {
		req.setAttribute("name", name);
		req.setAttribute("surname", surname);
		req.setAttribute("username", username);
		req.setAttribute("password", "");
		req.setAttribute("confirm", "");
		req.setAttribute("description", description);
		req.setAttribute("secretQuestion", secretQuestion);
		req.setAttribute("secretAnswer", secretAnswer);
	}
}
