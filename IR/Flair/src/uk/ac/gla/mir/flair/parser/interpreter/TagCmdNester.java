package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers Description: Copyright:    Copyright (c) 2002 Company:      Department of Computing Science, University of Glasgow
 * @author  Joemon M Jose & Anu Joseph
 * @version  1.0
 */

public class TagCmdNester extends TagCmd {

  private TagCmd _Nester;

  public TagCmdNester(String name, int id, TagProcessor p, TagCmd t) {
    super(name,id,p);
    _Nester = t;
  }

  public TagObject evaluate(char[] data, int pos) {
    Debug.info("In TagCmdNester:evaluate: " + getName(),"..."+pos);
    TagObject to = nextChunk(data,pos);
    if(to == null) {
        Debug.fatal(Constants.globalInfo,"In TagCmdNester:evaluate: TO null");
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
         Debug.warn(Constants.globalInfo,"in Evaluate :: TagCmdNester");
         Debug.warn(flag,new String(to.buf) + to.pos +"  original position " + pos);
         return null;
       }
    }
    String str1, str2;
    str1 = asTagName(to.buf);
    str2 = getName();
    str1 = str1.trim();

    if( !(str1.equals(str2) )) {
        Debug.info(str1,str2 );
        return null; // for some other tag
    }
    _TagProcessor.DoBgnProcess(_Id,Constants.cNoData,this);

    to = _Nester.evaluate(to.originalbuf,to.pos);

    _TagProcessor.DoProcessTag(_Id,"In Nester " +Constants.cNoData,this);

    to = nextChunk(data,to.pos);
    if (to == null) return null;
    if (to.type != Constants.cEndTag) {
      return null;
    } else {
      to.setBool(true);
    }
    return to;

  }
}
