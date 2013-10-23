package uk.ac.gla.mir.flair.irmodel.filter;

/*
 * The Original Code is StopWords.java.
 * from
 * Terrier - Terabyte Retriever 
 * Webpage: http://ir.dcs.gla.ac.uk/terrier 
 * Contact: terrier{a.}dcs.gla.ac.uk
 * University of Glasgow - Department of Computing Science
 * http://www.gla.ac.uk/
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is Copyright (C) 2004-2008 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Craig Macdonald <craigm{a.}dcs.gla.ac.uk> (original author)
 *   David Hannah <davidh@dcs.gla.ac.uk> (modified)
 */


import java.io.*;

import uk.ac.gla.mir.flair.datamodel.dataElement.DataElement;
import uk.ac.gla.mir.flair.datamodel.dataElement.SequenceDE;
import uk.ac.gla.mir.flair.util.*;
import gnu.trove.THashSet;

public class Stopper extends Filter{

	/** The hashset that contains all the stop words.*/
	protected final THashSet<String> stopWords = new THashSet<String>();
	
	public Stopper(){
		loadStopwordsList( Settings.stopfilelocation);
	}
	/**
	 * Loads the Stopwords from File
	 * @param stopwordsFilename
	 */
	public void loadStopwordsList(String stopwordsFilename)
	{
		try {
			BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(stopwordsFilename)));
			String word;
			while ((word = br.readLine()) != null)
			{
				word = word.trim();
				if (word.length() > 0)
				{
					stopWords.add(word);
				}
			}
			br.close();
		} catch (IOException ioe) {
			Assert.fatal(true, "Errror: Input/Output Exception while reading stopword list ("+stopwordsFilename+")");
			
		}
		if (stopWords.size() == 0)
            Assert.warn(true, "Error: Empty stopwords file was used ("+stopwordsFilename+")");
	}
	
	/* (non-Javadoc)
	 * @see irmodel.filter.Filter#doFilter(datamodel.dataElement.DataElement)
	 */
	@Override
	public DataElement doFilter(DataElement de) {
		//I Should get a SeqDE of Single Strings
		try{
			final SequenceDE terms = (SequenceDE)de;
			final int size =  terms.size();
			for(int i = size-1; i >= 0; i--){
				final String term = terms.getElementAt(i).getCharValue();
				if( stopWords.contains( term ) )
					terms.removeDataElement( terms.getElementAt(i) );
			}
		}catch (ClassCastException cce) {
			Assert.fatal(true, "Stopper expects a SequenceDE of Strings.");
		}
		return de;
	}

	/* (non-Javadoc)
	 * @see irmodel.filter.Filter#getName()
	 */
	@Override
	public String getName() {
		return "irmodel.filter.Stopper";
	}

}
