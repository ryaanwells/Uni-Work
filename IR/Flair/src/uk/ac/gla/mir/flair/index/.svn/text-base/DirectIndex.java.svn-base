package uk.ac.gla.mir.flair.index;

import uk.ac.gla.mir.flair.datamodel.dataElement.DataElement;
import uk.ac.gla.mir.flair.util.Settings;

/**
 * Title:        DirectIndex <br/>
 * Description:  Generates an abstract Direct Index <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public abstract class DirectIndex {

	protected static int MAX_INDEX_SIZE; 
	
	/** The FileName for the Direct Index */
	protected String directIndexFileName;
	
	/**
	 * Constructs a New Direct Index Using the Default Index File Names
	 */
	public DirectIndex(){
		this.directIndexFileName = Settings.getIndexPath()+Settings.fileSep+"directIndex";
	}	
	
	/**
	 * Constructs a New Direct Index Using the filenames suppiled
	 * @param directIndexFileName
	 */
	public DirectIndex(String directIndexFileName){
		this.directIndexFileName = directIndexFileName;
	}
	
	/**
	 * Indexes a DataElement
	 * @param de The Data Element to Index
	 * @param IRObjectID The IRObjects Identifier
	 */
	public abstract void index(DataElement de, long IRObjectID, int documentLength);
	
	/**
	 * Closes any DataStreams in use.
	 */
	public abstract void close();

	public abstract void createCollectionStatistics();
}
