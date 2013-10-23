package uk.ac.gla.mir.flair.datamodel;

import uk.ac.gla.mir.flair.util.Settings;

/**
 * Title:        TRECResult <br/>
 * Description:  Identical to IRResult but the toString is in TREC format<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author		 David Hannah
 * @version 1.0
 */
public class TRECResult extends IRResult {

	public TRECResult(	long irObjectID, String documentIdentifier, 
						int rank,double score) {
		super(irObjectID, documentIdentifier, rank, score);
	}

	
	/**
	 * @param irObjectID
	 * @param documentIdentifier
	 * @param rank
	 * @param score
	 * @param queryID
	 */
	public TRECResult(long irObjectID, String documentIdentifier,
					  int rank,double score, String queryID) {
		super(irObjectID, documentIdentifier, rank, score, queryID);
	}


	/**
	 * Gets the TREC format for this Result
	 */
	public String toString(){
		return queryID + "\t" + 0 + "\t" + documentIdentifier + "\t"+ rank + "\t" + score + "\t" + Settings.getRunID();
	}
	
}
