package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Department of Computing Science, University of Glasgow
 * @author Joemon M Jose & Anu Joseph
 * @version 1.0
 */


public class Debug {

  public static void info(String msg, String position) {
    if(Constants.globalInfo)  {
        output(msg + "**" + position, "info");
    }
  }

  public static void warn(boolean b, String message) {
	if(b)
	    output(message, "warning");
  }

  public static void debug(boolean b, String msg) {
	if(b) {
	    output(msg, "debug");
	    Thread.dumpStack();
	}
  }

  public static void fatal(boolean b, String msg) {
	if(b) {
	    output(msg, "fatal");
	    Thread.dumpStack();
	    System.exit(1);
	}
  }


  protected static void output(String msg, String type) {
	System.out.println("**********\n* Assertion " + type + ": \n* " +
			       msg + "\n**********");
	System.out.flush();
  }
}
