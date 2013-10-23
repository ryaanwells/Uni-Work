package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers 
 * Description: 
 * Copyright:    Copyright (c) 2002 
 * Company:      Department of Computing Science, University of Glasgow
 * @author  Joemon M Jose & Anu Joseph
 * @version  1.0
 */

public abstract class TagCmd {
	
  boolean  flag=true;
  public AttributeList _AtList;
  public String _Name;
  int _Id;
  public TagProcessor _TagProcessor;

  private TagCmd _NextCmd;
  private int _TagListNextSlot=0;

  public TagCmd() {
    _AtList = new AttributeList();
  }

  public TagCmd(String name, int id, TagProcessor p) {
    _Name = name.trim();
    _Id = id;
    _TagProcessor = p;
    _AtList = new AttributeList();
    _NextCmd = null;
  }

  public TagObject evaluate(char[] data, int pos) {
    Debug.warn(flag,"in Evaluate :: TagCmd");
    //Debug.warn(flag, data);
    return null;
  }
  public void add(TagCmd p) { }
  public void resetTagProcessor(TagProcessor p ) {
    _TagProcessor =p;
  }
  public AttributeList getAttributeList()  {
    return   _AtList;
  }

  public void setName(String name) {
    _Name = name;
  }
  public String getName() {
    return _Name;
  }
  public int getId() {
    return _Id;
  }

  public void setNextCmd(TagCmd p) {
    _NextCmd = p;
  }
  public TagCmd getNext() {
    return _NextCmd;
  }

  public void addTagCmd(TagCmd t) {

  }

  public boolean isInTagList(TagCmd t) {
    return false;     // wrong!!
  }
  //Managing AttributeList
  void parseBgnTag(String data) { }
  void resetAttributeList() { }
  void addAttributeData(String name, String value) {
    _AtList.setAttributeNameAndValue(name,value);
  }
  void initAttributeList() {
    _AtList = new AttributeList();
  }
  void clearAttributeList() {
    //?
  }

  //Debugging
  public void displayAttributeList() {
    _AtList.display();
  }
  
  /**
  * This will return next chunk of data. The data returned is placed in the#
  * TagObject.
  * @param data a character array
  * @return TagObject.
  */
  public TagObject nextChunk(char[] data, int pos) {
    bufObject bo = nextBlock(data,pos);
    TagObject to = new TagObject();
    to.setData(data,bo.buf,bo.pos,bo.type);
    return to;
  }
  /**
  * This block returns a block beteween < >  or before <
  */
  public bufObject nextBlock(char[] data, int pos) {
    char[] copybuf =null;
    int ln = data.length;
    int type = 0;
    int i=0,j=0;
    int newpos=0;

    System.out.println(" position " + pos + "  buf length " + ln);
    if( pos >= ln) return new bufObject(copybuf,pos,Constants.cEOFS);

    if (data[pos] == '<') {  // it can be a tag - search until next >
       if(data[pos+1] == '/') type = Constants.cEndTag;
       else type = Constants.cBgnTag;
       int p= pos;
       while( (data[p] != '>') && (p < ln)  ) p++;
        newpos = p ;
        copybuf = new char[newpos-pos+1];
        for(i=pos; i<=newpos; i++) copybuf[j++] = data[i];
        return new bufObject(copybuf,newpos+1,type);
    }  else {    // it must be data
        type = Constants.cData;
        int p= pos;
        while( (data[p] != '<') && (p < ln)  ) p++;
        newpos = p;   // before <
        copybuf = new char[newpos-pos+1];
        for(i=pos; i< newpos; i++) copybuf[j++] = data[i];
        return new bufObject(copybuf,newpos,type);
    }
    //return null;
  }

  // Just to transfer data;
  class bufObject{
    char[] buf;
    int pos;
    int type = 0;
    public bufObject(char[] d, int p, int t) {
      buf = d;
      pos=p;
      type = t;
    }
  }    /* end of bufObject */
  /**
  * given a begin tag or end tag it returns its name.
  * it assumes that the input is in a format like <name> or </name>
  * or <name x=? y=? >
  * @param data input character array
  * @return String
  */
  public String asTagName(char[] data) {
    int ln = data.length;
    char[] copy = new char[ln];
   // for(int i=0;i<data.length;i++)
     // System.out.println("data = "+data[i]+" "+copy[i]);

    char c;
    int i=0, j=0;
    // A dumb check

    if(data[0] != '<' ) Debug.warn(flag,"TagCmd: asTagName: Input format error: not starting with '<' ");
    if((data[i] == '<') && (data[i+1] == '/')) i += 2;
    if(data[i] == '<')  i++;
    if(data[i] == ' ')  i++;
    while(i < ln) {
        c=data[i++];
        if( ( c== ' ')  || (c == '>')) break;
        copy[j++] = c;
    }  // end of while
     while(j < ln) {
       // System.out.print((byte)copy[j]);
        copy[j++] = ' ';
     }
  //  System.out.println("len = "+copy.length);

    String x =new String(copy);
   // System.out.println(" trimmed "+x.trim());
   // System.out.println("len = "+copy.length);
    return   x.trim();

  }  // end of asTagName

}  /* end class TagCmd */
