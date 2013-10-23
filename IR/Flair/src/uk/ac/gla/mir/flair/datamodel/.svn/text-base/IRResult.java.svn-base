package uk.ac.gla.mir.flair.datamodel;

/**
 * Title:        IRResult <br/>
 * Description:  Contains the DocumentID, DocumentNO, score, rank, posttype <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author		 David Hannah
 * @version 1.0
 */
public class IRResult extends IRObject {

	/**
	 * 
	 */
	protected String 	documentIdentifier;
	
	/**
	 * 
	 */
	protected long		internalID = -1;

	/**
	 * 
	 */
	protected int		rank = 0;
	
	/**
	 * 
	 */
	protected double	score = 0.0;
	
	protected String	queryID = "";
	
	/**
	 * 
	 * @param irObjectID
	 * @param documentIdentifier
	 * @param rank
	 * @param score
	 */
	public IRResult(long irObjectID, String documentIdentifier, int rank, double score) {
		internalID = irObjectID;
		this.documentIdentifier = documentIdentifier;
		this.rank = rank;
		this.score = score;
	}
	
	/**
	 * 
	 * @param irObjectID
	 * @param documentIdentifier
	 * @param rank
	 * @param score
	 * @param queryID
	 */
	public IRResult(	long irObjectID, String documentIdentifier,
						int rank, double score, String queryID ) {
		internalID = irObjectID;
		this.documentIdentifier = documentIdentifier;
		this.rank = rank;
		this.score = score;
		this.queryID = queryID;
	}

	/**
	 * @return the documentIdentifier
	 */
	public String getDocumentIdentifier() {
		return documentIdentifier;
	}

	/**
	 * @param documentIdentifier the documentIdentifier to set
	 */
	public void setDocumentIdentifier(String documentIdentifier) {
		this.documentIdentifier = documentIdentifier;
	}

	/**
	 * @return the internalID
	 */
	public long getInternalID() {
		return internalID;
	}

	/**
	 * @param internalID the internalID to set
	 */
	public void setInternalID(long internalID) {
		this.internalID = internalID;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @return the queryID
	 */
	public String getQueryID() {
		return queryID;
	}

	/**
	 * @param queryID the queryID to set
	 */
	public void setQueryID(String queryID) {
		this.queryID = queryID;
	}
	
	/**
	 * 
	 */
	public String toString(){
		return queryID + "\t" + documentIdentifier + "\t"+ rank + "\t" + score;
	}
	
}
