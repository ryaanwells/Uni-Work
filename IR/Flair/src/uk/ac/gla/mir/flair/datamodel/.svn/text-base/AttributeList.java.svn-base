package uk.ac.gla.mir.flair.datamodel;

/**
 * Title:        General Purpose Parsers
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Department of Computing Science, University of Glasgow
 * @author 		 Joemon M Jose & Anu Joseph
 * @version 1.0
 */

import java.util.*;  // this is for hashtable

public class AttributeList {

   // Attributes
    private Hashtable<String, String> attribs;

    // Operations
    /**
     * Constructor.
     */
    public AttributeList()
    {
    	attribs = new Hashtable<String, String>();
    }

    /**
     * Returns the names of all known attributes.
     *
     * @return An enumeration of all attribute names.
     */
    public Set<String> getAttributeNames()
    {
	    return attribs.keySet();
    }

    public void display() {

    }

    /**
     * Returns the value for a given attribute name.
     *
     * @param name The name of the attribute.
     */
    public String getAttributeForName(String name)
    {
	    return attribs.get(name);
    }
    
    /**
     * Returns the value for a given attribute name.
     * Then Removes the Attribute from the list
     * @param name The name of the attribute.
     */
    public String getAttributeForNameAndRemove(String name)
    {
    	String tmp = attribs.get(name);
    	attribs.remove(name);
    	return tmp;
    }

    /**
     * Sets an attribute/name pair into the list. An attempt
     * to store a value with the same name as an existing attribute
     * will overwrite the first value.
     *
     * @param name The attribute name.
     * @param value The attribute value.
     */
    public void setAttributeNameAndValue(String name, String value)
    {
    	attribs.put(name, value);
    }
    
    /**
     * toString
     * @return The String representation of this Attribute List
     */
    public String toString(){
    	String msg = "[";
    	final Iterator<String> keys = attribs.keySet().iterator();
    	while(keys.hasNext()){
    		final String key = keys.next();
    		final String value = attribs.get(key);
    		msg += " "+key+"="+value+" ";
    	}
    	msg += "]";
    	return msg;
    }
    
} /* end of class */
