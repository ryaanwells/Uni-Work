package uk.ac.gla.mir.flair.index.spec;


import java.util.*;

import uk.ac.gla.mir.flair.index.DirectIndex;
import uk.ac.gla.mir.flair.index.IdentifierDirectIndex;
import uk.ac.gla.mir.flair.index.TextInvertedIndex;
import uk.ac.gla.mir.flair.irmodel.filter.FilterChain;
import uk.ac.gla.mir.flair.util.Settings;

/**
 * Title:        IndexSpec <br/>
 * Description:  Contains All the information about the Current Index<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class IndexSpec {

	ArrayList<InvertedIndexSpec> indexSpecs;
	ArrayList<DirectIndexSpec> directIndexSpecs;
	
	/**
	 * Constructs an IndexSpec from an ArrayList of InvertedIndexSpecs
	 * @param indexSpecs
	 */
	public IndexSpec(ArrayList<InvertedIndexSpec> indexSpecs,
					 ArrayList<DirectIndexSpec> directIndexSpecs){
		this.indexSpecs = indexSpecs;
		this.directIndexSpecs = directIndexSpecs;
	}
	
	/**
	 * Gets the Inverted Index Spec List
	 * @return The Inverted Index Spec List
	 */
	public ArrayList<InvertedIndexSpec> getInvertedIndexSpecs() {
		return indexSpecs;
	}

	/**
	 * Gets the direct Index Spec List
	 * @return The direct Index Spec List
	 */
	public ArrayList<DirectIndexSpec> getDirectIndexSpecs(){
		return directIndexSpecs;
	}
	
	/**
	 * Sets the Inverted Index Spec List
	 * @param indexSpecs the indexSpecs to set
	 */
	public void setIndexSpecs(ArrayList<InvertedIndexSpec> indexSpecs) {
		this.indexSpecs = indexSpecs;
	}
	
	/**
	 * Sets the Direct Index Spec List
	 * @param indexSpecs the indexSpecs to set
	 */
	public void setDirectIndexSpecs(ArrayList<DirectIndexSpec> indexSpecs) {
		this.directIndexSpecs = indexSpecs;
	}
	
	/**
	 * Gets an Iterator for the Inverted Index Spec List
	 * @return The Iterator of Inverted Index Specs
	 */
	public Iterator<InvertedIndexSpec> invertedSpecIterator(){
		return indexSpecs.iterator();
	}
	
	/**
	 * Gets an Iterator for the Direct Index Spec List
	 * @return The Iterator of Direct Index Specs
	 */
	public Iterator<DirectIndexSpec> directSpecIterator(){
		return directIndexSpecs.iterator();
	}	
	
	/**
	 * Loads the IndexSpec from File
	 * @return the IndexSpec
	 */
	public static IndexSpec initIndexSpec(){
		//Load the IndexSpec from file
		IndexSpec indexSpec = IndexSpecBuilder.loadIndexSpec();
		
		//For Each Inverted Index, Create Correct Filter Chain and Load Indexes
		final Iterator<InvertedIndexSpec> iterator = indexSpec.invertedSpecIterator();
		while(iterator.hasNext()){
			final InvertedIndexSpec invertedIndexSpec = iterator.next();
			System.out.println("Loading Inverted Index : "+invertedIndexSpec);
			final String path = Settings.getIndexPath();
			final String name = path + Settings.fileSep + invertedIndexSpec.getIndexName();
			System.out.println(name);
			final TextInvertedIndex textInvertedIndex = 
				TextInvertedIndex.readTextInvertedIndex( name+"_index", name+"_data");
			invertedIndexSpec.setInvertedIndex(textInvertedIndex);
			final FilterChain filterChain = new FilterChain(invertedIndexSpec);
			invertedIndexSpec.setFilterChain( filterChain );

		}
		
		//For each Direct Index, Create Correct Filter Chain and Init Indexes
		final Iterator<DirectIndexSpec> directIterator = indexSpec.directSpecIterator();
		while(directIterator.hasNext()){
			final DirectIndexSpec directIndexSpec = directIterator.next();
			final String path = Settings.getIndexPath();
			final String name = path + Settings.fileSep + directIndexSpec.getIndexName();
			System.out.println("Loading Direct Index : "+directIndexSpec);
			if(directIndexSpec.getTokenType().equalsIgnoreCase("IDENTIFIER_TOKEN")){
				final DirectIndex directIndex = IdentifierDirectIndex.readIdentifierDirectIndex(name);
				directIndexSpec.setDirectIndex(directIndex);
			}
		}
		return indexSpec;
	}
}
