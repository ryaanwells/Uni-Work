package uk.ac.gla.mir.flair.datamodel;

import java.util.ArrayList;

import uk.ac.gla.mir.flair.datamodel.dataElement.*;


/**
 * IRObject represents one document in the collection.  It is used
 * heavily in building the index.
 *
 * @author $Author: stewarcd $
 * @version $Id: IRObject.java,v 1.2 2006/06/29 15:39:21 stewarcd Exp $
 */
public class IRObject extends DataElement{

	/** The system-internal ID of this Object */
	protected long iDocID;
	
	/**
	 * An attribute that represents ...
	 */
	public DataElement PickData;

	/**
	 * Reference to the TypeTable that defined the struture of this Object
	 */
	private TypeTable TypeTable;

	public int numberOfTerms;
	/**
	 * Constructor.
	 */
	public IRObject() {
		PickData = new SequenceDE();
	}

	public IRObject(TypeTable tt) {
		PickData = new SequenceDE();
		TypeTable = tt;
	}

	/**
	 * Sets the Internal ID of the Object.
	 *
	 * @param id The internal ID.
	 */
	public void setID(long id) {
		iDocID = id;
	}
	
	/**
	 * Returns the internal IDnumber.
	 *
	 * @return The internal Document ID.
	 */
	public long getID() {
		return iDocID;
	}
	
	/**
	 * Returns the DataElement whose path from the root
	 * element is enumerated in <code>address</code>.  This is
	 * an example of using Address to define a path down the
	 * DataElement tree.
	 *
	 * @param address The address of the element to return
	 */
	public DataElement getDataElement(Address address) {

		/*
		 * 1. If the address is 0, return PickData.getElementAt(0)
		 * 2. If the address is 1 long return PickData.getElementAt(0).getElementAt(addr_0)
		 *
		 * 3. If the address is a sequence, e.g.3 long
		 *  3.1 Get Root element
		 *  3.2 Get addr[0]'th child of Root
		 *  3.3 Make that new root
		 *  3.4 iterate
		 */
		DataElement root = PickData.getElementAt(0);
		for (int i = 0; i < address.size(); i++) {
			DataElement el = root.getElementAt(address.getElementAt(i));
			root = el;
		}

		return root;
	}

	/**
	 * Returns the DataElement whose ID is <code>id</code>.
	 * @param id The first address to get.
	 */
	public DataElement getDataElement(int id) {
		/*
		 * This method is a convenience method
		 * for the above, saving the client from
		 * having to obtain the address of the data
		 * element themselves
		 */
		if (id == 0)
			return PickData.getElementAt(0);
		else {
			Address add = TypeTable.getAddress(id);

			if (add != null)
				return getDataElement(add);
			else
				return null;
		}
	}
	
	
	private ArrayList<DataElement> dataElements;
	/** 
	 * Returns the DataElement with the <code>id</code>
	 */
	public DataElement[] getDataElements(final int id){
		dataElements = new ArrayList<DataElement>();
		getDataElements(id, this.PickData);
		return dataElements.toArray(new DataElement[0]);
	}
	
	private void getDataElements(final int id, final DataElement de){
				
		if(de.getId() == id)
			dataElements.add(de);
		
		if(de.getTag() == uk.ac.gla.mir.flair.datamodel.dataElement.Constants.cDeREP){
				final RepeatDE repeat = (RepeatDE)de;
				final int size = repeat.size();
				for(int i = 0; i < size; i++){
					getDataElements(id, repeat.getElementAt(i));
				}
		}else if(de.getTag() == uk.ac.gla.mir.flair.datamodel.dataElement.Constants.cDeSEQ){
				final SequenceDE repeat = (SequenceDE)de;
				final int size = repeat.size();
				for(int i = 0; i < size; i++){
					getDataElements(id, repeat.getElementAt(i));
				}
		}
	}
	
	/**
	 * Sets the Data of the Element found in id.
	 *
	 * @param id The filed to get the element to be set from
	 * @param de The String to set as the value of the DataElement
	 */
	public void setDataElement(int id, String de) {

		DataElement theDe = null;

		Address add = TypeTable.getAddress(id);
		if (add != null) {

			theDe = getDataElement(add);
			theDe.setValue(de);
			// In C++, this line is outside the if statement,
			// but after an assert, which we're not using here
			// so I moved it inside for safety.
		}
	}

	/**
	 * Adds an element to the Element indicated by id.
	 *
	 * @param id The field to look at for the Element to add to.
	 * @param de The DataElement to add to the retrieved element.
	 */
	public void addAnElement(int id, DataElement de) {

		if (id != 0) {
			int parentID = TypeTable.getParentID(id);
			DataElement e2 = getDataElement(parentID);
			e2.add(de);
		}
		else {
			PickData.add(de);
		}
	}

	/**
	 * Sets the TypeTable.
	 *
	 * @param tTable The new TypeTable
	 */
	public void initialize(TypeTable tTable) {
		TypeTable = tTable;
	}

	/**
	 * This seems to chuck away a bunch of stuff from the TypeTable.
	 */
	public void cleanUp() {
		// From the C++:
		// query the typetable and get RepDE
		// remove the element at position 0 and delete
		TypeObject typeObject = null;
		RepeatDE repDe;
		DataElement theDe;
		Address address;

		for (int counter = 0; counter < TypeTable.size(); counter++) {
			typeObject = TypeTable.at(counter);
			// C++ has assert(typeObject) here
			if (typeObject == null)
				System.out.println("typeObject null in IRObject.cleanUp()");

			if (typeObject.getFieldType() == Constants.cDeREP) {
				address = typeObject.getAddress();
				repDe = (RepeatDE) getDataElement(address);

				if (repDe.size() != 0) {
					theDe = repDe.getElementAt(0);
					repDe.removeDataElement(theDe);
					theDe = null;
				}
			}
		}
	}

	/**
	 * This method returns a string of markup data.  In C++ this method
	 * is void and uses a char* to get the data back.  Java doesn't do that
	 * so the char* becomes a StringBuffer and the result comes back in the
	 * StringBuffer.
	 * <P>
	 * The C++ API uses GetAsMarkup() to initialise the char* and return
	 * the string to the caller.  I've made this method private and reworked
	 * getAsMarkup().
	 *
	 * @param data The StringBuffer that the result comes back in.
	 * @param theDE The dataelement to start with.
	 * @param id I can't find any use for this parameter in the C++.
	 */
	public void printMarkupData(StringBuffer data, DataElement theDE, int id) {

		TypeObject typeObject = TypeTable.getTypeObject(id);
		// assert(typeObject); in C++
		DataElement theDe = theDE;
		RepeatDE repDe;
		SequenceDE seqDe;
		int repSize = 0;
		Address children = typeObject.getChildren();
		int size = children.size(), counter = 0, repId = 0;

		// In C++, data is a char*, but since strings are immutable
		// in Java, I'm using StringBuffers as a replacement for
		// strcat().
		data.append(typeObject.getBeginTag());
		data.append(" ");
		System.out.print(typeObject.getBeginTag() + " ");
		
		if (typeObject.getFieldType() == Constants.cDeSEQ) {
			seqDe = (SequenceDE) theDE;
			// assert(size = seqDe.size());
			for (counter = 0; counter < size; counter++) {
				repId = children.getElementAt(counter);
				theDe = seqDe.getElementAt(counter);
				printMarkupData(data, theDe, repId);
			}
		}
		else if (typeObject.getFieldType() == Constants.cDeREP) {
			// assert(size ==1);
			repDe = (RepeatDE) theDE;
			repSize = repDe.size();
			for (counter = 0; counter < repSize; counter++) {
				theDe = repDe.getElementAt(counter);
				printMarkupData(data, theDe, children.getElementAt(0));
			}
		}
		else {
			// assert(size==0)
			data.append(theDe.getCharValue());
			data.append(" ");
		}
		data.append(typeObject.getEndTag());
		System.out.println(theDe.getCharValue()+ " " +typeObject.getEndTag());
		data.append(" ");
	}

	/**
	 * Returns this Object as a markup stream.
	 *
	 * @return A string of markup text.
	 */
	public String getAsMarkUp() {
		StringBuffer br = new StringBuffer();
		printMarkupData(br, PickData, 0);
		return br.toString();
	}

	/** Implementation of cloneable interface */
	public Object clone() {
		return new Object();
	}

	/** @see java.lang.String#toString */
	public String toString() {
		String msg  = "";
		SequenceDE pick = (SequenceDE) PickData;

		msg += "PickData:\n";
		//System.out.println(pick.size());
		for (int i = 0; i < pick.size(); i++) {
			DataElement de = pick.getElementAt(i);
			msg += " "+de.toString()+"\n";
		}
		return msg;
	}

	@Override
	public String getCharValue() {
		return this.toString();
	}

	/**
	 * @return the typeTable
	 */
	public TypeTable getTypeTable() {
		return TypeTable;
	}

	/**
	 * @param typeTable the typeTable to set
	 */
	public void setTypeTable(TypeTable typeTable) {
		TypeTable = typeTable;
	}

} /* end class IRObject */
