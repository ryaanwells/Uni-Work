package uk.ac.gla.mir.flair.util;

import java.util.*;

/**
 * Title:        KeyValue <br/>
 * Description:  This generic class stores a key and a value,
 * allowing document IDs to be sorted by some value.
 * @author David Hannah
 */
public class KeyValue<T extends Comparable, Z> implements Comparable<KeyValue>{
	
	/** Integer ID Value */
	private Z key;
	
	/** Generic Value **/
	private T value;	
	
	public KeyValue(Z key, T value){
		this.key = key;
		this.value = value;
	}
	
	/**
	 * @return the key
	 */
	public Z getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(Z key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(KeyValue otherKeyValue) {
		return -1* value.compareTo(otherKeyValue.value);
	}

	/**
	 * Sort an ArrayList of KeyValues
	 * @param listToSort
	 * @return a sorted array of KeyValue
	 */
	public KeyValue<T,Z>[] sort( ArrayList<KeyValue<T,Z>> listToSort){
		KeyValue<T,Z>[] tmp = listToSort.toArray( new KeyValue[0] );
		return sort(tmp);
	}	
	
	public KeyValue<T,Z>[] sort(KeyValue<T,Z>[] listToSort){
		Arrays.sort(listToSort);
		return listToSort;
	}

	public String toString(){
		return key + " : " + value;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		KeyValue<Double,Integer>[] tmp = new KeyValue[2];
		tmp[0] = new KeyValue<Double,Integer>(1,2.0);
		tmp[1] = new KeyValue<Double,Integer>(2, 1.0);
		
		Arrays.sort( tmp );
		
		System.out.println( Arrays.toString( tmp ));
	}
}
