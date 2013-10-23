package uk.ac.gla.mir.flair.index;

import gnu.trove.TIntArrayList;
import gnu.trove.TLongIntHashMap;
import java.io.*;
import java.util.*;

import uk.ac.gla.mir.flair.datamodel.IRObject;
import uk.ac.gla.mir.flair.datamodel.dataElement.*;
import uk.ac.gla.mir.flair.util.*;

/**
 * Title:        TextInvertedIndex <br/>
 * Description:  Generates a TextInverted Index <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class TextInvertedIndex extends InvertedIndex {
	
	private static long MAX_INDEX_SIZE; 
	private static int TERM_BYTE_LENGTH = 20;
					//Settings.getProperty("term.byte.length", 20)
	
	/** The Size of an Entry In the TermPositionIndex is the Length of the String + A Long (Pointer) + An Int (PostingList Count)**/
	private static int ENTRY_SIZE = TERM_BYTE_LENGTH + 8 + 4;
	
	protected static byte[] zeroBuffer = new byte[TERM_BYTE_LENGTH];
		
	protected static byte[] tmp = new byte[TERM_BYTE_LENGTH];
	
	/** The Current Term that Has Just been read **/
	private String currentTerm;
	
	/** Used Internaly When Merging Indexes **/
	private String TermTmp;
	
	/** The Current PostingList for this Entry **/
	private PostingListEntry[] postings;
	
	/** Used Internaly When Merging Indexes **/
	private PostingListEntry[] postingsTmp;
	
	/** The Actual In Memory Inverted Index **/
	private HashMap<String, TLongIntHashMap> invertedIndex;
	
	/** The Output Stream to write data to the Term Position Index File */
	private DataOutputStream termPositionIndexOut;
	
	/** The Output Stream to write data to the Posting List Data File */
	private DataOutputStream postingListDataOut;
	
	/** The Input Stream to Read data from the Term Position Data File */
	private RandomAccessFile termPositionDataIn;
	
	/** The Input Stram to Read Data from the Document Posting List File */
	private RandomAccessFile postingListDataIn;
	
	private long numberOfTermsIndex;

	/**
	 * Constructs a New Text Inverted Index Using the Default Index File Names
	 */
	public TextInvertedIndex(){
		indexCounter = 1;
		numberOfTermsIndex = 0;
		this.termPositionIndexFileName = Settings.getIndexPath()+Settings.fileSep+"termPositionIndexFile";
		this.postingListDataFileName = Settings.getIndexPath()+Settings.fileSep+"postingListDataFile";
		MAX_INDEX_SIZE = Settings.getProperty("max.index.size", 10000000);
		invertedIndex = new HashMap<String, TLongIntHashMap>();
	}
	
	/**
	 * Constructs a New Text Inverted Index Using the filenames suppiled
	 * @param termPositionIndexFileName
	 * @param postingListDataFileName
	 */
	public TextInvertedIndex(String termPositionIndexFileName, String postingListDataFileName){
		indexCounter = 1;
		numberOfTermsIndex = 0;
		this.termPositionIndexFileName = termPositionIndexFileName;
		this.postingListDataFileName = postingListDataFileName;
		MAX_INDEX_SIZE = Settings.getProperty("max.index.size", 10000000);
		invertedIndex = new HashMap<String, TLongIntHashMap>();
	}
	

	/**
	 * Constructs a New Text Inverted Index Using the filenames suppiled
	 * @param termPositionIndexFileName
	 * @param postingListDataFileName
	 */
	public static TextInvertedIndex createTextInvertedIndex(String termPositionIndexFileName,
															String postingListDataFileName){
		TextInvertedIndex tii = new TextInvertedIndex(termPositionIndexFileName, postingListDataFileName);
		try{
			tii.termPositionIndexOut = 
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(tii.termPositionIndexFileName+"_"+tii.indexCounter)));
			tii.postingListDataOut =
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(tii.postingListDataFileName+"_"+tii.indexCounter)));
		}catch (Exception e) {
			Assert.fatal(true, "Problem Creating Document Position Index "
							 	+ tii.termPositionIndexFileName 
							 	+ " / " +tii.postingListDataFileName);
		}
		return tii;
	}
	
	/**
	 * Constructs a New Text Inverted Index Using the Default Index File Names
	 */
	public static TextInvertedIndex createTextInvertedIndex(){
		TextInvertedIndex tii = new TextInvertedIndex();
		try{
			tii.termPositionIndexOut =
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(tii.termPositionIndexFileName+"_"+tii.indexCounter)));
			tii.postingListDataOut = 
				new DataOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(tii.postingListDataFileName+"_"+tii.indexCounter)));
		}catch (Exception e) {
			Assert.fatal(true, "Problem Creating Document Position Index "
							 	+ tii.termPositionIndexFileName 
							 	+ " / " +tii.postingListDataFileName);
		}
		return tii;
	}
	
	/** Opens a TextInvertedIndex for Reading **/
	public static TextInvertedIndex readTextInvertedIndex(){
		TextInvertedIndex tii = new TextInvertedIndex();
		try{
			tii.termPositionDataIn = new RandomAccessFile(  tii.termPositionIndexFileName+"_"+tii.indexCounter , "r");
			tii.postingListDataIn = new RandomAccessFile( tii.postingListDataFileName+"_"+tii.indexCounter, "r");
		}catch (Exception e) {
			Assert.fatal(true, "Problem Creating Document Position Index "
							 	+ tii.termPositionIndexFileName 
							 	+ " / " +tii.postingListDataFileName);
		}
		return tii;
	}
	
	/** Opens a TextInvertedIndex for Reading
	 * @param termPositionIndexFileName
	 * @param postingListDataFileName
	 **/
	public static TextInvertedIndex readTextInvertedIndex(String termPositionIndexFileName,
															String postingListDataFileName){
		TextInvertedIndex tii = new TextInvertedIndex(termPositionIndexFileName,postingListDataFileName);
		try{
			tii.termPositionDataIn = new RandomAccessFile(  tii.termPositionIndexFileName+"_"+tii.indexCounter , "r");
			tii.postingListDataIn = new RandomAccessFile( tii.postingListDataFileName+"_"+tii.indexCounter, "r");
		}catch (Exception e) {
			Assert.fatal(true, "Problem Creating Document Position Index "
							 	+ tii.termPositionIndexFileName 
							 	+ " / " +tii.postingListDataFileName);
		}
		return tii;
	}
	
	/**
	 * Indexes The Term <code>term</code>
	 * @param de The term to index
	 * @param IRObjectID The IRObject this term belongs to
	 */
	public void index(DataElement de, IRObject IRObjectID){
		final String className = de.getClass().getCanonicalName();
		//System.out.println("INDEXING "+className);
		if( className.endsWith("StringDE") ){
			final String term = ((StringDE)de).getCharValue();
			indexTerm(term, IRObjectID.getID(), IRObjectID);
		}else if( className.endsWith("SequenceDE") ){
			final int size = ((SequenceDE)de).size();
			for(int i = 0 ; i < size; i++){
				final DataElement de2 = de.getElementAt(i);
				index(de2,IRObjectID);
			}
		}else if(className.endsWith("RepeatDE")){
			final int size = ((RepeatDE)de).size();
			for(int i = 0 ; i < size; i++){
				final DataElement de2 = de.getElementAt(i);
				index(de2,IRObjectID);
			}
		}
		
	}
	
	/**
	 * Indexes The Term <code>term</code>
	 * @param term The term to index
	 * @param IRObjectID The IRObject this term belongs to
	 */
	public void indexTerm(String term, long IRObjectID, IRObject IRObject){
		IRObject.numberOfTerms++;
		numberOfTermsIndex++;
		TLongIntHashMap postings = invertedIndex.get(term);
		if(postings == null){
			postings = new TLongIntHashMap();
			postings.put(IRObjectID, 1);
			invertedIndex.put(term, postings);
		}else{
			postings.adjustOrPutValue(IRObjectID, 1, 1);
		}
		
		//System.err.println( getSize() +" > " + MAX_INDEX_SIZE);
		
		if( getSize() > MAX_INDEX_SIZE ){
			writeOutIndex();
			indexCounter++;
			invertedIndex = new HashMap<String, TLongIntHashMap>();
			numberOfTermsIndex = 0;
			System.gc();
			try{
				termPositionIndexOut = 
					new DataOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(termPositionIndexFileName+"_"+indexCounter)));
				postingListDataOut =
					new DataOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(postingListDataFileName+"_"+indexCounter)));
			}catch (Exception e) {
				Assert.fatal(true, "Problem Creating Document Position Index "
								 	+ termPositionIndexFileName 
								 	+ " / " +postingListDataFileName);
			}
		}
	}
	
	/**
	 * Writes out the in Memory Index to Disk
	 *
	 */
	public void writeOutIndex(){
		
		String[] terms = invertedIndex.keySet().toArray(new String[0]);
		if( terms.length == 0 ){
			//System.out.println("terms.length = 0 " );
			try{
				termPositionIndexOut.close();
				postingListDataOut.close();
				File fout1 = new File(termPositionIndexFileName+"_"+indexCounter);
				//System.out.println("File "+fout1.getAbsolutePath()+" deleted. "+fout1.delete());
				File fout2 = new File(postingListDataFileName+"_"+indexCounter);
				//System.out.println("File "+fout2.getAbsolutePath()+" deleted. "+fout2.delete());
				indexCounter--;
			}catch (Exception e) {
				// TODO: handle exception
			}
		}else{
			
			Arrays.sort(terms);
	
			for(int i = 0; i < terms.length; i++){
				TLongIntHashMap postings = invertedIndex.get(terms[i]);
				
				PostingListEntry[] postingsList = new PostingListEntry[ postings.size() ];
				int counter = 0;
				for(long irobjectId : postings.keys()){
					postingsList[counter++] = new PostingListEntry(irobjectId, postings.get(irobjectId) );
				}
				Arrays.sort(postingsList);
				
				final int dataPosition = postingListDataOut.size();
				
				//System.out.println( terms[i] + " => "+ dataPosition +" :: "+postingsList.length +" : "+ Arrays.toString( postingsList ));
				
				try{
					
					byte[] tmpBytes = terms[i].getBytes();
					final int length = ( tmpBytes.length < TERM_BYTE_LENGTH ) ? tmpBytes.length : TERM_BYTE_LENGTH;
					
					termPositionIndexOut.write(tmpBytes, 0, length);
					termPositionIndexOut.write(
						zeroBuffer,
						0,
						TERM_BYTE_LENGTH - length);
					termPositionIndexOut.writeLong( dataPosition );
					termPositionIndexOut.writeInt(postingsList.length);
					
					// For Each Posting List Entry
					for(int j = 0; j < postingsList.length; j++){
						final PostingListEntry postingListEntry = postingsList[j];
						postingListDataOut.writeInt( (int)postingListEntry.getIrObjectID() );
						postingListDataOut.writeInt( postingListEntry.getFrequency() );
					}
				
					postingListDataOut.flush();
					termPositionIndexOut.flush();
				}catch (IOException ioe) {
					Assert.fatal(true, "Problem Writing to Index.");
				}
				
			}
			
			try{
				termPositionIndexOut.flush();
				termPositionIndexOut.close();
				postingListDataOut.close();
			}catch (Exception e) {
				Assert.fatal(true, "Problem Writing to Index Files\n"+
									termPositionIndexFileName+ " \\ " + postingListDataFileName  );
			}
		}
	}
	
	private static void writeOutPostings(String term, 
										PostingListEntry[] postingsList,
										DataOutputStream termPositionIndexOut,
										DataOutputStream postingListDataOut){
		try{
			final int dataPosition = postingListDataOut.size();
			byte[] tmpBytes = term.getBytes();
			final int length = tmpBytes.length;
			
			termPositionIndexOut.write(tmpBytes, 0, length);
			termPositionIndexOut.write(
				zeroBuffer,
				0,
				TERM_BYTE_LENGTH - length);
			termPositionIndexOut.writeLong( dataPosition );
			termPositionIndexOut.writeInt(postingsList.length);
			
			// For Each Posting List Entry
			for(int j = 0; j < postingsList.length; j++){
				final PostingListEntry postingListEntry = postingsList[j];
				postingListDataOut.writeInt( (int)postingListEntry.getIrObjectID() );
				postingListDataOut.writeInt( postingListEntry.getFrequency() );
			}
		
			postingListDataOut.flush();
			termPositionIndexOut.flush();
		}catch (IOException ioe) {
			Assert.fatal(true, "Problem Writing to Index.");
		}
	}
	
	/**
	 * Returns the Size of the Inverted Index 
	 * @return The Size
	 */
	public long getSize(){
		//System.out.println( invertedIndex.size() + " < " + MAX_INDEX_SIZE + " = " + ( invertedIndex.size()<MAX_INDEX_SIZE));
		return numberOfTermsIndex;
	}
	
	/**
	 * Reads the Next Entry from the Text Inverted Index
	 * @return Returns the number of bytes read from the stream, or 
	 * 		   -1 if EOF has been reached.
	 * @throws java.io.IOException if an I/O error occurs.
	 */
	public int readNextEntry() throws IOException{
		try {
			int bytes = tmp.length + 8 + 4;
			termPositionDataIn.read( tmp, 0, tmp.length);
			currentTerm = new String( tmp ).trim();
			//System.out.print( currentTerm  + " => ");
			
			final long postingsPosition = termPositionDataIn.readLong();
			postingListDataIn.seek(postingsPosition);
			final int numberOfPostings = termPositionDataIn.readInt();
			
			//System.out.print(postingsPosition+" :: "+numberOfPostings+" : ");
			postings = new PostingListEntry[numberOfPostings];
			for(int i = 0; i < numberOfPostings; i++){
				//Read in IRObjectID
				final long IROBjectID = postingListDataIn.readInt();
				//Read in the Frequency
				final int frequency = postingListDataIn.readInt();
				bytes += 12;
				postings[i] = new PostingListEntry(IROBjectID, frequency);
			}
			
			//System.out.println( Arrays.toString(postings));
			return bytes;
		} catch (EOFException eofe) {
			return -1;
		}
	}
	
	/**
	 * Reads the Next Entry from the Text Inverted Index
	 * @param termID The Term Identifier
	 * @return Returns the number of bytes read from the stream, or 
	 * 		   -1 if EOF has been reached.
	 * @throws java.io.IOException if an I/O error occurs.
	 */
	public int readNextEntry(long termID) throws IOException{
		try {
			
			final long position = termID*ENTRY_SIZE;
			termPositionDataIn.seek(position);
			
			int bytes = tmp.length + 8 + 4;
			termPositionDataIn.read( tmp, 0, tmp.length);
			currentTerm = new String( tmp ).trim();
			//System.out.print( currentTerm  + " => ");
			
			final long postingsPosition = termPositionDataIn.readLong();
			postingListDataIn.seek(postingsPosition);
			final int numberOfPostings = termPositionDataIn.readInt();
			
			//System.out.print(postingsPosition+" :: "+numberOfPostings+" : ");
			postings = new PostingListEntry[numberOfPostings];
			for(int i = 0; i < numberOfPostings; i++){
				//Read in IRObjectID
				final long IROBjectID = postingListDataIn.readInt();
				//Read in the Frequency
				final int frequency = postingListDataIn.readInt();
				bytes += 12;
				postings[i] = new PostingListEntry(IROBjectID, frequency);
			}
			
			//System.out.println( Arrays.toString(postings));
			return bytes;
		} catch (EOFException eofe) {
			return -1;
		}
	}
	
	/**
	 * Closes the Text Inverted Index Files
	 */
	public void close() {
		
		try{
			if(postingListDataOut != null){
				postingListDataOut.close();
			}
			
			if(termPositionIndexOut != null){
				termPositionIndexOut.close();
			}
			
			if(postingListDataIn != null){
				postingListDataIn.close();
			}
			
			if(termPositionDataIn != null){
				termPositionDataIn.close();
			}
		}catch (IOException e) {
			Assert.fatal(true, 
					"Error Closing Index Files "+
					this.termPositionIndexFileName+" / "+
					this.postingListDataFileName);
		}
	}
	
	/**
	 * Merges Serveral Index Files Together 
	 */
	public void merge(){
		//Make Sure Indexes are Closed
		try{
			close();
		}catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		
		while(indexCounter > 1){
			final int MERGE_BLOCK_SIZE = (16 < indexCounter) ? 16 : indexCounter;
		
			final DataInputStream[] termPositionIndex = new DataInputStream[MERGE_BLOCK_SIZE];
			final DataInputStream[] postingListData = new DataInputStream[MERGE_BLOCK_SIZE];
			final String[] termsArray = new String[MERGE_BLOCK_SIZE];
			final PostingListEntry[][] postingsArray = new PostingListEntry[MERGE_BLOCK_SIZE][]; 
			
			DataOutputStream termPositionIndexOutTmp = null;
			DataOutputStream postingListDataOutTmp = null;
			
			try{
				termPositionIndexOutTmp =
					new DataOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(termPositionIndexFileName+"_tmp")));
				postingListDataOutTmp =
					new DataOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(postingListDataFileName+"_tmp")));
			}catch (Exception e) {
				Assert.fatal(true, "Error writing temp index file "+termPositionIndexFileName+
									"_tmp or "+postingListDataFileName+"_tmp.");
			}
			
			String msg = "Merge :\n";
			for(int i = 0; i < MERGE_BLOCK_SIZE; i++){
				try{
					termPositionIndex[i] = new DataInputStream(
											new FileInputStream(
													termPositionIndexFileName+"_"+(indexCounter-i) ));
					postingListData[i] = new DataInputStream(
											new FileInputStream(
													postingListDataFileName+"_"+(indexCounter-i) ));
					msg += termPositionIndexFileName+"_"+(indexCounter-i)+"\n";
				}catch (FileNotFoundException fnf) {
					Assert.fatal(true, 
							"Could not find index file "+termPositionIndexFileName+"_"+(indexCounter-i)+
							" or "+postingListDataFileName+"_"+(indexCounter-i));
				}
			}
			Assert.info(true, msg);
			
			for(int i = 0; i < MERGE_BLOCK_SIZE; i++){
				try{
					final int ok = readNextEntry(termPositionIndex[i], postingListData[i]);
					if(ok == -1)
						throw new IOException();
					else
						termsArray[i] = TermTmp;
						postingsArray[i] = postingsTmp;
				}catch (IOException ioe) {
					Assert.fatal(true, "Problem Reading File index file "+termPositionIndexFileName+"_"+(indexCounter-i)+
										" or "+postingListDataFileName+"_"+(indexCounter-i));
				}		
			}
			
			//Find the Smallest Strings
			String smallestString = termsArray[0];
			TIntArrayList smallestIndexes = new TIntArrayList();
			smallestIndexes.add(0);
			
			while(true){
				smallestString = null;
				for(int i=0; i<MERGE_BLOCK_SIZE; i++){
					if(smallestString == null) {
						smallestString = termsArray[i];
						smallestIndexes = new TIntArrayList();
						smallestIndexes.add(i);
					
					}else if( ( termsArray[i] != null && termsArray[i].compareTo(smallestString) < 0) ){
						smallestString = termsArray[i];
						smallestIndexes = new TIntArrayList();
						smallestIndexes.add(i);
					}else if( (termsArray[i] != null && termsArray[i].compareTo(smallestString) == 0) ){
						smallestIndexes.add(i);
					}
				}

				if(smallestString == null){
					try{
						termPositionIndexOutTmp.close();
						postingListDataOutTmp.close();
						
						//Delete Merged Indexes, Rename Tmp 
						for(int i = 0 ; i < MERGE_BLOCK_SIZE; i++){
							termPositionIndex[i].close();
							postingListData[i].close();
							File termIndexFile1 = new File(termPositionIndexFileName+"_"+(indexCounter-i) );
							File postingIndexFile1 = new File(postingListDataFileName+"_"+(indexCounter-i) );
							System.out.println("Deleting : "+ termIndexFile1.getAbsolutePath());
							System.out.println( termIndexFile1.delete() );
							System.out.println( postingIndexFile1.delete() );
						}
						
						indexCounter -= MERGE_BLOCK_SIZE-1;
						File termIndexFileTmp = new File(termPositionIndexFileName+"_tmp");
						File postingIndexFileTmp = new File(postingListDataFileName+"_tmp");
						System.out.println("Rename : "+ termIndexFileTmp.getAbsolutePath() + " to "+ termPositionIndexFileName+"_"+indexCounter);
						termIndexFileTmp.renameTo( new File( termPositionIndexFileName+"_"+indexCounter) );
						postingIndexFileTmp.renameTo( new File(postingListDataFileName+"_"+indexCounter) );
						
					}catch (IOException ioe) {
						System.err.println(ioe.getLocalizedMessage());
						ioe.printStackTrace();
						System.exit(-1);
					}
					break;
				}else{
					PostingListEntry[] tmpPostingsList = new PostingListEntry[0];
					for(int i=0; i<smallestIndexes.size(); i++){
						final int index = smallestIndexes.get(i);
						tmpPostingsList = PostingListEntry.merge(tmpPostingsList, postingsArray[index]);
					}
					
					writeOutPostings(smallestString, tmpPostingsList, termPositionIndexOutTmp, postingListDataOutTmp);
					
					//Read in New Data to arrays
					for(int i=0; i < smallestIndexes.size(); i++){
						final int index = smallestIndexes.get(i);
						try{
							final int ok = readNextEntry(termPositionIndex[index], postingListData[index]);
							if(ok != -1){
								termsArray[index] = TermTmp;
								postingsArray[index] = postingsTmp;
							}else{
								termsArray[index] = null;
								postingsArray[index] = null;
							}
						}catch (IOException ioe) {
							System.err.println(ioe.getLocalizedMessage());
							ioe.printStackTrace();
							System.exit(-1);
						}
					}
				}
			}
		}
	}
	
//	public void merge(){
//		
//		//Make Sure Indexes are Closed
//		try{
//			close();
//		}catch (Exception e) {
//			System.err.println(e.getLocalizedMessage());
//		}
//		while( indexCounter > 1){
//			final int merge1 = indexCounter;
//			final int merge2 = indexCounter-1;
//			Assert.info(true, "Merge: "+termPositionIndexFileName+"_"+merge1 +" + " + termPositionIndexFileName+"_"+merge2);
//			/*
//			 * TO MERGE, We Need 4 DataInputStreams, 2 DataOutputStreams
//			 * Read An Entry From Both, Write Out the Smallest Term First,
//			 * OR Merge If the terms are the Same
//			 */
//			try{
//				DataInputStream termPositionIndex1 = new DataInputStream(new FileInputStream(termPositionIndexFileName+"_"+merge1));
//				RandomAccessFile postingListDataIn1 = new RandomAccessFile(new File(postingListDataFileName+"_"+merge1), "r");
//				DataInputStream termPositionIndex2 = new DataInputStream(new FileInputStream(termPositionIndexFileName+"_"+merge2));
//				RandomAccessFile postingListDataIn2 = new RandomAccessFile(new File(postingListDataFileName+"_"+merge2), "r");
//				
//				DataOutputStream termPositionIndexOutTmp = 
//					new DataOutputStream(
//						new BufferedOutputStream(
//							new FileOutputStream(termPositionIndexFileName+"_tmp")));
//				DataOutputStream postingListDataOutTmp =
//					new DataOutputStream(
//						new BufferedOutputStream(
//							new FileOutputStream(postingListDataFileName+"_tmp")));
//			
//				String term1 = null;
//				String term2 = null;
//				PostingListEntry[] postings1 = null;
//				PostingListEntry[] postings2 = null;
//	
//				if( readNextEntry(termPositionIndex1, postingListDataIn1) !=-1){
//					term1 = TermTmp;
//					postings1 = postingsTmp;
//				}else{
//					Assert.warnFatal(true,"Index Empty.");
//				}
//				
//				if( readNextEntry(termPositionIndex2, postingListDataIn2) !=-1){
//					term2 = TermTmp;
//					postings2 = postingsTmp;
//				}else{
//					Assert.warnFatal(true,"Index Empty.");
//				}
//				
//				while( term1 != null && term2 != null){
//					final int condition = term1.compareToIgnoreCase(term2);
//					
//					if( condition < 0){
//						writeOutPostings(term1, postings1, termPositionIndexOutTmp, postingListDataOutTmp);
//						
//						if( readNextEntry(termPositionIndex1, postingListDataIn1) !=-1){
//							term1 = TermTmp;
//							postings1 = postingsTmp;
//						}else{
//							term1 = null;
//						}
//						
//					}else if( condition > 0){
//						writeOutPostings(term2, postings2, termPositionIndexOutTmp, postingListDataOutTmp);
//						
//						if( readNextEntry(termPositionIndex2, postingListDataIn2) !=-1){
//							term2 = TermTmp;
//							postings2 = postingsTmp;
//						}else{
//							term2 = null;
//						}
//					}else{
//						
//						PostingListEntry[] postingsMerged = PostingListEntry.merge(postings1, postings2);
//						writeOutPostings(term1, postingsMerged, termPositionIndexOutTmp, postingListDataOutTmp);
//						
//						if( readNextEntry(termPositionIndex1, postingListDataIn1) !=-1){
//							term1 = TermTmp;
//							postings1 = postingsTmp;
//						}else{
//							term1 = null;
//						}
//						
//						if( readNextEntry(termPositionIndex2, postingListDataIn2) !=-1){
//							term2 = TermTmp;
//							postings2 = postingsTmp;
//						}else{
//							term2 = null;
//						}
//					}
//				}
//				
//				while( term1 != null){
//					writeOutPostings(term1, postings1, termPositionIndexOutTmp, postingListDataOutTmp);
//					if( readNextEntry(termPositionIndex1, postingListDataIn1) !=-1){
//						term1 = TermTmp;
//						postings1 = postingsTmp;
//					}else{
//						term1 = null;
//					}
//				}
//				
//				while( term2 != null){
//					writeOutPostings(term2, postings2, termPositionIndexOutTmp, postingListDataOutTmp);
//					if( readNextEntry(termPositionIndex2, postingListDataIn2) !=-1){
//						term2 = TermTmp;
//						postings2 = postingsTmp;
//					}else{
//						term2 = null;
//					}
//				}
//				
//				termPositionIndexOutTmp.close();
//				termPositionIndex1.close();
//				termPositionIndex2.close();
//				postingListDataIn1.close();
//				postingListDataIn2.close();
//				postingListDataOutTmp.close();
//			}catch ( IOException ioe) {
//				ioe.printStackTrace();
//				System.err.println(ioe.getLocalizedMessage());
//				System.exit(-1);
//				//Assert.fatal(true, "Problem With Index Files. "+e.getLocalizedMessage());
//			}
//			
//			//Delete Index Merge1 and Merge2, Rename Tmp to be Merge2
//			File termIndexFile1 = new File(termPositionIndexFileName+"_"+merge1);
//			File postingIndexFile1 = new File(postingListDataFileName+"_"+merge1);
//			//System.err.println(
//			termIndexFile1.delete();
//			//System.err.println(
//			postingIndexFile1.delete();
//			
//			File termIndexFile2 = new File(termPositionIndexFileName+"_"+merge2);
//			File postingIndexFile2 = new File(postingListDataFileName+"_"+merge2);
//			//System.err.println(
//			termIndexFile2.delete();
//			//System.err.println(
//			postingIndexFile2.delete();
//			
//			File termIndexFileTmp = new File(termPositionIndexFileName+"_tmp");
//			File postingIndexFileTmp = new File(postingListDataFileName+"_tmp");
//			termIndexFileTmp.renameTo( new File( termPositionIndexFileName+"_"+merge2 ));
//			postingIndexFileTmp.renameTo( new File(postingListDataFileName+"_"+merge2 ));
//			indexCounter--;
//		}
//	}
	
	/**
	 * Reads the Next Entry from the Text Inverted Index
	 * @return Returns the number of bytes read from the stream, or 
	 * 		   -1 if EOF has been reached.
	 * @throws java.io.IOException if an I/O error occurs.
	 */
	private int readNextEntry( DataInputStream termPositionIndexTmp , DataInputStream postingListDataInTmp) throws IOException{
		try {
			int bytes = tmp.length + 8 + 4;
			termPositionIndexTmp.read( tmp, 0, tmp.length);
			TermTmp = new String( tmp ).trim();
			//System.out.print( currentTerm  + " => ");
			
			final long postingsPosition = termPositionIndexTmp.readLong();
			//postingListDataInTmp.seek(postingsPosition);
			final int numberOfPostings = termPositionIndexTmp.readInt();
			
			//System.out.print(postingsPosition+" :: "+numberOfPostings+" : ");
			postingsTmp = new PostingListEntry[numberOfPostings];
			for(int i = 0; i < numberOfPostings; i++){
				//Read in IRObjectID
				final long IROBjectID = postingListDataInTmp.readInt();
				//Read in the Frequency
				final int frequency = postingListDataInTmp.readInt();
				bytes += 12;
				postingsTmp[i] = new PostingListEntry(IROBjectID, frequency);
			}
			
			//System.out.println( Arrays.toString(postings));
			return bytes;
		} catch (EOFException eofe) {
			return -1;
		}
	}	
	
	/** 
	 * Performs a binary search in the term position file
	 * in order to locate the given term.
	 * If the term is located, currentTerm and Postings contain the correct data
	 * @param _term The term to search for.
	 * @return true if the term is found, and false otherwise.
	 */
	public boolean findTerm(String _term) {
		try {
			int low = -1;
			final int numberOfEntries = (int)termPositionDataIn.length()/ENTRY_SIZE;
			int high = numberOfEntries;
			//System.out.println( low + " : " + high);
			int i;
			int compareStrings;
		
			while (high-low>1) {
				
				i = (high + low)/2;
				
				final int status = readNextEntry(i);
				if(status < 0){
					return false;
				}
							
				//System.out.print( _term +" ?? "+this.currentTerm +" == "+_term.compareTo( this.currentTerm ));
				if ((compareStrings = _term.compareTo( this.currentTerm ))< 0)
					high = i;
				else if (compareStrings > 0)
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
			if (_term.compareTo( currentTerm ) == 0) 
				return true; 
			return false;
		
		} catch(IOException ioe) {
			Assert.fatal(true,"IOException while binary searching the term Position Index " + ioe);
		}
		return false;
	}
	
	/**
	 * @return the currentTerm
	 */
	public String getCurrentTerm() {
		return currentTerm;
	}

	/**
	 * @return the postings
	 */
	public PostingListEntry[] getPostings() {
		return postings;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		
		//System.setProperty("flair.home", "Y:\\FlairGrammer");
		Settings.initSettings();
		
		final String flair_home = Settings.getProperty("flair.home", "");
		final String flair_etc = Settings.getProperty("flair.etc", "");
		final String document_description_file = Settings.getProperty("flair.documents.file", "");
		final String inputDocument = Settings.getProperty("flair.documents.file", "");
		final String stopwordsFile = Settings.stopfilelocation;
		final String index_path = Settings.getIndexPath();
		final String tmpDir = Settings.getProperty("flair.temp.dir", "");
		final String docPositionIndex = index_path+Settings.fileSep+"docPosition.index";
		
		Assert.info(true, "Flair Home = "+flair_home+
						  "\nFlair ETC = "+flair_etc+
						  "\nFlair Decsription File = "+document_description_file+
						  "\nFlair Input File = "+inputDocument+
						  "\nFlair Stopwords File = " +stopwordsFile+
						  "\nFlair Index Path = "+ index_path);
		
		TextInvertedIndex textInvertedIndex = TextInvertedIndex.readTextInvertedIndex(args[1]+"_index",args[1]+"_data");
		
		//textInvertedIndex = TextInvertedIndex.readTextInvertedIndex();
		String searchTerm  =  args[0];
		if( textInvertedIndex.findTerm(searchTerm) ){
			final String term = textInvertedIndex.getCurrentTerm();
			final PostingListEntry[] postings = textInvertedIndex.getPostings();
			System.out.println("\n*********************\n");
			
			System.out.println( term +" => " + Arrays.toString(postings));
		}else{
			System.out.println("\n*********************\n");
			
			System.out.println( searchTerm+ " not found.");
		}
		searchTerm  =  "york";
		if( textInvertedIndex.findTerm(searchTerm) ){
			final String term = textInvertedIndex.getCurrentTerm();
			final PostingListEntry[] postings = textInvertedIndex.getPostings();
			System.out.println("\n*********************\n");
			
			System.out.println( term +" => " + Arrays.toString(postings));
		}else{
			System.out.println("\n*********************\n");
			
			System.out.println( searchTerm+ " not found.");
		}	
	}

}
