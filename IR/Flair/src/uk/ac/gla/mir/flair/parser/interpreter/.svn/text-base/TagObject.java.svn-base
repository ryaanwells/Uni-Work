package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers 
 * Description: 
 * Copyright:    Copyright (c) 2002
 * Company:      Department of Computing Science, University of Glasgow
 * @author  Joemon M Jose & Anu Joseph
 * @version  1.0
 */
public class TagObject {
	
	
	public int type;
	public int pos;
	public int len;
	public char[] buf;
	public char[] originalbuf;
	public boolean success;
	
	public TagObject() {
		len=0;
		pos=0;
		type=0;
	}

	/**
	 * Sets the Buffer
	 * @param data A Character array to use as a Buffer 
	 */
	public void setBuf(char[] data) {
		buf = data;
		len = buf.length;
 	 }

	/**	
	 * Sets the Buffer
	 * @param data A Character array to use as a Buffer
	 * @param p The Position
	 */
	public void setBuf(char[] data, int p) {
		originalbuf = data;
		pos=p;
	}

	/**
	 * @param p The pos to set.
	 */	
	public void setPos(int p) {
		pos = p;
	}

	public void  setData(char[] orig, char[] bu, int newp, int t) {
		originalbuf = orig;
		buf = bu;
		pos = newp;
		type = t;
	}

	public void setBool(boolean b) {
		success = b;
	}
}
