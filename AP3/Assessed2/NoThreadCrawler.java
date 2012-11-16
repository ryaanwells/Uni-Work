import java.util.regex.*;
import java.util.concurrent.*;
public class NoThreadCrawler {

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
		DirectoryTree d = new DirectoryTree();
		LinkedBlockingQueue<String> LBQ = new LinkedBlockingQueue<String>();
		ConcurrentSkipListSet<String> CSL = new ConcurrentSkipListSet<String>();
		
		Thread h1 = new Thread(new HarvestThread(1,LBQ,CSL,regPat));
		h1.start();
		if (args.length != 1){
			for(int i=1; i<=args.length-1; i++){
				d.processDirectory(args[i], LBQ);
			}
		}
		else{
			d.processDirectory(".",LBQ);
		}
		h1.interrupt();
		try{
			h1.join();
		}catch (Exception e){};
		
		System.out.println();
		for(String s: CSL){
			System.out.println(s);
		}
	}

}
