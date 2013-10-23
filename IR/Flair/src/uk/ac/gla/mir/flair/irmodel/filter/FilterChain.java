package uk.ac.gla.mir.flair.irmodel.filter;


import java.util.*;

import uk.ac.gla.mir.flair.datamodel.TypeTable;
import uk.ac.gla.mir.flair.datamodel.dataElement.DataElement;
import uk.ac.gla.mir.flair.index.Indexer;
import uk.ac.gla.mir.flair.index.spec.InvertedIndexSpec;
import uk.ac.gla.mir.flair.index.spec.Spec;
import uk.ac.gla.mir.flair.util.Assert;
import uk.ac.gla.mir.flair.util.Settings;

/**
 * Title:        FilterChain <br/>
 * Description:  Implements a Chain of Filters, tokenizer, stemmer, etc<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class FilterChain extends Filter{

	/** Simple ArrayList Could Handle This **/
	private ArrayList<Filter> filterChain;
	
	
	public FilterChain(){
		filterChain = new ArrayList<Filter>();
	}
		
	/**
	 * Returns a new FilterChain based on the Inverted Index Specification
	 * @param invertedIndexSpec The Inverted Index Specification where this Filter is specified
	 */
	public FilterChain(Spec invertedIndexSpec){
		filterChain = new ArrayList<Filter>();

		final Iterator<String> iterator = invertedIndexSpec.getAttributes().getAttributeNames().iterator();
		while(iterator.hasNext()){
			final String attribName = iterator.next();
			if( attribName.trim().equalsIgnoreCase("invname") || 
					attribName.trim().equalsIgnoreCase("posttype") ){
				continue;
			}else{
				final String value = invertedIndexSpec.getAttributes().getAttributeForName(attribName);
			
				if(value.trim().equalsIgnoreCase("IDENTIFIER_TOKEN"))
					continue;
				
				final String filterClassName = Settings.getProperty("flair."+value, "");	
				try{
					Class lc = Class.forName(filterClassName, false, FilterChain.class.getClassLoader() );
					Filter filter = (Filter)lc.newInstance();
					filterChain.add( filter );
					System.out.println("Using :"+filter.getName());	
				}catch (ClassNotFoundException e) {
					Assert.warnFatal(true,"Cannot Find Filter " + "flair."+value+"="+filterClassName ); 
				}catch (Exception e) {
					// TODO: handle exception
					System.err.println(e.getLocalizedMessage());
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see irmodel.filter.Filter#doFilter(datamodel.dataElement.DataElement)
	 */
	@Override
	public DataElement doFilter(DataElement de) {
		DataElement tmp = de;
		final Iterator<Filter> iterator = filterChain.iterator();
		while(iterator.hasNext()){
			final Filter filter = iterator.next();
			tmp = filter.doFilter(tmp);
			//DataElement.printDataElement(tmp, 0,  Indexer.tmp);
			//System.out.println(tmp.getClass().getCanonicalName());
		}
		return tmp;
	}

	/* (non-Javadoc)
	 * @see irmodel.filter.Filter#getName()
	 */
	@Override
	public String getName() {
		return "irmodel.filter.FilterChain";
	}
}
