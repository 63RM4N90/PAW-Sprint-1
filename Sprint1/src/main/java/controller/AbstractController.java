package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public abstract class AbstractController extends HttpServlet {

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
			String searchHTML = "<a href='/hashtag?tag=" + search + "'>"
					+ result + "</a>";
			comment = comment.replace(result, searchHTML);
		}
		System.out.println(comment);
		return comment;
	}
}
