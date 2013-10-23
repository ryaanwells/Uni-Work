package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Department of Computing Science, University of Glasgow
 * @author Joemon M Jose & Anu Joseph
 * @version 1.0
 */


public  class TagProcessor {

    public TagProcessor() {

    }

    public void  DoBgnProcess(int id, String data, TagCmd cmd) {
        Debug. info("... Begin Tag "+id+"...  ",data);
    }
    public  void DoProcessTag(int id, String data, TagCmd cmd) {
        Debug.info(" ... Process Data "+id+"...  ",data);
    }
    public void  DoEndProcess(int id, String data, TagCmd cmd) {
        Debug.info("....  End Tag "+id+"...  ",data);
    }
} // end class    TagProcessor
