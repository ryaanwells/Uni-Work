package uk.ac.gla.mir.flair.datamodel;
import java.util.*;

/**
 * The Context represents some extra data about a DocFragment in the system. 
 * Essentially, it composes it's documentID, it's componentID within the document,
 * a reference to the TypeTable where it's defined and a mapping of arbitrary 
 * name-value pairs which is a slightly undocumented hack.
 * @author  $Author: stewarcd $
 * @version  $Id: Context.java,v 1.2 2006/07/03 16:32:15 stewarcd Exp $
 */
public class Context/* extends DataElement*/ {

    /** Reference to the defining TypeTable */
    protected TypeTable typetab;

    /**
     * The internal ID of the document from whence the
     * associated fragment came.  Remember that
     * documents have internal and external IDs.
     * @see uk.ac.gla.dcs.flair.Index.DocumentIDMap
     */
    protected long docID;

    /** The component ID from which the fragment came. */
    protected int componentID;

    /** Storage for our NVP metadata */
    protected Hashtable pairs;

    /**
     * Constructor
     */
    public Context()
    {
	pairs = new Hashtable();
    }

    /**
     * Constructor providing inital values for typetable, doc
     * and component ID.
     *
     * @param docID The document ID.
     * @param compID The Component ID
     * @param tt The TypeTable pointer.
     */
    public Context(long docID, int compID, TypeTable tt) {
	this();
	this.docID = docID;
	setProperty("document.id", new Long(docID));
	componentID = compID;
	typetab = tt;
    }

    /**
     * Copy Constructor.  This method does a shallow copy of the
     * Hashtable - keys and values are NOT cloned.
     *
     * @param ctxt The Context to clone.
     */
    public Context(Context ctxt)
    {
	this();
	this.docID = ctxt.docID;
	this.componentID = ctxt.componentID;
	this.typetab = ctxt.typetab;
	this.pairs = (Hashtable)ctxt.pairs.clone();
    }


    /**
     * Returns the Document ID of this Context.
     *
     * @return The Doc ID.
     */
    public long getDocumentID() { return docID; }

    /**
	 * Returns the Component ID of this Context.
	 * @return  The Component ID
	 */
    public int getComponentID() { return componentID; }

    /**
     * Returns the TypeTable for this context.
     *
     * @return A reference to the typetable.
     */
    public TypeTable getTypeTable() { return typetab; }


    /**
     * Sets an arbitrary name-value pair.
     *
     * @param name The string name of the value.
     * @param value The value to store.
     */
    public void setProperty(String name, Object value) {
	pairs.put(name, value);
    }

    /**
     * Returns the value for the named Property.
     *
     * @param propname The property name.
     * @return The object, or null if none found.
     */
    public Object getProperty(String propname) {
	return pairs.get(propname);
    }

    /**
     * Implementation of Cloneable.
     */
    public Object clone()
    {
	return new Context(this);
    }

    /**
     * Returns a defining tag for a ContraintDE.
     *
     * @return An int tag, as defined in Constants.java
     * @see Constants
     */
 /*   public int getTag() {
	
    return Constants.cDeCNS;
  }
   */ 
    
    
    
    
    
} /* end class Context */
