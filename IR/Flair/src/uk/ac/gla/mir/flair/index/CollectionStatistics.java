package uk.ac.gla.mir.flair.index;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * Title:        CollectionStatistics <br/>
 * Description:  Get the Collection Statistics from a given index <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class CollectionStatistics {

	/*
	 * Since working out the collection stats for various scoring methods during
	 * query time is too slow, the stats are worked out as a final stage of indexing
	 * and stored in a file.
	 * This class writes and retrieves the data in this file.
	 */
	
	protected int NumberOfDocuments; // = identifierIndex.getNumberOfDocuments();
	protected double AverageLengthOfDocuments; // = identifierIndex.getAverageDocLength(); 
	/**
	 * @return the numberOfDocuments
	 */
	public int getNumberOfDocuments() {
		return NumberOfDocuments;
	}
	/**
	 * @param numberOfDocuments the numberOfDocuments to set
	 */
	public void setNumberOfDocuments(int numberOfDocuments) {
		NumberOfDocuments = numberOfDocuments;
	}
	/**
	 * @return the averageLengthOfDocuments
	 */
	public double getAverageLengthOfDocuments() {
		return AverageLengthOfDocuments;
	}
	/**
	 * @param averageLengthOfDocuments the averageLengthOfDocuments to set
	 */
	public void setAverageLengthOfDocuments(double averageLengthOfDocuments) {
		AverageLengthOfDocuments = averageLengthOfDocuments;
	}
	
	public void write(String directIndexFileName) {
		Properties props = new Properties();
		
		props.setProperty("ave.doc.length", AverageLengthOfDocuments+"");
		props.setProperty("number.docs", NumberOfDocuments+"");
		
		try{
			PrintWriter out = new PrintWriter( new FileWriter( directIndexFileName+".stat") );
			props.list( out );
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void read(String directIndexFileName){
		Properties props =new Properties();
		try{
			InputStream inStream = new FileInputStream( directIndexFileName+".stat");
			props.load( inStream );
			inStream.close();
			
			AverageLengthOfDocuments = Double.parseDouble( props.getProperty("ave.doc.length") );
			NumberOfDocuments = Integer.parseInt( props.getProperty("number.docs") );
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
