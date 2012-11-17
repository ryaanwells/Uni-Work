import java.io.File;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HarvestThread implements Runnable{
	private ConcurrentSkipListSet<String> CSL;
	private LinkedBlockingQueue<ConcurrentNode> LBQ;
	private Pattern p;
	
	public HarvestThread(LinkedBlockingQueue<ConcurrentNode> L, 
			ConcurrentSkipListSet<String> C, Pattern p){
		CSL = C;
		LBQ = L;
		this.p = p;
	}
	
	public void run(){
		while(true){
			try{
				ConcurrentNode cn;
				cn = LBQ.take();
				if (cn.isEnd()){
					LBQ.put(cn);
					return;
				}
				File file = new File(cn.getFileName());
				String[] contents = file.list();
				if (contents != null) {
					for (String f : contents) {
						if (f != null) {
							File ff = new File(f);
							if (!ff.isDirectory()) {
								Matcher m = p.matcher(f);
								if (m.matches()) {
									CSL.add(f);
								}
							}
						}
					}
				}
			} catch(InterruptedException e){return;}
		}
	}
	
}
