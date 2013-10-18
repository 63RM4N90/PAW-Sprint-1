package ar.edu.itba.it.paw.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.form.UserForm;
import ar.edu.itba.it.paw.formValidators.PasswordRecoveryFormValidator;
import ar.edu.itba.it.paw.formValidators.UserFormValidator;
import ar.edu.itba.it.paw.model.Comment;
import ar.edu.itba.it.paw.model.RankedHashtag;
import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.services.CommentService;
import ar.edu.itba.it.paw.services.HashtagService;
import ar.edu.itba.it.paw.services.UserService;

@Controller
public class UserController {

	private UserService userService;
	private HashtagService hashtagService;
	private CommentService commentService;
	private UserFormValidator userFormValidator;

	@Autowired
	public UserController(UserService userService,
			HashtagService hashtagService, CommentService commentService,
			UserFormValidator userFormValidator) {
		this.userService = userService;
		this.hashtagService = hashtagService;
		this.commentService = commentService;
		this.userFormValidator = userFormValidator;
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

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userForm", new UserForm());
		showTopTenHashtags(mav);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView registration(HttpServletRequest req, UserForm userForm,
			Errors errors, HttpSession session) {
		// DiskFileUpload fu = new DiskFileUpload();
		ModelAndView mav = new ModelAndView();
		List<ObjectError> errorList = errors.getAllErrors();
		for (ObjectError each : errorList) {
			System.out.println(each.toString());
		}
		userFormValidator.validate(userForm, errors);

		if (errors.hasErrors()) {
			return null;
		}

		try {
			userService.save(userForm.build());
		} catch (Exception e) {
			errors.rejectValue("username", "duplicated");
			return null;
		}
		session.setAttribute("username", userForm.getUsername());
		mav.setViewName("redirect:profile?user=" + userForm.getUsername());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value = "username", required = false) User user,
			@RequestParam(value = "password", required = false) String password,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if (user != null) {
			session.setAttribute("username", user.getUsername());
			mav.setViewName("redirect:profile?user=" + user.getUsername());
		} else {
			// mav.addObject("username", username);
			mav.addObject("error", "Invalid user or password.");
			mav.setViewName("login");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView profile(
			@RequestParam(value = "user", required = false) User profile,
			@RequestParam(value = "period", required = false) Integer period,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User userSession = userService.getUser((String) session
				.getAttribute("username"));

		showTopTenHashtags(mav);

		if (profile == null) {
			if (userSession != null) {
				if (period == null) {
					period = 30;					
				}
				mav.setViewName("redirect:profile?user="
						+ userSession.getUsername() + "&period=" + period);
			} else {
				mav.setViewName("user/login");
			}
		} else {
			if (userSession != null) {
				if (profile.getUsername().equals(userSession.getUsername())) {
					mav.addObject("isOwner", true);
				}
			}
			session.setAttribute("user", profile);
			mav.addObject("isEmptyPicture", profile.getPicture() == null);
			List<Comment> comments = commentService.getComments(profile);
			for (Comment comment : comments) {
				comment.setComment(getProcessedComment(comment.getComment()));
			}
			mav.addObject("comments", comments);
		}
		
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editProfile(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String username = (String) session.getAttribute("username");
		User userSession = userService.getUser(username);
		mav.addObject("sessionUser", userSession);
		setDefaults(mav, userSession);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView recoverPassword(
			@RequestParam(value = "userToRecover", required = false) String username) {
		ModelAndView mav = new ModelAndView();
		User user = null;
		if (username != null) {
			if (username != "") {
				user = userService.getUser(username);
			} else {
				user = new User("", "", "", "", "", null, "", "", null);
			}
		} else {
			username = "";
			mav.addObject("userToRecover", username);
			user = new User("", "", "", "", "", null, "", "", null);
		}
		if (username != "" && user.getUsername() == "") {
			mav.addObject("error", "User does not exist");
			mav.addObject("userSelected", false);
		} else if (user.getUsername() != "") {
			mav.addObject("success",
					"Recovering password for " + user.getUsername());
			mav.addObject("user", user);
			mav.addObject("userSelected", true);
		} else {
			mav.addObject("userSelected", false);
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView recoverPassword(
			@RequestParam(value = "secretAnswer", required = false) String secretAnswerSubmited,
			@RequestParam(value = "password", required = false) String newPassword,
			@RequestParam(value = "confirm", required = false) String newPasswordConfirm,
			@RequestParam(value = "userToRecover", required = false) String userToRecover) {
		ModelAndView mav = new ModelAndView();
		User u = userService.getUser(userToRecover);
		mav.addObject("userSelected", true);
		PasswordRecoveryFormValidator validator = new PasswordRecoveryFormValidator(
				secretAnswerSubmited, newPassword, newPasswordConfirm,
				userToRecover);
		// validator.validate(target, errors);
		return mav;
	}

	/*
	 * @Override protected void doGet(HttpServletRequest req,
	 * HttpServletResponse resp) throws ServletException, IOException { User
	 * user = (User) req.getSession().getAttribute("user"); if (user == null) {
	 * showTopTenHashtags(req);
	 * getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp")
	 * .forward(req, resp); } else { resp.sendRedirect("profile?user=" +
	 * user.getUsername()); } }
	 * 
	 * @Override protected void doPost(HttpServletRequest req,
	 * HttpServletResponse resp) throws ServletException, IOException {
	 * showTopTenHashtags(req); String username = req.getParameter("username");
	 * String password = req.getParameter("password"); User user =
	 * userService.authenticate(username, password); if (user != null) {
	 * req.getSession().setAttribute("user", user);
	 * resp.sendRedirect("profile?user=" + user.getUsername()); } else {
	 * req.setAttribute("username", username); req.setAttribute("error",
	 * "Invalid user or password.");
	 * req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp); }
	 * }
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

	public String getProcessedComment(String comment) {
		// Search for URLs
		if (comment != null && comment.contains("http:")) {
			int indexOfHttp = comment.indexOf("http:");
			int endPoint = (comment.indexOf(' ', indexOfHttp) != -1) ? comment
					.indexOf(' ', indexOfHttp) : comment.length();
			String url = comment.substring(indexOfHttp, endPoint);
			String targetUrlHtml = "<a href='" + url + "' target='_blank'>"
					+ url + "</a>";
			comment = comment.replace(url, targetUrlHtml);
		}

		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(comment);
		String result = "";

		// Search for Hashtags
		while (matcher.find()) {
			result = matcher.group();
			result = result.replace(" ", "");
			String search = result.replace("#", "");
			String searchHTML = "<a href='./hashtag?tag=" + search + "'>"
					+ result + "</a>";
			comment = comment.replace(result, searchHTML);
		}
		return comment;
	}

	private void setDefaults(ModelAndView mav, User original) {
		mav.addObject("name", original.getName());
		mav.addObject("surname", original.getSurname());
		mav.addObject("password", original.getPassword());
		mav.addObject("confirm", original.getPassword());
		mav.addObject("description", original.getDescription());
	}

	private void fillInputs(String name, String surname, String username,
			String description, String secretQuestion, String secretAnswer,
			ModelAndView mav) {
		mav.addObject("name", name);
		mav.addObject("surname", surname);
		mav.addObject("username", username);
		mav.addObject("password", "");
		mav.addObject("confirm", "");
		mav.addObject("description", description);
		mav.addObject("secretQuestion", secretQuestion);
		mav.addObject("secretAnswer", secretAnswer);
	}
}
