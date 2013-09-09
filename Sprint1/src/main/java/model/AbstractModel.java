package model;

public abstract class AbstractModel {
	
	protected int id;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isNew(){
		return id == -1?true:false;
	}

}
