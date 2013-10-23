package uk.ac.gla.mir.flair.datamodel.dataElement;

import uk.ac.gla.mir.flair.util.Assert;

/**
 * A DataElement that represents the system-external document ID of the document.
 * This is only used for document collections that define ID numbers in their data.
 * This is a bit of a hack, since not all the functions are appropriate.  I just 
 * had to fit it in somewhere under DataElement.  
 * See MarkupVisitor.operate() for the rest of the hack.
 *  
 * @author  $Author: stewarcd $
 * @version  $Id: DocumentIDDE.java,v 1.1 2006/06/21 15:02:33 stewarcd Exp $
 * @see  flair.Parser.MarkupVisitor#operate(String,TextNode)
 */
/** 
 * AHH hacking .. lovely
 */

public class DocumentIDDE extends DataElement implements Cloneable{

    private long id;

    public DocumentIDDE(long id) {
	setId(id);
    }

    public DocumentIDDE(int id) {
	setId(id);
    }

    /**
     * Does a conversion from int to long and Sets the ID.
     *
     * @param id The ID to set.
     */
    public void setId(int id) { setId((long)id); }

    /**
	 * Sets the ID.
	 * @param id  The long ID.
	 */
    public void setId(long id) {
	this.id = id;
    }

    /**
	 * Does nothing in this implementation.  Use getLongID().
	 */
    public int getId() { return 0; }

    /**
     * Returns the internal ID of the document.
     */
    public long getLongID() {
	return id;
    }

    /**
     * Does nothing.
     */
    public int getTag() {
	return 0;
    }

    /**
     * Returns null.
     */
    public DataElement getElementAt(int at) {
	return null;
    }

    /**
     * All subclasses of DataElement should be Cloneable
     */
    public Object clone() {
	return new DocumentIDDE(id);
    }


    /**
     * Does nothing
     */
    public void setValue(String str)
    {
	Assert.warn(true, "Calling DocumentIDDE.setValue(). Could be bad.");
    }


    /**
     * Returns a char value. <b>Will return null</B>.
     *
     * @return Null. Always.
     */
    public String getCharValue() {
	Assert.warn(true, "Calling DocumentIDDE.getCharValue().  This could be bad.");
	return null;
    }

    /**
     * Adds a dataelement to this.  Unimplemented.
     *
     * @param elem The DataElement to add.
     */
    public void add(DataElement elem)
    {
	Assert.warn(true, "Calling DocumentIDDE.Add().  Maybe a bad idea.");
    }


} /* end class DataElement */
