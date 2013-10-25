package ar.edu.itba.it.paw.web;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Hashtag;

@Controller
public class HashtagController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView detail(
			@RequestParam(value = "tag", required = false) Hashtag hashtag) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", hashtag.getAuthor());
		mav.addObject("tag", hashtag);
		Set<Comment> comments = hashtag.getComments();
		for (Comment comment : comments) {
			comment.setComment(getProcessedComment(comment.getComment()));
		}
		mav.addObject("comments", comments);
		return mav;
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
