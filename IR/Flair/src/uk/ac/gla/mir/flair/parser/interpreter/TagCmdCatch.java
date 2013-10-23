package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Department of Computing Science, University of Glasgow
 * @author Joemon M Jose & Anu Joseph
 * @version 1.0
 */

public class TagCmdCatch extends TagCmd {

  public TagCmdCatch(String name, int id, TagProcessor p) {
    super(name,id,p);
  }

  public TagObject evaluate(char[] data, int pos) {
    TagObject to = nextChunk(data,pos);

    if (to.type != Constants.cBgnTag) {
       Debug.warn(flag,"in Evaluate :: TagCmdAtom");
       return null;
    }

    if(!asTagName(to.buf).equals(getName())) return null; // for some other tag

    _TagProcessor.DoBgnProcess(_Id," some data late",this);

    to = nextChunk(data,to.pos);

    while ((true) && (to != null)){

        if((asTagName(to.buf)).equals(getName())) break;
        to = nextChunk(data,to.pos);
    }
    if(to != null) to.setBool(true);
    else return null;
    return to;
  }
}
