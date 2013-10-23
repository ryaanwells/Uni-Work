package uk.ac.gla.mir.flair.datamodel;

import uk.ac.gla.mir.flair.datamodel.dataElement.*;

/**
 * Wrapper on a DataElement that pairs it with a String type name.
 * @see DataElement
 * @author  $Author: stewarcd $
 * @version  $Id: DataType.java,v 1.1 2006/06/21 15:02:39 stewarcd Exp $
 */
public class DataType {

    /**
     * An attribute that represents the type of this object.
     */
    private String type;

    /**
     * The DataElement whose type is being wrapped.
     */
    private DataElement dataElement;
    /**
     * Constructor
     *
     * @param type The Type to use
     * @param de The dataelement to use.
     */
    public DataType(String type, DataElement de) {
	this.type = type;
	dataElement = de;
    }

    /**
     * Compares the supplied type with the internal type of this
     * object. <B>Check semantics against C++ version</B>.
     *
     * @param type The type to compare to 'this'.
     * @return True if 'type' is the same type as 'this'
     */
    public boolean isSameType(String type) {
	/*
	 * The C++ version looks like:
	 *
	 * if(strcmp(_Type,type) == 0) return 0
	 * else return -1;
	 *
	 * So, if _Type and type match, the result is 0, otherwise
	 * the result is nonzero.
	 *
	 * This implementation returns true if they match,
	 * false otherwise.  NEED to check that these semantics
	 * match.
	 */
	return this.type.equals(type);
    }

    /**
     * Returns the DataElement in this object.
     *
     * @return The stored DataElement.
     */
    public DataElement getElement() {
	return dataElement;
    }

    /**
     * Debugs.
     */
    public void display() {
	System.out.println(type + ": " + dataElement.getClass().getName());
    }
} /* end class DataType */
