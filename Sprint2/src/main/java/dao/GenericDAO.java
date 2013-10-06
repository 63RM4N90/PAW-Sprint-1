package dao;

import java.util.List;

public interface GenericDAO<T> {

	public T store(T obj);
	
	public T find(int id);
	
	public void delete(T obj);
	
	public List<T> findAll();
}
