
public class ConcurrentNode {
	private String fileName;
	private boolean end;
	
	public ConcurrentNode(){
		fileName = null;
	}
	
	public ConcurrentNode(String s, boolean n){
		fileName = s;
		end = n;
	}
	
	public String getFileName(){
		return fileName;
	}

	
	public boolean isEnd(){
		return end;
	}
}
