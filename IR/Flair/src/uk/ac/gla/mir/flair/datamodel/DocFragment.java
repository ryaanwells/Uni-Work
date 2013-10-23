package uk.ac.gla.mir.flair.datamodel;
import java.util.*;

import uk.ac.gla.mir.flair.datamodel.dataElement.DataElement;

/**
 * DocFragment represents a fragment of a document that is in the process of being indexed.
 * It is essentially a pair consisting of a DataElement and a Context.
 * @author  $Author: stewarcd $
 * @version  $Id: DocFragment.java,v 1.1 2006/06/21 15:02:39 stewarcd Exp $
 */
public class DocFragment extends DataElement {

    /**
     * The data element
     */
    private DataElement _DataElement;
    /**
     * The fragment context
     */
    private Context _Context;


    // Operations
    /**
     * Constructor.
     */
    public DocFragment() {
	_DataElement = null;
	_Context = null;
    }

    /**
     * Copy Constructor.  Performs a shallow copy of the object.
     *
     * @param frag The DocFragment to clone.
     */
    public DocFragment(DocFragment frag) {
	_DataElement = frag._DataElement;
	_Context = (Context)frag._Context.clone();
    }

    /**
     * Constructor, providing initial values for members.
     *
     * @param de The DataElement member value
     * @param context The Context value.
     */
    public DocFragment(DataElement de, Context context) {
	_DataElement = de;
	_Context = (Context)context.clone();
    }

    /**
     * Sets the DataElement member to null.
     */
    public void deleteDataElement() {
	_DataElement = null;
    }

    /**
     * Checks to see if the given value is a transitive parent of
     * this node in the TypeTable.
     * <P>
     * PENDING: possible name change to something more descriptive?
     *
     * @param i The integer to look for.
     */
    public boolean isContain(int i) {
	Stack stk = _Context.getTypeTable().getPathTo(_Context.getComponentID());
	while(!stk.empty()) {
	    int j = ((Integer)stk.pop()).intValue();
	    if(j == i)
		return true;
	}
	return false;
    }

    /**
     * Returns the DataElement of this Fragment.
     *
     * @return The DataElement.
     */
    public DataElement getDataElement() {
	return _DataElement;
    }

    /**
     * Returns the context for this fragment.
     *
     * @return The Context.
     */
    public Context getContext() {
	return _Context;
    }

    /**
     * Sets the DataElement
     *
     * @param de The new DataElement.
     */
    public void add(DataElement de) {
	_DataElement = de;
    }

    /**
     * Sets the Context.
     * @param context The new Context.
     */
    public void addContext(Context context) {
	_Context = context;
    }

    /**
     * Implementation of Cloneable.
     */
    public Object clone()
    {
	return new DocFragment(this);
    }

    /** @see java.lang.Object#toString() */
    public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("DocFragment:\nDataElement:");
		sb.append(_DataElement.toString());
		sb.append("\n");
		sb.append(_Context.toString());
		sb.append("\n");
		return sb.toString();
    }

	@Override
	public String getCharValue() {
		return this.toString();
	}
    
    
} /* end class DocFragment */
