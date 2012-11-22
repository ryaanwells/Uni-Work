/**
 * AP3:AE1
 * 1002253w
 * This is my own work as defined in the Academic Ethics 
 * agreement I have signed.
 */


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
