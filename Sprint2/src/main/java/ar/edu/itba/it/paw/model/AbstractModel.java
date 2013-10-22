package ar.edu.itba.it.paw.model;


//@MappedSuperclass
public abstract class AbstractModel {
	
	//@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isNew(){
		return id == -1;
	}
}
