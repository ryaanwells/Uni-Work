package uk.ac.gla.mir.flair.datamodel.dataElement;
import java.util.Iterator;

import uk.ac.gla.mir.flair.datamodel.AttributeList;
import uk.ac.gla.mir.flair.datamodel.TypeObject;
import uk.ac.gla.mir.flair.datamodel.TypeTable;
import uk.ac.gla.mir.flair.util.Assert;

/**
 * The abstract base class for all the types in the Flair document model package.
 * DataElements in Flair essentially form a tree where there are atomic DataElements (StringDE, IntegerDE, etc.)
 * and structural DataElements (RepeatDE, SequenceDE).
 * @author  $Author: stewarcd $
 * @version  $Id: DataElement.java,v 1.1 2006/06/21 15:02:33 stewarcd Exp $
 */
public abstract class DataElement implements Cloneable{

    private int id;
    private AttributeList attributes;
    
     /**
	 * Sets the ID.
	 * @param id  The ID to set.
	 */
    public void setId(int id) { this.id = id; }

    /**
	 * Gets the ID.
	 * @return  the ID of this datamodel.dataElement
	 */
    public int getId() { return id; }

    /**
     * Returns the tag, which is a type descriptor for types in
     * the Document Model.
     *
     * @return An int which should be compared to the constants
     * declared in the interface DocumentModel.Constants.
     * @see Constants
     */
    public int getTag() {
    	return Constants.cDeDAT;
    }

    /**
     * Returns the child element at the given index.
     *
     * @param at The index to retrieve from.
     * @return The DataElement in question, or null
     * if index is not valid.
     */
    public DataElement getElementAt(int at){
    	return this;
    }

    /**
     * All subclasses of DataElement should be Cloneable
     *
     */
    public abstract Object clone();


    /**
     * Sets a string value.  Currently unimplemented.
     *
     * @param str The string to set.
     */
    public void setValue(String str)
    {
    	Assert.warn(true, "Calling DataElement.setValue(). Could be bad.");
    }


    /**
     * Returns a char value. <b>Will return null</B>.
     *
     * @return Null. Always.
     */
    public abstract String getCharValue();
    /*{
    	Assert.warn(true, "Calling DataElement.getCharValue().  This could be bad.");
    	return null;
    }*/

    /**
     * Adds a data element to this.  Unimplemented.
     *
     * @param elem The DataElement to add.
     */
    public void add(DataElement elem)
    {
    	Assert.warn(true, "Calling DataElement.Add().  Maybe a bad idea.");
    }

    /**
     * Adds an Attribute to the Attribute List
     * @param name The name of the attribute to add
     * @param value The value of the attribute to add
     */
    public void addAttribute(String name, String value){
    	attributes.setAttributeNameAndValue(name, value);
    }
    
    /**
     * Gets the Attribute List of this DataElement
     * @return The attribute list
     */
    public AttributeList getAttributeList(){
    	return attributes;
    }

	/**
	 * Test Method to Display The Contents of a DataElement
	 * @param dataElement
	 * @param tab Number of Spaces for an Indent, 0
	 * @param tt The TypeTable of the DataElement
	 */
	public static void printDataElement(DataElement dataElement, int tab, TypeTable tt){
			
		final String typeName = dataElement.getClass().getName();
		final int id = dataElement.getId();
		final TypeObject currentType = tt.getTypeObject(id);
		int fieldID = currentType.getFieldType();
		for(int i = 0; i < tab; i++)
			System.out.print(" ");
		
		if( tab != 0)
			System.out.print(currentType.getBeginTag() + " " );
		else 
			System.out.print("<ROOT>");
		if( fieldID ==  Constants.cDeSEQ  ){ // "datamodel.dataElement.SequenceDE" )){
			
			Iterator<DataElement> sequenceElements = ((SequenceDE)dataElement).iterator();
			System.out.println();
			while(sequenceElements.hasNext())
				printDataElement(	sequenceElements.next(), tab+5, tt);
			
			for(int i = 0; i < tab; i++)
				System.out.print(" ");
			
			if( tab != 0)
				System.out.println( currentType.getEndTag());
			else
				System.out.println( "</ROOT> ");
			
		}else if (  fieldID == Constants.cDeREP  ){
			System.out.println();
			Iterator<DataElement> sequenceElements = ((RepeatDE)dataElement).iterator();
			while(sequenceElements.hasNext())
				printDataElement(	sequenceElements.next(), tab+5, tt);
			
			for(int i = 0; i < tab; i++)
				System.out.print(" ");
			
			if( tab != 0)
				System.out.println( currentType.getEndTag());
			else
				System.out.println( "</ROOT> ");
			
		}else if (  fieldID ==  Constants.cDeSTR  ){
			System.out.print(dataElement.toString()+" ");
			System.out.println( currentType.getEndTag());
		}else if (  fieldID ==  Constants.cDeINT ){
			System.out.print(dataElement.toString()+" ");
			System.out.println( currentType.getEndTag());
		}else if (  fieldID ==  Constants.cDePNT ){
			System.out.print(dataElement.toString()+" ");
			System.out.println( currentType.getEndTag());
		}
	}
	
	public static void printDataElement2(DataElement dataElement, int tab, TypeTable tt){
		
		final String typeName = dataElement.getClass().getName();
		final int id = dataElement.getId();
		final TypeObject currentType = tt.getTypeObject(id);
		int fieldID = currentType.getFieldType();
		for(int i = 0; i < tab; i++)
			System.out.print(" ");
		
		System.out.print(currentType.getBeginTag() + " " );
		if(  fieldID ==  Constants.cDeSEQ ){
			
			Iterator<DataElement> sequenceElements = ((SequenceDE)dataElement).iterator();
			System.out.println();
			while(sequenceElements.hasNext())
				printDataElement2(	sequenceElements.next(), tab+5, tt);
			
			for(int i = 0; i < tab; i++)
				System.out.print(" ");
			
			if( tab != 0)
				System.out.println( currentType.getEndTag());
			else
				System.out.println( "</ROOT> ");
			
		}else if ( fieldID ==  Constants.cDeREP ){
			System.out.println();
			Iterator<DataElement> sequenceElements = ((RepeatDE)dataElement).iterator();
			while(sequenceElements.hasNext())
				printDataElement2(	sequenceElements.next(), tab+5, tt);
			
			for(int i = 0; i < tab; i++)
				System.out.print(" ");
			
			System.out.println( currentType.getEndTag());
			
		}else if ( fieldID == Constants.cDeSTR ){
			System.out.print(dataElement.toString()+" ");
			System.out.println( currentType.getEndTag());
		}else if ( fieldID ==  Constants.cDeINT ){
			System.out.print(dataElement.toString()+" ");
			System.out.println( currentType.getEndTag());
		}else if ( fieldID == Constants.cDePNT ){
			System.out.print(dataElement.toString()+" ");
			System.out.println( currentType.getEndTag());
		}
	}

} /* end class DataElement */
