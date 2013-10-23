package uk.ac.gla.mir.flair.datamodel;
import java.util.*;

/**
 * Models the 'address' of a DataElement in the <code>TypeTable</code>.
 * Address is used in two ways in the system:
 * <OL>
 * <LI>It is used in {@link TypeObject TypeObject} to represent the
 *     children of the specific object.
 * <LI>It is used in the {@link TypeTable#getIRObject() getIRObject call}
 *     and subsequent calls into <code>IRObject</code>, where it represents
 *     a sequence of paths down the tree.
 * </OL>
 * <P>
 * In the second case, think of Address as instructions to traverse a Trie:
 * [ 0, 1, 3 ], for example, would indicate the 0'th child of the root, then the
 * 1st child of that element, then the third child of that subsequent element.
 * <p>
 * Essentially, address is an ordered list of integers that represent
 * specific sequences of IDs.
 *
 * @see TypeTable
 * @author $Author: stewarcd $
 * @version $Id: Address.java,v 1.1 2006/06/21 15:02:39 stewarcd Exp $
 */
public class Address {

    /**
     * List of children.  Contains Integer.
     */
    private List ChildList;

    /**
     * Constructor.
     */
    public Address() {
    	ChildList = new LinkedList();
    }

    /**
     * Returns the entry at the given index.
     *
     * @param idx The entry index to get.
     * @return The value of the entry at the given index, or -1 if not found
     */
    public int getElementAt(int idx) {
		Integer in = (Integer)ChildList.get(idx);
		return (in != null) ? in.intValue() : -1;
    }

    /**
     * Returns size of the entry list.
     *
     * @return Number of entries.
     */
    public int size() {
    	return ChildList.size();
    }

    /**
     * Adds an integer to the List.
     * @param i The integer to add.
     */
    public void add(Integer i) {
    	ChildList.add(i);
    }

    /**
     * Adds an Int to the list.
     * @param j The int to add.
     */
    public void add(int j) {
    	ChildList.add(new Integer(j));
    }

    /**
     * Adds the Integer at the specified position in the List.
     * @param i The integer to add.
     * @param at The index to add i at.
     */
    public void add(Integer i, int at) {
    	ChildList.add(at, i);
    }

    /**
     * Same as add(Integer, int) but takes (int,int).
     * @param i The int to add.
     * @param at The index to add i at.
     */
    public void add(int i, int at) {
    	ChildList.add(at, new Integer(i));
    }

    /**
     * Is the given integer in the list?
     * @param j The integer to search for.
     * @return True if found, false if not.
     */
    public boolean contains(int j) {
    	return ChildList.contains(new Integer(j));
    }

    /**
     * Returns the index of a given integer.
     *
     * @param i The int to look for.
     * @return The index of the object, or -1 if not found.
     */
    public int getIndexOf(int i) {
		for(int idx = 0; idx < ChildList.size(); idx++) {
		    if( i == getElementAt(idx) )
			return idx;
		}
		return -1;
    }

      /**
     * Resets the List to empty.
     */
    public void clear() {
    	ChildList = new LinkedList();
    	System.gc();
    }

    public Iterator iterator(){
    	return ChildList.iterator();
    }
    
    /** @see java.lang.Object#toString() */
    public String toString() {
		StringBuffer sb = new StringBuffer();
		ListIterator i = ChildList.listIterator();
		while(i.hasNext()) {
		    sb.append(i.next());
		    if(i.hasNext())sb.append(", ");
		}
		return sb.toString();
    }
} /* end class Address */
