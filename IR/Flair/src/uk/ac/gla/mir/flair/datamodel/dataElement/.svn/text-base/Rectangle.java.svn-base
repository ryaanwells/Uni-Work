package uk.ac.gla.mir.flair.datamodel.dataElement;


/**
 * This is the class for holding the boxes drawn around an object
 *
 * @author $Author: stewarcd $
 * @version $Id: Rectangle.java,v 1.1 2006/06/21 15:02:33 stewarcd Exp $
 */
public class Rectangle extends DataElement {

    public int theX1;
    public int theY1;
    public int theX2;
    public int theY2;

   
    /**
     *
     * @param x1 Origin X
     * @param y1 Origin Y
     * @param x2 Opposite X
     * @param y2 Opposite Y
     */
    public Rectangle(int x1, int y1, int x2, int y2) {
		theX1 = x1;
		theY1 = y1;
		theX2 = x2;
		theY2 = y2;
    }

    /**
     * Copy Constructor.
     * @param r The rectangle to copy.
     */
    public Rectangle(Rectangle r) {
		theX1 = r.theX1;
		theY1 = r.theY1;
		theX2 = r.theX2;
		theY2 = r.theY2;
    }

    /**
     * Noarg constructor.  Everything gets set to 0.
     */
    public Rectangle() {
		theX1 = 0;
		theY1 = 0;
		theX2 = 0;
		theY2 = 0;
    }

    /**
     * Sets the Bounds of this rectangle.
     *
     * @param x1 Origin X
     * @param y1 Origin Y
     * @param x2 Opposite X
     * @param y2 Opposite Y
     */
    public void setRectangle(int x1, int y1, int x2, int y2) {
		theX1 = x1;
		theY1 = y1;
		theX2 = x2;
		theY2 = y2;
    }

    /**
     * Returns X1.
     * @return The X1 value
     */
    public int getX1() {
    	return theX1;
    }

    /**
     * Returns X2.
     * @return The X2 value
     */
    public int getX2() {
    	return theX2;
    }

    /**
     * Returns Y1.
     * @return The Y1 Value.
     */
    public int getY1() {
    	return theY1;
    }

    /**
     * Returns Y2.
     * @return The Y2 value.
     */
    public int getY2() {
    	return theY2;
    }

    /**
     * Dumps contents to System.out.
     */
    public void display() {
    	System.out.println("[" + theX1 + "," + theY1 + "," + theX2 + "," + theY2 + "]");
    }

    /**
     * 
     */
    public String toString(){
    	return "[" + theX1 + "," + theY1 + "," + theX2 + "," + theY2 + "]";
    }
    
    
    @Override
	public String getCharValue() {
		return this.toString();
	}

	/**
     * Equality on Rectangles.  Equality is defined as all 4 points being equal.
     *
     * @param rect The rectangle to compare to this.
     * @return True if rect == this, false otherwise.
     */
    public boolean equal(Rectangle rect) {
		if(theX1 != rect.theX1) return false;
		if(theX2 != rect.theX2) return false;
		if(theY1 != rect.theY1) return false;
		if(theY2 != rect.theY2) return false;
		return true;
    }


    /**
     * Computes distance between two Rectangles.
     *
     * @param rect The Rectange to compare with this one.
     */
    public double distance(Rectangle rect) {
    	double sum = 0.0;

		// This calculation copied straight from rectangle.cc
	   sum =   (theX1- rect.theX1) * (theX1-rect.theX1)
                + (theX2- rect.theX2) * (theX2-rect.theX2)
                + (theY1- rect.theY1) * (theY1-rect.theY1)
                + (theY2- rect.theY2) * (theY2-rect.theY2);
        return 1-Math.sqrt(sum)/Math.sqrt(780616);
	// ^^ Changed this line to use java.lang.Math ^^
    }

    /**
     * Cloneable interface
     */
    public Object clone()
    {
    	return new Rectangle(this);
    }
} /* end class Rectangle */
