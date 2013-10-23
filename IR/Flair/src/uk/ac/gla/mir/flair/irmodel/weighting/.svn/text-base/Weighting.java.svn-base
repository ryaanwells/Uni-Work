package uk.ac.gla.mir.flair.irmodel.weighting;

/**
 * Root class of the weighing models, 
 * For scoring documents for query results
 * @author David Hannah
 */

/**
* Title:        Weighting <br/>
* Description:  Root class of the weighing models for scoring documents
* Copyright:    Copyright (c) 2010<br/>
* Company:      Department of Computing Science, University of Glasgow<br/>
* @author David Hannah
* @version 1.0
*/
public abstract class Weighting {

	/**
	 * Store the Number of Documents in the collection
	 * You can get this from CollectionStatistics
	 * Should be set at start, no need to change during scoring
	 */
	protected int numberOfDocumentsInCollection;
	
	/**
	 * Store the Average Number of Tokens in the Documents
	 * You can get this from CollectionStatistics
	 * Should be set at start, no need to change during scoring
	 */
	protected double averageDocumentLength;
	
	/**
	 * Store the number of Tokens in the current document being weighted
	 * Should be changed for each document
	 */
	protected double documentLength;
	
	/**
	 * Store the number of times the current term is found in the document
	 * Should be changed for each term in the query
	 */
	protected double termFrequencyForDocument;
	
	/**
	 * Store the number of documents in the collection containing the current term
	 * Should be changed for each term in the query
	 */
	protected double documentFrequencyForTerm;
	
	/**
	 * Store the number of times the current term is found in the whole collection
	 * Should be changed for each term in the query
	 */
	protected double termFrequencyForCollection;
	
	/**
	 * Store the number of times the current term is found in the query
	 * Should be changed for each term in the query
	 */
	protected double termFrequencyForQuery;
	
	/**
	 * Some paramaters that can be used by subclasses
	 */
	protected double[] params; 

	/**
	 * @return the termFrequencyForQuery
	 */
	public double getTermFrequencyForQuery() {
		return termFrequencyForQuery;
	}

	/**
	 * @param termFrequencyForQuery the termFrequencyForQuery to set
	 */
	public void setTermFrequencyForQuery(double termFrequencyForQuery) {
		this.termFrequencyForQuery = termFrequencyForQuery;
	}

	/**
	 * @return the params
	 */
	public double[] getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(double[] params) {
		this.params = params;
	}

	/**
	 * @return the numberOfDocumentsInCollection
	 */
	public int getNumberOfDocumentsInCollection() {
		return numberOfDocumentsInCollection;
	}

	/**
	 * @param numberOfDocumentsInCollection the numberOfDocumentsInCollection to set
	 */
	public void setNumberOfDocumentsInCollection(int numberOfDocumentsInCollection) {
		this.numberOfDocumentsInCollection = numberOfDocumentsInCollection;
	}

	/**
	 * @return the averageDocumentLength
	 */
	public double getAverageDocumentLength() {
		return averageDocumentLength;
	}

	/**
	 * @param averageDocumentLength the averageDocumentLength to set
	 */
	public void setAverageDocumentLength(double averageDocumentLength) {
		this.averageDocumentLength = averageDocumentLength;
	}

	/**
	 * @return the documentLength
	 */
	public double getDocumentLength() {
		return documentLength;
	}

	/**
	 * @param documentLength the documentLength to set
	 */
	public void setDocumentLength(double documentLength) {
		this.documentLength = documentLength;
	}

	/**
	 * @return the termFrequencyForDocument
	 */
	public double getTermFrequencyForDocument() {
		return termFrequencyForDocument;
	}

	/**
	 * @param termFrequencyForDocument the termFrequencyForDocument to set
	 */
	public void setTermFrequencyForDocument(double termFrequencyForDocument) {
		this.termFrequencyForDocument = termFrequencyForDocument;
	}

	/**
	 * @return the documentFrequencyForTerm
	 */
	public double getDocumentFrequencyForTerm() {
		return documentFrequencyForTerm;
	}

	/**
	 * @param documentFrequencyForTerm the documentFrequencyForTerm to set
	 */
	public void setDocumentFrequencyForTerm(double documentFrequencyForTerm) {
		this.documentFrequencyForTerm = documentFrequencyForTerm;
	}

	/**
	 * @return the termFrequencyForCollection
	 */
	public double getTermFrequencyForCollection() {
		return termFrequencyForCollection;
	}

	/**
	 * @param termFrequencyForCollection the termFrequencyForCollection to set
	 */
	public void setTermFrequencyForCollection(double termFrequencyForCollection) {
		this.termFrequencyForCollection = termFrequencyForCollection;
	}

	/**
	 * Subclasses should use their own weighing
	 * calculations to score the documents.
	 * @return the document score
	 */
	public abstract double score();
	
}
