package ar.edu.itba.it.paw.web.hashtag;

import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.common.TopTenHashtagsPanel;

@SuppressWarnings("serial")
public class TopTenHashtagsPage extends BasePage {

	public TopTenHashtagsPage() {
		add(new TopTenHashtagsPanel("top_ten_hashtags_panel"));
	}
}
