package uk.ac.gla.mir.flair.query;

import uk.ac.gla.mir.flair.datamodel.IRObjectSet;
import uk.ac.gla.mir.flair.index.spec.IndexSpec;
import uk.ac.gla.mir.flair.query.spec.QuerySpec;
import uk.ac.gla.mir.flair.query.spec.Spec;

/**
 * Title:        Query <br/>
 * Description:  Perform The Query <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public abstract class Query {

	protected Spec querySpec;
	protected IndexSpec indexSpec;
	
	public Query( Spec querySpec, IndexSpec indexSpec){
		this.querySpec = querySpec;
		this.indexSpec = indexSpec;
	}
	
	/**
	 * @return the querySpec
	 */
	public Spec getQuerySpec() {
		return querySpec;
	}

	/**
	 * @param querySpec the querySpec to set
	 */
	public void setQuerySpec(Spec querySpec) {
		this.querySpec = querySpec;
	}

	/**
	 * @return the indexSpec
	 */
	public IndexSpec getIndexSpec() {
		return indexSpec;
	}

	/**
	 * @param indexSpec the indexSpec to set
	 */
	public void setIndexSpec(IndexSpec indexSpec) {
		this.indexSpec = indexSpec;
	}

	/**
	 * Perform the Query
	 * @return the results
	 * @throws Exception
	 */
	public abstract IRObjectSet doQuery() throws Exception;
	
}
