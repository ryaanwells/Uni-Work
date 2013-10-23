package uk.ac.gla.mir.flair.irmodel.filter;

import uk.ac.gla.mir.flair.datamodel.dataElement.DataElement;

/**
 * Title:        Filter <br/>
 * Description:  Abstract Filter Class, Tokenizers, Stemmers, etc should extend this
 * class and implement doFilter <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public abstract class Filter {

	/**
	 * doFilter should perfored on <code>de</code> the result 
	 * of which should be returned
	 * @param de The DataElement to apply the filter to
	 * @return The result of the filter
	 */
	public abstract DataElement doFilter(DataElement de);
	
	/**
	 * Returns the Name of the Filter
	 * @return the name
	 */
	public abstract String getName();
}
