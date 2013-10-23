package uk.ac.gla.mir.flair.datamodel;
import java.util.*;

import uk.ac.gla.mir.flair.datamodel.dataElement.DataElement;


/**
 * A class that represents a list of DataTypes
 *
 * @author $Author: stewarcd $
 * @version $Id: DataTypeList.java,v 1.1 2006/06/21 15:02:39 stewarcd Exp $
 */
public class DataTypeList {

    /**
     * An attribute that represents the set of types.
     */
    private List types;

    /**
     * Constructor
     */
    public DataTypeList() {
	types = new LinkedList();
    }

    /**
     * Resets the list to empty.
     */
    public void clear() {
	types = new LinkedList();
	System.gc();
    }

    /**
     * Debug.
     */
    public void display() {
	System.out.println("Debugging a DataTypeList not supported.");
    }

    /**
     * Adds a DataType to the list.
     *
     * @param data The DataType to add.
     */
    public void add(DataType data) {
	types.add(data);
    }

    /**
     * Creates a list containing one of each known datatype.
     * Unimplemented.
     */
    public void create() {
	/*
	  DataType data;

	  data = new DataType("DE",new DataElement());
	  Add(data);
	  data.display();

	  data = new DataType("INT_DE",new IntegerDE());
	  Add(data);
	  data.display();

	  data = new DataType("CHAR_DE",new StringDE());
	  Add(data);
	  data.display();

	  data = new DataType("PNT_DE",new PointDE());
	  Add(data);
	  data.display();

	  data = new DataType("SEQ_DE",new SequenceDE());
	  Add(data);
	  data.display();

	  data = new DataType("REP_DE",new RepeatDE());
	  Add(data);
	  data.display();

	  display();
	*/
    }

    /**
     * Returns the size of the list.
     *
     * @return An int - size of the list.
     */
    public int size() {
	return types.size();
    }

    /**
     * Returns the element at the specified index.
     *
     * @param at The index to retrieve from
     * @return A DataElement if 'at' is a valid index.  Null, otherwise.
     */
    public DataType getElementAt(int at) {
	return (DataType)types.get(at);
    }

    /**
     * Returns the first DataElement that matches the
     * supplied type parameter.
     *
     * @param type The type to look for.
     * @return A DataElement if found.  Null if no matching
     * element is found.
     */
    public DataElement getDataElement(String type) {
	DataType typeObject;

	for(int counter=0; counter < types.size(); counter++) {
	    typeObject = getElementAt(counter);
	    if(typeObject.isSameType(type)) {
		return typeObject.getElement();
	    }
	}

	return null;

    }
} /* end class DataTypeList */
