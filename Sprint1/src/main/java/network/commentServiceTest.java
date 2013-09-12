package network;

import services.CommentService;

public class commentServiceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(CommentService.getInstance().getProcessedComment("jajajaja #cacona jajaja"));

	}

}
