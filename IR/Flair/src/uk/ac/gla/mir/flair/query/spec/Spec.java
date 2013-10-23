package uk.ac.gla.mir.flair.query.spec;

import uk.ac.gla.mir.flair.index.spec.IndexSpec;
import uk.ac.gla.mir.flair.query.Query;
import gnu.trove.TIntArrayList;

/**
 * Title:        Spec <br/>
 * Description:  Contains the specification of a Query<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public abstract class Spec {
	
	public static String TextQueryType = "TextQuery";
	public static String TRECQueryType = "TREC";
	
	/** The Type of the Query **/
	protected String type;
	
	/** List of Field the Query is to be performed over **/
	protected TIntArrayList fieldIDs;

	/** **/
	protected int TypeTableID;
	
	/** Name of the weighting method used **/
	protected String weighting;
	
	/** List of parameters for the particular weighting
	 *  Care Need Taken when assigning the correct parameters
	 */
	protected  double[] params;
	
	/**
	 * The Query Identifier
	 */
	protected String queryID;
	
	
	
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
	 * @return the weighting
	 */
	public String getWeighting() {
		return weighting;
	}

	/**
	 * @param weighting the weighting to set
	 */
	public void setWeighting(String weighting) {
		this.weighting = weighting;
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
	 * @return the fieldIDs
	 */
	public TIntArrayList getFieldIDs() {
		return fieldIDs;
	}

	/**
	 * @param fieldIDs the fieldIDs to set
	 */
	public void setFieldIDs(TIntArrayList fieldIDs) {
		this.fieldIDs = fieldIDs;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the typeTableID
	 */
	public int getTypeTableID() {
		return TypeTableID;
	}

	/**
	 * @param typeTableID the typeTableID to set
	 */
	public void setTypeTableID(int typeTableID) {
		TypeTableID = typeTableID;
	}

	/**
	 * Creates the Appropriate type of query
	 * @return the appropriate class of query
	 */
	public abstract Query createQuery( final IndexSpec indexSpec );
}
