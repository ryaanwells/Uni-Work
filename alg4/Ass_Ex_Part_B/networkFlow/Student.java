package networkFlow;

public class Student extends Vertex {
	
	private int id;
	private boolean isSE;
	private int[] projectChoices;

	public Student(int guid, int id, boolean isSE, String[] choices) {
		super(guid);
		this.id = id;
		this.isSE = isSE;
		this.projectChoices = new int[choices.length - 2];
		for (int i = 2; i < choices.length; i++){
			this.projectChoices[i-2] = Integer.parseInt(choices[i]);
		}
	}
	
	public int id(){
		return this.id;
	}
	
	public boolean isSE(){
		return this.isSE;
	}
	
	public int[] choices(){
		return this.projectChoices;
	}

}
