package uk.ac.gla.mir.flair.index.spec;

import gnu.trove.TIntArrayList;

import java.io.*;
import java.util.*;

import uk.ac.gla.mir.flair.datamodel.AttributeList;
import uk.ac.gla.mir.flair.util.*;

/**
 * Title:        IndexSpecBuilder <br/>
 * Description:  Reads In the Index Spec File<br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class IndexSpecBuilder {
	
	private static String indexSpecFileName;
	private static ArrayList<InvertedIndexSpec> invertedIndexSpectList;
	private static ArrayList<DirectIndexSpec> directIndexSpecList;
	
	/**
	 * Reads and Builds an Index Spec from file
	 * @param indexSpecFileName The Index Spec FileName to read from
	 */
	public static IndexSpec loadIndexSpec(final String indexSpecFileName){
		
		Assert.info(true, "Flair Index Spec File = "+indexSpecFileName);
		invertedIndexSpectList = new ArrayList<InvertedIndexSpec>();
		directIndexSpecList = new ArrayList<DirectIndexSpec>();
		
		Spec theSpec;
		
		File fin = new File(indexSpecFileName);
		if(fin.exists()){
			Assert.info(true, "Flair Index Spec File Exists.");
			final String data = MarkupTokeniser.separateDescTokens(fin);
			//System.out.println(data);
			final StreamTokenizer stk = new StreamTokenizer(new StringReader(data));
			stk.eolIsSignificant(false);
			stk.wordChars(33, 126);
			stk.whitespaceChars(0, 32);
			
			try{
				//System.out.println( stk.nextToken() +" : "+stk.sval);
				if( stk.nextToken() == -1) return null;
				if(stk.sval.equalsIgnoreCase("<indexing>")) {
					while( (theSpec = readIndexSpec(stk) ) != null){
						if( theSpec != null){
					
							if (theSpec instanceof InvertedIndexSpec) {
								invertedIndexSpectList.add((InvertedIndexSpec)theSpec);
							}else
								directIndexSpecList.add((DirectIndexSpec)theSpec);
							
						}
					}
				}else{
					Assert.fatal(true, "Index Specification File Malformed.");
				}
			}catch (IOException e) {
				Assert.fatal(true, "Error Readind Index Spec.");
			}
		}else{
			Assert.warn(true, "Flair Index Spec Does Not Exists.");
		}		
		return new IndexSpec(invertedIndexSpectList,directIndexSpecList);//indexSpectList;
	}
	
	/**
	 * Reads and Builds an Index Spec from the file 
	 * specified by the property <code>flair.index.spec</code>
	 */
	public static IndexSpec loadIndexSpec(){
		
		indexSpecFileName = Settings.getProperty("flair.index.spec", "");
		Assert.info(true, "Flair Index Spec File = "+indexSpecFileName);
	
		invertedIndexSpectList = new ArrayList<InvertedIndexSpec>();
		directIndexSpecList = new ArrayList<DirectIndexSpec>();
		Spec theSpec;
		
		File fin = new File(indexSpecFileName);
		if(fin.exists()){
			final String data = MarkupTokeniser.separateDescTokens(fin);
			//System.out.println(data);
			final StreamTokenizer stk = new StreamTokenizer(new StringReader(data));
			stk.eolIsSignificant(false);
			stk.wordChars(33, 126);
			stk.whitespaceChars(0, 32);
			
			try{
				//System.out.println( stk.nextToken() +" : "+stk.sval);
				if( stk.nextToken() == -1) return null;
				if(stk.sval.equalsIgnoreCase("<indexing>")) {
					while( (theSpec = readIndexSpec(stk) ) != null){
						if( theSpec != null){
					
							if (theSpec instanceof InvertedIndexSpec) {
								invertedIndexSpectList.add((InvertedIndexSpec)theSpec);
							}else
								directIndexSpecList.add((DirectIndexSpec)theSpec);
							
						}
					}
				}else{
					Assert.fatal(true, "Index Specification File Malformed.");
				}
			}catch (IOException e) {
				Assert.fatal(true, "Error Readind Index Spec.");
			}
		}else{
			Assert.warnFatal(true, "Flair Index Spec Does Not Exists.");
		}		
		return new IndexSpec(invertedIndexSpectList,directIndexSpecList);
	}
	
	private static Spec readIndexSpec(StreamTokenizer stk) throws IOException{
		Spec tmpSpec = null;	
		
		AttributeList attributeList = new AttributeList();
		if( stk.nextToken() == -1 ) return null;
		while (stk.sval == null)
			if( stk.nextToken() == -1 ) return null;
		//stk.pushBack();
		
		if(stk.sval.equalsIgnoreCase("<index")){
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
				Assert.warnFatal(true, "Index Specification Malformed.");
				return null;
			}
			
			if( attributeList.getAttributeForName("invname") != null ){
				tmpSpec = new InvertedIndexSpec();
				((InvertedIndexSpec)tmpSpec).setAttributes( attributeList );
			}else{
				tmpSpec = new DirectIndexSpec();
				((DirectIndexSpec)tmpSpec).setAttributes( attributeList );
			}
				
			TIntArrayList ids = new TIntArrayList();
			data = "";
			if( stk.nextToken() == -1 ) return null;
			
			while( true ){
		//		System.out.println(stk.ttype + " : "+stk.sval + " " + stk.nval);
				
				if( stk.ttype == StreamTokenizer.TT_WORD){
					
					if(stk.sval.toUpperCase().equalsIgnoreCase("<ID>")){
						if( stk.nextToken() == -1 ) return null;
						if( stk.ttype != StreamTokenizer.TT_NUMBER) return null;
						else
							ids.add( (int)stk.nval );
					}
				}
				if(stk.ttype == StreamTokenizer.TT_WORD )
					if(stk.sval.toLowerCase().equalsIgnoreCase("</index>")){
						tmpSpec.setDataElementIDs(ids);	
						break;
					}				
				if( stk.nextToken() == -1 ) return null;
			}
			//System.out.println("\n" + " *** " +data);
		}else if(stk.sval.equalsIgnoreCase("</indexing>") || stk.sval.trim().equalsIgnoreCase(""))
			return null;
		else{
			Assert.warnFatal(true, "Index Specification Malformed.");
		}
		return tmpSpec;
	}
	
	public static void main(String[] args) {
		
	
		System.setProperty("flair.home", "Y:\\FlairGrammer");

		Settings.initSettings();
	
		indexSpecFileName = Settings.getProperty("flair.index.spec", "D:\\flairfiles\\index\\test.txt");
		
		IndexSpec indexSpec = loadIndexSpec();
		
		Iterator<InvertedIndexSpec> invIterator = indexSpec.invertedSpecIterator();
		while(invIterator.hasNext()){
			InvertedIndexSpec invertedIndexSpec = invIterator.next();
			
			System.out.println( invertedIndexSpec.getIndexName() +"\n"+
								invertedIndexSpec.getTokenType() +"\n"+
								invertedIndexSpec.getStemType() +"\n"+
								invertedIndexSpec.getStopType() +"\n"+
								invertedIndexSpec.getTypeTableID() +"\n"+
								invertedIndexSpec.getDataElementIDs() );
		}
		
		Iterator<DirectIndexSpec> directIterator = indexSpec.directSpecIterator();
		while(directIterator.hasNext()){
			DirectIndexSpec directIndexSpec = directIterator.next();
			
			System.out.println( directIndexSpec.getIndexName() +"\n"+
					directIndexSpec.getTokenType() +"\n"+
					directIndexSpec.getStemType() +"\n"+
					directIndexSpec.getStopType() +"\n"+
					directIndexSpec.getTypeTableID() +"\n"+
					directIndexSpec.getDataElementIDs() );
		}
	}
}
