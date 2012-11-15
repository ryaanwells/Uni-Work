
public class ConcurrentNode {
	private String fileName;
	private ConcurrentNode next;
	
	public ConcurrentNode(){
		fileName = null;
		next = null;
	}
	
	public ConcurrentNode(String s, ConcurrentNode n){
		fileName = s;
		next = n;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public ConcurrentNode getNext(){
		return next;
	}
}
