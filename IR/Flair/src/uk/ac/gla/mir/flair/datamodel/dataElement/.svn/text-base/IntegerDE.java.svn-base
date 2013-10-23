package uk.ac.gla.mir.flair.datamodel.dataElement;


/**
 * A class that represents an Integer DataElement
 *
 * @author $Author: stewarcd $
 * @version $Id: IntegerDE.java,v 1.1 2006/06/21 15:02:33 stewarcd Exp $k
 */
public class IntegerDE extends DataElement{

    // Attributes
    /**
     * The integer value
     */
    private int integer;

    /**
     * No argument constructor
     */
    public IntegerDE() {}

    /**
     * Constructor
     *
     * @param i The integer value to give the new IntegerDE.
     */
    public IntegerDE(int i)
    {
	integer = i;
    }

    /**
     * Copy constructor.  Helps with implementing cloneable.
     */
    public IntegerDE(IntegerDE ide)
    {
	integer = ide.integer;
    }

    /**
     * Returns the value of this integerDE
     *
     * @return The integer value
     */
    public int getValue()
    {
	return integer;
    }

    /**
     * Sets the integer value of this DE.
     *
     * @param v The new value for this DE.
     */
    public void setInt(int v)
    {
	integer = v;
    }

    /**
     * Sets the value from a string.
     *
     * @param s The string representation of an integer
     * @exception NumberFormatException If s does not represent an integer
     */
    public void setValue(String s) throws NumberFormatException
    {
    	
    	integer = Integer.parseInt(s.trim());
    }

    /**
     * Returns the value of the integer as a string.
     *
     * <B>This may be a pointless method given the
     *    automatic conversions Java performs.</b>
     *
     * @return A string representing the value of this integer.
     */
    public String getCharValue()
    {
    	return integer+"";
    }


    /**
     * Returns a defining tag for a ContraintDE.
     *
     * @return An int tag, as defined in Constants.java
     * @see Constants
     */
    public int getTag()
    {
	return Constants.cDeINT;
    }

    /**
     * Dumps the value to System.out
     */
    public void display()
    {
	System.out.println("IntegerDE: " + integer);
    }

    /**
     * Implements the Cloneable interface.
     */
    public Object clone() {
	return new IntegerDE(this);
    }

    /** @see java.lang.Object#toString() */
    public String toString() { return ""+integer; }
} /* end class IntegerDE */
