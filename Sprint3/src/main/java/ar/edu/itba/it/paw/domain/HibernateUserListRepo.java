package ar.edu.itba.it.paw.domain;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateUserListRepo extends AbstractHibernateRepo implements
		UserListRepo {

	@Autowired
	public HibernateUserListRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public void createUserList(UserList user_list) {
		super.save(user_list);
	}

	@Override
	public void deleteUserList(UserList user_list) {
		super.delete(user_list);
	}

	@Override
	public UserList userList(int id) {
		return super.get(UserList.class, id);
	}
}
