package uk.ac.gla.mir.flair.datamodel.dataElement;
import java.util.*;

/**
 * A class that represents a list of DataElements
 *
 * @author $Author: stewarcd $
 * @version $Id: RepeatDE.java,v 1.1 2006/06/21 15:02:33 stewarcd Exp $
 */
public class RepeatDE extends DataElement {

    // Attributes
    /**
     * An attribute that represents ...
     */
    private List<DataElement> _DataElementList;

    // Operations

    /** No-arg constructor */
    public RepeatDE() {
    	_DataElementList = new ArrayList<DataElement>();
    }

    /**
     * Constructor.
     *
     * @param estimatedSize The initial size for the list.
     */
    public RepeatDE(int estimatedSize) {
    	_DataElementList = new ArrayList<DataElement>(estimatedSize);
    }

    /**
     * Copy Constructor.
     *
     * @param de The RepeatDE to copy from.
     */
    public RepeatDE(RepeatDE de)
    {
    	_DataElementList = new ArrayList<DataElement>();
    	
    	Iterator<DataElement> it = de.iterator();
    	while(it.hasNext()){
    		DataElement next = it.next();
    		DataElement copy  = (DataElement)next.clone();
    		_DataElementList.add(copy);
    	}
    }

    /**
     * Cloneable Interface
     */
    public Object clone() {
    	return new RepeatDE(this);
    }

    /**
     * Returns an ID tag.
     * @return A tag for this class, as defined in Constants.java
     */
    public int getTag() {
    	return Constants.cDeREP;
    }

    /**
     * Adds a DataElement to this list. FIFO Style.
     *
     * @param elem The DataElement to add.
     */
    public void add(DataElement elem) {
    	_DataElementList.add(elem);
    }

    /**
     * Removes the given element from the list. See remove() in
     * java.util.List for a discussion of the semantics of this
     * operation.
     *
     * @see java.util.List#remove
     * @param elem The Element to remove
     */
    public void removeDataElement(DataElement elem) {
    	_DataElementList.remove(elem);
    }

    /**
     * Returns the size of the List
     *
     * @return The size of the list.
     */
    public int size() {
    	return _DataElementList.size();
    }

    /**
     * Returns the DataElement in the given position of
     * the list.
     *
     * @param i The index of the DataElement to Return.
     * @return The DataElement in position i.
     */
    public DataElement getElementAt(int i) {
    	return (DataElement)_DataElementList.get(i);
    }

    /**
     * Clears the list.
     */
    public void clear() {
		_DataElementList= new ArrayList<DataElement>();
		System.gc(); // We might have just freed a big list.
    }

    /**
     * Returns a <code>ListIterator</code> for the elements of
     * the repeat.
     *
     * @return A ListIterator whose contents are DataElements
     */
    public Iterator<DataElement> iterator() {
    	return _DataElementList.listIterator();
    }

    /** @see java.lang.Object#toString() */
    public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("RepeatDE:\n");
		for(int i = 0; i < _DataElementList.size(); i++) {
		    sb.append("--R--> " + _DataElementList.get(i).toString());
		    sb.append("\n");
		}
		return sb.toString();
    }

	@Override
	public String getCharValue() {
		return this.toString();
	}
    
    

} /* end class RepeatDE */
