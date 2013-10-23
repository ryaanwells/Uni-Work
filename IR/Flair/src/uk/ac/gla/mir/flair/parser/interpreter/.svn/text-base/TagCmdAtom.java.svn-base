package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Department of Computing Science, University of Glasgow
 * @author Joemon M Jose & Anu Joseph
 * @version 1.0
 */

public class TagCmdAtom extends TagCmd{
   boolean  flag=true;
  public TagCmdAtom() {
  }

  public TagCmdAtom(String name, int id, TagProcessor p) {
    super(name,id,p);
  }

  public TagObject evaluate(char[] data, int pos) {

    //Debug.warn(flag, data);
    TagObject to = nextChunk(data,pos);
    if (to.type != Constants.cBgnTag) {
       Debug.warn(flag,"in Evaluate :: TagCmdAtom");
       Debug.warn(flag,new String(to.buf) + to.pos);     
       return null;
    }


    String str1=asTagName(to.buf);
    String str2 = getName();
    if( !(str1.equals(str2) )) {
        Debug.warn(flag,str1 + "  " +str2 );
        to.success = false;
        return to; // for some other tag
    }

    _TagProcessor.DoBgnProcess(_Id," Begin Process some data later",this);

    to = nextChunk(data,to.pos);

    if (to == null) return null;

    if (to.type == Constants.cData) {
        _TagProcessor.DoProcessTag(_Id, new String(to.buf),this);
        to = nextChunk(data,to.pos);
    } else {
        Debug.warn(flag,"TagCmdAtom:in Evaluate :: Data Not returned.");
    }

    if (to.type != Constants.cEndTag) return null;
    to.setBool(true);
    return to;
  }

}
