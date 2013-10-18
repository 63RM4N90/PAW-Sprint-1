package ar.edu.itba.it.paw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.services.CommentService;

@Controller
public class CommentaryController {

	private CommentService commentService;
	
	@Autowired
	public CommentaryController(CommentService commentService) {
		this.commentService = commentService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView asdf() {
		return new ModelAndView();
	}
	
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		String param = req.getParameter("commentid");
//		String user = req.getParameter("user");
//		if (param != null) {
//			int id = Integer.parseInt(param);
//			commentService.removeComment(id);
//		}
//		if (user == null) {
//			user = "";
//		}
//		resp.sendRedirect("profile?user=" + user);
//	}
}
