package networkFlow;

public class Lecturer extends Vertex {

	private int id;
	private int min;
	private int capacity;
	
	public Lecturer(int guid, int id, int min, int capacity) {
		super(guid);
		this.id = id;
		this.min = min;
		this.capacity = capacity;
	}
	
	public int id(){
		return this.id;
	}
	
	public int capacity(){
		return this.capacity;
	}
	
	public int min(){
		return this.min;
	}

}
