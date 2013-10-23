package uk.ac.gla.mir.flair.index;
import java.io.*;

import uk.ac.gla.mir.flair.util.Assert;
import uk.ac.gla.mir.flair.util.Settings;


/**
 * Title:        DocumentPositionsIndex <br/>
 * Description:  Reads and Writes Document Positions to File <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author 	David Hannah
 * @version 1.0
 */
public class DocumentPositions {

	/** The Number of Bytes used in an Enrty in the Document Position Index */
	private static final int INDEX_ENTRY_LENGTH = 4;
	
	/** The FileName for the Document Position Index File */
	private String documentPositionIndexFileName;
	
	/** The File Name for the Document Position Data File */
	private String documentPositionDataFileName;
	
	/** The Output Stream to write data to the Document Position Index File */
	private DataOutputStream documentPositionIndexOut;
	
	/** The Output Stream to write data to the Document Position Data File */
	private DataOutputStream documentPositionDataOut;
	
	/** The Input Stream to Read data from the Document Position Data File */
	private DataInputStream documentPositionDataIn;
	
	/** The Input Stram to Read Data from the Document Position Index File */
	private DataInputStream documentPositionIndexIn;
	
	/** The Position of the Last Read Document Position */
	private long documentPosition;
	
	/** The File name of the last Read Document Position */
	private String fileName;
	
	/**
	 * Creates a new Document Positions Index using the default file names
	 * located at <code>flair.index.path</code>
	 */
	private DocumentPositions(){
		final String indexPath = Settings.getIndexPath();
		documentPositionIndexFileName = indexPath + Settings.fileSep + "docPosition.index";
		documentPositionDataFileName = indexPath + Settings.fileSep + "docPosition.data";
	}
	
	/**
	 * Creates a new Document Positions Index
	 * @param dpIf The Document Position Index File Name
	 * @param dpDf The Document Position Data File Name
	 */
	private DocumentPositions(String dpIf, String dpDf) {
		documentPositionDataFileName = dpDf;
		documentPositionIndexFileName = dpIf;
	}
	
	/**
	 * Creates a new DocumentPositionsIndex using the default file names
	 * located at <code>flair.index.path</code>
	 * @return The newly created DocumentPositions
	 */
	public static DocumentPositions createNewDocumentPositions() {
		final DocumentPositions docPos = new DocumentPositions();
		
		try{
			docPos.documentPositionIndexOut = 
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(docPos.documentPositionIndexFileName)));
			docPos.documentPositionDataOut = 
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(docPos.documentPositionDataFileName)));
		}catch (Exception e) {
			Assert.fatal(true, "Problem Creating Document Position Index "
							 	+ docPos.documentPositionIndexFileName 
							 	+ " / " +docPos.documentPositionDataFileName);
		}
		return docPos;
	}
	
	/**
	 * Creates a new DocumentPositionsIndex
	 * @param dpIf The Document Position Index File Name
	 * @param dpDf The Document Position Data File Name
	 * @return The newly created DocumentPositions
	 */
	public static DocumentPositions createNewDocumentPositions(String dpIf, String dpDf){
		final DocumentPositions docPos = new DocumentPositions(dpIf, dpDf);
		
		try{
			docPos.documentPositionIndexOut = 
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(docPos.documentPositionIndexFileName)));
			docPos.documentPositionDataOut =
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(docPos.documentPositionDataFileName)));
		}catch (Exception e) {
			Assert.fatal(true, "Problem Creating Document Position Index "
							 	+ docPos.documentPositionIndexFileName 
							 	+ " / " +docPos.documentPositionDataFileName);
		}
		return docPos;
	}
	
	/**
	 * Reads a DocumentPositionsIndex From File using the default file names
	 * located at <code>flair.index.path</code>
	 * @return The DocumentPositions
	 * @throws FileNotFoundException
	 */
	public static DocumentPositions readDocumentPositions() {
		final DocumentPositions docPos = new DocumentPositions();
		
		try{
			docPos.documentPositionIndexIn = new DataInputStream(new FileInputStream(docPos.documentPositionIndexFileName));
			docPos.documentPositionDataIn = new DataInputStream(new FileInputStream(docPos.documentPositionDataFileName));
		}catch (Exception e) {
			Assert.fatal(true, "Problem Loading Document Position Index "
								+ docPos.documentPositionIndexFileName 
								+ " / " +docPos.documentPositionDataFileName);
		}
		return docPos;
	}
	
	/**
	 * Reads a DocumentPositionsIndex From File
	 * @param dpIf The Document Position Index File Name
	 * @param dpDf The Document Position Data File Name
	 * @throws FileNotFoundException
	 */
	public static DocumentPositions readDocumentPositions(String dpIf, String dpDf) {
		final DocumentPositions docPos = new DocumentPositions(dpIf, dpDf);
		
		try{
			docPos.documentPositionIndexIn = new DataInputStream(new FileInputStream(docPos.documentPositionIndexFileName));
			docPos.documentPositionDataIn = new DataInputStream(new FileInputStream(docPos.documentPositionDataFileName));
		}catch (Exception e) {
			Assert.fatal(true, "Problem Loading Document Position Index "
								+ docPos.documentPositionIndexFileName 
								+ " / " +docPos.documentPositionDataFileName);
		}
		return docPos;
	}
	
	
	/**
	 * Adds a New Document to the Document Position Index <br>
	 * Documents are Stored in an Index File, In Order <br>
	 * E.g. DOCID 0, is located at position 0; DOCID 1 is located at position 1*ENTRY_LENGTH
	 * @param documentPosition The Position Of the IRObject Located in the Document
	 * @param fileName The file name of the Document
	 * @throws IOException
	 */
	public void add(Long documentPosition, String fileName) throws IOException{
		final int dataPosition = documentPositionDataOut.size();
		documentPositionIndexOut.writeInt(dataPosition);

		//Write the Position in the File
		documentPositionDataOut.writeLong(documentPosition);
		
		//Write the File name
		byte[] tmp = fileName.getBytes();
		documentPositionDataOut.writeInt( tmp.length );
		documentPositionDataOut.write( tmp );
	}
	
	/**
	 * Reads the Next Entry from the Document Position Data File
	 * @return Returns the number of bytes read from the stream, or 
	 * 		   -1 if EOF has been reached.
	 * @throws java.io.IOException if an I/O error occurs.
	 */
	public int readNextEntry() throws IOException{
		try {
			int bytes = 12;
			documentPosition = documentPositionDataIn.readLong();
			final int length = documentPositionDataIn.readInt();
			final byte[] tmp = new byte[length];
			for(int i = 0; i < length; i++){
				tmp[i] = documentPositionDataIn.readByte();
				bytes += 2;
			}
			fileName = new String(tmp);
			return bytes;
		} catch (EOFException eofe) {
			return -1;
		}
	}
	
	/**
	 * Reads an Entry For a Given Document ID
	 * @param docid The document Id to find the position and File
	 * @return Returns the number of bytes read from the stream, or
	 * 			-1 if EOF has been reached.
	 * @throws java.io.IOException
	 */
	public int readEntry(long docid) throws IOException{
		try {
			documentPositionIndexIn = new DataInputStream(new FileInputStream(documentPositionIndexFileName));
			documentPositionDataIn = new DataInputStream(new FileInputStream(documentPositionDataFileName));
			
			final long indexPosition = INDEX_ENTRY_LENGTH * docid;
			//System.out.println("Index position : "+indexPosition);
			documentPositionIndexIn.skip(indexPosition);
			final int dataPosition = documentPositionIndexIn.readInt();			
			//System.out.println("Data position : "+ dataPosition);

			int bytes = 12;
			documentPositionDataIn.skip(dataPosition);
			documentPosition = documentPositionDataIn.readLong();
			final int length = documentPositionDataIn.readInt();
			final byte[] tmp = new byte[length];
			for(int i = 0; i < length; i++){
				tmp[i] = documentPositionDataIn.readByte();
				bytes += 2;
			}
			fileName = new String(tmp);
			return bytes;
		} catch (EOFException eofe){
			return -1;
		}
	}
	
	/**
	 * Closes the Document Position Index Output Files Only
	 * @throws IOException
	 */
	public void closeOutputFiles() throws IOException{
		if(documentPositionDataOut != null){
			documentPositionDataOut.close();
		}
		
		if(documentPositionIndexOut != null){
			documentPositionIndexOut.close();
		}
	}
	
	/**
	 * Closes the Document Position Index Files
	 * @throws IOException
	 */
	public void close() throws IOException{
		
		if(documentPositionDataOut != null){
			documentPositionDataOut.close();
		}
		
		if(documentPositionIndexOut != null){
			documentPositionIndexOut.close();
		}
		
		if(documentPositionDataIn != null){
			documentPositionDataIn.close();
		}
		
		if(documentPositionIndexIn != null){
			documentPositionIndexIn.close();
		}
	}
	
	/**
	 * @return the documentPosition
	 */
	public long getDocumentPosition() {
		return documentPosition;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 
	 */
	public String toString(){
		return "Document Position Index : \n"
				+documentPositionIndexFileName
				+"\n"+documentPositionDataFileName;
	}
}
