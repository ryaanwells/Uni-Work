package uk.ac.gla.mir.flair.irmodel.weighting;

/**
* Title:        TFIDF <br/>
* Description:  Weighting Model based on the TF-IDF
* To use specify in the flair.query.spec 
* weighting = TFIDF <br/>
* Copyright:    Copyright (c) 2010<br/>
* Company:      Department of Computing Science, University of Glasgow<br/>
* @author David Hannah
* @version 1.0
*/
public class TFIDF extends Weighting {

	/**
	 * Store the Inverted Document Frequency for the current term and document
	 */
	protected double IDF;
	
	@Override
	public void setDocumentFrequencyForTerm(double documentFrequencyForTerm) {
		super.setDocumentFrequencyForTerm(documentFrequencyForTerm);
		IDF = Math.log( ( (double)numberOfDocumentsInCollection/ (double)documentFrequencyForTerm ) );
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.gla.mir.flair.irmodel.weighting.Weighting#score()
	 */
	@Override
	public double score() {
		double TF = (double)termFrequencyForDocument / (double)documentLength;
		return IDF * TF;
	}

}
