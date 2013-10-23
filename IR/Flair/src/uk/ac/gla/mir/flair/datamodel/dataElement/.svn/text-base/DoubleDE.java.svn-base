package uk.ac.gla.mir.flair.datamodel.dataElement;


/**
 * A subclass of DataElement that encapsulates double values.
 *
 * @author $Author: stewarcd $
 * @version $Id: DoubleDE.java,v 1.1 2006/06/21 15:02:33 stewarcd Exp $
 */
public class DoubleDE extends DataElement {

	/**
	 * The internal double storage
	 */
	private double Dble;

	/**
	 * No-arg constructor
	 */
	public DoubleDE() {
	}

	/**
	 * Constructor.
	 *
	 * @param v An initial double value.
	 */
	public DoubleDE(double v) {
		Dble = v;
	}

	/**
	 * Copy Constructor.
	 *
	 * @param de The double to be copied from.
	 */
	public DoubleDE(DoubleDE de) {
		Dble = de.Dble;
	}

	/**
	 * Returns the value of the Double.
	 * @return The value of this double.
	 */
	public double getValue() {
		return Dble;
	}

	/**
	 * Sets the double value.
	 * @param v The new value for the double.
	 */
	public void setDouble(double v) {
		Dble = v;
	}

	/**
	 * Sets the value of the double using a string.
	 *
	 * @param s A String representation of a double.
	 */
	public void setValue(String s) throws NumberFormatException {
		Dble = Double.parseDouble(s);
	}

	/**
	 * Redundant with toString(), returns a String representation of this DE.
	 */
	public String getCharValue() {
		return toString();
	}

	/**
	 * Returns a defining tag for a ContraintDE.
	 *
	 * @return An int tag, as defined in Constants.java
	 * @see Constants
	 */
	public int getTag() {
		return Constants.cDeDOU;
	}

	/** @see java.lang.Object#toString() */
	public String toString() {
		return Dble + "";
	}

	/**
	 * Implementation of Cloneable interface
	 */
	public Object clone() {
		return new DoubleDE(this);
	}
}
