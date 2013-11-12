package uk.ac.gla.mir.flair.irmodel.filter;

import uk.ac.gla.mir.flair.datamodel.dataElement.*;
import uk.ac.gla.mir.flair.util.Assert;

/**
 * Title:        TextTokenize <br/>
 * Description:  A Filter Which Tokenizes a StringDE into a Sequence of StringDE<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class TextTokenizer extends Filter{

	/**
	 * @param de The DataElement to Tokenize
	 * @return Returns a new Sequence Data Element a Strings
	 */
	public DataElement doFilter(DataElement de){
		SequenceDE returnDE = new SequenceDE();
		String s;
		StringDE theDe = null;

		try{
			theDe = (StringDE)de;}
		catch(ClassCastException e){
		    Assert.fatal(true, "TextTokenizer : DataElement is not of type StringDE");
		}
		
		s = theDe.getValue();
		s.trim();

		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < s.length(); i++) {
		    char ch = s.charAt(i);
		    if(Character.isLetterOrDigit(ch))
		    	sb.append(ch);
		    else { // Finalize StringBuffer on whitespace
		    	String tok = sb.toString().trim();
		    	if(tok.length() > 0) {
		    		System.out.println(tok);
		    		final DataElement token = new StringDE(tok.toLowerCase());
		    		returnDE.add(token);
		    		sb = new StringBuffer();
		    	}
		    }
		}

		// Need to finalize last StringBuffer
		String tok = sb.toString().trim();
		if(tok.length() > 0) {
			System.out.println(tok);
			final DataElement token = new StringDE(tok.toLowerCase());
    		returnDE.add(token);
    		sb = new StringBuffer();
		}

		return returnDE;
	 }	
	
	public String getName(){
		return "irmodel.filter.TextTokenizer";
	}
}
