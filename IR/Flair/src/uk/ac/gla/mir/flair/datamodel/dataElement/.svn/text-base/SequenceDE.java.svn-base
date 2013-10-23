package uk.ac.gla.mir.flair.datamodel.dataElement;

import java.util.*;

/** A class that represents ...
 *
 * @author $Author: stewarcd $
 * @version $Id: SequenceDE.java,v 1.2 2006/07/03 16:32:16 stewarcd Exp $
 */
public class SequenceDE extends DataElement {

    
    /**
     * A List of the Data Elements.
     */
    private List<DataElement> DataElementList;

    
    public SequenceDE() {
    	DataElementList = new ArrayList<DataElement>();
    }

    /**
     * Copy Constructor.  Performs a S copy.
     *
     * @param SDE The sequenceDE to copy.
     */
    public SequenceDE(SequenceDE SDE) {
	
		DataElementList = new ArrayList<DataElement>();
    	
    	Iterator<DataElement> it = SDE.iterator();
    	while(it.hasNext()){
    		DataElement next = it.next();
    		DataElement copy = (DataElement)next.clone();
    		DataElementList.add(copy);
    	}
    }

    /**
     * Adds the data element into the given position of the array.
     *
     * @param e The Element to add
     * @param i The index to add into.
     */
    public void add(DataElement e, int i) {
    	DataElementList.add(i,e);
    }

    /**
     * Adds a dataelement at the end of the list.
     *
     * @param e The DataElement to add.
     */
    public void add(DataElement e) {
    	DataElementList.add(e);
    }

    /**
     * Removes the given data element from the list.
     *
     * @param elem The Element to remove.
     */
    public void removeDataElement(DataElement elem) {
    	DataElementList.remove(elem);
    }

    /**
     * Removes the element at the given index.
     *
     * @param at The index to remove from.
     */
    public void remove(int at) {
    	DataElementList.remove(at);
    }

    /**
     * Returns the size of the list.
     * @return The size of the list.
     */
    public int size() {
    	return DataElementList.size();
    }

    /**
     * Returns the Element at the given index
     * @param i The index to return
     */
    public DataElement getElementAt(int i) {
    	return (DataElement)DataElementList.get(i);
    }

    /**
     * An operation that does nothing!
     */
    public int getTag() {
    	return Constants.cDeSEQ;
    }

    /**
     * Resets the lsit to the emptylist.
     */
    public void clear() {
		DataElementList = new ArrayList<DataElement>();
		System.gc();
    }

    /**
     * Implementation of Cloneable.
     *
     * @return A shallow-copy of this Object.
     */
    public Object clone() {
    	return new SequenceDE(this);
    }
    
    /**
     * Returns an iterator to allow access to the Contents of this sequence.
     * @return The Iterator
     */
    public Iterator<DataElement> iterator(){
    	return DataElementList.iterator();
    }

    /** @see java.lang.Object#toString() */
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	ListIterator li = DataElementList.listIterator();
    	while(li.hasNext()) {
    		DataElement o = (DataElement)li.next();
    		sb.append("  ---> " + o.toString());
    		sb.append("\n");
    	}
    	return sb.toString();
    }

	@Override
	public String getCharValue() {
		return this.toString();
	}
    
    
    
} /* end class SequenceDE */
