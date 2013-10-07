package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.HashtagService;
import services.UserService;

@Controller
public class LoginController {

	private UserService userService;
	private HashtagService hashtagService;
	
	@Autowired
	public LoginController(UserService userService, HashtagService hashtagService) {
		this.userService = userService;
		this.hashtagService = hashtagService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	/*@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		if (user == null) {
			showTopTenHashtags(req);
			getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp")
			.forward(req, resp);
		} else {
			resp.sendRedirect("profile?user=" + user.getUsername());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		showTopTenHashtags(req);
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = userService.authenticate(username, password);
		if (user != null) {
			req.getSession().setAttribute("user", user);
			resp.sendRedirect("profile?user=" + user.getUsername());
		} else {
			req.setAttribute("username", username);
			req.setAttribute("error", "Invalid user or password.");
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req,
					resp);
		}
	}

	private void showTopTenHashtags(HttpServletRequest req) {
		List<RankedHashtag> top10;

		if (req.getParameter("period") == null) {
			top10 = hashtagService.topHashtags(30);
		} else {
			top10 = hashtagService.topHashtags(Integer.valueOf(req
					.getParameter("period")));
		}

		boolean isempty = top10.size() == 0;
		req.setAttribute("previous", "login");

		req.setAttribute("ranking", top10);
		req.setAttribute("isempty", isempty);
	}*/
}
