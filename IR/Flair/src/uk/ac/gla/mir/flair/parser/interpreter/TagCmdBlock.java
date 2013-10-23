package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Department of Computing Science, University of Glasgow
 * @author Joemon M Jose & Anu Joseph
 * @version 1.0
 */


public class TagCmdBlock extends TagCmd{

  public TagCmdBlock(String name, int id, TagCmd t) {
    super(name,id,null);
    this.setNextCmd(t);
  }

  public void  addTagCmd(TagCmd t) {
    TagCmd t2 = this.getNext();
    TagCmd t1;

    while( (t1=t2.getNext()) != null ) {
        if(t == t1) return;
        t2=t1;
    }

    if(t != t2) {
        t2.setNextCmd(t);
        t.setNextCmd(null);
    }
  }
  public TagObject evaluate(char[] data, int pos) {
    return null;
  }
}
