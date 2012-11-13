import java.io.File;
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
		if (args.length != 1){
			for(int i=1; i<args.length-1; i++){

			}
		}

	}

	public void processDirectory(String name) {
		try {
			File file = new File(name); // create a File object
			if (file.isDirectory()) { // a directory - could be symlink
				String entries[] = file.list();
				if (entries != null) { // not a symlink
					System.out.println(name);// print out the name
					for (String entry : entries) {
						if (entry.compareTo(".") == 0)
							continue;
						if (entry.compareTo("..") == 0)
							continue;
						processDirectory(name + "/" + entry);
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error processing " + name + ": " + e);
		}
	}
	
}