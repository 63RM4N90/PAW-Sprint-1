package ar.edu.itba.it.paw.domain;

public interface UserListRepo {

	public void createUserList(UserList user_list);
	
	public UserList userList(int id);
	
	public void deleteUserList(UserList user_list);
}
