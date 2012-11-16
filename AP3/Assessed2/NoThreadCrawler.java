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
		ConcurrentQueue CQ = new ConcurrentQueue(10); 
		ConcurrentSkipListSet<String> CSL = new ConcurrentSkipListSet<String>();
		
		HarvestThread h1 = new HarvestThread(1,CQ,CSL,regPat);
		h1.run();
		
		if (args.length != 1){
			for(int i=1; i<=args.length-1; i++){
				System.out.println("here");
				d.processDirectory(args[i], CQ);
			}
		}
		else{
			d.processDirectory(".",CQ);
		}

		
		System.out.println();
		for(String s: CSL){
			System.out.println(s);
		}
	}

}
