package controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import services.UserService;

@SuppressWarnings("serial")
public class Picture extends HttpServlet {
	
	private UserService userService = UserService.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		User user = userService.getUsuer(username);
		if (user != null) {
			if (user.getPicture() != null) {
				resp.getOutputStream().write(user.getPicture());
			} else {
				File defaultPicture = new File("src/main/webapp/img/a.jpg");
				resp.getOutputStream().write(fileToByte(defaultPicture));				
			}
		}
	}
	
	private static byte[] fileToByte(File file) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
			}
			fis.close();
		} catch (IOException ex) {
		}
		byte[] bytes = bos.toByteArray();

		return bytes;
	}
}
