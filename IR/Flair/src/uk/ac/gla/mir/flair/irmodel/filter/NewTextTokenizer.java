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
public class NewTextTokenizer extends Filter{

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
		    Assert.fatal(true, "NewTextTokenizer : DataElement is not of type StringDE");
		}
		
		s = theDe.getValue();
		s.trim();
		
		String[] newText = s.split("\\s+");
		for( int i = 0; i < newText.length; i++){
			if( newText[i].length() > 0 ){
				final DataElement token = new StringDE(newText[i].toLowerCase());
				returnDE.add(token);
			}
		}

		return returnDE;
	 }	
	
	public String getName(){
		return "irmodel.filter.NewTextTokenizer";
	}
}
