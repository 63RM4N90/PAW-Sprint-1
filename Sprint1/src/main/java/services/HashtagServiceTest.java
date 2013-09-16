package services;

import java.util.List;

import model.Hashtag;

public class HashtagServiceTest {

	public static void main(String[] args) {
		HashtagService service = HashtagService.getInstance();
		
		List<Hashtag> top = service.TopHashtags(7);
		
		for(Hashtag each: top){
			System.out.println(each.getHashtag());
		}
	}

}
