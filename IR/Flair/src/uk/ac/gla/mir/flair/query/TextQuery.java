package uk.ac.gla.mir.flair.query;

import java.io.IOException;
import java.util.*;
import gnu.trove.*;
import uk.ac.gla.mir.flair.datamodel.IRObject;
import uk.ac.gla.mir.flair.datamodel.IRObjectSet;
import uk.ac.gla.mir.flair.datamodel.IRResult;
import uk.ac.gla.mir.flair.datamodel.dataElement.*;
import uk.ac.gla.mir.flair.index.*;
import uk.ac.gla.mir.flair.index.spec.*;
import uk.ac.gla.mir.flair.irmodel.filter.FilterChain;
import uk.ac.gla.mir.flair.irmodel.weighting.Weighting;
import uk.ac.gla.mir.flair.query.spec.*;
import uk.ac.gla.mir.flair.query.spec.Spec;
import uk.ac.gla.mir.flair.util.*;

/**
 * Title:        TextQuery <br/>
 * Description:  Contains the specification of a TextQuery <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class TextQuery extends Query {
	
	final int MAX_RESULTS = Settings.getProperty("flair.max.results", 1000);
	
	public TextQuery( Spec querySpec, IndexSpec indexSpec){
		super( querySpec, indexSpec );
	}

	public IRObjectSet doQuery() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		final IRObjectSet results = new IRObjectSet( MAX_RESULTS );
		
		final long startTime = System.currentTimeMillis();
		
		final TextQuerySpec textQuerySpec = (TextQuerySpec)querySpec;
		final TLongDoubleHashMap documentID2Scores = new TLongDoubleHashMap();
		final TLongObjectHashMap<IdentifierDirectIndex> identifierMap = new TLongObjectHashMap<IdentifierDirectIndex>();
		
		System.out.println( "Executing Query : "+textQuerySpec.getTerms()+" : "+textQuerySpec.getTypeTableID());
		final StringDE queryDE = new StringDE(textQuerySpec.getTerms());
		
		final String weightingClassName = Settings.getProperty("flair."+textQuerySpec.getWeighting(), "uk.ac.gla.mir.flair.irmodel.weighting.RAWTF" );
		Class lc = Class.forName(weightingClassName, false, Weighting.class.getClassLoader() );
		final Weighting weighting = (Weighting)lc.newInstance();
		
		/*
		 * Find the Correct Index for the Query
		 */
		final Iterator<InvertedIndexSpec> iterator = indexSpec.invertedSpecIterator();
		while(iterator.hasNext()){
			final InvertedIndexSpec invertedIndexSpec = iterator.next();
			
			/*
			 * Only Works with text queries
			 */
			if( invertedIndexSpec.getTokenType().compareToIgnoreCase(uk.ac.gla.mir.flair.index.spec.Spec.TEXT_TOKEN) == 0
				&& invertedIndexSpec.getTypeTableID() == textQuerySpec.getTypeTableID() ){
				
				/*
				 * For Each Index Field specified in the query, we need to process the query text with
				 * the correct filters 
				 */
				final int[] queryFieldIDs = textQuerySpec.getFieldIDs().toNativeArray();
				final TIntArrayList indexFieldIDs = invertedIndexSpec.getDataElementIDs();
				for(int i=0; i< queryFieldIDs.length; i++){
					if( indexFieldIDs.contains( queryFieldIDs[i] )){

						/*
						 * We need the correct filter chain to use on the query fields
						 * so the will match with the already filtered index fields
						 */
						final FilterChain filterChain = invertedIndexSpec.getFilterChain();
						final TextInvertedIndex textInvertedIndex = invertedIndexSpec.getInvertedIndex();
						
						//Need to Find Correct Identifier Index
						//There should not be many a simple linear search should be fast enough
						final Iterator<DirectIndexSpec> directIterator = indexSpec.directSpecIterator();
						IdentifierDirectIndex identifierIndex = null;
						while(directIterator.hasNext()){
							final DirectIndexSpec directSpec = directIterator.next();
							if(   ( directSpec.getTokenType().equalsIgnoreCase( uk.ac.gla.mir.flair.index.spec.Spec.IDENTIFIER_TYPE ) )
								&&( directSpec.getTypeTableID() == textQuerySpec.getTypeTableID() ) ){
								identifierIndex = (IdentifierDirectIndex)directSpec.getDirectIndex();
							}
						}

						/*
						 * Get Collection Statistics to use to calculate scores
						 */
						CollectionStatistics collStat = identifierIndex.getCollectionStatistics();
						
						final SequenceDE newQueryDE = (SequenceDE)filterChain.doFilter(queryDE);
						final TObjectIntHashMap<String> queryTermCounts = getQueryTermCounts( newQueryDE );
						final int size = newQueryDE.size();
						
						weighting.setAverageDocumentLength( collStat.getAverageLengthOfDocuments() );
						weighting.setNumberOfDocumentsInCollection( collStat.getNumberOfDocuments() );
						
						double[] params = textQuerySpec.getParams();
						weighting.setParams( params );
						
						for(int j = 0 ; j < size; j++){
							DataElement de = newQueryDE.getElementAt(j);
							final String term =  de.getCharValue();
							weighting.setTermFrequencyForQuery( queryTermCounts.get( term ) );
							if( textInvertedIndex.findTerm( term ) ){
								final PostingListEntry[] postings = textInvertedIndex.getPostings();
								weighting.setDocumentFrequencyForTerm( postings.length );
								
								for(int d = 0; d < postings.length; d++){
									
									final long irObjectID = postings[d].getIrObjectID();;
									identifierIndex.readNextEntry(irObjectID);
									weighting.setTermFrequencyForDocument( postings[d].getFrequency() );
									weighting.setDocumentLength( identifierIndex.getCurrentDocumentLength() );
									
									final double score = weighting.score();								
									documentID2Scores.adjustOrPutValue(irObjectID, score, score);
									identifierMap.put(irObjectID, identifierIndex);
								}
							
							}
						}
					}
				}
			}
		}
		
		long[] keys = documentID2Scores.keys();
		KeyValue[] keyValues = new KeyValue[keys.length];
		for( int i = 0; i < keys.length; i++){
			keyValues[i] = new KeyValue<Double, Long>(keys[i], documentID2Scores.get( keys[i] ) );
		}
		Arrays.sort(keyValues);
		for(int i = 0; i < keyValues.length && i < MAX_RESULTS; i++){
			final long irObjectID = (Long)keyValues[i].getKey();
			final IdentifierDirectIndex identIndex = identifierMap.get(irObjectID);
			if( identIndex.findIdentifier(irObjectID) ){
				final String ident = identIndex.getCurrentData();
				IRResult result = new IRResult( irObjectID, ident, i, documentID2Scores.get( irObjectID ) );
				results.add( result );
			}
//			else{
//				System.out.println( irObjectID + " == "+null + " : " + documentID2Scores.get( irObjectID ) );
//			}
		}
		System.out.println(keyValues.length + " results.");
		final long endTime = System.currentTimeMillis();
		final long time = endTime - startTime;
		System.out.println("Query took "+time+"ms.");
		return results;
	}
	
	private TObjectIntHashMap<String> getQueryTermCounts(final SequenceDE newQueryDE) {
		final TObjectIntHashMap<String> tmp = new TObjectIntHashMap<String>();
		final int size = newQueryDE.size();
		for(int j = 0 ; j < size; j++){
			final DataElement de = newQueryDE.getElementAt(j);
			final String term = de.getCharValue();
			tmp.adjustOrPutValue(term, 1, 1);
		}
		return tmp;
	}
	
}
