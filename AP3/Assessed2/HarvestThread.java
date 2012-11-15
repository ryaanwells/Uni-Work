import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;


public class HarvestThread implements Runnable{

	private String name;
	private ConcurrentSkipListSet<String> CSL;
	private ConcurrentLinkedQueue<String> CLQ;
	
	public HarvestThread(int i, ConcurrentSkipListSet<String> C,
			ConcurrentLinkedQueue<String>CL){
		name = "Harvest " + i;
		CSL = C;
		CLQ = CL;
	}
	
	public void run(){
		while(true){
			try{
				String fn;
				fn = CLQ.poll();
				CSL.add(fn);
			} catch(Exception e){return;}
		}
	}
	
}
