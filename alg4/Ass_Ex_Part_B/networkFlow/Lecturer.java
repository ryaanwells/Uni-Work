package networkFlow;

public class Lecturer extends Vertex {

	private int id;
	private int capacity;
	
	public Lecturer(int guid, int id, int capacity) {
		super(guid);
		this.id = id;
		this.capacity = capacity;
	}
	
	public int id(){
		return this.id;
	}
	
	public int capacity(){
		return this.capacity;
	}

}
