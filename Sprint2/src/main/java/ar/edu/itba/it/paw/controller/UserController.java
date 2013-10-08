package ar.edu.itba.it.paw.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.model.RankedHashtag;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.services.HashtagService;
import ar.edu.itba.it.paw.services.UserService;

@Controller
public class UserController {

	private UserService userService;
	private HashtagService hashtagService;

	@Autowired
	public UserController(UserService userService, HashtagService hashtagService) {
		this.userService = userService;
		this.hashtagService = hashtagService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login(HttpSession s) {
		ModelAndView mav = new ModelAndView();
		if (s.getAttribute("userId") != null) {
			mav.setViewName("redirect:profile");
		}
		showTopTenHashtags(mav);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView login(@RequestParam(value = "username", required = true) String username,
								@RequestParam(value = "password", required = true) String password, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = userService.authenticate(username, password);
		if (user != null) {
			session.setAttribute("userId", user.getId());
			mav.setViewName("redirect:profile");
		} else {
			mav.addObject("username", username);
			mav.addObject("error", "Invalid user or password.");
			mav.setViewName("login");
		}
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	/*
	@Override
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
	*/
	
	private void showTopTenHashtags(ModelAndView mav) {
		List<RankedHashtag> top10;

		if (mav.getModel().get("period") == null) {
			top10 = hashtagService.topHashtags(30);
		} else {
			top10 = hashtagService.topHashtags(Integer.valueOf((String) mav
					.getModel().get("period")));
		}

		boolean isempty = top10.size() == 0;
		mav.addObject("previous", "login");

		mav.addObject("ranking", top10);
		mav.addObject("isempty", isempty);
	}
}
