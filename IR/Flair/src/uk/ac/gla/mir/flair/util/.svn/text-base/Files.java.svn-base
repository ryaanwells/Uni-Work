package uk.ac.gla.mir.flair.util;

import java.io.*;
import java.util.zip.*;

/* 
 * The Original Code is Files.java.
 * From Terrier - Terabyte Retriever
 * Webpage: http://ir.dcs.gla.ac.uk/terrier
 * Contact: terrier{a.}dcs.gla.ac.uk
 * University of Glasgow - Department of Computing Science
 * http://www.ac.gla.uk
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
 *
 * The Original Code is Copyright (C) 2004-2008 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Gianni Amati <gba{a.}fub.it> (original author)
 *   Vassilis Plachouras <vassilis{a.}dcs.gla.ac.uk>
 *   Ben He <ben{a.}dcs.gla.ac.uk>
 *   Craig Macdonald <craigm{a.}dcs.gla.ac.uk>
 */
/**
* Title:        Files <br/>
* Description:  Provides utility functions of open files for 
* reading and writing.<br/>
*/
public class Files {

	/** Opens a reader to the file called file. Provided for easy overriding for encoding support etc in 
	  * child classes. Called from openNextFile().
	  * @param file File to open.
	  * @return BufferedReader of the file
	  */
	public static BufferedReader openFileReader(File file) throws IOException
	{
		return openFileReader(file.getPath(),null);
	}

	/** Opens a reader to the file called filename. Provided for easy overriding for encoding support etc in
	  * child classes. Called from openNextFile().
	  * @param file File to open.
	  * @param charset Character set encoding of file. null for system default.
	  * @return BufferedReader of the file
	  */
	public static BufferedReader openFileReader(File file, String charset) throws IOException
	{
		return openFileReader(file.getPath(), charset);
	}

	/** Opens a reader to the file called filename. Provided for easy overriding for encoding support etc in
	  * child classes. Called from openNextFile().
	  * @param filename File to open.
	  * @return BufferedReader of the file
	  */
	public static BufferedReader openFileReader(String filename) throws IOException
	{
		return openFileReader(filename,null);
	}
	

	/** Opens a reader to the file called filename. Provided for easy overriding for encoding support etc in 
	  * child classes. Called from openNextFile().
	  * @param filename File to open.
	  * @param charset Character set encoding of file. null for system default.
	  * @return BufferedReader of the file
	  */
	public static BufferedReader openFileReader(String filename, String charset) throws IOException
	{
		BufferedReader rtr = null;
		if (filename.toLowerCase().endsWith("gz")) {
			rtr = new BufferedReader(
				 charset == null				
				? new InputStreamReader(new GZIPInputStream(new FileInputStream(filename)))
				: new InputStreamReader(new GZIPInputStream(new FileInputStream(filename)), charset)
			);
		} else {
			rtr = new BufferedReader(
				charset == null 
					? new FileReader(filename)
					: new InputStreamReader(new FileInputStream(filename), charset)
				);
		}
		return rtr;
	}
	
	/** Opens an Writer to a file called file.
	  * @param filename File to open.
	  * @param charset Character set encoding of file. null for system default.
	  * @return Writer of the file
	  */
	public static Writer writeFileWriter(String filename, String charset) throws IOException
	{
		BufferedWriter rtr = null;
		if (filename.toLowerCase().endsWith("gz")) {
			rtr = new BufferedWriter(
				 charset == null
				? new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(filename)))
				: new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(filename)), charset)
			);
		} else {
			rtr = new BufferedWriter(
				charset == null
					? new FileWriter(filename)
					: new OutputStreamWriter(new FileOutputStream(filename), charset)
				);
		}
		return rtr;		
	}
	
}
