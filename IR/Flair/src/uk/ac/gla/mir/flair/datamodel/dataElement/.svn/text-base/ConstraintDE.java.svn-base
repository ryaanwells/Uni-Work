package uk.ac.gla.mir.flair.datamodel.dataElement;
import java.util.*;

/**
 * A class that represents a set of query constraints.  Query
 * constraints are entries in the XML query-specification
 * file that say which fields of the original document structure
 * should be queried.
 *
 * @author $Author: stewarcd $
 * @version $Id: ConstraintDE.java,v 1.1 2006/06/21 15:02:33 stewarcd Exp $
 */
public class ConstraintDE extends DataElement {

    protected List constraints;

    /**
     * Constructor, supplying an initial set of Constraints -
     * presented as a space delimited String of integers.
     */
    public ConstraintDE(String initCons) {
	constraints = new ArrayList(parseConstraints(initCons));
    }

    /**
     * Copy constructor.  Performs a shallow copy.
     *
     * @param cde The ConstraintDE to copy.
     */
    public ConstraintDE(ConstraintDE cde) {
	constraints = new ArrayList(cde.constraints);
    }

    /**
     * Runs a StringTokenizer on the string parameter and
     * returns the tokens in a List.
     *
     * @param con The constraint string.
     */
    protected List parseConstraints(String con) {
	StringTokenizer tok = new StringTokenizer(con, " ");
	List l = new ArrayList(tok.countTokens());
	while(tok.hasMoreTokens()) {
	    String tk = tok.nextToken();
	    int i = Integer.parseInt(tk);
	    l.add(new IntegerDE(i));
	}
	return l;
    }

    /**
     * Returns the child element at the given index.
     *
     * @param at The index to retrieve from.
     * @return The DataElement in question (an IntegerDE), or null
     * if index is not valid.
     */
    public DataElement getElementAt(int at) {
	return (DataElement)constraints.get(at);
    }

    /**
     * Dumps toString to System.out.
     */
    public void display(){ System.out.println(this); }

    /**
     * Implementation of Cloneable interface.
     */
    public Object clone() { return new ConstraintDE(this); }


    /**
     * Sets the constraints from a String, presented as a space
     * delimited string of integers.
     *
     * @param str The string to set.
     */
    public void setValue(String str)
    {
	constraints = new ArrayList(parseConstraints(str));
    }

	//Added by morrison (31/07/03)
	public int size() { return constraints.size(); }

    /**
     * Returns a string representation of this constraint.
     * This method is redundant with toString().
     *
     * @return String representation
     */
    public String getCharValue() {
	return this.toString();
    }

    /**
     * If the parameter is an instance of IntegerDE,
     * this method adds it to the list of constraints.
     *
     * @param elem The DataElement to add.
     */
    public void add(DataElement elem)
    {
	if(elem instanceof IntegerDE)
	    constraints.add(elem);
    }

    /** @see java.lang.Object#toString() */
    public String toString() {
	StringBuffer sb = new StringBuffer();
	ListIterator it = constraints.listIterator();
	while(it.hasNext()) {
	    sb.append(it.next().toString());
	    if(it.hasNext()) sb.append(", ");
	}

	return sb.toString();
    }
}



