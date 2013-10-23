package uk.ac.gla.mir.flair.index;

import java.util.Iterator;
import uk.ac.gla.mir.flair.datamodel.*;
import uk.ac.gla.mir.flair.datamodel.dataElement.DataElement;
import uk.ac.gla.mir.flair.index.spec.*;
import uk.ac.gla.mir.flair.irmodel.filter.FilterChain;
import uk.ac.gla.mir.flair.util.Settings;

/**
 * Title:        Indexer <br/>
 * Description:  Builds Index Specs, Creates Indexes, Filter Chains and Indexes IRObjects<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class Indexer {

	/** The Index Specification **/
	private IndexSpec indexSpec;
	
	public static TypeTable tmp;
	
	public static TextInvertedIndex textInvertedIndex;
	
	/**
	 * Loads the Index Specification Files
	 * Specified by the property <code>flair.index.spec</code>
	 */
	public Indexer(){
		//Load the IndexSpec from file
		indexSpec = IndexSpecBuilder.loadIndexSpec();
		
		//For Each Inverted Index, Create Correct Filter Chain and Init Indexes
		final Iterator<InvertedIndexSpec> iterator = indexSpec.invertedSpecIterator();
		while(iterator.hasNext()){
			final InvertedIndexSpec invertedIndexSpec = iterator.next();
			System.out.println("Creating Inverted Index : "+invertedIndexSpec);
			//if(invertedIndexSpec.getTokenType().equalsIgnoreCase("TEXT_TOKEN")){
				final String path = Settings.getIndexPath();
				final String name = path + Settings.fileSep + invertedIndexSpec.getIndexName();
				final TextInvertedIndex textInvertedIndex = TextInvertedIndex.createTextInvertedIndex(
														name+"_index", name+"_data");
				invertedIndexSpec.setInvertedIndex(textInvertedIndex);
				final FilterChain filterChain = new FilterChain(invertedIndexSpec);
				invertedIndexSpec.setFilterChain( filterChain );
				//System.out.println("Creating Inverted Index : " + name+"_index"+ " : "+ name+"_data");
			//}
		}
		
		//For each Direct Index, Create Correct Filter Chain and Init Indexes
		final Iterator<DirectIndexSpec> directIterator = indexSpec.directSpecIterator();
		while(directIterator.hasNext()){
			final DirectIndexSpec directIndexSpec = directIterator.next();
			final String path = Settings.getIndexPath();
			final String name = path + Settings.fileSep + directIndexSpec.getIndexName();
			System.out.println("Creating Direct Index : "+directIndexSpec);
			if(directIndexSpec.getTokenType().equalsIgnoreCase("IDENTIFIER_TOKEN")){
				final DirectIndex directIndex = IdentifierDirectIndex.createIdentifierDirectIndex(name);
				directIndexSpec.setDirectIndex(directIndex);
			}
		}
	}
	
	/**
	 * Indexes a Single IRObject
	 * @param irobject The IRObject to Index
	 */
	public void index(final IRObject irobject){
		
		//For each inverted Index, look-up would be slightly more efficent
		final Iterator<InvertedIndexSpec> iterator = indexSpec.invertedSpecIterator();
		while(iterator.hasNext()){
			final InvertedIndexSpec invertedIndexSpec = iterator.next();
			if( invertedIndexSpec.getTypeTableID() == irobject.getTypeTable().getTypeTableID() ){
				
				final int[] ids = invertedIndexSpec.getDataElementIDs().toNativeArray();
				if( ids == null )continue;
				
				for(int i = 0; i < ids.length; i++){
					/*
					 * Extractor, Can Simple use IRObject.getDataElements(int id); 
					 */
					DataElement[] dataElements = irobject.getDataElements(ids[i]);
					
					for(int j = 0; j < dataElements.length; j++){
						
						//Process With Filter Chain, Stopper, Stemmer, etc.
						final FilterChain filterChain = invertedIndexSpec.getFilterChain();
						tmp = irobject.getTypeTable();
						//DataElement.printDataElement(irobject.PickData, 0, tmp);
						dataElements[j] = filterChain.doFilter(dataElements[j]);
						textInvertedIndex = invertedIndexSpec.getInvertedIndex();
						textInvertedIndex.index(dataElements[j], irobject );
					}
				}
			}		
		}
		
		//For each direct Index
		final Iterator<DirectIndexSpec> directIterator = indexSpec.directSpecIterator();
		while(directIterator.hasNext()){
			final DirectIndexSpec directIndexSpec = directIterator.next();
			if(directIndexSpec.getTypeTableID() == irobject.getTypeTable().getTypeTableID() ){
				final int[] ids = directIndexSpec.getDataElementIDs().toNativeArray();
				if(ids == null)
					continue;
				
				for(int i = 0; i < ids.length; i++){
					/*
					 * Extractor, Can Simple use IRObject.getDataElements(int id); 
					 */
					DataElement[] dataElements = irobject.getDataElements(ids[i]);
					
					for(int j = 0; j < dataElements.length; j++){
						
						//Process With Filter Chain, Stopper, Stemmer, etc.
						final FilterChain filterChain = directIndexSpec.getFilterChain();
						tmp = irobject.getTypeTable();
//						DataElement.printDataElement(irobject.PickData, 0, tmp);
//						DataElement.printDataElement(dataElements[j], 0, tmp);
//						System.out.println(irobject.getID());
						//dataElements[j] = filterChain.doFilter(dataElements[j]);
						final DirectIndex directIndex = directIndexSpec.getDirectIndex();
						directIndex.index(dataElements[j], irobject.getID(), irobject.numberOfTerms );
					}
				}
			}
		}
	}
	
	/**
	 * Finishes the Indexing Process, writes out the last temp Index
	 * merges any if required and then closes the files
	 */
	public void finalise(){
		
		//For each Inverted Index
		final Iterator<InvertedIndexSpec> iterator = indexSpec.invertedSpecIterator();
		while(iterator.hasNext()){
			final InvertedIndexSpec invertedIndexSpec = iterator.next();
			final TextInvertedIndex textInvertedIndex = invertedIndexSpec.getInvertedIndex();
			textInvertedIndex.writeOutIndex();
			textInvertedIndex.merge();
			textInvertedIndex.close();
		}
		
		//For each Direct Index
		final Iterator<DirectIndexSpec> directIterator = indexSpec.directSpecIterator();
		while(directIterator.hasNext()){
			final DirectIndexSpec directIndexSpec = directIterator.next();
			final DirectIndex directIndex = directIndexSpec.getDirectIndex();
			directIndex.close();
			directIndex.createCollectionStatistics();			
			/* ******* Not Finished! ******** */
		}
	}
}
