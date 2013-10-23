package uk.ac.gla.mir.flair.datamodel.dataElement;

/**
 * A class that represents a Point DataElement.
 *
 * @author $Author: stewarcd $
 * @version $Id: PointDE.java,v 1.1 2006/06/21 15:02:33 stewarcd Exp $
 */
public class PointDE extends DataElement {

	private int X;
    private int Y;

   
    /** No-arg constructor */
    public PointDE() { X = 0; Y = 0; }

    /**
     * @param x The initial X value
     * @param y The initial Y value
     */
    public PointDE(int x, int y) {
	X=x;
	Y=y;
    }

    /**
     * @param pt The point to copy from.
     */
    public PointDE(PointDE pt) {
		this.X = pt.X;
		this.Y = pt.Y;
    }

    /**
     * Returns the X value of the point.
     * @return The X value.
     */
    public int getXValue() {
    	return X;
    }

    /**
     * Returns the Y value of the point.
     * @return The Y value.
     */
    public int getYValue() {
    	return Y;
    }

    /**
     * An operation that sets the value of X and Y for this point.
     * @param x The x value
     * @param y The y value
     */
    public void setPoint(int x, int y) {
    	X = x;
    	Y = y;
    }

    /**
     * Parses a point value from a String.  The string should be
     * presented in the form <code>(x,y)</code> with no whitespace
     * leading or trailing. <code>IllegalArgumentException</code>
     * is thrown if the string cannot be parsed.
     *
     * @param s The string to parse.
     */
    public void setValue(String s) throws IllegalArgumentException{
	try {
	    int comma = s.indexOf(",");
	    if(comma == -1) throw new Exception();
	    int lbrack = s.indexOf("(");
	    if(lbrack == -1) throw new Exception();
	    int rbrack = s.indexOf(")");
	    if(rbrack == -1) throw new Exception();

	    /*
	     * The point of making these 2 temps is that if the x
	     * value was parsed OK and assigned to X and then the
	     * Y value bombed at parseInt() we would get to be
	     * inconsistent, having the new X and the old Y.
	     */
	    int xTmp = Integer.parseInt(s.substring(lbrack+1, comma));
	    int yTmp = Integer.parseInt(s.substring(comma+1, rbrack));
	    X = xTmp;
	    Y = yTmp;
	}catch(Exception e) {
	    throw new IllegalArgumentException("String cannot be parsed as a Point");
	}
    }

    /**
     * Returns a String representation of this point.  In the form
     * (x,y) - suitable for parsing into another PointDE.
     */
    public String getCharValue() {
    	return "(" + X + "," + Y + ")";
    }

    /**
     * An operation that does apparently nothing.
     */
    public int getTag() {
    	return 0;
    }

    /**
     * Dumps data to System.out.
     */
    public void display() {
    	System.out.println("PointDE: " + this.toString());
    }

    public String toString()
    {
    	return getCharValue();
    }

    /**
     * Implementation of Clone.
     */
    public Object clone() {
	return new PointDE(this);
    }
} /* end class PointDE */
