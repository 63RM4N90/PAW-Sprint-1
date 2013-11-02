package ar.edu.itba.it.paw.web;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Hashtag;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserRepo;

@Controller
public class HashtagController extends AbstractController{

		private UserRepo userRepo;
	
	@Autowired
	public HashtagController(UserRepo userRepo){
		this.userRepo = userRepo;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView detail(
			@RequestParam(value = "tag", required = false) Hashtag hashtag,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", hashtag.getAuthor());
		mav.addObject("tag", hashtag);
		mav.addObject("commentOwnerURL",
				"../user/profile/" + hashtag.getAuthor());
		
		String usrSession = (String)session.getAttribute("username");
		User usr;
		if(usrSession == null){
			usr = null;
		}else{
			usr = userRepo.getUser(usrSession);
		}
		
		List<Comment> comments = new ArrayList<Comment>(hashtag.getComments());
		SortedSet<CommentWrapper> transformedComments = transformComments(comments,usr);
		mav.addObject("comments", transformedComments);
		return mav;
	}	
}
