package uk.ac.gla.mir.flair.query.spec;

import gnu.trove.TIntArrayList;
import java.util.*;

import uk.ac.gla.mir.flair.index.spec.IndexSpec;
import uk.ac.gla.mir.flair.query.Query;
import uk.ac.gla.mir.flair.query.TextQuery;

/**
 * Title:        TextQuerySpec <br/>
 * Description:  Contains the specification of a TextQuery<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class TextQuerySpec extends Spec {

	/** The Terms of the Query **/
	private String terms;
	
	public TextQuerySpec(TIntArrayList fieldIDs, String terms){
		this.type = TextQueryType;
		this.terms = terms;
		this.fieldIDs =fieldIDs;
	}
	
	/**
	 * @return the terms
	 */
	public String getTerms() {
		return terms;
	}

	/**
	 * @param terms the terms to set
	 */
	public void setTerms(String terms) {
		this.terms = terms;
	}
	
	public String toString(){
		return ""+Arrays.toString(fieldIDs.toNativeArray())+" : "+terms;
	}

	/* (non-Javadoc)
	 * @see uk.ac.gla.mir.flair.query.spec.Spec#createQuery(uk.ac.gla.mir.flair.index.spec.IndexSpec)
	 */
	@Override
	public Query createQuery(IndexSpec indexSpec) {
		final TextQuery tq = new TextQuery(this,indexSpec);
		return tq;
	}

	
	
}
