package uk.ac.gla.mir.flair.parser.grammer;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import uk.ac.gla.mir.flair.datamodel.*;
import uk.ac.gla.mir.flair.datamodel.dataElement.*;
import uk.ac.gla.mir.flair.engine.input.spec.DTDReader;
import uk.ac.gla.mir.flair.util.*;
import gnu.trove.TIntObjectHashMap;
import gnu.trove.TObjectIntHashMap;

/**
 * Title:        Interpreter based parsers
 * Description:  This is a parser based on the Interpreter patern
 * Copyright:    Copyright (c) 2003
 * Company:      University of Glasgow
 * @author 		 David Hannah
 * @version 1.0
 */
public class DocParser {

	/**
	 * A Buffered Reader Reads in the File to Parse
	 */
	private BufferedReader bin;
	private TypeObject currentType;
	private TypeTable tt;
	private long counter;
	private byte encodingType;
	
	/**
	 * 
	 */
	public DocParser(){
		counter = 0;
	}
	
	/**
	 * Builds a Document Parser from the TypeTable Supplied
	 * @param tt The TypeTable of the Objects to Parse
	 * @param fin The File to parse
	 * @return The Document Parser
	 */
	public TypeObject buildParser(TypeTable tt, File fin) throws IOException{
		bin = Files.openFileReader(fin);
		final char[] cbuff = new char[100];
		while( bin.read(cbuff) != 1 ){
			final String sbuff = new String(cbuff);
			if(sbuff.contains("\r\n")){
				encodingType = 0;
				break;
			}else{
				encodingType = 1;
				break;
			}
		}
		bin.close();
		bin = Files.openFileReader(fin);
		// Index O Must always be the document Root
		currentType =	tt.getTypeObject(0);
		this.tt = tt;
		counter = 0;
		return currentType;
	}
	
	/**
	 * Gets the File Position in the Current File
	 * @return The File Position
	 */
	public long getFilePosition(){
		return counter;
	}
	
	public void resetFilePositions(){
		counter = 0;
	}
	
	public BufferedReader getBufferedReader(){
		return bin;
	}
	
	/**
	 * Parses a Single Document
	 * @param currentType
	 * @return the IRObject Representing this document
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InstantiationException
	 */
	public IRObject parseSingleIRObject(TypeObject currentType) throws IllegalAccessException,
															ClassNotFoundException,
															IOException,
															InstantiationException{
		
		theIRObjectString = readDocument( currentType );
		positionInFile = 0;
		
		DataElement dataElement = parseDocument( new TypeObject[]{currentType} );
			
		IRObject irObject = null;
		if(dataElement != null){
			irObject = new IRObject();
			irObject.initialize(tt);
			irObject.addAnElement(0, dataElement);
		}
		
		return irObject;
	}
	
	/**
	 * Reads the Document Returning a Single IRObject
	 * as a String
	 * @param currentType The Type of the IRObject
	 * @return The String representation of the IRObject
	 * @throws IOException
	 */
	public String readDocument( TypeObject currentType ) throws IOException{
		final StringBuffer returnString  = new StringBuffer();
		final Pattern startPattern = Pattern.compile( currentType.getBeginTag(), Pattern.CASE_INSENSITIVE );
		final Pattern endPattern = Pattern.compile( currentType.getEndTag(), Pattern.CASE_INSENSITIVE );
	
		boolean inDocument = false;
		String line = null;
		while( (line = bin.readLine()) != null){
			if(encodingType==0)
				counter += line.length()+2;
			else
				counter += line.length()+1;
			final Matcher startTagMatcher = startPattern.matcher(line);
			if(startTagMatcher.find())
				inDocument = true;
			
			if(inDocument){
				returnString.append(line);
				returnString.append(" ");
			}
			final Matcher endTagMatcher = endPattern.matcher(line);
			if(endTagMatcher.find()){
				inDocument=false;
				break;
			}
		}
		
		return new String(returnString);
	}
	
	private int positionInFile;
	private String theIRObjectString;
	
	/**
	 * Parses the String irObjectString and returns the DataElement
	 * @param currentTypes
	 * @return the IRObject that has been parsed
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private DataElement parseDocument(TypeObject[] currentTypes) throws FileNotFoundException,
															IOException, 
															ClassNotFoundException,
															IllegalAccessException,
															InstantiationException{
		
		final HashMap<String, TypeObject> tags = new HashMap<String, TypeObject>();
		
		final String irObjectString = theIRObjectString.substring(positionInFile);
		//System.out.println(irObjectString + " : "+positionInFile);
		final String[] tagNames = new String[currentTypes.length];
		for(int i = 0; i < tagNames.length; i++){
			tagNames[i] = currentTypes[i].TagName.toUpperCase();
			tags.put(tagNames[i], currentTypes[i]);
		}
				
		DataElement dataElement = null;
			
		String startTagPatternString = "";
		for(int i = 0; i < tagNames.length; i++){
			startTagPatternString += "<"+tagNames[i]+"|";
		}
		startTagPatternString = startTagPatternString.substring(0, startTagPatternString.length()-1);
		
		String endTagPatternString = "";
		for(int i = 0; i < tagNames.length; i++){
			endTagPatternString += "</"+tagNames[i]+">|";
		}
		endTagPatternString = endTagPatternString.substring(0, endTagPatternString.length()-1);
		
		final Pattern startTagPattern = Pattern.compile( startTagPatternString , Pattern.CASE_INSENSITIVE );
		final Pattern endTagPattern = Pattern.compile( endTagPatternString, Pattern.CASE_INSENSITIVE );
		
		final Matcher startTagMatcher = startTagPattern.matcher(irObjectString);
		if( startTagMatcher.find() ){
			final String currentTag = startTagMatcher.group().replaceAll("<", "").replaceAll(">", "").toUpperCase();
			//System.out.println("Found: "+currentTag);
			final TypeObject theCurrentType = tags.get(currentTag);
			
			final int fieldType = theCurrentType.getFieldType();
			final String className = uk.ac.gla.mir.flair.datamodel.dataElement.Constants.classnames[fieldType % 1000];
			
			//System.out.println( className );
			Class lc = Class.forName(className, false, DocParser.class.getClassLoader() );
			dataElement = (DataElement)lc.newInstance();
			dataElement.setId(theCurrentType.getFieldId());
			//System.out.println( dataElement.getClass().getName() );
			
			final String currentCMD = theCurrentType.getCmdType();
			if( currentCMD.equalsIgnoreCase( Constants.cmd_typeSeries )){
				//System.out.println("This is a Seq DataElement." + theCurrentType.getCmdType());
							
				final Address children  = theCurrentType.getChildren();
				final ArrayList<TypeObject> nextTypes = new ArrayList<TypeObject>();
				final Iterator it = children.iterator();
				while(it.hasNext()){
					final int id = (Integer)it.next();
					//System.out.print("Going to parse : " + id + " " );
					final TypeObject nextType = tt.getTypeObject(id);
					nextTypes.add(nextType);
				}
					
				final TypeObject[] childrenObjects = nextTypes.toArray( new TypeObject[0] );
				String newIrObject = irObjectString.substring(startTagMatcher.end());

				positionInFile += startTagMatcher.end();
				positionInFile += newIrObject.indexOf(">")+1;
				newIrObject = newIrObject.substring( newIrObject.indexOf(">")+1 );
							
				final String[] childTtagNames = new String[childrenObjects.length];
				for(int i = 0; i < childTtagNames.length; i++){
					childTtagNames[i] = childrenObjects[i].TagName.toUpperCase();
				}
				
				String childStartTagPatternString = "";
				for(int i = 0; i < childTtagNames.length; i++){
					childStartTagPatternString += "<"+childTtagNames[i]+"|";
				}
				childStartTagPatternString = childStartTagPatternString.substring(
												0, childStartTagPatternString.length()-1);
				
				final Pattern childStartTagPattern = Pattern.compile( childStartTagPatternString , Pattern.CASE_INSENSITIVE );
				
				SEQUENCELOOP:while(true){
					//System.out.println( "Hello: "+newIrObject );
					if( newIrObject.trim().equalsIgnoreCase("") ){
						break SEQUENCELOOP;
					}
					
					final String firstWord = newIrObject.substring(
								newIrObject.indexOf("<"),newIrObject.indexOf(">")+1);
					final String tmp = newIrObject.substring(0, newIrObject.indexOf("<"));
					final Matcher endTagMatcher = endTagPattern.matcher(firstWord);
					if(endTagMatcher.find()){
					//	System.out.println( firstWord +" == "+endTagMatcher.pattern().pattern() + " Exiting!");
						positionInFile += firstWord.length()+tmp.length();
						break SEQUENCELOOP;	
					}
					TypeObject documentRoot = tt.getTypeObject(0);
					String endTag = documentRoot.getEndTag();
					final Pattern endDocPattern = Pattern.compile( endTag, Pattern.CASE_INSENSITIVE);
					//System.out.println( "Pattern: "+endDocPattern.pattern() );
					final Matcher endDocumentMatcher = endDocPattern.matcher(firstWord);
					if(endDocumentMatcher.find() ){
						//System.out.println( firstWord +" == "+endTagMatcher.pattern().pattern() + " Exiting!");
						positionInFile += firstWord.length()+tmp.length();
						break SEQUENCELOOP;	
					}
					final Matcher childrenStartMatcher = childStartTagPattern.matcher(firstWord);
					if(childrenStartMatcher.find()){
						DataElement tmpDe = parseDocument(childrenObjects);
						if(tmpDe != null){
							((SequenceDE)dataElement).add(tmpDe);
						}
					}else{
						//System.out.println( "In the else " +childrenStartMatcher.pattern().pattern() + " == " + firstWord);
						positionInFile += newIrObject.indexOf(">")+1;
					}
					newIrObject = theIRObjectString.substring(positionInFile);
				//	System.out.println(newIrObject +" : "+positionInFile);
				}
				
			}else if( currentCMD.equalsIgnoreCase( Constants.cmd_typeRepeat )){
				//System.out.println("This is a Seq DataElement." + theCurrentType.getCmdType());
							
				final Address children  = theCurrentType.getChildren();
				final ArrayList<TypeObject> nextTypes = new ArrayList<TypeObject>();
				final Iterator it = children.iterator();
				while(it.hasNext()){
					final int id = (Integer)it.next();
					//System.out.print("Going to parse : " + id + " " );
					final TypeObject nextType = tt.getTypeObject(id);
					nextTypes.add(nextType);
				}
					
				final TypeObject[] childrenObjects = nextTypes.toArray( new TypeObject[0] );
				String newIrObject = irObjectString.substring(startTagMatcher.end());
				
				positionInFile += startTagMatcher.end();
				positionInFile += newIrObject.indexOf(">")+1;
				newIrObject = newIrObject.substring( newIrObject.indexOf(">")+1 );
		
				final String[] childTtagNames = new String[childrenObjects.length];
				for(int i = 0; i < childTtagNames.length; i++){
					childTtagNames[i] = childrenObjects[i].TagName.toUpperCase();
				}
				
				String childStartTagPatternString = "";
				for(int i = 0; i < childTtagNames.length; i++){
					childStartTagPatternString += "<"+childTtagNames[i]+"|";
				}
				childStartTagPatternString = childStartTagPatternString.substring(
												0, childStartTagPatternString.length()-1);
				
				final Pattern childStartTagPattern = Pattern.compile( childStartTagPatternString , Pattern.CASE_INSENSITIVE );
				
				SEQUENCELOOP:while(true){
					//System.out.println( newIrObject );
					final String firstWord = newIrObject.substring( 	newIrObject.indexOf("<"), newIrObject.indexOf(">")+1 );
					final String tmp = newIrObject.substring(0, newIrObject.indexOf("<"));
					final Matcher endTagMatcher = endTagPattern.matcher(firstWord);
						if(endTagMatcher.find()){
							//System.out.println( firstWord +" == "+endTagMatcher.pattern().pattern() + " Exiting!");
							positionInFile += firstWord.length()+tmp.length();
							break SEQUENCELOOP;	
						}
					
					TypeObject documentRoot = tt.getTypeObject(0);
					String endTag = documentRoot.getEndTag();
					final Pattern endDocPattern = Pattern.compile( endTag, Pattern.CASE_INSENSITIVE);
					final Matcher endDocumentMatcher = endDocPattern.matcher(firstWord);
					if(endDocumentMatcher.find() ){
						//System.out.println( firstWord +" == "+endTagMatcher.pattern().pattern() + " Exiting!");
						positionInFile += firstWord.length()+tmp.length();
						break SEQUENCELOOP;	
					}
					final Matcher childrenStartMatcher = childStartTagPattern.matcher(firstWord);
					if(childrenStartMatcher.find()){
						DataElement tmpDe = parseDocument(childrenObjects);
						if(tmpDe != null){
							((RepeatDE)dataElement).add(tmpDe);
						}
					}else{
						//System.out.println( "In the else " +childrenStartMatcher.pattern().pattern() + " == " + firstWord);
						positionInFile += newIrObject.indexOf(">")+1;
					}
					//System.out.println(positionInFile);
					newIrObject = theIRObjectString.substring(positionInFile);
					//System.out.println(newIrObject +" : "+positionInFile);
				}	
			}else if( currentCMD.equalsIgnoreCase( Constants.cmd_typePlain ) ){
				
				//System.out.println("Plain Type "+ irObjectString);
				positionInFile += irObjectString.indexOf(">")+1;
				String newIRObject = irObjectString.substring( irObjectString.indexOf(">")+1);
				//System.out.println(positionInFile +" : "+newIRObject);
				//System.out.println(theCurrentType.getEndTag());
				final String tmp = newIRObject.toUpperCase();
				String data = "";
				try{
					data = newIRObject.substring(0, tmp.indexOf( theCurrentType.getEndTag().toUpperCase() ));
					
					//System.out.println(data);
					positionInFile += data.length() + theCurrentType.getEndTag().length();
				}catch (Exception e) {
					positionInFile += data.length();
				}
					//System.out.println(positionInFile);
					if( fieldType == uk.ac.gla.mir.flair.datamodel.dataElement.Constants.cDeSTR){
						//System.out.println(" ** STRING DE ** == "+data);
						((StringDE)dataElement).setValue(data);
						dataElement.setId( theCurrentType.getFieldId() );
					}else if( fieldType == uk.ac.gla.mir.flair.datamodel.dataElement.Constants.cDePNT){
						//System.err.println(data);
						((PointDE)dataElement).setValue(data);
						dataElement.setId( theCurrentType.getFieldId() );
					}else if( fieldType == uk.ac.gla.mir.flair.datamodel.dataElement.Constants.cDeINT){
						((IntegerDE)dataElement).setValue(data);
						dataElement.setId( theCurrentType.getFieldId() );
					}
			}
		}
		return dataElement;
	}
	
	/**
	 * Seeks through the input stream to the Position docPosition
	 * @param docPosition The Position in the File to Skip To
	 * @throws IOException
	 */
	public void skip(final long docPosition) throws IOException{
		bin.skip(docPosition);
	}
	
	/**
	 * Parses a Single IRObject from the first document in the Settings files 
	 * @param args No Arguments Needed
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		//System.setProperty("flair.home", ".");
		//System.setProperty("flair.etc", "etc/");
		Settings.initSettings();
		
		final String flair_home = Settings.getProperty("flair.home", "");
		final String flair_etc = Settings.getProperty("flair.etc", "");
		final String document_description_file = Settings.getProperty("flair.description.file", "");
		final String inputDocument = Settings.getProperty("flair.documents.file", "");
		final String stopwordsFile = Settings.stopfilelocation;
		final String index_path = Settings.getIndexPath();
		final String tmpDir = Settings.getProperty("flair.temp.dir", "");
		
		Assert.info(true, "Flair Home = "+flair_home+
						  "\nFlair ETC = "+flair_etc+
						  "\nFlair Descriptions File = "+document_description_file+
						  "\nFlair Input File = "+inputDocument+
						  "\nFlair Stopwords File = " +stopwordsFile+
						  "\nFlair Index Path = "+ index_path);
		
		final TIntObjectHashMap<String> descriptionFiles = Settings.getDocumentDescriptionFiles();
		final TIntObjectHashMap<TypeTable> typeTables = new TIntObjectHashMap<TypeTable>();
		final int[] typeTabelIDs = descriptionFiles.keys();
		
		for(int i = 0; i < typeTabelIDs.length; i++){
			try{
				System.out.println("Reading Description File "+typeTabelIDs[i]+" = "+
									descriptionFiles.get( typeTabelIDs[i] ) );
				final File fin = new File( descriptionFiles.get( typeTabelIDs[i] ) );
				final DTDReader descReader = new DTDReader(fin);
				final TypeTable tt = descReader.buildDescription();
				typeTables.put(typeTabelIDs[i], tt);
				
			}catch (IOException e) {
				Assert.fatal(true, "Problem Reading Description File " + descriptionFiles.get( typeTabelIDs[i] ) );
			}
		}
		
		final TObjectIntHashMap<String> documents = Settings.getDocumentFiles();
		final String[] documentFileNames = documents.keys(new String[0]);
		
		for(int i = 0; i < documentFileNames.length; i++){
			
			System.out.println( documents.get(documentFileNames[i]) + " " + documentFileNames[i]);
			final int currentType = documents.get(documentFileNames[i]);
//	//		Make Sure Input File is Ok
			File docFile = new File(documentFileNames[i]);
			DocParser ps = new DocParser();
			
			try{
				TypeObject to = ps.buildParser( typeTables.get( currentType ), docFile);
				long filePosition = ps.getFilePosition();
				IRObject irobject;
				long irobjectID = 0;
				while( (irobject = ps.parseSingleIRObject( to )) != null ){
					irobject.setID(irobjectID);
					DataElement.printDataElement(irobject.PickData, 0, typeTables.get( documents.get( documentFileNames[i] )));
					
					filePosition = ps.getFilePosition();
					System.out.println( documentFileNames[i] + " : "+ filePosition);
					System.exit(-1);
					// ************* Add To Index ************* //
					
					irobjectID++;
				}
			}catch (FileNotFoundException fnfe) {
				Assert.fatal(false, "Parser Could Not Find Document File " + tmpDir+Settings.fileSep+"temp_input_file");
				fnfe.printStackTrace();
			}catch (IllegalAccessException iae) {
				Assert.fatal(false, "Parser Could Not Find Document File " + tmpDir+Settings.fileSep+"temp_input_file");
				iae.printStackTrace();
			}catch (ClassNotFoundException cnfe) {
				Assert.fatal(false, "Parser Could Not Find Document File " + tmpDir+Settings.fileSep+"temp_input_file");
				cnfe.printStackTrace();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}catch (InstantiationException ie) {
				Assert.fatal(false, "Parser Could Not Find Document File " + tmpDir+Settings.fileSep+"temp_input_file");
				ie.printStackTrace();
			}
		}
	}
}