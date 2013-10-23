package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        Interpreter based parsers
 * Description:  This is a parser based on the Interpreter patern
 * Copyright:    Copyright (c) 2003
 * Company:      University of Glasgow
 * @author 		 Lots of authors (steven & ....)
 * @version 1.0
 */

import java.io.*;

import uk.ac.gla.mir.flair.datamodel.*;
import uk.ac.gla.mir.flair.engine.input.*;
import uk.ac.gla.mir.flair.engine.input.spec.DTDReader;


public class DocParser {

	// These are for handling the input stream;
	private FileInputStream fileIn;
	private InputStreamReader inrd;
	private BufferedReader in;

	//private DocumentPositionSet docPositions;
	;//private int filePathID;	

	public DocParser(){
	}

	/**
	* Creates a TagCmd to parse a file with the structure defined in typeSet
	* @param tt Description of file structure
	* @return TagCmd able to evaluate a file of typeSet's structure's content
	* ASSUME: Any Repeat tag has only one child
	*/
	public TagCmd buildParser(TypeTable tt) {

		TagCmd tempCmd;
		//	TagCmd[] cmdArray = new TagCmd[idList.size()];
		TagCmd[] cmdArray = new TagCmd[tt.size()];
		int currentID;
		TagProcessor processor = new TagProcessor();
		String currentCmdType;

		//Changed when moving this method from DescriptionReader
		/*	for (int i=0; i<idList.size(); i++) {
			    currentID = Integer.parseInt((String)idList.elementAt(i));
			    currentTag = typeSet.getTypeObject(currentID);
			    currentCmdType = currentTag.getCmdType();*/
		for (int i = 0; i < tt.size(); i++) {
					
			TypeObject currentTag = tt.at(i);
			currentCmdType = currentTag.getCmdType();
			currentID = currentTag.getFieldId();
	
			if (currentCmdType.equals(Constants.cmd_typePlain)) {
				//Create atomic TagCmd and add to array
				tempCmd =
					new TagCmdAtom(
						currentTag.getTagName(),
						currentTag.getFieldId(),
						processor);
	
				cmdArray[i] = tempCmd;
			}
			else if (currentCmdType.equals(Constants.cmd_typeSeries)) {	
				
				//Create a series TagCmd and add to array
				Address childList = currentTag.getChildren();
				TagCmd[] tempCmdArr = new TagCmd[childList.size()];
				int tempID;

				for (int j = 0; j < childList.size(); j++) {
					tempID = childList.getElementAt(j);

					for (int k = 0; k < i; k++) {
					
						if (cmdArray[k].getId() == tempID) {
							tempCmdArr[j] = cmdArray[k];
						}					
					}
				}

				tempCmd = new TagCmdSeries(tempCmdArr);
		
				cmdArray[i] =
					new TagCmdNester(
						currentTag.getTagName(),
						currentID,
						processor,
						tempCmd);
			}
			else if (currentCmdType.equals(Constants.cmd_typeRepeat)) {
				//Create a Repeat TagCmd and add to array
				Address childList = currentTag.getChildren();
				int tempID;

				//ASSUME: childList is of length 1
				tempID = childList.getElementAt(0);

				for (int k = 0; k < i; k++) {

					if (cmdArray[k].getId() == tempID) {
						tempCmd = cmdArray[k];
						tempCmd = new TagCmdRepeat(tempCmd, processor);
						cmdArray[i] =
							new TagCmdNester(
								currentTag.getTagName(),
								currentID,
								processor,
								tempCmd);
					}
				}
			}
			else if (currentCmdType.equals(Constants.cmd_typeAnyOf)) {
				//Create an AnyOf TagCmd and add to array
				Address childList = currentTag.getChildren();
				TagCmd[] tempCmdArr = new TagCmd[childList.size()];
				int tempID;

				for (int j = 0; j < childList.size(); j++) {
					tempID = childList.getElementAt(j);

					for (int k = 0; k < i; k++) {
						if (cmdArray[k].getId() == tempID) {
							tempCmdArr[j] = cmdArray[k];
						}
					}
				}

				tempCmd = new TagCmdAnyOf(tempCmdArr);
				cmdArray[i] =
					new TagCmdNester(
						currentTag.getTagName(),
						currentID,
						processor,
						tempCmd);
			}
		
		}
		
		for(int i = 0; i < cmdArray.length; i++){
			System.out.println( cmdArray[i] + " : " + cmdArray[i].getName() + " : " + cmdArray[i]._Id );
		}
		
		return cmdArray[cmdArray.length - 1];
	}

	/**
	 * Opens docFile and parses its contents using tagCmd, then evaluates the
	 * contents. Copied from parsers.FileReader.
	 * @param tagCmd Structure of the file to be parsed
	 * @param docFile File to parse
	 */
	public void parse(TagCmd tagCmd, File docFile) {

		//Setup storage of file and document locations
//		try {
//			FilePathSet files =
//				FilePathSet.load();
//			filePathID = files.add(docFile);
//
//			files.save();
//		}
//		catch (IOException ioe) {
//			System.out.println("Unable to add new file to current set.");
//			System.exit(1);
//		}
//
//		try{docPositions =
//			DocumentPositionSet.load();}
//		catch (IOException w)
//		{
//			w.printStackTrace();
//			System.out.println("settings not initialized");
//		}

		String line = null;
		String copyLine = null;
		String dataString = null;
		String slackLine = null;
		String tagName = tagCmd.getName();
		int len = tagName.length();
		char[] lineArray = null;
		int startL = 0, endL = 0, startC = 0, endC = 0;
		int i = 0;
		boolean readFlag = true, readFlag1 = true, flag = true;
		int currentLineNum = 0, startTagLineNum;
		long nextID = 0;

		try {
			fileIn = new FileInputStream(docFile);
			inrd = new InputStreamReader(fileIn);
			in = new BufferedReader(inrd);
		}
		catch (IOException e) {
			System.out.println(
				"Error opening file "
					+ docFile.getAbsoluteFile()
					+ ". Please ensure the specified path exists");
			System.exit(1);
		}

		try {
			while (readFlag) {
				if ((line = in.readLine()) != null) {
					currentLineNum++;
					long externalID;

					if (slackLine != null) {
						line = slackLine + " " + line;
						slackLine = null;
					}
					startL = line.indexOf("<" + tagName + ">");

					if (startL == -1) {
						Debug.warn(
							flag,
							"TagFileReader: Looking for"
								+ tagName
								+ "**"
								+ line);
					}
					else {
						startTagLineNum = currentLineNum;
						readFlag1 = true;
						endL = line.indexOf("</" + tagName + ">");
						if (endL == -1) {
							copyLine = new String(line.substring(startL));
						}
						else {
							if (copyLine == null) {
								copyLine =
									new String(
										line.substring(startL, endL + len + 3));
							}
							else {
								copyLine
									+= new String(
										line.substring(0, endL + len + 3));
							}
							dataString = new String(copyLine);
							copyLine = null;
							readFlag1 = false;
						}

						while (readFlag1) {
							if ((line = in.readLine()) != null) {
								currentLineNum++;
								endL = line.indexOf("</" + tagName + ">");
								startL = line.indexOf("<" + tagName + ">");

								if (startL != -1) { // missed one endtag!
									Debug.debug(
										flag,
										"TagFileReader: missing </"
											+ tagName
											+ "> **"
											+ line);
								}
								if (endL == -1) {
									copyLine += line;

								}
								else { // found end tag
									copyLine
										+= new String(
											line.substring(0, endL + len + 3));
									dataString = new String(copyLine);
									if (line.length() > endL + len + 3) {
										slackLine =
											new String(
												line.substring(endL + len + 3));
									}
									copyLine = null;
									readFlag1 = false;
								}
							}
							else
								readFlag1 = false;
						}
						System.out.println(dataString);

						TagObject to =
							tagCmd.evaluate(dataString.toCharArray(), 0);
						System.out.println("TAGOBJ"+to);
						//Add new document to the current set
						int size = currentLineNum - startTagLineNum;
						externalID = nextID++;
//						long internal =
//							docPositions.add(
//								externalID,
//								filePathID,
//								startTagLineNum,
//								size);

					}
				}
				else {
					readFlag = false;
				}
			}
//			docPositions.save();
//			docPositions.displayFile(filePathID);

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	} // end of parse()

	public static void main(String[] args) {
		
		
		try{
			DTDReader dr = new DTDReader("D:\\flairfiles/inputfiles/desc/apDocDescriptionInt.txt");
			TypeTable tt = dr.buildDescription();
	
			System.out.println(tt);
			
//			IRBase base = new IRBase();
//			base.getObjectSet().setTypeTable(tt);
			DocParser parser = new DocParser();
			TagCmd tc = parser.buildParser(tt);
			parser.parse(tc, new File("D:\\flairfiles"+"/inputfiles/aptest1/apDoc.txt"));
		}
		catch (IOException ioe)
		{
			System.out.println("File not found");
			System.exit(1);
		}		
	}
}