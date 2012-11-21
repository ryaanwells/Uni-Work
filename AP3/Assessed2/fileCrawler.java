import java.util.regex.*;
import java.util.concurrent.*;

public class fileCrawler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length<1){
			System.out.println("Usage: java NoThreadCrawler pattern [dir] ...");
			return;
		}
		String preReg = Regex.cvtPattern(args[0]);
		Pattern regPat = Pattern.compile(preReg);
		LinkedBlockingQueue<ConcurrentNode> LBQ = new LinkedBlockingQueue<ConcurrentNode>();
		ConcurrentSkipListSet<String> CSL = new ConcurrentSkipListSet<String>();
		DirectoryTree d = new DirectoryTree(LBQ);
		Thread[] workers;
		
		if (System.getenv("CRAWLER_THREADS")!= null){
			workers = new Thread[Integer.parseInt(System.getenv("CRAWLER_THREADS"))];
		}
		else{
			workers = new Thread[20];
		}
		
		for (int i = 0; i< workers.length; i++){
			workers[i] = new Thread(new HarvestThread(LBQ,CSL,regPat));
			workers[i].start();
		}

		if (args.length != 1){
			for(int i=1; i<=args.length-1; i++){
				d.processDirectory(args[i]);
			}
		}
		else{
			d.processDirectory(".");
		}
		
		try {
			LBQ.put(new ConcurrentNode("",true));
		} catch (InterruptedException e1) {}
		
		for (int i = 0; i < workers.length; i++) {
			try{
				workers[i].join();
			} catch (Exception e){};
		}

		for(String s: CSL){
			System.out.println(s);
		}
	}

}
