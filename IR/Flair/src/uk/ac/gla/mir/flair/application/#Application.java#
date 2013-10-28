package uk.ac.gla.mir.flair.application;

import gnu.trove.*;
import java.io.*;
import java.util.*;
import uk.ac.gla.mir.flair.datamodel.*;
import uk.ac.gla.mir.flair.datamodel.dataElement.*;
import uk.ac.gla.mir.flair.engine.input.spec.DTDReader;
import uk.ac.gla.mir.flair.index.*;
import uk.ac.gla.mir.flair.index.spec.*;
import uk.ac.gla.mir.flair.parser.grammer.DocParser;
import uk.ac.gla.mir.flair.query.Query;
import uk.ac.gla.mir.flair.query.spec.*;
import uk.ac.gla.mir.flair.query.spec.Spec;
import uk.ac.gla.mir.flair.util.*;

/**
 * Title:        Application <br/>
 * Description:  The Main Application of Flair<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class Application {

	
	private static String flair_home;// = Settings.getProperty("flair.home", "");
	private static String flair_etc;// = Settings.getProperty("flair.etc", "");
	private static String document_description_file;// = Settings.getProperty("flair.description.filename", "");
	private static String inputDocument;// = Settings.getProperty("flair.document.filename", "");
	private static String stopwordsFile;// = Settings.stopfilelocation;
	private static String index_path;// = Settings.getIndexPath();
	private static String docPositionIndex;// = index_path+Settings.fileSep+"docPosition.index";
	private static Indexer indexer;

	/**
	 * Displays the Usage Instructions to Standard Output
	 */
	private static void displayUsage(){
		System.out.println();
		System.out.println("This application can be invoked with the following options:");
		System.out.println();
		System.out.println("     --help                       Displays this help message.");
		System.out.println("     -i                           Index files, Specified in the,");
		System.out.println("                                  'flair.documents' and 'flair.descriptions' files.");
		System.out.println("     -q                           Query the collection based on the 'flair.query.spec' file.");
		System.out.println("     -v | --view  docid:docid     Shows IRObjects, Specified by a Colon Separated List.");
		System.out.println("     --print-terms typeID         Shows statistics about the inverted index. ");
		System.out.println("     --print-docs typeID          Shows statistics about the direct index. ");
		System.out.println("     --print-stat typeID          Shows statistics about the collection.");
		System.out.println();
	}
	
	/**
	 * Create the Indexes from flair.pryesoperties settings
	 */
	private static void doIndexing(){
		
		File documentPositionsFile = new File(docPositionIndex);
		indexer = new Indexer();
		DocumentPositions documentPositionsIndex = (documentPositionsFile.exists() ? null : DocumentPositions.createNewDocumentPositions());
		long irobjectID = 0;
		
		if(documentPositionsIndex == null){
			Assert.warn(true, "Document Position Index "+docPositionIndex+ " already exists, it will be overwritten.");
			final Scanner input = new Scanner(System.in);
			while(true){
				System.out.print("Do you want to continue?");
				
				final String cmd = input.nextLine();
				if( cmd.charAt(0)=='y' || cmd.charAt(0)=='Y' ){
					documentPositionsIndex = DocumentPositions.createNewDocumentPositions();
					break;
				}else if(cmd.charAt(0)=='n' || cmd.charAt(0)=='N'){
					System.exit(-1);
				}
			}
		}
		
		final long StartTime = System.currentTimeMillis();
		final TIntObjectHashMap<String> descriptionFiles = Settings.getDocumentDescriptionFiles();
		final TIntObjectHashMap<TypeTable> typeTables = new TIntObjectHashMap<TypeTable>();
		final int[] typeTabelIDs = descriptionFiles.keys();
		
		for(int i = 0; i < typeTabelIDs.length; i++){
			try{
				System.out.println("Reading Description File "+typeTabelIDs[i]+" = " +descriptionFiles.get( typeTabelIDs[i]) );
				final File fin = new File(descriptionFiles.get( typeTabelIDs[i]));
				final DTDReader descReader = new DTDReader(fin);
				final TypeTable tt = descReader.buildDescription();
				tt.setTypeTableID(typeTabelIDs[i]);
				typeTables.put(typeTabelIDs[i], tt);
				
			}catch (IOException e) {
				Assert.fatal(true, "Problem Reading Description File " + descriptionFiles.get( typeTabelIDs[i]));
			}
		}
		
		final TObjectIntHashMap<String> documents = Settings.getDocumentFiles();
		final String[] documentFileNames = documents.keys(new String[0]);
		
		for(int i = 0; i < documentFileNames.length; i++){
			
			System.out.println( "Indexing "+documentFileNames[i] + " (" + ( i*100 / documentFileNames.length ) + ") ");

			File docFile = new File(documentFileNames[i]);
			final DocParser ps = new DocParser();
			try{
				final TypeTable tt = typeTables.get( documents.get( documentFileNames[i] ) );
				if(tt == null){
					Assert.warn(true, "TypeTable ID "+documents.get( documentFileNames[i] )+" does not exist."+
									  "\nSkipping "+documentFileNames[i]+
									  ". Check IDs match in descriptions and documents file");
					continue;
				}
				final TypeObject to = ps.buildParser( tt, docFile);
				long filePosition = ps.getFilePosition();
				IRObject irobject;
				while( (irobject = ps.parseSingleIRObject( to )) != null ){
					irobject.setID(irobjectID);
					documentPositionsIndex.add(filePosition, documentFileNames[i]);
					
					filePosition = ps.getFilePosition();
		
					// ************* Add To Index ************* //
					indexer.index(irobject);
					
					irobjectID++;
				}
			}catch (FileNotFoundException fnfe) {
				Assert.fatal(true, "Parser Could Not Find Document File " + fnfe.getMessage());
			}catch (IllegalAccessException iae) {
				iae.printStackTrace();
				Assert.fatal(true, "Parser Construct/Invoke object " + iae.getMessage());
			}catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
				Assert.fatal(true, "Parser Could Not Find The Class File " + cnfe.getMessage());
			}catch(IOException ioe){
				ioe.printStackTrace();
			}catch (InstantiationException ie) {
				ie.printStackTrace();
			}
		}
		
		try{
			documentPositionsIndex.close();
		}catch (IOException ioe) {
			Assert.fatal(true, "Problem Closing Document Position Index.");
		}
		
		indexer.finalise();
		final Long EndTime = System.currentTimeMillis();
		final Long time = EndTime-StartTime;
		Assert.info(true, "Indexing Complete. Taking "+time+"ms.");
	}
	
	/**
	 * Shows A Document
	 */
	public static void showDoc(final int docid){
		
		DocumentPositions documentPositionsIndex = DocumentPositions.readDocumentPositions();
		
		try{
			documentPositionsIndex.readEntry(docid);
		}catch (IOException e) {
			Assert.fatal(true, "Error Reading Document Position Index ");
		}
		
		final long docPosition = documentPositionsIndex.getDocumentPosition();
		final String fileName = documentPositionsIndex.getFileName();
		
		System.out.println("Document : "+ docid +" : is located in "+fileName+" at byte "+docPosition);
		
		//Need To Load TypeTables
		final TIntObjectHashMap<String> descriptionFiles = Settings.getDocumentDescriptionFiles();
		final TIntObjectHashMap<TypeTable> typeTables = new TIntObjectHashMap<TypeTable>();
		final int[] typeTabelIDs = descriptionFiles.keys();
		
		for(int i = 0; i < typeTabelIDs.length; i++){
			try{
				final File fin = new File( descriptionFiles.get(typeTabelIDs[i]) );
				final DTDReader descReader = new DTDReader(fin);
				final TypeTable tt = descReader.buildDescription();
				tt.setTypeTableID( typeTabelIDs[i] );
				typeTables.put(typeTabelIDs[i], tt);
				
			}catch (IOException e) {
				Assert.fatal(true, "Problem Reading Description File " + descriptionFiles.get(typeTabelIDs[i]) );
			}
		}
		
		// Need To find the Correct File Name
		final TObjectIntHashMap<String> documents = Settings.getDocumentFiles();
		final String[] documentFileNames = documents.keys(new String[0]);
		
		for(int i = 0; i < documentFileNames.length; i++){
						
			if( documentFileNames[i].equals( fileName ) ){
				
				//Make Sure Input File is Ok
				File docFile = new File(documentFileNames[i]);
				DocParser ps = new DocParser();
				try{
					TypeObject to = ps.buildParser( typeTables.get( documents.get( documentFileNames[i]) ) , docFile);
					
					//Now Seek Through the file to the correct location
					//Parse the IRObject
					ps.skip(docPosition);
					IRObject irobject = ps.parseSingleIRObject( to );
					DataElement.printDataElement(irobject.PickData, 0, typeTables.get( documents.get( documentFileNames[i]) ) );
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}	
	
	/**
	 * Perform the queries specified in the QuerySpec file
	 * @throws IOException
	 */
	private static void doQuery() throws IOException{
		final IndexSpec indexSpec = IndexSpec.initIndexSpec();
		final QuerySpec qs = QuerySpecBuilder.loadQuerySpec();
		final Iterator<Spec> queryIterator = qs.iterator();
		while( queryIterator.hasNext() ){
			final Spec qspec = queryIterator.next();
			final Query query = qspec.createQuery( indexSpec );
			try {
				IRObjectSet results = query.doQuery( );
				for( int i = 0; i < results.size(); i++){
					IRResult result = (IRResult)results.getElementAt(i);
					System.out.println( result );
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param docType
	 * @throws IOException
	 */
	private static void doPrintStat(int docType) throws IOException {
		final IndexSpec indexSpec = IndexSpec.initIndexSpec();	
		IdentifierDirectIndex identifierIndex = null;
		final Iterator<DirectIndexSpec> directIterator = indexSpec.directSpecIterator();
		while(directIterator.hasNext()){
			final DirectIndexSpec directSpec = directIterator.next();
			if(   ( directSpec.getTokenType().equalsIgnoreCase( uk.ac.gla.mir.flair.index.spec.Spec.IDENTIFIER_TYPE ) )
				&&( directSpec.getTypeTableID() == docType ) ){
				identifierIndex = (IdentifierDirectIndex)directSpec.getDirectIndex();
				System.out.println("Direct Index for " + docType + " : " + directSpec.getIndexName() );
			

				final Iterator<InvertedIndexSpec> iterator = indexSpec.invertedSpecIterator();
				while(iterator.hasNext()){
					final InvertedIndexSpec invertedIndexSpec = iterator.next();
					if( invertedIndexSpec.getTypeTableID() == docType ){
						System.out.println("Inverted Index for " + docType + " : " + invertedIndexSpec.getIndexName() );
						CollectionStatistics collStat = identifierIndex.getCollectionStatistics();
						double AVELength = collStat.getAverageLengthOfDocuments();
						int numberOfDocs = collStat.getNumberOfDocuments();
						System.out.println("Total Number of Documents = " + numberOfDocs );
						System.out.println("Average Document Length = " + AVELength );
					
						TextInvertedIndex invIndex = invertedIndexSpec.getInvertedIndex();
						int termCounter = 0;
						while( invIndex.readNextEntry() > 0 ){
							termCounter++;
						}
						System.out.println("Total Number of Uniq Terms = " + termCounter );
					}
				}
			}
		}
	}

	/**
	 * Prints a list of documents with the number of terms
	 * @param docType
	 * @throws IOException
	 */
	private static void doPrintDocs(int docType) throws IOException {
		final IndexSpec indexSpec = IndexSpec.initIndexSpec();		
		IdentifierDirectIndex identifierIndex = null;
		final Iterator<DirectIndexSpec> directIterator = indexSpec.directSpecIterator();
		while(directIterator.hasNext()){
			final DirectIndexSpec directSpec = directIterator.next();
			if(   ( directSpec.getTokenType().equalsIgnoreCase( uk.ac.gla.mir.flair.index.spec.Spec.IDENTIFIER_TYPE ) )
				&&( directSpec.getTypeTableID() == docType ) ){
				identifierIndex = (IdentifierDirectIndex)directSpec.getDirectIndex();
				System.out.println("Direct Index for " + docType + " : " + directSpec.getIndexName() );
				
				CollectionStatistics collStat = identifierIndex.getCollectionStatistics();
				double AVELength = collStat.getAverageLengthOfDocuments();
				int numberOfDocs = collStat.getNumberOfDocuments();
				System.out.println("Total Number of Documents = " + numberOfDocs );
				System.out.println("Average Document Length = " + AVELength );
				System.out.println("DocumentID\t: DocumentNo\t: Document Length");
				System.out.println(" ======= ");
				
				for( int docid = 0; docid < numberOfDocs; docid++){
					identifierIndex.readNextEntry( docid );
					long irObjectID = identifierIndex.getCurrentIROBjectID();
					String docNo = identifierIndex.getCurrentData();
					int documentLength = identifierIndex.getCurrentDocumentLength();
					System.out.println( irObjectID +"\t: "+docNo+"\t: "+documentLength );
				}
			}
		}
	}

	/**
	 * Prints the Inverted Index
	 * @param docType
	 * @throws IOException
	 */
	private static void doPrintTerms(int docType) throws IOException {	
		final IndexSpec indexSpec = IndexSpec.initIndexSpec();
		final Iterator<InvertedIndexSpec> iterator = indexSpec.invertedSpecIterator();
		while(iterator.hasNext()){
			final InvertedIndexSpec invertedIndexSpec = iterator.next();
			if( invertedIndexSpec.getTypeTableID() == docType ){
				
				System.out.println("Inverted Index for " + docType + " : " + invertedIndexSpec.getIndexName() );
				IdentifierDirectIndex identifierIndex = null;
				final Iterator<DirectIndexSpec> directIterator = indexSpec.directSpecIterator();
				while(directIterator.hasNext()){
					final DirectIndexSpec directSpec = directIterator.next();
					if(   ( directSpec.getTokenType().equalsIgnoreCase( uk.ac.gla.mir.flair.index.spec.Spec.IDENTIFIER_TYPE ) )
						&&( directSpec.getTypeTableID() == docType ) ){
						identifierIndex = (IdentifierDirectIndex)directSpec.getDirectIndex();
					}
				}
				
				TextInvertedIndex invIndex = invertedIndexSpec.getInvertedIndex();
//				System.out.println("Term\t: Doc#\t: Term#\t : [DocID:Term#]\t");
//				while( invIndex.readNextEntry() > 0 ){
//					String term = invIndex.getCurrentTerm();
//					PostingListEntry[] postings = invIndex.getPostings();
//					int DFt = postings.length;
//					int TF  = 0;
//					for( int i = 0; i < postings.length; i++ ){
//						TF += postings[i].getFrequency();
//					}
//					
//					System.out.print( term +"\t: " + DFt + "\t: " + TF + "\t: ["  );
//					for( int i = 0; i < postings.length; i++){
//						long docid = postings[i].getIrObjectID();
//						int feq = postings[i].getFrequency();
//						if( i < postings.length-1 )
//							System.out.print( docid+":"+feq+",");
//						else
//							System.out.println( docid+":"+feq+"]");
//					}
//				}

				CollectionStatistics collStat = identifierIndex.getCollectionStatistics();
				int numberOfDocs = collStat.getNumberOfDocuments();
				TLongArrayList docids = new TLongArrayList();
				System.out.print("\t");
				for( int docid= 0; docid < numberOfDocs; docid++){
					identifierIndex.readNextEntry( docid );
					long irObjectID = identifierIndex.getCurrentIROBjectID();
					docids.add( irObjectID );
					String docNo = identifierIndex.getCurrentData();
					System.out.print( docNo+"\t" );
				}
				System.out.println();
				while( invIndex.readNextEntry() > 0 ){
					String term = invIndex.getCurrentTerm();
					PostingListEntry[] postings = invIndex.getPostings();
					int TF  = 0;
					for( int i = 0; i < postings.length; i++ ){
						TF += postings[i].getFrequency();
					}
					
					System.out.print( term +"\t" );
					TLongIntHashMap tmp = new TLongIntHashMap();
					for( int i = 0; i < postings.length; i++){
						long docid = postings[i].getIrObjectID();
						int freq = postings[i].getFrequency();
						tmp.put( docid, freq );
					}
					for( int docid= 0; docid < docids.size(); docid++){
						long irObjectID = docids.get( docid );
						int freq = tmp.get( irObjectID );
						System.out.print(freq+"\t");
					}
					System.out.println();
				}
			}
		}
	}
	
	/**
	 * Main can be invoked with the following options:
	 * <ul>
	 * 		<li>    --help                     <br/>  Displays this help message. </li>
	 *		<li>    -i                         <br/>  Index files, Specified in the, 
	 *			                               <br/>  'flair.documents' and 'flair.descriptions' files. </li>
	 *		<li>    -q                         <br/>  Query the collection based on the 'flair.query.spec' file. </li>
	 *		<li>    -v | --view  docid:docid   <br/>  Shows IRObjects, Specified by a Colon Separated List. </li>
	 *		<li>    --print-terms typeID       <br/>  Shows statistics about the inverted index. </li>
	 *		<li>    --print-docs typeID        <br/>  Shows statistics about the direct index. </li>
	 *		<li>    --print-stat typeID    	 </li>
	 * </ul>
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
			
		if( (args.length == 0 ) 
			|| ( (args.length >0) && args[0].equalsIgnoreCase("--help") ) ){
			displayUsage();
			System.exit(-1);
		}
		
		Settings.initSettings();
		flair_home = Settings.getProperty("flair.home", "");
		flair_etc = Settings.getProperty("flair.etc", "");
		document_description_file = Settings.getProperty("flair.descriptions.file", "");
		inputDocument = Settings.getProperty("flair.documents.file", "");
		stopwordsFile = Settings.stopfilelocation;
		index_path = Settings.getIndexPath();
		docPositionIndex = index_path+Settings.fileSep+"docPosition.index";
		
		Assert.info(true, "Flair Home = "+flair_home+
						  "\nFlair ETC = "+flair_etc+
						  "\nFlair Descriptions File = "+document_description_file+
						  "\nFlair Input File = "+inputDocument+
						  "\nFlair Stopwords File = " +stopwordsFile+
						  "\nFlair Index Path = "+ index_path );
		
		if( args[0].equalsIgnoreCase("-i")){
				doIndexing();
		}else if( args[0].equalsIgnoreCase("-v") || args[0].equalsIgnoreCase("--view")){
			String[] docids = args[1].split(":");
			for(int i = 0; i < docids.length; i++){
				final int docid = Integer.parseInt( docids[i] );
				showDoc(docid);
			}
		}else if( args[0].equalsIgnoreCase("-q") || args[0].equalsIgnoreCase("--query")){
			doQuery();
		}else if( args[0].equalsIgnoreCase("--print-terms") ){
			doPrintTerms( Integer.parseInt( args[1] ) );
		}else if( args[0].equalsIgnoreCase("--print-docs") ){
			doPrintDocs( Integer.parseInt( args[1] ) );
		}else if( args[0].equalsIgnoreCase("--print-stat") ){
			doPrintStat( Integer.parseInt( args[1] ) );
		}else{
			displayUsage();
			System.exit(-1);
		}
	}
}
