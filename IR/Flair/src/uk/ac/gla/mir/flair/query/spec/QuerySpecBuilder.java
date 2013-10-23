package uk.ac.gla.mir.flair.query.spec;

import gnu.trove.TDoubleArrayList;
import gnu.trove.TIntArrayList;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;


import uk.ac.gla.mir.flair.datamodel.AttributeList;
import uk.ac.gla.mir.flair.util.*;

/**
 * Title:        QuerySpecBuilder <br/>
 * Description:  Reads In the Query Spec File<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class QuerySpecBuilder {

	private static String QuerySpecFileName;
	
	/**
	 * Reads and Builds a Query Spec from the file 
	 * specified by the property <code>flair.query.spec</code>
	 */
	public static QuerySpec loadQuerySpec(){
		QuerySpecFileName = Settings.getProperty("flair.query.spec", "");
		Assert.info(true, "Flair Query Spec File = "+QuerySpecFileName);
		ArrayList<Spec> querySpecs = new ArrayList<Spec>();
		
		File fin = new File(QuerySpecFileName);
		if(fin.exists()){
			final String data = MarkupTokeniser.separateDescTokens(fin);
			//System.out.println(data);
			final StreamTokenizer stk = new StreamTokenizer(new StringReader(data));
			stk.eolIsSignificant(false);
			stk.wordChars(33, 126);
			stk.whitespaceChars(0, 32);
			Spec theSpec;
			
			try{
				//System.out.println( stk.nextToken() +" : "+stk.sval);
				if( stk.nextToken() == -1) return null;
				if(stk.sval.equalsIgnoreCase("<querying>")) {
					while( (theSpec = readQuerySpec(stk) ) != null){
						querySpecs.add(theSpec);
						//System.out.println(theSpec);
					}
				}else{
					Assert.fatal(true, "Query Specification File Malformed.");
				}
			}catch (IOException e) {
				Assert.fatal(true, "Error Readind Query Spec.");
			}
		}else{
			Assert.warnFatal(true, "Flair Query Spec Does Not Exists.");
		}		
		return new QuerySpec(querySpecs);
	}
	
	private static Spec readQuerySpec(StreamTokenizer stk) throws IOException{
		Spec qspec = null;
	
		AttributeList attributeList = new AttributeList();
		if( stk.nextToken() == -1 ) return null;
		while (stk.sval == null)
			if( stk.nextToken() == -1 ) return null;
	

		if(stk.sval.equalsIgnoreCase("<query")){
			//System.out.println(stk.sval);
			String data = "";
			while(true){
				if( stk.nextToken() == -1 ) return null;
				
				if(stk.ttype == StreamTokenizer.TT_WORD){
					data += " "+stk.sval;
					if(stk.sval.contains(">")){
						break;
					}
				}else if(stk.ttype == StreamTokenizer.TT_NUMBER)
					data += " "+((int)stk.nval);
			}
		//	System.out.println("\n *** "+data);
		//	System.out.println(stk.sval);
			
			String startTag = data.trim().substring( data.indexOf(" "), data.indexOf(">") ).trim();
			//System.out.println(startTag);
			//while(startTag.contains(" "))
			//	startTag = startTag.replaceAll(" ", "");
			
			startTag = startTag.replaceAll(" = ", "=");
			startTag = startTag.replaceAll("\"", "");
			startTag = startTag.replaceAll(">", "");
			while(startTag.contains("=="))
				startTag = startTag.replaceAll("==", "=");
			
			//System.out.println(startTag);
			final String[] attribs = startTag.split("=|\\s+");
			//System.out.println(Arrays.toString( attribs) );
			try{
				for(int i = 0; i < attribs.length; i+=2){
					attributeList.setAttributeNameAndValue(attribs[i], attribs[i+1]);
				}
			}catch (Exception e) {
				Assert.warnFatal(true, "Query Specification Malformed. "+attributeList);
				return null;
			}
		
			final String type = attributeList.getAttributeForName("type");
			final String typeTable = attributeList.getAttributeForName("posttype");
			final String weighting = attributeList.getAttributeForName("weighting");
			
			/* Now Get the generic parameters 
			 * Sort the list in ascending order
			 */
			final TDoubleArrayList paramsList = new TDoubleArrayList();
			final String[] names = attributeList.getAttributeNames().toArray( new String[0]);
			Arrays.sort(names, new IntuitiveStringComparator<String>());  
			for( int i = 0; i < names.length; i++){
				final String name = names[i];
				if( name.toLowerCase().startsWith("param") ){
					final String param = attributeList.getAttributeForName( name );
					paramsList.add( Double.parseDouble( param ) );
				}
			}
			
			if(type != null && typeTable != null && weighting != null){
				final int typeTableID = Integer.parseInt( attributeList.getAttributeForName("posttype") );
				//Read in Text Query
				qspec = readTextQuerySpec(stk, type.equalsIgnoreCase("TREC"));
				qspec.setTypeTableID(typeTableID);
				qspec.setWeighting( weighting );
				qspec.setParams( paramsList.toNativeArray() );
				if ( type.equalsIgnoreCase("TREC") )
					qspec.setQueryID( attributeList.getAttributeForName("topicID") );
			
			}else
				Assert.warnFatal(true, "Query Specification Malformed. No 'type' or 'posttype' specified.");
		}
		
		return qspec;
	}
	
	/**
	 * 
	 * @param stk
	 * @param TREC
	 * @return the text/TREC query spec
	 * @throws IOException
	 */
	private static Spec readTextQuerySpec(StreamTokenizer stk, boolean TREC) throws IOException{
		Spec qspec = null;
		final TIntArrayList ids = new TIntArrayList();
		String theTerms = null;
		
		if( stk.nextToken() == -1 ) return null;
		
		while( true ){
	//		System.out.println(stk.ttype + " : "+stk.sval + " " + stk.nval);
			
			if( stk.ttype == StreamTokenizer.TT_WORD){
				
				if(stk.sval.toUpperCase().equalsIgnoreCase("<ID>")){
					if( stk.nextToken() == -1 ) return null;
					if( stk.ttype != StreamTokenizer.TT_NUMBER) return null;
					else
						ids.add( (int)stk.nval );
				}else if( stk.sval.endsWith("<terms>")){
					final String terms = readTerms(stk);
					
					if(terms == null)
						Assert.warnFatal(true, "Query Specification Malformed.");
					else{
						theTerms = terms;
					}					
				}
			}
			if(stk.ttype == StreamTokenizer.TT_WORD )
				if(stk.sval.toLowerCase().equalsIgnoreCase("</query>")){
					break;
				}				
			if( stk.nextToken() == -1 ) return null;
		}
		qspec = TREC ? new TRECQuerySpec( ids, theTerms) : 
					   new TextQuerySpec( ids, theTerms);
		return qspec;
	}
	
	/**
	 * Reads the Terms of a Text Query
	 * @param stk The Stream Tokenizer to get terms
	 * @return the Query
	 * @throws IOException
	 */
	public static String readTerms(StreamTokenizer stk) throws IOException{
		final StringBuffer terms = new StringBuffer();
		if( stk.nextToken() == -1 ) return null;
		
		while( true ){
			//		System.out.println(stk.ttype + " : "+stk.sval + " " + stk.nval);
					
			if( stk.ttype == StreamTokenizer.TT_WORD){
				if(!stk.sval.equalsIgnoreCase("</terms>"))
					terms.append(stk.sval+" ");
				else
					return terms.toString();
			}else if(stk.ttype == StreamTokenizer.TT_NUMBER){
				terms.append(stk.nval+" ");
			}
			
			if( stk.nextToken() == -1 ) return null;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Settings.initSettings();
		loadQuerySpec();
	}

}
