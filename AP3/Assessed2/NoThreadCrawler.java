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
		ConcurrentLinkedQueue<String> CLQ = new ConcurrentLinkedQueue<String>();
		if (args.length != 1){
			for(int i=1; i<=args.length-1; i++){
				d.processDirectory(args[i], regPat, CLQ);
			}
		}
		else{
			d.processDirectory(".", regPat, CLQ);
		}
		ConcurrentSkipListSet<String> CSL = new ConcurrentSkipListSet<String>();
		for(String s: CLQ){
			CSL.add(s);
		}
		System.out.println();
		for(String s: CSL){
			System.out.println(s);
		}
	}

}
