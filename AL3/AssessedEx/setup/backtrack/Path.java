
public class Path {

	private int[][] path;
	private int length;
	private int size;
	private int start;
	private int end;
	
	public Path(int size, int s, int e){
		path = new int[size][2];
		for (int i=0;i<size;i++){
			path[i][0]=-1;
			path[i][1]=0;
		}
		length = -1;
		this.size = size;
		start = s;
		end = e;
	}
	
	public Path(Path p){
		this.path = p.path;
		this.length = p.length;
		this.size = p.size;
		this.start = p.start;
		this.end = p.end;
	}
	
	public void addToPath(int n, int d){
		if (length == -1){
			length++;
			path[length][0] = n;
			path[length][1] = d;
		}
		else{
			int currentDistance = path[length][1];
			length++;
			path[length][0] = n;
			path[length][1] = currentDistance +d;
		}
	}
	
	public void remove(){
		if(length<0) return;
		path[length][0] = -1;
		length--;
	}
	
	public int getDistance(){
		if (length == -1){
			return -1;
		}
		return path[length][1];
	}
	
	public int getLatest(){
		return path[length][0];
	}
	
	public int getFloat(){
		return start;
	}
	
	public int getSink(){
		return end;
	}
	
	public String toString(){
		String returns = "";
		for(int i = 0; i<length; i++){
			returns = returns + " " + Integer.toString(path[i][0]);
		}
		return returns;
	}
}
