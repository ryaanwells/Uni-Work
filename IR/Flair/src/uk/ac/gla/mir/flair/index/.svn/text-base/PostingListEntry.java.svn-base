package uk.ac.gla.mir.flair.index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Title:        PostingListEntry <br/>
 * Description:  An Entry in the Posting List <br/>
 * Company:      Department of Computing Science, University of Glasgow<br/>
 * @author David Hannah
 * @version 1.0
 */
public class PostingListEntry implements Comparable{

	/** The IRObject ID of this Entry **/
	private long irObjectID;
	
	/** The frequency of X occuring in the IRObject **/
	private int frequency;
	
	public PostingListEntry(){}
	
	public PostingListEntry(long irObjectID, int frequency){
		this.irObjectID = irObjectID;
		this.frequency = frequency;
	}
	
	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the irObjectID
	 */
	public long getIrObjectID() {
		return irObjectID;
	}

	/**
	 * @param irObjectID the irObjectID to set
	 */
	public void setIrObjectID(long irObjectID) {
		this.irObjectID = irObjectID;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object arg0) {
		PostingListEntry arg = (PostingListEntry) arg0;
		
		if(this.frequency > arg.frequency){
			return -1;
		}
		
		if( this.frequency == arg.frequency){
			return 0;
		}else
			return 1;
		
	}

	public String toString(){
		return ""+irObjectID+" : "+frequency;
	}
	
	/**
	 * Returns an Array of PostingListEntries with <code>postings1 and postings2</code> Merged
	 * @param postings1
	 * @param postings2
	 * @return Returns a merged PostingList
	 */
	public static PostingListEntry[] merge(PostingListEntry[] postings1, PostingListEntry[] postings2){
		ArrayList<PostingListEntry> tmp = new ArrayList<PostingListEntry>();
		//System.out.println( Arrays.toString( postings1 ));
		//System.out.println( Arrays.toString( postings2 ));
		
		//I need Posting Entries Sorted by DocumentID to merge
		Comparator<PostingListEntry> sortByIRObjectID = new Comparator<PostingListEntry>(){
			/* (non-Javadoc)
			 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
			 */
			public int compare(PostingListEntry o1, PostingListEntry o2) {
				if(o1.irObjectID > o2.irObjectID){
					return -1;
				}
				if( o1.irObjectID == o2.irObjectID){
					return 0;
				}else
					return 1;
			}
		};
			
		Arrays.sort( postings1, sortByIRObjectID);
		Arrays.sort( postings2, sortByIRObjectID);
		
		int i = 0; int j = 0;
		while( i < postings1.length && j < postings2.length){
			final PostingListEntry posting1 = postings1[i];
			final PostingListEntry posting2 = postings2[j];
			
			if(posting1.irObjectID == posting2.irObjectID){
				posting1.frequency += posting2.frequency;
				tmp.add(posting1);
				i++; j++;
			}else{
				
				if(posting1.irObjectID >= posting2.irObjectID){
					tmp.add(posting1);
					i++;
				}else{
					tmp.add(posting2);
					j++;
				}
			}
		}
		
		while( i < postings1.length ){
			tmp.add(postings1[i++]);
		}
		
		while( j < postings2.length ){
			tmp.add(postings2[j++]);
		}
		PostingListEntry[] tmpArray = tmp.toArray(new PostingListEntry[0]);
		Arrays.sort( tmpArray );
		return tmpArray;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PostingListEntry posting1 = new PostingListEntry(1,2);
		PostingListEntry posting2 = new PostingListEntry(2,2);
		PostingListEntry posting3 = new PostingListEntry(3,1);
		PostingListEntry posting4 = new PostingListEntry(1,1);
		
		PostingListEntry[] postings = new PostingListEntry[]{posting1,posting2,posting3};
		PostingListEntry[] postings2 = new PostingListEntry[]{posting4, new PostingListEntry(4,7) };
		
		Arrays.sort(postings);
		
		PostingListEntry[] postings3 = merge(postings, postings2);
		System.out.println(Arrays.toString(postings3));
	}

}
