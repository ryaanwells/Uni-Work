package uk.ac.gla.mir.flair.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import uk.ac.gla.mir.flair.util.Settings;

/**
 * This Application create the 'etc/flair.query.spec' file
 * for a given TREC topic file.
 * @author David Hannah
 */
public class CreateQuerySpec {

	private static final String[] weightings = new String[]{"RAWTF", "TFIDF", "BM25"};
	
	private static String flair_home;// = Settings.getProperty("flair.home", "");
	private static String flair_etc;// = Settings.getProperty("flair.etc", "");
	
	private static String TRECFileName;
	
	private static String weighing = "TFIDF";
	private static String postType = null;
	private static String fieldID = "2";
	
	/**
	 * Gets the help message that is printed to the Console
	 * @return the help message
	 */
	public static String getHelpMessage(){
		String msg =  "******************************************************\n";
			   msg += "*                                                    *\n";
			   msg += "*   This simple program will convert TREC style      *\n";
			   msg += "*   Topic files to the Flair query specification.    *\n";
			   msg += "*   The file 'etc/flair.query.spec' will be created. *\n";
			   msg += "*                                                    *\n";
			   msg += "*   Current Settings;                                *\n";
			   msg += "*   TREC Topic FileName = "+(TRECFileName==null ? "unknown" : TRECFileName)+"\n";
			   msg += "*   Weighting Model     = "+ weighing +"\n";
			   msg += "*   Post Type           = "+(postType==null ? "unknown" : postType)+"\n";
			   msg += "*   Field ID            = "+(fieldID==null ? "unknown" : fieldID)+"\n";
			   msg += "*                                                    *\n";
			   msg += "*   1.) Get TREC Topics File Name                    *\n";
			   msg += "*   2.) Set Weighting Mode                           *\n";
			   msg += "*   3.) Set Post Type                                *\n";
			   msg += "*   4.) Set Field ID                                 *\n";
			   msg += "*   5.) Create flair.query.spec                      *\n";
			   msg += "*                                                    *\n";
			   msg += "*   9.) Display This Help Message                    *\n";
			   msg += "*                                                    *\n";
			   msg += "*                                                    *\n";
			   msg += "*   0.) Exit                                         *\n";
			   msg += "*                                                    *\n";
			   msg += "******************************************************\n";
		return msg;
	}
	
	/**
	 * Gets the help message that is printed to the Console for Setting the
	 * Weighting Model
	 * @return the set Weighting message
	 */
	public static String getWeightingMessage(){
		String msg =  "******************************************************\n";
			   msg += "*                                                    *\n";
			   msg += "*   Setting the Weighting Model                      *\n";
			   msg += "*   The weighting models to choose from are;         *\n";
			   msg += "*                                                    *\n";
			  
			   for( int i = 0; i < weightings.length; i++ ){
				   msg += "*   "+i+".) "+weightings[i]+"\n";
			   }
			   msg += "*                                                    *\n";
			   msg += "*                                                    *\n";
			   msg += "*   9.) Exit                                         *\n";
			   msg += "*                                                    *\n";
			   msg += "******************************************************\n";
		return msg;
	}
	
	/**
	 * Prints the Help Message to the Console
	 */
	public static void printHelpMessage(){
		String msg = getHelpMessage();
		System.out.println();
		System.out.println( msg );
		System.out.println();
	}
	
	/**
	 * 
	 * @return the command message
	 */
	public static String getCommandMessage(){
		String msg = "Enter option number";
		return msg;
	}
	
	/**
	 * 
	 */
	public static void printCommandMessage(){
		String msg = getCommandMessage();
		System.out.println( msg + " -> ");
	}
	
	/**
	 * Gets the TREC Topic filename from the input sc
	 * @param sc The scanner for input
	 * @return the filename
	 */
	private static String getFileName(Scanner sc) {
		String line = sc.nextLine();
		String tmp = "n";
		while( !tmp.startsWith("y") ){ 
			System.out.println("Enter the TREC Topic File Name -> ");
			line = sc.nextLine();
			System.out.println("File Name = "+ line);
			System.out.println("Correct Y/N [Y] -?");
			tmp = sc.nextLine().toLowerCase();
			if( tmp.trim().equalsIgnoreCase("") )
				tmp = "y";
		}
		return line;
	}
	
	private static void getWeightingMode(Scanner sc) {
		String msg = getWeightingMessage();
		
		int cmd = -1;
		CMDLoop2:while( cmd != 9 ){
			System.out.println();
			System.out.println( msg );
			System.out.println();
			printCommandMessage();
			cmd = sc.nextInt();
			switch (cmd) {				
			case 9:
				System.exit(0);
			default :
				weighing = weightings[cmd];
				break CMDLoop2;
			}
		}
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	private static void createQuerySpec() throws IOException{
		
		String line = "";
		String querySpecFile = Settings.getQuerySpecFileName();
		final BufferedReader bin = new BufferedReader( new FileReader( TRECFileName ) );
		final PrintWriter out = new PrintWriter( new FileWriter(querySpecFile), true );
		out.println("<querying>");
		String currentTopicID = null ;
		String currentTopicTerms = "";
		boolean inTerms = false;
		while( ( line = bin.readLine() ) != null ){
			
			if( line.startsWith(".I") ){
				if( currentTopicID != null ){
					out.println( "<query type = TREC topicID = "+currentTopicID+" weighting = "+weighing+" posttype = "+postType+">");
					out.println("<terms> "+ currentTopicTerms);
					out.println("</terms>");
					out.println("<id>"+fieldID+"</id>");
					out.println("</query>\n");
				}
				currentTopicID = line.replaceAll(".I", "");
				currentTopicID = currentTopicID.replaceAll("\\s+", "");
				currentTopicTerms = "";
				inTerms = false;
			}
			
			if( inTerms ){
				currentTopicTerms = currentTopicTerms + " "+ line +"\n";
			}
			
			if( line.startsWith(".W") ){
				inTerms = true;
			}
		}
		if( currentTopicID != null ){
			out.println( "<query type = TREC topicID = "+currentTopicID+" weighting = "+weighing+" posttype = "+postType+">");
			out.println("<terms> "+ currentTopicTerms);
			out.println("</terms>");
			out.println("<id>"+fieldID+"</id>");
			out.println("</query>\n");
		}
		
		out.println("</querying>");
		out.close();
		
		System.out.println( "Finished writing to '" + querySpecFile+"'" );
		System.exit( 0 );
	}

	
	/**
	 * 
	 */
	public static void commandLineMode() {
		
		Scanner sc = new Scanner( System.in );
		
		int cmd = -1;
		CMDLoop:while( cmd != 0 ){
			printHelpMessage();
			printCommandMessage();
			try{
				cmd = sc.nextInt();
			}catch (Exception e) {
				String tmp = sc.next();
				cmd = -1;
				System.err.println("Problem Reading '"+tmp+"' as an Integer. Try Again.");
			}
			//System.out.println( cmd );
			switch (cmd) {
			case 1:
				TRECFileName = getFileName(sc);
				break;
			case 2:
				getWeightingMode(sc);
				break;
			case 3:
				getPostType( sc );
				break;
			case 4:
				getFieldID( sc );
				break;
			case 5:
				if( TRECFileName == null ){
					System.err.println("ERROR: The TREC Topic file name has not been set.");
				}

				if( postType == null ){
					System.err.println("ERROR: The Post Type has not been set.");
				}

				if( TRECFileName != null && postType != null ){
					File f = new File( TRECFileName );
					if( f.exists() ){
						try{
							createQuerySpec();
						}catch (Exception e) {
							System.err.println( e.getLocalizedMessage() );
						}
					}else{
						System.err.println("The TREC Topic file '"+TRECFileName+"' has not been found.");
					}
				}
				break;
			case 0:
				break CMDLoop;

			default:
				break;
			}
		}
	}

	private static void getPostType(Scanner sc) {
		String line = sc.nextLine();
		String tmp = "n";
		while( !tmp.startsWith("y") ){ 
			System.out.println("Enter the Post Type -> ");
			line = sc.nextLine();
			System.out.println("Post Type = "+ line);
			System.out.println("Correct Y/N [Y] -?");
			tmp = sc.nextLine().toLowerCase();
			if( tmp.trim().equalsIgnoreCase("") )
				tmp = "y";
		}
		postType = line;
	}
	

	private static void getFieldID(Scanner sc) {
		String line = sc.nextLine();
		String tmp = "n";
		while( !tmp.startsWith("y") ){ 
			System.out.println("Enter the Field -> ");
			line = sc.nextLine();
			System.out.println("Field ID = "+ line);
			System.out.println("Correct Y/N [Y] -?");
			tmp = sc.nextLine().toLowerCase();
			if( tmp.trim().equalsIgnoreCase("") )
				tmp = "y";
		}
		fieldID = line;
		
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Settings.initSettings();
		commandLineMode();
	
	}

}
