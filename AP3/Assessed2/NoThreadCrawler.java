import java.util.regex.*;
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
		if (args.length != 1){
			for(int i=1; i<=args.length-1; i++){
				d.processDirectory(args[i], regPat);
			}
		}
	}

}
