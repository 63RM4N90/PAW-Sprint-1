package network;

import model.Hashtag;

public class HashtagDAOTest {

	public static void main(String[] args) throws Exception{
		HashtagDAO hashtagDAO = HashtagDAO.getInstance();
		
		Hashtag hashtag = hashtagDAO.getHashTag("prueba");
		
		System.out.println(hashtag == null);
	}
	
}
