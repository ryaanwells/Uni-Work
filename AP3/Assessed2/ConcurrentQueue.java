
public class ConcurrentQueue {
	private ConcurrentNode head;
	private int size;
	private int input;
	private int output;
	private int total;
	String[] queue;
	
	public ConcurrentQueue(int n){
		size = n;
		input = 0;
		output = 0;
		total = 0;
		queue = new String[size];
	}
	
	public synchronized void enqueue(String s){
		while(total == size){
			try{
				wait();
			} catch (Exception e){}
			queue[input] = s;
			input = (input+1)%size;
			total++;
			notifyAll();
		}
	}
	
	public synchronized String dequeue() throws InterruptedException{
		while(total == 0)
			wait();
		String s = queue[output];
		output = (output +1)%size;
		total--;
		notifyAll();
		return s;
	}	
}	