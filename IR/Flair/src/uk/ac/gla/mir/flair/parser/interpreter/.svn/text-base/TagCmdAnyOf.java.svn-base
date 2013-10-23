package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Department of Computing Science, University of Glasgow
 * @author Joemon M Jose & Anu Joseph
 * @version 1.0
 */

public class TagCmdAnyOf extends TagCmdBlock{

  public TagCmdAnyOf(TagCmd[] args) {
    super(Constants.cTagCmdNoName,Constants.cIdTagCmdBlock,args[0]);
    int len = args.length;
    TagCmd t = this;
    for(int i=0; i< len; i++) {
        t.setNextCmd(args[i]);
        t = args[i];
    }
    t.setNextCmd(null);
  }

  public TagObject evaluate(char[] data, int pos) {

    TagObject to = nextChunk(data,pos);
    if(to == null) {
        Debug.fatal(Constants.globalInfo,"In TagCmdAnyOf:evaluate: TO null");
    }
    if( (to.type == Constants.cEOFS) || (to.type == Constants.cEOF)) {
        to.success = false;
        return to;
    }
    if (to.type != Constants.cBgnTag) {
      if(to.type == Constants.cEndTag) {
         to.success = false;
         to.setPos(pos);
         return to;
      }  else {
         Debug.warn(Constants.globalInfo,"in Evaluate :: TagCmdAnyOf");
         Debug.warn(flag,new String(to.buf) + to.pos +"  original position " + pos);
         return null;
       }
    }
    TagCmd t = this.getNext();
    while(t != null) {
        System.out.println(t.getName() + " psotion " + pos);
        to =  t.evaluate(data,pos);
        if (to == null) {
            Debug.warn(flag,"TagCmdAnyOf:evaluate :: null evaluation");
            return null;
        }
        if(to.success) return to;
        else {
            t = t.getNext();
        }
    }
    Debug.warn(flag,"TagCmdAnyOf:evaluate :: didn't match any tags!!");
    to.success = false;
    return to;
  }
}
