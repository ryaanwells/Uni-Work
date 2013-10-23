package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Department of Computing Science, University of Glasgow
 * @author Joemon M Jose & Anu Joseph
 * @version 1.0
 */



public class TagCmdSeries extends TagCmdBlock{

  public TagCmdSeries(TagCmd[] args) {
    super(Constants.cTagCmdNoName,Constants.cIdTagCmdBlock,args[0]);
    int len = args.length;
    TagCmd t = this;
    for(int i=0; i< len; i++) {
        t.setNextCmd(args[i]);
        t = args[i];
    }
  }

  public TagObject evaluate(char[] data,int pos) {

    TagObject to = new TagObject();
    to.setBuf(data,pos);
    System.out.println("Series Tag List \n" );
    for(TagCmd t = this.getNext(); t != null; t = t.getNext()) {
        System.out.println(t.getName() + " ..... ");
    }
    for(TagCmd t = this.getNext(); t != null; t = t.getNext()) {
        System.out.println(t.getName() + " TagCmdSeries:evaluate ::  " + to.pos);
        to =  t.evaluate(to.originalbuf,to.pos);
        if (to == null) {
        	Debug.warn(flag,"TagCmdSeries:evaluate :: null evaluation");
        }
    }

    return to;
  }
}
