package uk.ac.gla.mir.flair.parser.interpreter;

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
    public Enumeration getAttributeNames()
    {
	    return attribs.keys();
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
	    return (String)attribs.get(name);
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
} /* end of class */
