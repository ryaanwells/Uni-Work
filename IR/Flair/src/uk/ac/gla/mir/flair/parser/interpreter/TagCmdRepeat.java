package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers Description: Copyright:    Copyright (c) 2002 Company:      Department of Computing Science, University of Glasgow
 * @author  Joemon M Jose & Anu Joseph
 * @version  1.0
 */


public class TagCmdRepeat extends TagCmd{
  private TagCmd _Repeat;
  public TagCmdRepeat(TagCmd t,TagProcessor p) {
    super(t.getName(),Constants.cIdTagCmd,p);
    _Repeat = t;
  }

  public TagObject evaluate(char[] data, int pos) {
    Debug.info("In TagCmdRepeat:evaluate: ","..."+pos);
    TagObject to = _Repeat.evaluate(data,pos);
    if(to == null) {
        Debug.fatal(Constants.globalInfo,"In TagCmdRepeat:evaluate: TO NULL ");
    }
    while (to.success) {
        Debug.info("In TagCmdRepeat:evaluate: ","..."+to.pos);
        to = _Repeat.evaluate(to.originalbuf,to.pos);
    }
    return to;
  }
}
