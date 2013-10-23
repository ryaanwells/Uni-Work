package uk.ac.gla.mir.flair.engine.input.spec;

/**
 * Title:        Description Readers and Writers
 * Description:  Reads a markup language description from a file and builds a
 *               TypeTable based on this description
 * Company:      Department of Computing Science, University of Glasgow
 * @author 		 Steven Morrison
 * @version 1.0
 */

import java.io.*;
import java.util.*;

import uk.ac.gla.mir.flair.datamodel.*;
import uk.ac.gla.mir.flair.engine.input.*;
import uk.ac.gla.mir.flair.util.MarkupTokeniser;
import uk.ac.gla.mir.flair.util.Settings;

public class DTDReader {

	// These are for handling the input stream;
	private StreamTokenizer stk;

/*	private TypeObject currentTag;*/
	private Vector idList;

//	private int filePathID;
//	private DocumentPositionSet docPositions;

	/**
	 * Constructor
	 * Opens the given <code>File</code> parameter for reading
	 * @param file Local file to parse contents of
	 */
	public DTDReader(File file) throws IOException{
		
		String fileContents = MarkupTokeniser.separateDescTokens(file);
	
		stk = new StreamTokenizer(new StringReader(fileContents));
		stk.eolIsSignificant(false);
		stk.wordChars(47, 90);
		stk.wordChars(95, 122);
		stk.whitespaceChars(0, 32);
	}
	
	/**
	 * Constructor using a String Filename
	 * @param fileName The File Name/Path of the File
	 * @throws IOException
	 */
	public DTDReader(String fileName) throws IOException{
		this(new File(fileName));
	}
	
	/**
	 * Reads to the end of <code>stk</code>, creating TypeObjects for each
	 * &lt;tag&gt;&lt;/tag&gt; pair.
	 * @return a TypeTable of all TypeObjects created from file description.
	 */
	public TypeTable buildDescription(){
		String /*line,*/ currentTagType, currentToken/*,nextToken*/ = "";
		//boolean inOpenTag;
		//boolean waitingForCloseTag;
		Address childList = new Address();
		TypeTable typeSet = new TypeTable(20);
		TypeObject currentTag;
		//int nextTokenInt = 0;
		idList = new Vector();
		try {
			while (stk.nextToken() != StreamTokenizer.TT_EOF) {

				while (stk.sval == null)
					stk.nextToken();

				currentToken = stk.sval;
				currentTag = new TypeObject();

				if (currentToken.equals(Constants.atomOpenTag))
					//ATOM type
					currentTagType = Constants.ATOM;
				else if (currentToken.equals(Constants.groupOpenTag))
					//GROUP type
					currentTagType = Constants.GROUP;
				else
					//Tag type not recognised. Exit loop
					throw new IOException();

				//boolean foundEndOpenTag = false;
				//Read attributes for current tag
				while (stk.nextToken() != StreamTokenizer.TT_EOF && !stk.sval.equals(Constants.endOpenTag)) {

					//Get next attribute from stk
					String[] tokens = getAttribute();

					//Add newly found attribute to the currentTag
					setAttribute(tokens, currentTag);

				} //End while (reading attributes)

				//If GROUP tag, read children
				if (currentTagType.equals(Constants.GROUP)) {
					childList = new Address();

					while (stk.nextToken() == StreamTokenizer.TT_NUMBER) {
						//Add child to current list
						childList.add((int) stk.nval);
					}

					currentTag.setChildren(childList);

					//Read close tag for GROUP
					if (!tagClosed(Constants.groupCloseTag))
						//Could not close tag. Stop parsing.
						break;
				}

				//Read close tag for ATOM
				else if (stk.nextToken() != StreamTokenizer.TT_EOF && !tagClosed(Constants.atomCloseTag)) {
					//Could not close tag. Stop parsing.
					break;
				}

				if (!isValidTag(currentTag, currentTagType))
				{
					System.out.println("Tag is poorly formed. One or more attributes missing.\n" + currentTag);
				}
				else
				{	typeSet.add(currentTag);}
		
				//Add tag ID to idList for use in other methods
				//Add as a String to keep the Vector happy
				idList.add(currentTag.getFieldId() + "");

				//Update parent IDs for any children of currentTag
				if (currentTagType.equals(Constants.GROUP)) {
					for (int i = 0; i < childList.size(); i++)
						typeSet.setParentId(childList.getElementAt(i), currentTag.getFieldId());
				}

			} //End while (reading <tag></tag> pairs)

			typeSet.completeTypeTable();
			typeSet.consistencyCheck();
		}
		catch (IOException ioe) {
			//stk ran out of tokens during a parse => illformed input file
			//Inform user
			System.out.println("Illformed input file: " +
					"At line " + stk.lineno() + ": Found \"" + stk.sval + "\" / " + stk.nval);
			System.exit(1);
		}
		return typeSet;
	} // end of  buildDescription()

	/**
	 * Returns true if the given tag has all its parameters set to valid values.
	 * Returns false otherwise
	 * @param tag The tag to verify
	 * @return true if the given tag has all its parameters set to valid values,
	 * false otherwise
	 */
	private boolean isValidTag(TypeObject tag, String tagType) {

		if (tagType.equals(Constants.ATOM)) {
			if (tag.getCmdType()!=null) {
				try {
					Object o = Class.forName(tag.getCmdType());
				}
				catch (ClassNotFoundException cnfe) {
					//return false;
				}
			}
			else
				return false;
		}

		if (tag.getFieldType()<1000)
			return false;

		if (tag.getName()==null)
			return false;

		if (tag.getTagName()==null)
			return false;

		return true;
	}

	/**
	 * Ensures tagName is closed properly in <code>stk</code>.<br>
	 * If not closed properly, a suitable error message is displayed.
	 * @param closeTag String of the form </tagName>
	 * @return True if tag was closed correctly. False otherwise.
	 * @throws IOException if reading from <code>stk</code> fails
	 */
	private boolean tagClosed(String closeTag) throws IOException {

		String currentToken = stk.sval;

		if (currentToken.equals(closeTag))
			//Matched full tag
			return true;
		else {
			//Tag not closed properly. Inform user
			showError(currentToken, closeTag);
			return false;
		}
	}

	/**
	 * Displays a simple error to show a syntax problem in the input file
	 * @param tokenFound The token found in the input file that caused the error
	 * @param tokenExpected The token which was expected where tokenFound was
	 */
	private void showError(String tokenFound, String tokenExpected) {

		System.out.println("Illformed input file:");
		System.out.println("At line " + stk.lineno() + ": Expected " + tokenExpected + ". Found " + tokenFound);
	}

	/**
	 * Determines the attribute to update in currentTag from tokens[0],
	 * then updates this value with token[1]
	 * @param tokens token[0] = Attribute name; token[1] = Attribute value
	 * @param currentTag TypeObject to update
	 */
	private void setAttribute(String[] tokens, TypeObject currentTag) {

		if (tokens[0].equals(Constants.typeLabel)) {
			if (tokens[1].equals(Constants.typeCHAR_DE))
				currentTag.setFieldType(Constants.typeCHAR_DEval);
			else if (tokens[1].equals(Constants.typeSEQ_DE))
				currentTag.setFieldType(Constants.typeSEQ_DEval);
			else if (tokens[1].equals(Constants.typeREP_DE))
				currentTag.setFieldType(Constants.typeREP_DEval);
			else if (tokens[1].equals(Constants.typeINT_DE))
				currentTag.setFieldType(Constants.typeINT_DEval);
			else if (tokens[1].equals(Constants.typeDOUBLE_DE))
				currentTag.setFieldType(Constants.typeDOUBLE_DEval);
			else if (tokens[1].equals(Constants.typePNT_DE))
				currentTag.setFieldType(Constants.typePNT_DEval);
			else
				{
				System.out.println("The given type for a field is not valid: " + tokens[1]);
				System.exit(1);
				}
		}
		else if (tokens[0].equals(Constants.cmd_typeLabel))
			currentTag.setCmdType(tokens[1]);
		else if (tokens[0].equals(Constants.nameLabel))
			currentTag.setFieldName(tokens[1]);
		else if (tokens[0].equals(Constants.idLabel)) {
			
			if(!(currentTag.getFieldType()>=1000)){
				System.out.println("Attempt to set Field ID before the Field Type is set(DTDReader)");
			}
			try {
				currentTag.setFieldId(Integer.parseInt(tokens[1]));
			}
			catch (NumberFormatException nfe) {
				System.out.println("Attempt to set Field ID to a string value: " + tokens[1]);
				System.exit(1);
			}
		}
		else if (tokens[0].equals(Constants.tag_nameLabel))
			currentTag.setTagName(tokens[1]);
		else
			System.out.println("The attribute name \"" + tokens[0] + "\" is not valid.(DTDREADER)");
	}

	/**
	 * Parse next set of tokens to ensure that attribute name and value are
	 * separate.
	 * @return String array of length 2 with [0]=attribute name and [1]=attribute value
	 * @throws IOException if reading from <code>stk</code> fails.
	 */
	private String[] getAttribute() throws IOException {

		String[] tokens = new String[2];
		//boolean tokensSet = false;

		tokens[0] = stk.sval;
		stk.nextToken();
		stk.nextToken();
		tokens[1] = stk.sval;

		//Changes "attr" + null into "attr" + "number" (for id tag)
		if (tokens[1] == null)
			tokens[1] = (int) stk.nval + "";

		return tokens;
	}

	/**
	 * Creates a new DescriptionReader and parses the given file.<br>
	 * 
	 */
	public static void main(String[] args) {
		
		System.setProperty("flair.home", ".");
		System.setProperty("flair.etc", "./etc");
		Settings.initSettings();
		
		final String flair_home = Settings.getProperty("flair.home", "");
		final String flair_etc = Settings.getProperty("flair.etc", "./etc/");
		final String document_description_file = Settings.getProperty("flair.descriptions.file", "");
		//final String dps = Settings.getdpsLocation();
		//final String fps = Settings.getfpsLocation();
		final String inputDocument = Settings.getProperty("flair.documents.file", "");
		final String stopwordsFile = Settings.stopfilelocation;
		
		System.out.println("flair.home = "+flair_home);
		System.out.println("flair.etc = "+flair_etc);
		System.out.println("flair.description.file = "+ document_description_file);
		System.out.println("flair.document.input = " + inputDocument);
		//System.out.println(dps);
		//System.out.println(fps);
		System.out.println("Stopwords File = "+stopwordsFile);
		
			DTDReader descReader;
			
			try{
				descReader = new DTDReader(new File( "etc/medlineDescription.txt"));
				
	//			Read TypeTable info from file
		    	TypeTable tt = descReader.buildDescription();
			    System.out.println(tt);
			    
			    
			    final int size = tt.size();
			    
			    for(int i = 0; i < size ; i++){
			    	TypeObject to = tt.at(i);
			    
			    	System.out.println(to);
			    }
			}
			catch (IOException e)
			{
				System.out.println("Settings Not initialsed");
			}	
	}

} 
