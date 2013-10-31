package ar.edu.itba.it.paw.web;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.command.EditUserForm;
import ar.edu.itba.it.paw.command.UserForm;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.CommentRepo;
import ar.edu.itba.it.paw.domain.HashtagRepo;
import ar.edu.itba.it.paw.domain.Notification;
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
	private static final int DEFAULT_PERIOD = 30;

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
		if (s.getAttribute("username") != null) {
			mav.setViewName("/user/profile/" + s.getAttribute("username"));
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
			mav.setViewName("redirect:./profile/" + user.getUsername());
		} else {
			mav.addObject("error", "Invalid user or password.");
			mav.setViewName("user/login");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpSession s) {
		s.removeAttribute("username");
		return "redirect:./login";
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
		return "redirect:profile/" + userForm.getUsername();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String home(HttpSession session) {
		if (session.getAttribute("username") != null) {
			return "redirect:profile/" + session.getAttribute("username");
		} else {
			return "redirect:login";
		}
	}

	@RequestMapping(value = "/user/profile/{profile}", method = RequestMethod.GET)
	public ModelAndView profile(@PathVariable User profile,
			@RequestParam(value = "period", required = false) Integer period,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String userSessionString = (String) session.getAttribute("username");
		if (period == null) {
			period = DEFAULT_PERIOD;
		}
		if (profile == null) {
			if (userSessionString != null) {
				mav.setViewName("redirect:../profile/" + userSessionString
						+ "&period=" + period);
			} else {
				mav.setViewName("redirect:../login");
			}
			return mav;
		} else {
			if (userSessionString == null) {
				if (profile.isPrivate()) {
					mav.setViewName("privacyError");
					return mav;
				}
			} else {
				session.setAttribute("username", userSessionString);
				User userSession = userRepo.getUser(userSessionString);
				boolean following = userSession.isFollowing(profile);
				mav.addObject("isFollowing", following);
			}
			profile.visit();
			mav.addObject("notifications",
					userRepo.getUser(profile.getUsername())
							.getUncheckedNotifications());

			showTopTenHashtags(mav);
			mav.addObject("isOwner",
					profile.getUsername().equals(userSessionString));
			mav.addObject("user", profile);
			mav.addObject("isEmptyPicture", profile.getPicture() == null);
			List<Comment> comments = profile.getComments();
			SortedSet<CommentWrapper> transformedComments = transformComments(comments);
			mav.addObject("comments", transformedComments);
		}
		mav.setViewName("/user/profile");
		return mav;
	}

	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Integer id) {
		if (id != null) {
			Comment comment = commentRepo.get(Comment.class, id);
			if (comment != null) {
				commentRepo.delete(comment);
			}
		}
		return "redirect:../home";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView recuthulu(
			@RequestParam(value = "user", required = false) User originalauthor,
			@RequestParam(value = "comment", required = false) String comment,
			@RequestParam(value = "commentid", required = false) Integer id,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String userSession = (String) session.getAttribute("username");
		User author = userRepo.getUser(userSession);

		Comment recuthulu = new Comment(author, new Date(), comment,
				commentRepo.getHashtagList(comment, originalauthor),
				commentRepo.getReferences(comment), originalauthor);
		commentRepo.save(recuthulu);

		mav.setViewName("redirect:profile?user=" + originalauthor.getUsername());

		return mav;

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView follow(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String username = (String) session.getAttribute("username");
		User sprofile = (User) session.getAttribute("user");
		User userSession = userRepo.getUser(username);

		userSession.follow(sprofile);

		mav.setViewName("redirect:user/profile/" + sprofile.getUsername());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView unfollow(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String username = (String) session.getAttribute("username");
		User sprofile = (User) session.getAttribute("user");
		User userSession = userRepo.getUser(username);

		userSession.unfollow(sprofile);

		mav.setViewName("redirect:user/profile/" + sprofile.getUsername());
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
		String username = (String) session.getAttribute("username");
		User user = userRepo.getUser(username);
		if (comment.getComment().length() > 0
				&& comment.getComment().length() < MAX_COMMENT_LENGTH) {
			commentRepo.save(comment);
		}
		mav.setViewName("redirect:./profile/" + user.getUsername());
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editProfile(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String username = (String) session.getAttribute("username");
		User userSession = userRepo.getUser(username);
		EditUserForm editUserForm = new EditUserForm(userSession);
		mav.addObject(editUserForm);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String editProfile(EditUserForm editUserForm, Errors errors) {
		editUserFormValidator.validate(editUserForm, errors);
		if (errors.hasErrors()) {
			return null;
		}
		User oldUser = userRepo.get(User.class, editUserForm.getId());
		editUserForm.update(oldUser);
		return "redirect:home";
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

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView notifications(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		String username = (String) session.getAttribute("username");
		if (username != null) {
			User user = userRepo.getUser(username);
			List<Notification> notifications = user.getNotifications();
			for (Notification notification : notifications) {
				notification.check();
			}
			mav.addObject("notifications", notifications);
		} else {
			mav.setViewName("redirect:login");
		}
		return mav;
	}

	private SortedSet<CommentWrapper> transformComments(List<Comment> comments) {
		SortedSet<CommentWrapper> ans = new TreeSet<CommentWrapper>();
		for (Comment comment : comments) {
			CommentWrapper aux = new CommentWrapper(comment,
					getProcessedComment(comment.getComment()));
			ans.add(aux);
		}
		return ans;
	}

	private String getProcessedComment(String comment) {
		// Search for URLs
		String aux = comment;
		if (aux != null && aux.contains("http:")) {
			int indexOfHttp = aux.indexOf("http:");
			int endPoint = (aux.indexOf(' ', indexOfHttp) != -1) ? aux.indexOf(
					' ', indexOfHttp) : aux.length();
			String url = aux.substring(indexOfHttp, endPoint);
			String targetUrlHtml = "<a href='" + url + "' target='_blank'>"
					+ url + "</a>";
			aux = aux.replace(url, targetUrlHtml);
		}

		// Search for Hashtags
		String patternStr = "#([A-Za-z0-9_]+)";
		Pattern pattern = Pattern.compile(patternStr);
		String[] words = aux.split(" ");
		String ans = "";
		String result = "";

		for (String word : words) {
			Matcher matcher = pattern.matcher(word);
			if (matcher.find()) {
				result = matcher.group();
				result = result.replace(" ", "");
				String search = result.replace("#", "");
				String searchHTML = "<a href='../../hashtag/detail?tag="
						+ search + "'>" + result + "</a>";
				ans += word.replace(result, searchHTML) + " ";
			} else {
				ans += word + " ";
			}
		}

		// Search for Users
		patternStr = "@([A-Za-z0-9_]+)";
		pattern = Pattern.compile(patternStr);
		words = ans.split(" ");
		System.out.println("Econtré " + words.length + "referencias");
		ans = "";
		for (String word : words) {
			Matcher matcher = pattern.matcher(word);
			if (matcher.find()) {
				result = matcher.group();
				result = result.replace(" ", "");
				String search = result.replace("@", "");
				String userHTML = "<a href='./" + search + "'>" + result
						+ "</a>";
				ans += word.replace(result, userHTML) + " ";
			} else {
				ans += word + " ";
			}
		}
		return ans;
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
}
