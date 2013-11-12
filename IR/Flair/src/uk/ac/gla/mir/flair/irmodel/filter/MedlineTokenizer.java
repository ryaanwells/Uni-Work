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
		s.trim();
		char[] ca = s.toCharArray();
		
		for (int i=0; i < ca.length; i++){	
			switch (ca[i]){
			case '!': case '"': case '#': case '$': case '%':
			case '&': case '*': case '<': case '=': case '>':
			case '?': case '@': case '\\':case '|': case '~':
				ca[i] = ' ';
				continue;
			case '.': case ':': case ';': case ',':
				if ((i+1 < ca.length && ca[i+1] == ' ') ||
						(i+1 == ca.length)){
					ca[i] = ' ';
				}
				continue;
			case '\'':
				if ((i-1 >= 0 && ca[i-1] == ' ') || 
						(i+1 < ca.length && ca[i+1] == ' ') ||
						i-1 < 0 || i+1 == ca.length){
					ca[i] = ' ';
					continue;
				}
				if (i+1 < ca.length && (ca[i+1] == 't' || ca[i+1] == 's')){
					ca[i] = ' ';
					continue;
				}
			case '/':
				if (i+1 < ca.length && ca[i+1] == ' '){
					ca[i] = ' ';
					continue;
				}
			case '[': case '(':
				if (i == 0 || (i-1 >=0 && ca[i-1] == ' ') ){
					Stack<Character> stack = new Stack<Character>();
					for (int j=i+1; j < ca.length; j++){
						if (ca[j] == '[' || ca[j] == '('){
							stack.push(ca[j]);
						}
						else if (ca[j] == ']' || ca[j] == ')'){
							if (stack.peek() == ca[j]){
								stack.pop();
								if (stack.isEmpty() && ca[i] == ca[j] && (
										(j + 1 < ca.length && ca[j+1] == ' ')) || 
										(j +1 == ca.length)){
									ca[i] = ' ';
									ca[j] = ' ';
									continue;
								}
							}
							else { // mismatch in bracket ordering, ignore this bracket
								continue;
							}
						}
					}
					// no matching bracket found, remove this bracket
					ca[i] = ' ';
					continue;
				}
			}
		}
		
		String[] result = new String(ca).split("\\s");
		
		for (String res: result){
			returnDE.add(new StringDE(res.toLowerCase()));
		}
		
		return returnDE;
		
	 }	
	
	public String getName(){
		return "irmodel.filter.MedlineTokenizer";
	}
}
