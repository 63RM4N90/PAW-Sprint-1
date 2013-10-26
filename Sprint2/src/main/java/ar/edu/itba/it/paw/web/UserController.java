package ar.edu.itba.it.paw.web;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.command.EditUserForm;
import ar.edu.itba.it.paw.command.UserForm;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.HashtagRepo;
import ar.edu.itba.it.paw.domain.RankedHashtag;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;
import ar.edu.itba.it.paw.validator.EditUserFormValidator;
import ar.edu.itba.it.paw.validator.UserFormValidator;

@Controller
public class UserController {

	private UserRepo userRepo;
	private HashtagRepo hashtagRepo;
	private CommentRepo commentRepo;
	private UserFormValidator userFormValidator;
	private EditUserFormValidator editUserFormValidator;
	private static final int MAX_COMMENT_LENGTH = 140;
	private static final int MAX_PASSWORD_LENGTH = 16;
	private static final int MIN_PASSWORD_LENGTH = 8;

	@Autowired
	public UserController(UserRepo userService, HashtagRepo hashtagService,
			CommentRepo commentService, UserFormValidator userFormValidator,
			EditUserFormValidator editUserFormValidator) {
		this.userRepo = userService;
		this.hashtagRepo = hashtagService;
		this.commentRepo = commentService;
		this.userFormValidator = userFormValidator;
		this.editUserFormValidator = editUserFormValidator;
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
	public ModelAndView login(
			@RequestParam(value = "username", required = false) User user,
			@RequestParam(value = "password", required = false) String password,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if (user != null && user.getPassword().equals(password)) {
			session.setAttribute("username", user.getUsername());
			mav.setViewName("redirect:profile?user=" + user.getUsername());
		} else {
			mav.addObject("error", "Invalid user or password.");
			mav.setViewName("user/login");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpSession s) {
		s.removeAttribute("username");
		return "redirect:login";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userForm", new UserForm());
		showTopTenHashtags(mav);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registration(HttpServletRequest req, UserForm userForm,
			Errors errors, HttpSession session) {
		// DiskFileUpload fu = new DiskFileUpload();
		userFormValidator.validate(userForm, errors);

		if (errors.hasErrors()) {
			return null;
		}

		try {
			userRepo.save(userForm.build());
		} catch (Exception e) {
			errors.rejectValue("username", "duplicated");
			return null;
		}
		session.setAttribute("username", userForm.getUsername());
		return "redirect:profile?user=" + userForm.getUsername();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView profile(
			@RequestParam(value = "user", required = false) User profile,
			@RequestParam(value = "period", required = false) Integer period,
			@RequestParam(value = "commentid", required = false) Integer id,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String userSessionString = (String) session.getAttribute("username");
		if (period == null) {
			period = 30;
		}
		if (profile == null) {
			if (userSessionString != null) {
				mav.setViewName("redirect:profile?user=" + userSessionString
						+ "&period=" + period);
			} else {
				mav.setViewName("redirect:login");
			}
			return mav;
		} else {
			User userSession = userRepo.getUser(userSessionString);
			if (id != null) {
				Comment comment = commentRepo.get(Comment.class, id);
				if (comment != null) {
					commentRepo.delete(comment);
					mav.setViewName("redirect:profile?user="
							+ userSessionString + "&period=" + period);
					return mav;
				}
			}

			showTopTenHashtags(mav);

			if (profile.getUsername().equals(userSession.getUsername())) {
				mav.addObject("isOwner", true);
			}
			session.setAttribute("user", profile);
			mav.addObject("isEmptyPicture", profile.getPicture() == null);
			Set<Comment> comments = profile.getComments();
			for (Comment commentt : comments) {
				commentt.setComment(getProcessedComment(commentt.getComment()));
			}
			mav.addObject("comments", comments);
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView profile(
			@RequestParam(value = "comment", required = false) Comment comment,
			@RequestParam(value = "period", required = false) String period,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<RankedHashtag> top10;

		if (period == null) {
			top10 = hashtagRepo.topHashtags(30);
		} else {
			top10 = hashtagRepo.topHashtags(Integer.valueOf(period));
		}

		mav.addObject("ranking", top10);
		User user = (User) session.getAttribute("user");
		if (comment.getComment().length() > 0
				&& comment.getComment().length() < MAX_COMMENT_LENGTH) {
			commentRepo.save(comment);
		}
		mav.setViewName("redirect:profile?user=" + user.getUsername());
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editProfile(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String username = (String) session.getAttribute("username");
		User userSession = userRepo.getUser(username);
		System.out.println("ID BEFORE = " + userSession.getId());
		mav.addObject(new EditUserForm(userSession));
		// setDefaults(mav, userSession);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String editProfile(EditUserForm editUserForm, Errors errors) {
		editUserFormValidator.validate(editUserForm, errors);
		if (errors.hasErrors()) {
			return null;
		}
		System.out.println("ID = " + editUserForm.getId());
		User oldUser = userRepo.get(User.class, editUserForm.getId());
		editUserForm.update(oldUser);
		return "redirect:profile";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView recoverPassword(
			@RequestParam(value = "userToRecover", required = false) User user) {
		ModelAndView mav = new ModelAndView();
		if (user != null) {
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
			@RequestParam(value = "userToRecover", required = false) User user) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userSelected", true);
		if (secretAnswerSubmited != null) {
			boolean matches = user.getSecretAnswer().equals(
					secretAnswerSubmited);
			boolean passwordMatches = false;
			if (newPassword != null && newPasswordConfirm != null) {
				passwordMatches = newPassword.equals(newPasswordConfirm);
			}
			if (!matches) {
				mav.addObject("error", "Wrong secret Answer");
				mav.addObject("success",
						"Recovering password for " + user.getUsername());
				mav.addObject("user", user);
			} else {
				if (passwordMatches
						&& newPassword.length() <= MAX_PASSWORD_LENGTH
						&& newPassword.length() >= MIN_PASSWORD_LENGTH) {
					mav.addObject("passwordRecovered", true);
					mav.addObject("success",
							"Your password was changed successfully!");
					user.setPassword(newPassword);
					userRepo.save(user);
				} else {
					mav.addObject("error",
							"Passwords dont match or have less than 8 characters or more than 16.");
					mav.addObject("success",
							"Recovering password for " + user.getUsername());
					mav.addObject("user", user);
				}
			}
		}
		return mav;
	}

	private void showTopTenHashtags(ModelAndView mav) {
		List<RankedHashtag> top10;

		if (mav.getModel().get("period") == null) {
			top10 = hashtagRepo.topHashtags(30);
		} else {
			top10 = hashtagRepo.topHashtags(Integer.valueOf((String) mav
					.getModel().get("period")));
		}

		boolean isempty = top10.size() == 0;
		mav.addObject("previous", "login");

		mav.addObject("ranking", top10);
		mav.addObject("isempty", isempty);
	}

	private String getProcessedComment(String comment) {
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
}
