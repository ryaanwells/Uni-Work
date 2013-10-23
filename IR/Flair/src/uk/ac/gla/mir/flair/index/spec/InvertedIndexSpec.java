package uk.ac.gla.mir.flair.index.spec;

import gnu.trove.TIntArrayList;
import uk.ac.gla.mir.flair.datamodel.AttributeList;
import uk.ac.gla.mir.flair.index.TextInvertedIndex;
import uk.ac.gla.mir.flair.util.Assert;

/**
 * Title:        InvertedIndexSpec <br/>
 * Description:  Contains All the information about ab InvertedIndex<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class InvertedIndexSpec extends Spec{
    
    /** A Reference to the actual Inverted Index, Created By The Indexer **/
    private TextInvertedIndex invertedIndex;
    
    /**
     * 
     */
    public InvertedIndexSpec(){
    	stopType = false;
    	dataElementIDs = new TIntArrayList();
    }
    
	/**
	 * Sets the instance variables in this class using an Attribute List
	 * @param attributes The attribute list to get the data
	 */
	public void setAttributes( AttributeList attributes ){
		this.attributes = attributes;
		//System.out.println(attributes);
		indexName = attributes.getAttributeForName("invname");
		if(indexName != null && indexName.trim().equalsIgnoreCase("")){
			Assert.warnFatal(true, attributes.getAttributeForName("invname") + " not valid Index Name.");
		}else if(indexName == null){
			Assert.warnFatal(true, attributes.getAttributeForName("invname") + " not valid Index Name.");
		}
		tokenType = attributes.getAttributeForName("tokentype");
		if(tokenType == null)
			Assert.warnFatal(true, attributes.getAttributeForName("tokentype") + " not valid Token Type Identifier.");
	
		stemType = attributes.getAttributeForName("stemtype");
		stopType =  ( attributes.getAttributeForName("stoptype") == null ) 
						? false : attributes.getAttributeForName("stoptype").equalsIgnoreCase("STOP");
		
		if( attributes.getAttributeForName("posttype") != null){
			try{
				 typeTableID = Integer.parseInt( attributes.getAttributeForName("posttype") );
			}catch (NumberFormatException nfe) {
				Assert.warnFatal(true, attributes.getAttributeForName("posttype") + " not valid Type Identifier.");
			}
		}else{
			Assert.warnFatal(true, attributes.getAttributeForName("posttype") + " not valid Type Identifier.");
		}
	}
	
	/**
	 * @return the invertedIndex
	 */
	public TextInvertedIndex getInvertedIndex() {
		return invertedIndex;
	}

	/**
	 * @param invertedIndex the invertedIndex to set
	 */
	public void setInvertedIndex(TextInvertedIndex invertedIndex) {
		this.invertedIndex = invertedIndex;
	}
}
