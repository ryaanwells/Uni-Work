package uk.ac.gla.mir.flair.engine;
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Uncompress {
/* take a compresed file and create a directory with the uncompressed files
 * */
	   // specify buffer size for extraction
	   static final int BUFFER = 2048;
	
	   private static String getExte(File f)
		{
			String exten = new String("");
			int dotPos = f.getName().lastIndexOf(".");
			return (exten = f.getName().substring(dotPos));
		}
	   
	   public static boolean iscompressed(File f)
		{
		   if (".zip".equals(getExte(f)))
			{
				return (true);
			}
			return (false);
		}   
	   
	   public static void unZip(File in, String destD) {
			// specify buffer size for extraction
			final int BUFFER = 2048;
			try {
				// Open Zip file for reading
				ZipFile zipFile = new ZipFile(in, ZipFile.OPEN_READ);
				
				// Create an enumeration of the entries in the zip file
				Enumeration zipFileEntries = zipFile.entries();

				// Process each entry
				while (zipFileEntries.hasMoreElements()) {
					// grab a zip file entry
					ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();

					String currentEntry = entry.getName();
					 System.out.println("Extracting: " + entry);
					 
					File destFile = new File(destD+"/"+in.getName(), currentEntry);
					destFile.deleteOnExit();
					// grab file's parent directory structure
					File destinationParent = destFile.getParentFile();
					// create the parent directory structure if needed
					destinationParent.mkdirs();
					// extract file if not a directory
					if (!entry.isDirectory()) {
						BufferedInputStream is = new BufferedInputStream(zipFile
								.getInputStream(entry));
						int currentByte;
						// establish buffer for writing file
						byte data[] = new byte[BUFFER];

						// write the current file to disk
						FileOutputStream fos = new FileOutputStream(destFile);
						BufferedOutputStream dest = new BufferedOutputStream(fos,
								BUFFER);

						// read and write until last byte is encountered
						while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
							dest.write(data, 0, currentByte);
						}
						dest.flush();
						dest.close();
						is.close();
					}
				}
				zipFile.close();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}	}