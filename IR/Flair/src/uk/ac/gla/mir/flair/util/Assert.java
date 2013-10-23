package uk.ac.gla.mir.flair.util;

/**
 * Title:        Assertion Printer<br/>
 * Description:  Prints to the command line given messages.<br/>
 * Copyright:    Copyright (c) 2002<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author Joemon M Jose & Anu Joseph
 * @version 1.0
 */
public class Assert {

	/**
	 * Prints <code>message</code> to command line as a informative message 
	 * if <code>b</code> is false.
	 * @param b Condition to base printing of message on
	 * @param message String to print
	 */
	public static void info(boolean b, String message){
		if(b)
			output(message, "Info");
	}
	
	/**
	 * Prints <code>message</code> to command line as a warning 
	 * if <code>b</code> is false.
	 * @param b Condition to base printing of message on
	 * @param message String to print
	 */
	public static void warn(boolean b, String message) {
		if (b)
			output(message, "warning");
	}

	/**
	 * Prints <code>message</code> to command line as a debug output 
	 * if <code>b</code> is false.
	 * @param b Condition to base printing of message on
	 * @param msg String to print
	 */
	public static void debug(boolean b, String msg) {
		if (b) {
			output(msg, "debug");
			System.out.flush();
			Thread.dumpStack();
		}
	}

	/**
	 * Prints <code>message</code> to command line as a fatal condition
	 * if <code>b</code> is false.
	 * @param b Condition to base printing of message on
	 * @param msg String to print
	 */
	public static void fatal(boolean b, String msg) {
		if (b) {
			output(msg, "fatal");
			Thread.dumpStack();
			System.out.flush();
			System.exit(1);
		}
	}

	/**
	 * Prints <code>message</code> to command line as a fatal condition
	 * if <code>b</code> is false.
	 * @param b Condition to base printing of message on
	 * @param msg String to print
	 */
	public static void warnFatal(boolean b, String msg) {
		if (b) {
			output(msg, "Warning");
			System.exit(1);
		}
	}
	
	/**
	 * Prints <code>msg</code> to command line as type <code>type</code>.
	 * @param type Label for printed message
	 * @param msg Message to print
	 */
	protected synchronized static void output(String msg, String type) {
		msg = msg.replaceAll("\n", "\n* ");
		System.out.println(
			"**********\n* Assertion "
				+ type
				+ ": \n* "
				+ msg
				+ "\n**********");
		System.out.flush();
	}
}
