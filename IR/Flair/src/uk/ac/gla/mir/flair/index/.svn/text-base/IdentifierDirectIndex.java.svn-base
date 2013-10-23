package uk.ac.gla.mir.flair.index;

import java.io.*;
import uk.ac.gla.mir.flair.datamodel.dataElement.*;
import uk.ac.gla.mir.flair.util.*;

/**
 * Title:        TextDirectIndex <br/>
 * Description:  Generates a Identifier Direct Index. 
 * 				 This class can hold mappings from IRObjectIDs to the actual content.<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class IdentifierDirectIndex extends DirectIndex {

	/** The Output Stream to write data to the Direct Index File */
	private DataOutputStream directIndexOut;
	
	/** The Input Stream to read data from the Direct Index File */
	private RandomAccessFile directIndexIn;
	
	private static int TERM_BYTE_LENGTH = Settings.getProperty("term.byte.length", 20);
	
	/** The Length of the Term Indexing and 4 Bytes for the IRObject ID and number of Terms**/
	private static int ENTRY_SIZE = TERM_BYTE_LENGTH + 4 + 4;
	
	protected static byte[] zeroBuffer = new byte[TERM_BYTE_LENGTH];
	protected static byte[] tmp = new byte[TERM_BYTE_LENGTH];
	
	private long currentIROBjectID;
	private String currentData;
	private int currentDocumentLength;
	
	//private int numberOfDocuments;
	//private double aveDocLength;
	
	/**
	 * Constructs a New Identifier Direct Index Using the Default Index File Names
	 */
	public IdentifierDirectIndex(){
		this.directIndexFileName = Settings.getIndexPath()+Settings.fileSep+"directIndex";
	}
	
	/**
	 * Constructs a New Identifier Direct Index Using the filenames suppiled
	 * @param directIndexFileName
	 */
	public IdentifierDirectIndex(String directIndexFileName){
		this.directIndexFileName = directIndexFileName;
	}
	
	/**
	 * Constructs a New Identifier Direct Index Using the filenames suppiled
	 * @param textDirectIndexFileName
	 */
	public static IdentifierDirectIndex createIdentifierDirectIndex(String textDirectIndexFileName){
		IdentifierDirectIndex tii = new IdentifierDirectIndex(textDirectIndexFileName);
		try{
			tii.directIndexOut = 
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(tii.directIndexFileName)));
		}catch (Exception e) {
			Assert.fatal(true, "Problem Creating Direct Index "
							 	+ tii.directIndexFileName);
		}
		return tii;
	}
	
	/**
	 * Constructs a New Identifier Direct Index Using the Default Index File Names
	 */
	public static IdentifierDirectIndex createIdentifierDirectIndex(){
		IdentifierDirectIndex tii = new IdentifierDirectIndex();
		try{
			
			tii.directIndexOut = 
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(tii.directIndexFileName)));
			
//			tii.numberOfDocuments = 0;
//			tii.aveDocLength = 0.0;
//			tii.directIndexIn = new RandomAccessFile( tii.directIndexFileName ,"r");
//			
//			int noDocs = 0;
//			int sum = 0;
//			while( tii.readNextEntry() > 0 ){
//				noDocs++;
//				int LD = tii.getCurrentDocumentLength();
//				sum += LD;
//			}
//			tii.aveDocLength = (double)sum / (double) noDocs;
//			tii.numberOfDocuments = noDocs;
			
			
		}catch (Exception e) {
			Assert.fatal(true, "Problem Creating Direct Index "
							 	+ tii.directIndexFileName);
		}
		return tii;
	}
	
	/** 
	 * Opens a IdentifierDirectIndex for Reading
	 * @param identifierDirectIndexFileName
	 **/
	public static IdentifierDirectIndex readIdentifierDirectIndex(String identifierDirectIndexFileName){
		IdentifierDirectIndex tii = new IdentifierDirectIndex(identifierDirectIndexFileName);
		try{
			
			tii.directIndexIn = new RandomAccessFile( tii.directIndexFileName, "r");		
			
		}catch (Exception e) {
			Assert.fatal(true, "Problem Reading Direct Index "
							 	+ tii.directIndexFileName );
		}
		return tii;
	}
	
	/**
	 * Indexes the Data Element as the Identifier for Current IRObject
	 * @param de The Data Element to Index
	 * @param IRObjectID The IRObject Identifier
	 */
	public void index(DataElement de, long IRObjectID, int documentLength){
		final String className = de.getClass().getCanonicalName();
		//System.out.println("INDEXING "+className);
		if( className.endsWith("StringDE") ){
			final String term = ((StringDE)de).getCharValue();
			index(term,IRObjectID, documentLength);
		}else if( className.endsWith("IntegerDE")){
			final String term = ((IntegerDE)de).getCharValue();
			System.err.println("Indexing "+ term);
			index(term,IRObjectID,documentLength);
		}else{
			Assert.warnFatal(true, "IdentifierDirectIndex.index() called with Wrong DataType.\n"+
									"Currently only String/Interger DataElements can be Identifiers.");
		}
		
	}
	
	/**
	 * Closes the DataOutPut/InPut Streams
	 */
	public void close(){
		try{
			if(directIndexOut != null)
				directIndexOut.close();
		}catch (IOException ioe) {
			Assert.fatal(true, "Error Closing Index File "+this.directIndexFileName);
		}
	}
	
	/**
	 * @param term The term to Index
	 * @param IRObjectID The IROBject Identifier to index
	 */
	public void index(String term, long IRObjectID, int documentLength){
		byte[] tmpBytes = term.getBytes();
		final int length = ( tmpBytes.length < TERM_BYTE_LENGTH ) ? tmpBytes.length : TERM_BYTE_LENGTH;
		
		try{
			directIndexOut.writeInt((int)IRObjectID);
			directIndexOut.write(tmpBytes, 0, length);
			directIndexOut.write(
				zeroBuffer,
				0,
				TERM_BYTE_LENGTH - length);
			directIndexOut.writeInt( documentLength );
		}catch (IOException ioe) {
			Assert.fatal(true, "Problem Writing to Index.");
		}
	}
	
	/**
	 * Reads the Next Entry from the Identifier Direct Index
	 * @param entryID The Identifier for the Current Entry in the File
	 * @return Returns the number of bytes read from the stream, or 
	 * 		   -1 if EOF has been reached.
	 * @throws java.io.IOException if an I/O error occurs.
	 */
	public int readNextEntry(long entryID) throws IOException{
		try {
			
			final long position = entryID*ENTRY_SIZE;
			directIndexIn.seek(position);
			
			int bytes = tmp.length + 4 + 4;
			currentIROBjectID = directIndexIn.readInt();
			directIndexIn.read( tmp, 0, tmp.length);
			currentData = new String( tmp ).trim();
			currentDocumentLength = directIndexIn.readInt();
			return bytes;
		} catch (EOFException eofe) {
			return -1;
		}
	}
	
	
	/**
	 * Reads the Next Entry from the Identifier Direct Index
	 * @return Returns the number of bytes read from the stream, or 
	 * 		   -1 if EOF has been reached.
	 * @throws java.io.IOException if an I/O error occurs.
	 */
	public int readNextEntry() throws IOException{
		try {
			int bytes = tmp.length + 8 + 4 + 4;
			currentIROBjectID = directIndexIn.readInt();
			directIndexIn.read( tmp, 0, tmp.length);
			currentData = new String( tmp ).trim();
			currentDocumentLength = directIndexIn.readInt();
			//System.out.print( currentTerm  + " => ");	
			//System.out.println( currentIROBjectID ));
			return bytes;
		} catch (EOFException eofe) {
			//eofe.printStackTrace();
			return -1;
		}
	}
	
	/** 
	 * Performs a binary search in the term position file
	 * in order to locate the given term.
	 * If the term is located, currentTerm and Postings contain the correct data
	 * @param _IROBjectID The term to search for.
	 * @return true if the term is found, and false otherwise.
	 */
	public boolean findIdentifier(long _IROBjectID ) {
		try {
			int low = -1;
			final int numberOfEntries = (int)directIndexIn.length()/ENTRY_SIZE;
			int high = numberOfEntries;
			//System.out.println( low + " : " + high);
			int i;
			
			while (high-low>1) {
				
				i = (high + low)/2;
				
				final int status = readNextEntry(i);
				if(status < 0){
					return false;
				}
							
				//System.out.print( _IROBjectID +" ?? "+this.currentIROBjectID +" == "+ () ));
				if ( _IROBjectID < currentIROBjectID )
					high = i;
				else if ( _IROBjectID > currentIROBjectID )
					low = i;
				else { 
					return true;
				}
			}
		
			if (high == numberOfEntries)
				return false;
			
			final int status = readNextEntry(high);
			if(status < 0){
				return false;
			}
			if ( _IROBjectID == currentIROBjectID ) 
				return true; 
			return false;
		
		} catch(IOException ioe) {
			Assert.fatal(true,"IOException while binary searching the lexicon: " + ioe);
		}
		return false;
	}
	/**
	 * @return the currentData
	 */
	public String getCurrentData() {
		return currentData;
	}

	/**
	 * @return the currentIROBjectID
	 */
	public long getCurrentIROBjectID() {
		return currentIROBjectID;
	}
	
	/**
	 * @return the currentDocumentLength
	 */
	public int getCurrentDocumentLength() {
		return currentDocumentLength;
	}

//	/**
//	 * @param currentDocumentLength the currentDocumentLength to set
//	 */
//	public void setCurrentDocumentLength(int currentDocumentLength) {
//		this.currentDocumentLength = currentDocumentLength;
//	}

//	/**
//	 * @return the numberOfDocuments
//	 */
//	public int getNumberOfDocuments() {
//		return numberOfDocuments;
//	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		final String filename = "D:\\FlairIndex\\IdentifiersAquaint";
		final IdentifierDirectIndex idents = IdentifierDirectIndex.readIdentifierDirectIndex(filename);
		
		while(idents.readNextEntry() != -1){
			final long id = idents.getCurrentIROBjectID();
			final String idS = idents.getCurrentData();
			
			System.out.println( id +" == "+idS);
			
		}
	}

//	public double getAverageDocLength() {
//		return aveDocLength;
//	}

	/* (non-Javadoc)
	 * @see uk.ac.gla.mir.flair.index.DirectIndex#createCollectionStatistics()
	 */
	@Override
	public void createCollectionStatistics() {
		IdentifierDirectIndex tii = new IdentifierDirectIndex( directIndexFileName );
		CollectionStatistics collStat = new CollectionStatistics();
		try{
			
			tii.directIndexIn = new RandomAccessFile( directIndexFileName, "r");
			int numberOfDocuments = 0;
			double aveDocLength = 0.0;
			
			int noDocs = 0;
			int sum = 0;
			while( tii.readNextEntry() > 0 ){
				noDocs++;
				int LD = tii.getCurrentDocumentLength();
				sum += LD;
			}
			aveDocLength = (double)sum / (double) noDocs;
			numberOfDocuments = noDocs;
			tii.directIndexIn.close();
			
			collStat.setNumberOfDocuments( numberOfDocuments );
			collStat.setAverageLengthOfDocuments( aveDocLength );
			
			collStat.write( directIndexFileName );
			
		}catch (Exception e) {
			Assert.fatal(true, "Problem Reading Direct Index "
							 	+ tii.directIndexFileName );
		}
	}

	public CollectionStatistics getCollectionStatistics() {
		CollectionStatistics collStat = new CollectionStatistics();
		
		collStat.read( directIndexFileName );
		
		return collStat;
	}
	
}
