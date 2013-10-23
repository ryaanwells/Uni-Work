package uk.ac.gla.mir.flair.datamodel;
import java.util.*;

import uk.ac.gla.mir.flair.datamodel.dataElement.DataElement;
import uk.ac.gla.mir.flair.datamodel.dataElement.RepeatDE;
import uk.ac.gla.mir.flair.util.Assert;

/**
 * This class represents a set of homogeneous IRObjects together with the 
 * TypeTable they are based on.
 * @author $Author: stewarcd $
 * @version $Id: IRObjectSet.java,v 1.3 2006/07/06 15:23:49 stewarcd Exp $
 */
public class IRObjectSet extends DataElement {

	/**
	 * Holds a list of IR_Objects
	 */
	private RepeatDE ObjectSet;

	/**
	 * The type structure used in all contained objects
	 */
	private TypeTable TypeTable;

	/**
	 * Constructor.
	 *
	 * @param estimatedSize The initial size of the set.
	 */
	public IRObjectSet(int estimatedSize) {
		ObjectSet = new RepeatDE(estimatedSize);
		TypeTable = new TypeTable(estimatedSize);
	}

	/**
	 * Adds a data element to the object set.
	 *
	 * @param de The DataElement to add.
	 */
	public void add(DataElement de) {
		ObjectSet.add(de);
	}
	
	/**
	 * Adds all the Objects In an IRObjectSet to this 
	 * @param newObjects The IRObjects to add
	 */
	public void addAll(IRObjectSet newObjects) {
		
		for (int i=0; i<newObjects.size(); i++) {
			add(newObjects.getElementAt(i));
		}
	}

	/**
	 * Returns the element at the given position in the set.
	 *
	 * @param idx The index of the element to return
	 * @return The idx'th DataElement in the set, or null of no such element.
	 */
	public DataElement getElementAt(int idx) {
		return ObjectSet.getElementAt(idx);
	}

	/**
	 * Returns the IRObject with the given ID.
	 *
	 * @param docID The ID of the document to return.
	 * @return The IRObject with ID docid, or null of no such document.
	 */
	public IRObject getObject(long docID) {
		Assert.warn(
			docID <= ObjectSet.size(),
			"IRObjectSet: Index out of bounds: " + docID);
		return (IRObject) getElementAt(((int) docID) - 1);
	}

	/**
	 * Returns the size of the set.
	 * @return The size of the set.
	 */
	public int size() {
		return ObjectSet.size();
	}

	/**
	 * Returns an Iterator for the elements of the set.
	 *
	 * @return An iterator whose elements are of class <code>DataElement</code>
	 */
	public Iterator<DataElement> iterator() {
		return ObjectSet.iterator();
	}

	/**
	 * Returns the DataElement with address fieldId from
	 * the Object with the given docId.
	 *
	 * @param docId The ID of the document to retrieve.
	 * @param fieldId The id of the field to retrieve.
	 */
	public DataElement getDataElement(int docId, int fieldId) {
		IRObject object = getObject(docId);
		return object.getDataElement(getAddress(fieldId));
	}

	/**
	 * Similar to the other form of getDataElement, but
	 * supplying the Address of the field instead of it's ID.
	 *
	 * @param docId The ID of the document to retrieve.
	 * @param address The address of the field to retrieve.
	 */
	public DataElement getDataElement(int docId, Address address) {
		IRObject object = getObject(docId);
		return object.getDataElement(address);
	}

	/**
	 * Returns the TypeTable in use here.
	 *
	 * @return The Typetable of this set.
	 */
	public TypeTable getTypeTable() {
		return TypeTable;
	}

	/**
	 * Returns theTagName for the given Field ID.
	 *
	 * @param fId The ID of the field.
	 */
	public String getTagName(int fId) {
		return TypeTable.getTagName(fId);
	}

	/**
	 * Returns the name of the field with the given ID.
	 *
	 * @param fieldId The ID of the field.
	 */
	public String getFieldName(int fieldId) {
		return TypeTable.getFieldName(fieldId);
	}

	/**
	 * Returns the type ofthe field.
	 *
	 * @param fieldId The ID of the field.
	 * @return The ID of the Field.
	 */
	public int getFieldType(int fieldId) {
		return TypeTable.getType(fieldId);
	}

	/**
	 * Returns the ID of the field with the given name.
	 *
	 * @param fieldName The name of the field.
	 */
	public int getFieldId(String fieldName) {
		return TypeTable.getFieldId(fieldName);
	}

	/**
	 * Returns the address of the field.
	 * @param fieldId
	 */
	public Address getAddress(int fieldId) {
		return TypeTable.getAddress(fieldId);
	}

	/**
	 * Returns the children of the field.
	 *
	 * @param fId The ID of the field.
	 */
	public Address getChildren(int fId) {
		return TypeTable.getChildren(fId);
	}

	/**
	 * Returns the ID of the parent of the given field.
	 *
	 * @param fid The field ID to returnt the parent for.
	 */
	public int getParentId(int fid) {
		return TypeTable.getParentID(fid);
	}

	/**
	 * Sets the TypeTable of this IRObjectSet
	 * @param tt
	 */
	public void setTypeTable(TypeTable tt) {
		TypeTable = tt;
	}

	/**
	 * 
	 */
	public String toString()
	{
		Iterator iter = ObjectSet.iterator();
		String msg = "";
		while (iter.hasNext())
		{
			final IRObject irobject = (IRObject)iter.next();
			msg += irobject.getAsMarkUp();
		}
		return msg;
	}
	
	@Override
	public String getCharValue() {
		return this.toString();
	}

	/**
	 * Should Clone this Object in memory
	 * @return The New IRObjectSet
	 */
	public Object clone() {
		
		IRObjectSet tmp =  new IRObjectSet( this.size() );
		tmp.setTypeTable( this.TypeTable );
		RepeatDE theObjects = this.ObjectSet;
		RepeatDE newObject = new RepeatDE(theObjects);
		tmp.ObjectSet = newObject;		
		return tmp;
	}
	
} /* end class IRObjectSet */
