package ar.edu.itba.it.paw.web.common;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.Hashtag;
import ar.edu.itba.it.paw.domain.HashtagRepo;
import ar.edu.itba.it.paw.domain.RankedHashtag;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.web.hashtag.HashtagDetailPage;
import ar.edu.itba.it.paw.web.user.ProfilePage;

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
		IModel<List<RankedHashtag>> hashtagsListModel = new LoadableDetachableModel<List<RankedHashtag>>() {

			@Override
			protected List<RankedHashtag> load() {
				return hashtags.topHashtags(period);
			}
		};
		add(new PropertyListView<RankedHashtag>("hashtag", hashtagsListModel) {
			@Override
			protected void populateItem(ListItem<RankedHashtag> item) {
				Link<Hashtag> hashtagLink = new Link<Hashtag>("name",
						new PropertyModel<Hashtag>(item.getModel(), "hashtag")) {

					@Override
					public void onClick() {
						setResponsePage(new HashtagDetailPage(new PageParameters().set("hashtag", getModelObject().getHashtag())));
					}
				};
				hashtagLink.add(new Label("hashtag.hashtag"));
				item.add(hashtagLink);
				Link<User> hashtagAuthorLink = new Link<User>("username", new PropertyModel<User>(item.getModel(), "hashtag.author")){

					@Override
					public void onClick() {
						setResponsePage(new ProfilePage(new PageParameters().set("username", getModelObject().getUsername())));						
					}
				};
				hashtagAuthorLink.add(new Label("hashtag.author.username"));
				item.add(hashtagAuthorLink);
				item.add(new Label("rank", new PropertyModel<String>(item.getModel(), "rank")));			}
		});
	}

}
