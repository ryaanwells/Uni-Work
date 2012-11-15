import java.util.concurrent.ConcurrentSkipListSet;


public class HarvestThread implements Runnable{

	private String name;
	private ConcurrentSkipListSet<String> CSL;
	private ConcurrentQueue CQ;
	
	public HarvestThread(int i,ConcurrentQueue ConQ, 
			ConcurrentSkipListSet<String> C){
		name = "Harvest " + i;
		CSL = C;
		CQ = ConQ;
	}
	
	public void run(){
		while(true){
			try{
				String fn;
				fn = CQ.dequeue();
				CSL.add(fn);
			} catch(InterruptedException e){return;}
		}
	}
	
}
