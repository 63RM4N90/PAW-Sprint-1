package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import services.UserService;

@SuppressWarnings("serial")
public class EditProfile extends FormController {

	private UserService userService = UserService.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		User userSession = (User) session.getAttribute("user");
		req.setAttribute("sessionUser", userSession);
		setDefaults(req, userSession);
		req.getRequestDispatcher("/WEB-INF/jsp/editProfile.jsp").forward(req,
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
			String password = fileItems.get(2).getString();
			String confirm = fileItems.get(3).getString();
			String description = fileItems.get(4).getString();
			String secretQuestion = fileItems.get(5).getString();
			String secretAnswer = fileItems.get(6).getString();
			FileItem pictureFile = fileItems.get(7);
			System.out.println(name + " " + surname + " " + password + " ");
			byte[] picture;
			User userSession = (User) req.getSession().getAttribute("user");
			if (super.validate(req, resp, name, surname, password, confirm,
					description, secretQuestion, secretAnswer)) {
				if (pictureFile.getName().length() == 0) {
					picture = null;
				} else {
					picture = pictureFile.get();
				}
				User user = new User(name, surname, userSession.getUsername(),
						description, password, picture, secretQuestion,
						secretAnswer, userSession.getRegistrationDate());
				int id = userSession.getId();
				user.setId(id);
				userService.save(user);
				req.getSession().setAttribute("user", user);
				resp.sendRedirect("profile?user=" + user.getUsername());
			} else {
				setDefaults(req, userSession);
				req.getRequestDispatcher("/WEB-INF/jsp/editProfile.jsp")
						.forward(req, resp);
			}
		} catch (FileUploadException e) {
		}
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
