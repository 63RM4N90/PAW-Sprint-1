package network;

import model.User;

public class UserDAOTest {
	public static void main(String[] args) {
		UserDAO manager = UserDAO.getInstance();
		
		User user = new User("German", "Romarion", "63RM4N90", "FACHAAA", "12345678", null, "que onda?", "to pio");
		user.setId(2);
		manager.removeUser(user);
		System.out.println(manager.getUser("63RM4N90").getUsername());
	}
}
