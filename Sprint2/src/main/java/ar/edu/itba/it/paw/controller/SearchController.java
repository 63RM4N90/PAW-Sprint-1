package ar.edu.itba.it.paw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.model.User;
import ar.edu.itba.it.paw.services.UserService;

@Controller
public class SearchController {

	UserService userService;
	
	@Autowired
	public SearchController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView results() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView results(@RequestParam(value = "search", required = false) String search) {
		ModelAndView mav = new ModelAndView();
		if (search == null) {
			search = " ";
		}
		List<User> users = userService.getUsersWithName(search);
		mav.addObject("users", users);
		mav.addObject("search", search);
		return mav;
	}
}
