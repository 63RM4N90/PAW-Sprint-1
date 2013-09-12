package network;

import services.CommentService;

public class CommentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String comment = "cacaaca #jaja caca caca";
		System.out.println(CommentService.getInstance().getProcessedComment(
				comment));
	}
}
