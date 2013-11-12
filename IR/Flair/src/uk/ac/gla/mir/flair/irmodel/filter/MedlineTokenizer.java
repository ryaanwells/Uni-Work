package uk.ac.gla.mir.flair.irmodel.filter;

import java.util.Stack;

import uk.ac.gla.mir.flair.datamodel.dataElement.*;
import uk.ac.gla.mir.flair.util.Assert;

/**
 * Title:        TextTokenize <br/>
 * Description:  A Filter Which Tokenizes a StringDE into a Sequence of StringDE<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author Ryan Wells
 * @version 1.0
 */
public class MedlineTokenizer extends Filter{

	/**
	 * @param de The DataElement to Tokenize
	 * @return Returns a new Sequence Data Element a Strings
	 */
	public DataElement doFilter(DataElement de){
		SequenceDE returnDE = new SequenceDE();
		String s;
		StringDE theDe = null;

		try{
			theDe = (StringDE)de;
		}
		catch(ClassCastException e){
		    Assert.fatal(true, "TextTokenizer : DataElement is not of type StringDE");
		}
		
		s = theDe.getValue();
		// Remove all simple replace characters (ones that are a straight match-and-drop)
		s = s.replaceAll("[!\"#$%&*<=>?@\\\\|~]", "");
		// Trim to reduce char array, may be a minor reduction.
		s.trim();
		
		char[] ca = s.toCharArray();
		
		// Remove all special delim characters that may infringe on other steps.
		for (int i=0; i < ca.length; i++){
			if ( (ca[i] == '.' || ca[i] == ':' ||
					ca[i] == ';' || ca[i] == ',') && 
					(i + 1 == ca.length || 
					(i + 1 < ca.length && ca[i+1] == ' ') ) ){
				ca[i] = ' ';
			}
		}
		
		for (int i=0; i < ca.length; i++){	
			switch (ca[i]){
			case '\'':
				// Remove ' if it is followed by a space
				if ((i-1 >= 0 && ca[i-1] == ' ') || 
						(i+1 < ca.length && ca[i+1] == ' ') ||
						i-1 < 0 || i+1 == ca.length){
					System.out.println("REMOVED");
					ca[i] = ' ';
					continue;
				}
				// Remove 's and 't if they are followed by a space
				if (i+1 < ca.length && (ca[i+1] == 't' || ca[i+1] == 's') && 
						((i+2 < ca.length && ca[i+2] == ' ') || i+2 == ca.length ) ){
					ca[i] = ' ';
					ca[i+1] = ' ';
					continue;
				}
			case '/':
				// Remove / if it is followed by a space
				if (i+1 < ca.length && ca[i+1] == ' '){
					ca[i] = ' ';
					continue;
				}
			case '[': case '(':
				// Find and remove all parenthesis brackets if the have a matching close and 
				// whitespace on the outside.
				if (i == 0 || (i-1 >=0 && ca[i-1] == ' ') ){
					// We don't care for correct ordering of brackets - [ ( ] ) is valid for 
					// all intents and purposes in this process.
					// Count all opening brackets of the same kind as me.
					int bracketCount = 1;
					for (int j=i+1; j < ca.length; j++){
						if ( (ca[i] == '[' && ca[j] == '[' ) || 
								(ca[i] == '(' && ca[j] == '(' ) ){
							// Open bracket found matches me, count it.
							bracketCount++;
						}
						else if ( (ca[i] == '[' && ca[j] == ']') ||
								(ca[i] == '(' && ca[j] == ')' ) ){
							// Closing bracket matches me, update count. 
							bracketCount--;
							// If we've found or partner
							if (bracketCount == 0 && (
									(j + 1 < ca.length && ca[j+1] == ' ')) || 
									(j + 1 == ca.length)){
								// Remove me and my partner
								ca[i] = ' ';
								ca[j] = ' ';
								continue;
							}
						}
					}
					// If reached here then:
					// No match found, treat as a normal character
				}
				continue;
			}
		}
		
		// Remove all extraneous whitespace and split down all whitespace.
		// We use trim so we don't have whitespace in our result from the beginning or
		// end of the character array.
		String[] result = new String(ca).trim().split("\\s+");
		
		for (String res: result){
			returnDE.add(new StringDE(res.toLowerCase()));
		}
		
		return returnDE;		
		
	 }	
	
	public String getName(){
		return "irmodel.filter.MedlineTokenizer";
	}
}
