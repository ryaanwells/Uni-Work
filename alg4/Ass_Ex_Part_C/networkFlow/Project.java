package networkFlow;

public class Project extends Vertex {

	private int id;
	private boolean isSE;
	private int lecturer;
	private int capacity;
	
	public Project(int guid, int id, boolean isSE, int lecturer, int capacity) {
		super(guid);
		this.id = id;
		this.isSE = isSE;
		this.lecturer = lecturer;
		this.capacity = capacity;
	}
	
	public int id(){
		return this.id;
	}
	
	public boolean se(){
		return this.isSE;
	}
	
	public int lecturer(){
		return this.lecturer;
	}
	
	public int capacity(){
		return this.capacity;
	}

}
