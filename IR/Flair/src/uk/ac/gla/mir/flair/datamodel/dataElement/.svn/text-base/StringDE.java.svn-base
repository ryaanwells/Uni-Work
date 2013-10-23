package uk.ac.gla.mir.flair.datamodel.dataElement;

/**
 * A class that represents a String data element
 *
 * @author $Author: stewarcd $
 * @version $Id: StringDE.java,v 1.1 2006/06/21 15:02:33 stewarcd Exp $
 */
public class StringDE extends DataElement {

    /**
     * The string attribute.
     */
    private String _String;

    /**
     * Basic Constructor
     *
     * @param s The string to construct a DataElement from
     */
    public StringDE(String s)
    {
    	_String = s+"";
    }

    /**
     * Copy Constructor.  Check the trick with the clone() method.
     */
    public StringDE(StringDE sde)
    {
    	_String = new String(sde._String)+"";
    }

    /**
     * No-arg Constructor - the empty string is used as
     * a default value.
     */
    public StringDE(){
    	this("");
    }

    /**
     * Returns the value of the string in this DataElement
     *
     * @return The String value
     */
    public String getValue()
    {
    	return _String;
    }

    /**
     * Set the value of the String in this data element.
     *
     * @param s The String value to set.
     */
    public void setValue(String s)
    {
    	_String = s;
    }

    /**
     * Returns some kind of identifying tag.
     */
    public int getTag() {
    	return Constants.cDeSTR;
    }

    /**
     * Returns the value of the string in this DataElement
     * @return The string value
     */
    public String getString() {
    	return _String;
    }

    /**
     * For StringDE, equivalent to getString()
     */
    public String getCharValue() {
    	return _String;
    }

    /**
     * Dumps String value to System.out
     */
    public void display() {
    	System.out.println("StringDE: " + _String);
    }

    /**
     * Implementation of cloneable interface
     */
    public Object clone() {
    	return new StringDE(this);
    }

    public String toString(){ return _String; }

} /* end class StringDE */
