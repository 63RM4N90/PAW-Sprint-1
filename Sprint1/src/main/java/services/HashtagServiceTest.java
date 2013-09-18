package services;

import java.util.Calendar;
import java.util.Date;

public class HashtagServiceTest {

	public static void main(String[] args) {
		//HashtagService service = HashtagService.getInstance();
		
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		calendar.roll(Calendar.DAY_OF_YEAR, -30);
		Date date2 = calendar.getTime();
		
		System.out.println("Dia de hoy: " + date);
		System.out.println("Dia hace 1 mes: " + date2);
		
		/*List<Hashtag> top = service.TopHashtags(30);
		for(Hashtag each: top){
			System.out.println(each.getHashtag());
		}*/
	}

}
