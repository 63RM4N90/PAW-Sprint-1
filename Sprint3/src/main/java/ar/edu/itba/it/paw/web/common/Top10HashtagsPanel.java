package ar.edu.itba.it.paw.web.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.omg.CORBA.portable.ApplicationException;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.Hashtag;
import ar.edu.itba.it.paw.domain.HashtagRepo;
import ar.edu.itba.it.paw.domain.RankedHashtag;

@SuppressWarnings("serial")
public class Top10HashtagsPanel extends Panel{
	
	@SpringBean
	private HashtagRepo hashtags;

	public Top10HashtagsPanel(String id) {
		super(id);
		
		add(new Label("emptyHashtagList"));
		add(new Link<Void>("1day") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
		});
		add(new Link<Void>("1week") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
		});
		add(new Link<Void>("1month") {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}
		});
		add(new RefreshingView<RankedHashtag>("hashtag") {
			@Override
			protected Iterator<IModel<RankedHashtag>> getItemModels() {
				List<IModel<RankedHashtag>> result = new ArrayList<IModel<RankedHashtag>>();
				for (RankedHashtag h : hashtags.topHashtags(1)) {
					result.add(new EntityModel<RankedHashtag>(RankedHashtag.class, h));
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
