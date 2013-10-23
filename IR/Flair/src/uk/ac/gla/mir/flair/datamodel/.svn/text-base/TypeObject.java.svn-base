package uk.ac.gla.mir.flair.datamodel;

import uk.ac.gla.mir.flair.datamodel.dataElement.*;

/**
 * A class that represents a tag in the specification file.
 * @author  $Author: stewarcd $
 * @version  $Id: TypeObject.java,v 1.2 2006/07/03 16:32:15 stewarcd Exp $
 */
public class TypeObject{

	private int FieldId;
	private int ParentId;
	private String FieldName;
	public String TagName;
	private int FieldType;
	private String CmdType;

	private Address Children;
	private Address Address;

	private DataElement Clone;


	public TypeObject() {
		Children = new Address();
	}

	/**
	 * Full-arg Constructor.
	 *
	 * @param fid The Field ID
	 * @param pid The Parent ID
	 * @param cmd The Command Type?
	 * @param fname The Field Name
	 * @param tagName The tag name
	 * @param ftype The type of the field
	 * @param context The address.
	 * @param de The DataElement
	 */
	public TypeObject(int fid, int pid, String cmd, String fname, String tagName, int ftype, Address context, DataElement de) {
		FieldId = fid;
		ParentId = pid;
		FieldType = ftype;
		TagName = tagName;
		FieldName = fname;
		CmdType = cmd;

		Children = new Address();
		for (int i = 0; i < context.size(); i++)
			Children.add(context.getElementAt(i));
		Address = new Address();
		Clone = (DataElement) de.clone();
		Clone.setId(fid);
	}

	/**
	 * Sets the FieldId.
	 * @param id  The new ID to set.
	 */
	public void setFieldId(int id) {
		FieldId = id;
		Clone.setId(id);
	}

	/**
	 * Sets the Parent ID.
	 * @param id  The id of the parent.
	 */
	public void setParentId(int id) {
		ParentId = id;
	}

	/**
	 * Sets the field type value.
	 * @param type  The new type to set.
	 */
	protected void setFieldType(int type) {
		FieldType = type;
	}

	/**
	 * Sets the fieldType with a string.
	 *
	 * @param type The string type.
	 */
	public void setFieldType( String type ) {
		try {
			Class.forName(type);
			Clone = (DataElement) Class.forName( type).newInstance();
			Clone.setId(FieldId);
		}
		catch (ClassNotFoundException e) {
			System.out.println("TypeObject.setFieldType(): could not load class " + type);
			e.printStackTrace();
			System.exit(1);
		}
		catch (Exception e) {
			System.out.println("TypeObject.setFieldType(): Other exception caught: " + e);
			e.printStackTrace();
			System.exit(1);
		}

		// see DataModel.dataElement.Constants for the classname array
		for (int i = 0; i < Constants.classnames.length; i++) {
			if (type.equals(Constants.classnames[i])) {
				setFieldType(1000 + i);
			}
		}

	}

	/**
	 * Sets the Address of this TypeObject
	 * @param add  The new Address.
	 */
	public void setAddress(Address add) {
		Address = add;
	}

	/**
	 * Sets the Field name for this object.
	 * @param fname  The new field name.
	 */
	public void setFieldName(String fname) {
		FieldName = fname;
	}

	/**
	 * Sets the TagName for this object.
	 * @param tag  The new TagName.
	 */
	public void setTagName(String tag) {
		TagName = tag;
	}

	/**
	 * Sets the command type for this object.
	 * @param cmd  The new command type.
	 */
	public void setCmdType(String cmd) {
		CmdType = cmd;
	}

	/**
	 * Sets the Children property.
	 * @param ch  The new Children address
	 */
	public void setChildren(Address ch) {
		Children = ch;
	}

	/* ********** Getters ********** */

	/**
	 * Gets the Field ID for this TypeObject.
	 */
	public int getFieldId() {
		return FieldId;
	}

	/**
	 * Gets the Parent Id for the TypeObject
	 */
	public int getParentId() {
		return ParentId;
	}

	/**
	 * Gets the FieldType of this TypeObject
	 */
	public int getFieldType() {
		return FieldType;
	}

	/**
	 * Gets the Tag Name of this TypeObject
	 */
	public String getTagName() {
		return TagName;
	}

	/** 
	 * Getst the name of this Type Object
	 */
	public String getName() {
		return FieldName;
	}

	/**
	 * Gets the Address of the Children of this object
	 */
	public Address getChildren() {
		return Children;
	}

	/**
	 * Gets the Address of this Type Object
	 */
	public Address getAddress() {
		return Address;
	}

	/**
	 * Gets the Command Type of this Type Object
	 */
	public String getCmdType() {
		return CmdType;
	}

	/** 
	 * Clones this TypeObject
	 */
	public Object clone() {
		TypeObject tmp = new TypeObject(this.FieldId,this.ParentId,
										this.CmdType,this.FieldName,
										this.TagName,this.FieldType,
										this.Children,this.Clone);
		return tmp;
	}

	/** 
	 * Gets the Start Tag of this Type Object
	 */
	public String getBeginTag() {
		return "<" + TagName + ">";
	}

	/** 
	 * Gets the End Tag of this Type Object
	 */
	public String getEndTag() {
		return "</" + TagName + ">";
	}

	/**
	 * Returns a data element that is appropriate to the
	 * type of this TypeObject.
	 *
	 * @return a DataElement
	 */
	public DataElement getDataElementRepresentation() {
		return (DataElement) Clone.clone();
	}

	/** @see java.lang.Object#toString() */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("TypeObject\n----------\n");
		sb.append("FieldID: " + FieldId);
		sb.append(", ");
		sb.append("ParentId:" + ParentId);
		sb.append(", ");
		sb.append("FieldType:" + FieldType);
		sb.append(", ");
		sb.append("TagName:" + TagName);
		sb.append("\n");
		sb.append("FieldName:" + FieldName);
		sb.append(", ");
		sb.append("CmdType:" + CmdType);
		sb.append(", ");
		sb.append("Children: ");
		if (Children != null)
			sb.append(Children.toString());
		else
			sb.append("No children\n");

		return sb.toString();
	}
} /* end class TypeObject */
