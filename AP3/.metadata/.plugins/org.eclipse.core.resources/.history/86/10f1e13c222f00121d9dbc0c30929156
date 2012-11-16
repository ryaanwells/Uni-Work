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
			for(int i=1; i<=args.length-1; i++){
				processDirectory(args[i], regPat);
			}
		}
	}

	public static void processDirectory(String name, Pattern p) {
		try {
			File file = new File(name); // create a File object
			if (file.isDirectory()) { // a directory - could be symlink
				String entries[] = file.list();
				if (entries != null) { // not a symlink
					for (String entry : entries) {
						if (entry.compareTo(".") == 0)
							continue;
						if (entry.compareTo("..") == 0)
							continue;
						processDirectory(name + "/" + entry,p);
					}
				}
			}
			if (file.isFile()){
				Matcher m = p.matcher(file.getName());
				if (m.matches()){
					System.out.println(name);
				}
			}
		} catch (Exception e) {
			System.err.println("Error processing " + name + ": " + e);
		}
	}
	
}
