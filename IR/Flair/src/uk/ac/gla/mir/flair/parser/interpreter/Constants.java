package uk.ac.gla.mir.flair.parser.interpreter;

/**
 * Title:        General Purpose Parsers
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Department of Computing Science, University of Glasgow
 * @author Joemon M Jose & Anu Joseph
 * @version 1.0
 */


public class Constants {
    public static boolean globalInfo = true;

    public static final int cBgnTag = 0 ;
    public static final int cEndTag = 1 ;
    public static final int cData = 2 ;
    public static final int cComment = 3 ;
    public static final int cEOF = 4 ;
    public static final int cEOFS = 5 ;

    public static final int cIdTagCmdBlock = - 1 ;
    public static final int cIdTagCmd = - 1 ;
    public static final String cTagCmdNoName = "No Name" ;
    public static final String cNoData = "null" ;

    public static final String cmd_typeLabel = "cmd_type";
    public static final String cmd_typePlain = "Plain";
    public static final String cmd_typeSeries = "Series";
    public static final String cmd_typeRepeat = "Repeat";
	public static final String cmd_typeAnyOf = "AnyOf";

}
