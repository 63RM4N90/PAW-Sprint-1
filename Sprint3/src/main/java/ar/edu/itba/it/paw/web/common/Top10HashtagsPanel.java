package ar.edu.itba.it.paw.web.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.HashtagRepo;
import ar.edu.itba.it.paw.domain.RankedHashtag;

@SuppressWarnings("serial")
public class Top10HashtagsPanel extends Panel{
	
	@SpringBean
	private HashtagRepo hashtags;
	private int period = 30;
	private Label emptyList;

	public Top10HashtagsPanel(String id) {
		super(id);
		
		emptyList = new Label("emptyHashtagList", getString("no_hashtags"));
		emptyList.setVisible(false);
		add(emptyList);
		add(new Link<Void>("1day") {

			@Override
			public void onClick() {
				period = 1;
			}
		});
		add(new Link<Void>("1week") {

			@Override
			public void onClick() {
				period = 7;				
			}
		});
		add(new Link<Void>("1month") {

			@Override
			public void onClick() {
				period = 30;				
			}
		});
		add(new RefreshingView<RankedHashtag>("hashtag") {
			@Override
			protected Iterator<IModel<RankedHashtag>> getItemModels() {
				List<IModel<RankedHashtag>> result = new ArrayList<IModel<RankedHashtag>>();
				List<RankedHashtag> hashtagList = hashtags.topHashtags(period);
				for (RankedHashtag h : hashtagList) {
					result.add(new EntityModel<RankedHashtag>(RankedHashtag.class, h));
				}
				if(result.isEmpty()) {
					emptyList.setVisible(true);
				}
				return result.iterator();
			}

			@Override
			protected void populateItem(Item<RankedHashtag> item) {
				item.add(new Label("name", new PropertyModel<String>(item.getModel(), "hashtag.hashtag")));
				item.add(new Label("username", new PropertyModel<String>(item.getModel(), "hashtag.author.username")));
				item.add(new Label("rank", new PropertyModel<String>(item.getModel(), "rank")));
			}
		});
	}

}
