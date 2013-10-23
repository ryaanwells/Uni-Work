package uk.ac.gla.mir.flair.index.spec;

import uk.ac.gla.mir.flair.datamodel.AttributeList;
import uk.ac.gla.mir.flair.irmodel.filter.FilterChain;
import gnu.trove.TIntArrayList;

/**
 * Title:        Spec <br/>
 * Description:  Contains All the information about <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class Spec {

	public final static String TEXT_TOKEN = "TEXT_TOKEN";
    public final static String IDENTIFIER_TYPE = "IDENTIFIER_TOKEN";
	/**
     * The name of an index
     */
    protected String indexName;

    /**
     * This Inverted Index has stop words removed yes/no
     */
    protected boolean stopType;

    /**
     * This Inverted Index uses stemming yes/no
     */
    protected String stemType;

    /**
     * The Type of Tokens this index contains
     */
    protected String tokenType;
	
    /**
     * The Type Table Identifier for the type of the IRObjects 
     * stored in this InvertedIndex
     */
    protected int typeTableID;
    
    /** A list of the Data element IDS to extract from the IROBjects **/
    protected TIntArrayList dataElementIDs;
        
    protected AttributeList attributes;
    
    /**  **/
    protected FilterChain filterChain;
    
    /**
     * 
     */
    public Spec(){
    	stopType = false;
    	dataElementIDs = new TIntArrayList();
    }
    
	/**
	 * @return the indexName
	 */
	public String getIndexName() {
		return indexName;
	}

	/**
	 * @param indexName the indexName to set
	 */
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	/**
	 * @return the stemType
	 */
	public String getStemType() {
		return stemType;
	}

	/**
	 * @param stemType the stemType to set
	 */
	public void setStemType(String stemType) {
		this.stemType = stemType;
	}

	/**
	 * @return the stopType
	 */
	public boolean getStopType() {
		return stopType;
	}

	/**
	 * @param stopType the stopType to set
	 */
	public void setStopType(boolean stopType) {
		this.stopType = stopType;
	}

	/**
	 * @return the tokenType
	 */
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * @param tokenType the tokenType to set
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * Gets the Attribute List
	 * @return the attribute list
	 */
	public AttributeList getAttributes(){
		return attributes;
	}
	
	/**
	 * @return the typeTableID
	 */
	public int getTypeTableID() {
		return typeTableID;
	}

	/**
	 * @param typeTableID the typeTableID to set
	 */
	public void setTypeTableID(int typeTableID) {
		this.typeTableID = typeTableID;
	}

	/**
	 * @return the dataElementIDs
	 */
	public TIntArrayList getDataElementIDs() {
		return dataElementIDs;
	}

	/**
	 * @param dataElementIDs the dataElementIDs to set
	 */
	public void setDataElementIDs(TIntArrayList dataElementIDs) {
		this.dataElementIDs = dataElementIDs;
	}

	/**
	 * Debugging
	 */
	public String toString(){
		String msg = "\""+this.indexName+"\" : "+this.stemType+" : "+this.tokenType+" : "+this.typeTableID + " : " + this.dataElementIDs;
		return msg;
	}

	/**
	 * @return the filterChain
	 */
	public FilterChain getFilterChain() {
		return filterChain;
	}

	/**
	 * @param filterChain the filterChain to set
	 */
	public void setFilterChain(FilterChain filterChain) {
		this.filterChain = filterChain;
	}	
}
