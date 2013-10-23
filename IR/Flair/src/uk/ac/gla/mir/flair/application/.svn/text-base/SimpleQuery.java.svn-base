package uk.ac.gla.mir.flair.application;

import java.util.*;
import gnu.trove.TIntObjectHashMap;
import uk.ac.gla.mir.flair.datamodel.dataElement.*;
import uk.ac.gla.mir.flair.index.*;
import uk.ac.gla.mir.flair.index.spec.*;
import uk.ac.gla.mir.flair.index.spec.Spec;
import uk.ac.gla.mir.flair.irmodel.filter.FilterChain;
import uk.ac.gla.mir.flair.util.*;

/**
 * Title:        SimpleQuery <br/>
 * Description:  A Simple String Query Program <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class SimpleQuery {

	private static String flair_home;// = Settings.getProperty("flair.home", "");
	private static String flair_etc;// = Settings.getProperty("flair.etc", "");
	private static String document_description_file;// = Settings.getProperty("flair.description.filename", "");
	private static String inputDocument;// = Settings.getProperty("flair.document.filename", "");
	private static String stopwordsFile;// = Settings.stopfilelocation;
	private static String index_path;// = Settings.getIndexPath();
	private static IndexSpec indexSpec;
	
	private static IndexSpec initIndex(){
//		Load the IndexSpec from file
		indexSpec = IndexSpecBuilder.loadIndexSpec();
		
		//For Each Inverted Index, Create Correct Filter Chain and Load Indexes
		final Iterator<InvertedIndexSpec> iterator = indexSpec.invertedSpecIterator();
		while(iterator.hasNext()){
			final InvertedIndexSpec invertedIndexSpec = iterator.next();
			System.out.println("Loading Inverted Index : "+invertedIndexSpec);
			if(invertedIndexSpec.getTokenType().equalsIgnoreCase("TEXT_TOKEN")){
				final String path = Settings.getIndexPath();
				final String name = path + Settings.fileSep + invertedIndexSpec.getIndexName();
				final TextInvertedIndex textInvertedIndex = TextInvertedIndex.readTextInvertedIndex(
														name+"_index", name+"_data");
				invertedIndexSpec.setInvertedIndex(textInvertedIndex);
				final FilterChain filterChain = new FilterChain(invertedIndexSpec);
				invertedIndexSpec.setFilterChain( filterChain );
				//System.out.println("Creating Inverted Index : " + name+"_index"+ " : "+ name+"_data");
			}
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
	
	private static void doQuery(String query){
		final int MAX_RESULTS = 1000;
		int currentResultsLength = 0;
		TIntObjectHashMap<String> resultIDS = new TIntObjectHashMap<String>();
		PostingListEntry[] tmpResults = null;
		
		final StringDE queryDE = new StringDE(query);
		//System.out.println(queryDE.getCharValue());
		final Iterator<InvertedIndexSpec> iterator = indexSpec.invertedSpecIterator();
		while(iterator.hasNext()){
			final InvertedIndexSpec invertedIndexSpec = iterator.next();
			//System.out.println( invertedIndexSpec.getTokenType() );
			if( invertedIndexSpec.getTokenType().compareToIgnoreCase(Spec.TEXT_TOKEN) == 0 ){
				
				final int typeTableID = invertedIndexSpec.getTypeTableID();
				final TextInvertedIndex textInvertedIndex = invertedIndexSpec.getInvertedIndex();
				final FilterChain textFilterChain = invertedIndexSpec.getFilterChain();
				
				final SequenceDE newQueryDE = (SequenceDE)textFilterChain.doFilter(queryDE);
				
				//Need to Find Correct Identifier Index
				final Iterator<DirectIndexSpec> directIterator = indexSpec.directSpecIterator();
				IdentifierDirectIndex identifierIndex = null;
				while(directIterator.hasNext()){
					final DirectIndexSpec directSpec = directIterator.next();
					if(   ( directSpec.getTokenType().equalsIgnoreCase( Spec.IDENTIFIER_TYPE ) )
						&&( directSpec.getTypeTableID() == typeTableID ) ){
						identifierIndex = (IdentifierDirectIndex)directSpec.getDirectIndex();
					}
				}
				
				final int size = newQueryDE.size();
				for(int i = 0 ; i < size; i++){
					DataElement de = newQueryDE.getElementAt(i);
					//System.out.println( de.getCharValue() );
					if( textInvertedIndex.findTerm( de.getCharValue() ) ){
						final String currentTerm = textInvertedIndex.getCurrentTerm();
						final PostingListEntry[] postings = textInvertedIndex.getPostings();
						if(tmpResults == null)
							tmpResults = postings;
						else
							tmpResults = PostingListEntry.merge(postings, tmpResults);
						
						for(int j=0; j < postings.length && j < MAX_RESULTS; j++){
							final long irObjectId = postings[j].getIrObjectID();
							if( identifierIndex.findIdentifier(irObjectId) ){
								final String identifier = identifierIndex.getCurrentData();
								resultIDS.put( (int)irObjectId, identifier);
								System.out.println( irObjectId +" == "+identifier);
							}
						}
					}
				}
			}
		}
		
		System.out.println(resultIDS.size() + " results.");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final Scanner userIn = new Scanner(System.in);
		Settings.initSettings();
		flair_home = Settings.getProperty("flair.home", "");
		flair_etc = Settings.getProperty("flair.etc", "");
		document_description_file = Settings.getProperty("flair.documents.file", "");
		inputDocument = Settings.getProperty("flair.documents.file", "");
		stopwordsFile = Settings.stopfilelocation;
		index_path = Settings.getIndexPath();
		
		Assert.info(true, "Flair Home = "+flair_home+
						  "\nFlair ETC = "+flair_etc+
						  "\nFlair Description File = "+document_description_file+
						  "\nFlair Input File = "+inputDocument+
						  "\nFlair Stopwords File = " +stopwordsFile+
						  "\nFlair Index Path = "+ index_path );
		
		final long startIndexTime = System.currentTimeMillis();
		initIndex();
		final long endIndexTime = System.currentTimeMillis();
		final long indexTime = endIndexTime - startIndexTime;
		System.out.println("Loading Indexes took "+indexTime+"ms.");
		
		while(true){
			System.out.println("Enter Query:");
			final String cmd = userIn.nextLine();
			final long startTime = System.currentTimeMillis();
			
			if(cmd.equalsIgnoreCase("quit") || cmd.equalsIgnoreCase("exit")){
				break;
			}
			
			doQuery(cmd);
			
			final long endTime = System.currentTimeMillis();
			final long time = endTime - startTime;
			System.out.println("Query took "+time+"ms.");
		}
	}

}
