package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class FormController extends HttpServlet {

	protected boolean validate(HttpServletRequest req,
			HttpServletResponse resp, String name, String surname,
			String password, String confirm, String description)
			throws ServletException, IOException {
		if (name.length() == 0 || surname.length() == 0
				|| password.length() == 0 || confirm.length() == 0
				|| description.length() == 0) {
			req.setAttribute("generalError",
					"One or more mandatory fields are empty!");
			return false;
		}
		int pwdlength = password.length();
		if (pwdlength < 8 || pwdlength > 16) {
			req.setAttribute("passwordError",
					"Password must contain between 8 and 16 characters!");
			return false;
		}
		if (!password.equals(confirm)) {
			req.setAttribute("passwordError",
					"Password confirmation doesn't match the password field!");
			return false;
		}
		return true;
	}
}
