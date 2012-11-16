import java.io.File;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HarvestThread{

	private String name;
	private ConcurrentSkipListSet<String> CSL;
	private ConcurrentQueue CQ;
	private Pattern p;
	
	public HarvestThread(int i,ConcurrentQueue ConQ, 
			ConcurrentSkipListSet<String> C, Pattern p){
		name = "Harvest " + i;
		CSL = C;
		CQ = ConQ;
	}
	
	public void run(){
		while(true){
			try{
				String fn;
				fn = CQ.dequeue();
				File file = new File(fn);
				File[] contents = file.listFiles();
				for(File f: contents){
					String fileName = f.getName();
					Matcher m = p.matcher(fileName);
					if (m.matches()){
						System.out.println(fileName);
						CSL.add(fileName);
					}
				}
			} catch(InterruptedException e){return;}
		}
	}
	
}
