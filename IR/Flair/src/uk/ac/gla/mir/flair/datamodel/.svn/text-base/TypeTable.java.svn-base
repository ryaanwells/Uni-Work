package uk.ac.gla.mir.flair.datamodel;

import java.util.*;

import uk.ac.gla.mir.flair.datamodel.dataElement.DataElement;

/** A class that represents ...
 *
 * @author $Author: stewarcd $
 * @version $Id: TypeTable.java,v 1.2 2006/07/06 00:56:16 stewarcd Exp $
 */
public class TypeTable{
	
    // Attributes
    private List<TypeObject> TypeTable;
    
    /** 
     * An Integer ID which maps to the Descriptions File ID
     * It also specifies which Inverted Index Spec to use
     */
    private int typeTableID;
    
    // Operations
    /**
     * Constructor.
     * @param estimatedSize The initial sie for the table
     */
    public TypeTable(int estimatedSize) {
    	TypeTable = new ArrayList<TypeObject>(estimatedSize);
    }
    
    public TypeTable(int estimatedSize, int typeTableID) {
    	this.typeTableID = typeTableID;
    	TypeTable = new ArrayList<TypeObject>(estimatedSize);
    }

    /**
     * Returns the anme of the field with the given ID
     *
     * @param id The Field ID to look up.
     */
    public String getFieldName(int id) {
		TypeObject type = getTypeObject(id);
		if(type != null)
		    return type.getName();
		else
		    return null;
    }

    /**
     * Gets the tag name for the field with the given ID.
     *
     * @param id The ID of the field to look up.
     */
    public String getTagName(int id) {
		TypeObject type = getTypeObject(id);
		if(type != null)
		    return type.getTagName();
		else
		    return null;
    }

    /**
     * Returns the type for the given FieldID
     *
     * @param id The ID of the field to look up.
     */
    public int getType(int id) {
		TypeObject type = getTypeObject(id);
		if(type != null)
		    return type.getFieldType();
		else
		    return -1;	
    }

    /**
     * Returns the parent id for the given ID.
     * @param id The ID of the field to look up.
     */
    public int getParentID(int id) {
		TypeObject type = getTypeObject(id);
		if(type != null)
		    return type.getParentId();
		else
		    return -1;
    }

    /**
     * Searches for the TypeObject having a child with the
     * given ID, returns its ID.
     *
     * @param childID The child id to look for.
     * @return The TypeObject that has childID in its children, or -1 if none.
     */
    public int findIdOfParent(int childID) {
	ListIterator it = TypeTable.listIterator();
	while(it.hasNext()) {
	    TypeObject to = (TypeObject)it.next();
	    Address kids = to.getChildren();
	    if(kids != null) {
		if(kids.contains(childID)) {
		    return to.getFieldId();
		}
	    }
	}
	return -1;
    }

    /**
     * Returns the field ID for a given field name.
     * @return The field ID for the gien field name or -1 if not found
     */
    public int getFieldId(String name) {
		TypeObject type = getNameTypeObject(name);
		if(type != null)
		    return type.getFieldId();
		else
		    return -1;
    }

    /**
     * Returns the field ID for the field with the given tag name.
     * @param tag The tag anme to search for.
     * @return The field ID of the given tag or -1 if not.
     */
    public int getFieldIdByTagName(String tag) {
		ListIterator it = TypeTable.listIterator();
		while(it.hasNext()) {
		    TypeObject to = (TypeObject)it.next();
		    if(to.getTagName().equals(tag))
			return to.getFieldId();
		}
	
		return -1;
    }

    /**
     *
     */
    public void completeTypeTable() {
		int size = size();
		TypeObject type=null;
		Address tempAdd =null;
		for(int counter=0; counter <size; counter++) {
		    type = at(counter);
		    tempAdd= computeAddress(type.getFieldId());
		    type.setAddress(tempAdd);
		}
    }

    /**
     * Returns an address for the given ID.
     *
     * @param id The ID of the field to look up.
     */
    public Address getAddress(int id) {
	TypeObject type = getTypeObject(id);
	if(type != null) {
	    return type.getAddress();
	}
	else
	    return null;
    }

    /**
     * Returns children for the given ID.
     *
     * @param id The ID of the field to look up.
     */
    public Address getChildren(int id) {
	/*
	 * This is a direct translation of the C++ version
	 * but appears identical to getAddress()
	 */
	TypeObject type = getTypeObject(id);
	if(type!= null)
	    return type.getAddress();
	else
	    return null;
    }

    /**
     * Adds a TypeObject to the table.
     *
     * @param type The TypeObject to add.
     */
    public void add(TypeObject type) {
		// The C++ version uses a method insert_last()
		// in an OStore list.  List.add() probably has
		// similar semantics.
    	TypeTable.add(type);
    }

    /**
     * Adds a TypeObject to the table at the given position.
     *
     * @param type The TypeObject to add.
     * @param idx The index to add it at.
     */
    public void add(TypeObject type, int idx) {
    	TypeTable.add(idx,type);
    }

    /**
     * Sets the Parent ID of the TypeObject with the given ID
     *
     * @param id The ID of the TypeObject to operate on.
     * @param parentId The id to set as the parent.
     */
    public void setParentId(int id, int parentId) {
	TypeObject type = getTypeObject(id);
	if(type != null)
	    type.setParentId(parentId);
    }

    /**
     * Returns the TypeObject with the given ID.
     *
     * @param id The ID to look up.
     */
    public TypeObject getTypeObject(int id) {
    	// typetable size is usually comparitvly small
    	// hence we can get away with this implementation
    	final int j = TypeTable.size();
		for(int i = 0; i < j; i++) {
		    TypeObject to = (TypeObject)TypeTable.get(i);
		    //System.out.println( to.getFieldId() + " == " + id);
		    if(to.getFieldId() == id)
		    	return to;
		}
		// if not found....
		return null;
    }

    /**
     * Empties the table.
     */
    public void clear() {
		int size = TypeTable.size();
		TypeTable = new ArrayList(size);
		System.gc();
    }


    /**
     * Gets the size of the table.
     *
     * @return The size of the table.
     */
    public int size() {
    	return TypeTable.size();
    }

    /**
     * Returns the TypeObject at the given index.
     *
     * @param idx The index to get the TypeTable from.
     */
    public TypeObject at(int idx) {
    	return (TypeObject)TypeTable.get(idx);
    }

    /**
     * Returns the Path to a field from the root element.
     *
     * @param id The id of the field to get the path to.
     * @return A stack which, by successively popping Integers off it
     * will iterate from the root id to the leaf.
     */
    public Stack getPathTo(int id) {
		Stack s = new Stack();
		s.push(new Integer(id));
		int parent = getParentID(id);
		while(parent != 0) {
		    s.push(new Integer(parent));
		    parent = getParentID(parent);
		}
		s.push(new Integer(0));
		return s;
    }

    /**
     * Goes through the table, making sure parent-child
     * links are consistent. Depends on the assumption that
     * field IDs start at 0 and ascend sequentially.
     */
    public void consistencyCheck() {
		ListIterator it = TypeTable.listIterator();
		while(it.hasNext()) {
		    TypeObject to = (TypeObject)it.next();
		    int parent = findIdOfParent(to.getFieldId());
		    if(parent != -1)
			to.setParentId(parent);
		    else
			to.setParentId(0);
		}
    }

    /**
     * Vends a skeleton IRObject in the correct shape for this
     * TypeTable's document.
     *
     */
    public IRObject getIRObject() {
		IRObject iro = new IRObject(this);
	
		for(int i = 0; i < TypeTable.size(); i++) {
		    TypeObject to = getTypeObject(i);
		    DataElement de = to.getDataElementRepresentation();
		    de.setId(to.getFieldId());
		    iro.addAnElement(to.getFieldId(), de);
		}
		return iro;
    }


    ////////////////////////////////////////////////////////
    // Private Methods.
    ////////////////////////////////////////////////////////

    private TypeObject getNameTypeObject(String name)
    {
		for(int i = 0, j = TypeTable.size(); i < j; i++) {
		    TypeObject to = (TypeObject)TypeTable.get(i);
		    
		    if(to.getName().equals(name))
		    	return to;
		}
		return null;
    }

    private Address computeAddress(int id) {
		/*
		  Assumption: Root Id is always 0
		*/
		Vector v = new Vector();
		TypeObject to = getTypeObject(id);
		int parentid = -1;
		do{
		    parentid = to.getParentId();
		    TypeObject parent_to = getTypeObject(parentid);
		    Address ad = parent_to.getChildren();
		    int childidx = ad.getIndexOf(to.getFieldId());
		    v.add(0, new Integer(childidx));
		    if(parentid != 0) to = getTypeObject(parentid);
		}while(parentid != 0);
	
		Address addr = new Address();
		for(int i = 0; i < v.size(); i++) {
		    Integer in = (Integer)v.get(i);
		    addr.add(in.intValue());
		}
		return addr;
    }

    public String toString() {
		StringBuffer sb = new StringBuffer();
	
		for(int i = 0; i < TypeTable.size(); i++) {
		    sb.append(((TypeObject)TypeTable.get(i)).toString());
		    sb.append("\n\n");
		}
	
		for(int i = 0; i < TypeTable.size(); i++) {
		    TypeObject tob = (TypeObject)TypeTable.get(i);
		    sb.append(tob.getFieldId());
		    sb.append("(" + tob.getAddress() + ")");
		    sb.append("  ");
		}
		sb.append("\n");
		return sb.toString();
    }

	/**
	 * @return the typeTableID
	 */
	public int getTypeTableID() {
		return typeTableID;
	}

	/**
	 * @param typeTableID the typeTableID to set
	 */
	public void setTypeTableID(int typeTableID) {
		this.typeTableID = typeTableID;
	}

} /* end class TypeTable */
