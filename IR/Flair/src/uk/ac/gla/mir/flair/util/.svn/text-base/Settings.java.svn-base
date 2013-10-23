package uk.ac.gla.mir.flair.util;

import gnu.trove.TIntObjectHashMap;
import gnu.trove.TObjectIntHashMap;

import java.io.*;
import java.rmi.server.UID;
import java.util.*;

/**
 * Title:        Settings <br/>
 * Description: This class Records and makes accessible, 
 * Enviroment variables and Flair Settings based on 
 * the Java Systems Properties Class
 * @author David Hannah
 */
public class Settings {

	/** Java Properties to Hold Properties **/
	private static Properties props = new Properties();
	
	public static String fileSep = System.getProperty("file.separator");
	
	/**  **/
	public static boolean DEBUG = false;
	
	/** The Install Directory of the Crawler **/
	public static String FLAIR_HOME = System.getProperty("flair.home");
	
	/** The Settings Directory of Flair **/
	public static String ETC = System.getProperty("flair.etc");
		
	/* Legacy Static Variables */
	
	/** The Location of the Flair Setting Files **/
	private static String location;
	
	/** The Location of the Flair temp Dir **/
	private static String temp = "tmp";
	
	/** The Location of the Flair Query Description File **/
	private static String query;
	
	/** The Location of the Index files **/
	private static String INDEX_PATH;

	
	/** The Full Path of the Stop Words File **/
	public static String stopfilelocation = "" ;
	
	/**
	 * Loads the Properties file from the Hard disk and Initalises
	 * all Inportant Settings
	 */
	public static void initSettings(){
		
		if(FLAIR_HOME == null){
			Assert.fatal(true, "flair.home is Not Set.");
		}
		
		if(ETC == null && FLAIR_HOME != null){
			ETC = FLAIR_HOME+fileSep+"etc";
			location = ETC;
		}
		
		//Load Flair Properties
		if(ETC != null){
			try{
				props.load( new FileInputStream( ETC+fileSep+"flair.properties") );
				setProperty("flair.etc", ETC);
			}catch (Exception e) {
				Assert.fatal(true, "Properties Load Failed.");
			}
		}
		
		//Add Java VM and Enviroment Properties
		//So all access is consistent
		Properties p = System.getProperties();
		Set<Object> keys = p.keySet();
		Iterator<Object> iterator = keys.iterator();
		while(iterator.hasNext()){
			String key = (String)iterator.next();	
			String value = p.getProperty(key);
			props.setProperty(key, value);
		}
		DEBUG = getProperty("flair.debug", true);		
		
		//Set Legacy Code Variables
		stopfilelocation = getProperty("flair.stopword.file", "");
		temp = getProperty("flair.temp.dir", "tmp");
		INDEX_PATH = getProperty("flair.index.path","");
		Assert.info(DEBUG, "Properties Loaded OK");
	}
	
	/* Legacy */
	public static synchronized void getSettings(String location) {
		initSettings();
	}
	
	public static String getIndexPath() {
		return INDEX_PATH;
	}

	public static String getLocation() throws IOException{
		return location;
	}

//	public static String getQuery() throws IOException {
//		return location;
//	}
	
	public static String getQuerySpecFileName() {
		return ETC+fileSep+"flair.query.spec";
	}
	
	public static String getTemp(){
		return temp;
	}
	
	/**
	 * Gets the Document File Names
	 * @return returns a hashmap of the filename indexed by the description type
	 */
	public static TObjectIntHashMap<String> getDocumentFiles(){
		
		TObjectIntHashMap<String> tmp = new TObjectIntHashMap<String>();
		final String documentsFileName = getProperty("flair.documents.file", "");
		try{
			final BufferedReader bin = new BufferedReader( new FileReader(documentsFileName));
			String line = "";
			while( (line=bin.readLine())!=null){
				if( line.trim().equalsIgnoreCase("") )
					continue;
				if( !line.startsWith("#") ){
					String[] fields = line.split("\\s+");
					tmp.put(fields[1], Integer.parseInt( fields[0]) );
				}
			}
		}catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			Assert.fatal(true, "Error Reading Document File \""+documentsFileName+"\"");
		}
		return tmp;
	}
		
	/**
	 * Gets the DocumentDescription Files<br>
	 * The files are stored in a HashMap
	 * @return returns a hashmap of filenames, indexed by the descriptionID
	 */
	public static TIntObjectHashMap<String> getDocumentDescriptionFiles(){
		
		TIntObjectHashMap<String> tmp = new TIntObjectHashMap<String>();
		final String descriptionsFileName = getProperty("flair.descriptions.file", "");
		try{
			final BufferedReader bin = new BufferedReader( new FileReader(descriptionsFileName));
			String line = "";
			while( (line=bin.readLine())!=null){
				if( line.trim().equalsIgnoreCase("") )
					continue;
				if( !line.startsWith("#") ){
					System.out.println(line);
					String[] fields = line.split("\\s+");
					tmp.put( Integer.parseInt(fields[0]), fields[1]);
				}
			}
		}catch (Exception e) {
			Assert.fatal(true, "Error Reading Descriptions File \""+descriptionsFileName+"\"");
		}
		return tmp;
	}
	
	/**
	 * Lists all the Settings
	 */
	public static void list(){
		final Iterator<Object> it = props.keySet().iterator();
		while(it.hasNext()){
			final Object key = it.next();
			final Object value = props.get(key);
			System.out.println( key +" == " + value);
		}
	}
	
	/**
	 * Gets the Property 'name' or sets it to 'defaultVal' if it has not been set already.
	 * @param name The name Specifying the Property value to obtain.
	 * @param defaultVal The default value the property should be Set to is is has not been set already.
	 * @return The value for the property specified by 'name'
	 */
	public static String getProperty(String name, String defaultVal){
		if( props.containsKey(name) )
			return props.getProperty(name);
		else{
			props.setProperty(name, defaultVal);
			return defaultVal;
		}
	}
	
	/**
	 * Gets the Property 'name' or sets it to 'defaultVal' if it has not been set already.
	 * @param name The name Specifying the Property value to obtain.
	 * @param defaultVal The default value the property should be Set to is is has not been set already.
	 * @return The value for the property specified by 'name'
	 */
	public static double getProperty(String name, double defaultVal){
		if( props.containsKey(name) )
			return Double.parseDouble( props.getProperty(name) );
		else{
			props.setProperty(name, defaultVal+"");
			return defaultVal;
		}
	}
	
	/**
	 * Gets the Property 'name' or sets it to 'defaultVal' if it has not been set already.
	 * @param name The name Specifying the Property value to obtain.
	 * @param defaultVal The default value the property should be Set to is is has not been set already.
	 * @return The value for the property specified by 'name'
	 */
	public static boolean getProperty(String name, boolean defaultVal){
		if( props.containsKey(name) )
			return Boolean.parseBoolean( props.getProperty(name) );
		else{
			props.setProperty(name, defaultVal+"");
			return defaultVal;
		}
	}
	
	/**
	 * Gets the Property 'name' or sets it to 'defaultVal' if it has not been set already.
	 * @param name The name Specifying the Property value to obtain.
	 * @param defaultVal The default value the property should be Set to is is has not been set already.
	 * @return The value for the property specified by 'name'
	 */
	public static int getProperty(String name, int defaultVal){
		if( props.containsKey(name) )
			return Integer.parseInt( props.getProperty(name) );
		else{
			props.setProperty(name, defaultVal+"");
			return defaultVal;
		}
	}
	
	/**
	 * Gets the Property 'name' or sets it to 'defaultVal' if it has not been set already.
	 * @param name The name Specifying the Property value to obtain.
	 * @param defaultVal The default value the property should be Set to is is has not been set already.
	 * @return The value for the property specified by 'name'
	 */
	public static long getProperty(String name, long defaultVal){
		if( props.containsKey(name) )
			return Long.parseLong( props.getProperty(name) );
		else{
			props.setProperty(name, defaultVal+"");
			return defaultVal;
		}
	}
	
	/**
	 * Sets the Property called name to the value 'value'
	 * @param name The name Specifying the Property value to set.
	 * @param value The value the property should be set to.
	 */
	public static void setProperty(String name, boolean value){
		props.setProperty(name, value+"");
	}
	
	/**
	 * Sets the Property called name to the value 'value'
	 * @param name The name Specifying the Property value to set.
	 * @param value The value the property should be set to.
	 */
	public static void setProperty(String name, String value){
		props.setProperty(name, value);
	}
	
	/**
	 * Sets the Property called name to the value 'value'
	 * @param name The name Specifying the Property value to set.
	 * @param value The value the property should be set to.
	 */
	public static void setProperty(String name, int value){
		props.setProperty(name, value+"");
	}
	
	/**
	 * Sets the Property called name to the value 'value'
	 * @param name The name Specifying the Property value to set.
	 * @param value The value the property should be set to.
	 */
	public static void setProperty(String name, double value){
		props.setProperty(name, value+"");
	}
	
	/**
	 * Simple Test of Settings Class
	 * @param args
	 */
	public static void main(String[] args) {
		initSettings();
		list();
	}
	
	/**
	 * The runID, static for this execution of Flair
	 */
	private static String runID;

	/**
	 * Generate a unique run identifier for the current
	 * execution of Flair 
	 * @return the run identifier
	 */
	public static String getRunID() {
		if( runID == null ){
			UID uid = new UID();
			runID = uid.toString();
		}
		return runID;
	}

}
