package uk.ac.gla.mir.flair.index;

import uk.ac.gla.mir.flair.util.Settings;

/**
 * Title:        InvertedIndex <br/>
 * Description:  Generates an abstract Inverted Index <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public abstract class InvertedIndex {

	protected static int MAX_INDEX_SIZE; 
	
	/** The FileName for the Term Position Index File */
	protected String termPositionIndexFileName;
	
	/** The File Name for the Posting List Data File */
	protected String postingListDataFileName;
	
	/** The Number of temp index files **/
	protected int indexCounter;

	/**
	 * Constructs a New Inverted Index Using the Default Index File Names
	 */
	public InvertedIndex(){
		indexCounter = 1;
		this.termPositionIndexFileName = Settings.getIndexPath()+Settings.fileSep+"termPositionIndexFile";
		this.postingListDataFileName = Settings.getIndexPath()+Settings.fileSep+"postingListDataFile";
	}
	
	/**
	 * Constructs a New Inverted Index Using the filenames suppiled
	 * @param termPositionIndexFileName
	 * @param postingListDataFileName
	 */
	public InvertedIndex(String termPositionIndexFileName, String postingListDataFileName){
		indexCounter = 1;
		this.termPositionIndexFileName = termPositionIndexFileName;
		this.postingListDataFileName = postingListDataFileName;
	}
}
