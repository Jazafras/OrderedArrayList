import java.lang.reflect.Array;
import java.util.Arrays;

import javax.management.openmbean.ArrayType;


public class OrderedArrayList<T extends Comparable<T>> {

	/** This is an array of Objects of type T */
	private T[] array;


	@SuppressWarnings("unchecked")
	/**
	 * Construct an OrderedArrayList with 10 empty slots.
	 */
	public OrderedArrayList() {
		array = (T[]) new Comparable[10];

	}

	@SuppressWarnings({ "unchecked", "unused" })
	/**
	 * Inserts a new item in the OrderedArrayList. This method ensures
	 * that the list can hold the new item and grow the backing array if
	 * necessary. If the backing array must grow to accommodate the new item, it
	 * grows by a factor of 2. The new item is placed in sorted
	 * order using insertion sort.
	 * 
	 * @return the index at which the item was placed.
	 */
	public int insert(T item) {
		int j,index = -1;
		if (array[array.length-1] != null)
		{
			array = Arrays.copyOf(array, array.length * 2); // re-allocate the array
		}	
		for (j = array.length-1; 0 <= j; j--) //starting from the end of the array
		{	
			if (array[j] != null)
			{	
				if (array[j].compareTo(item) > 0) //if new element should be placed before another element
				{
					array[j+1] = array[j]; //copy old element into the space after it
					array [j] = item; //place new element into the old element's original space
					index = j;
				}
				else //if element should be placed after another element
				{			
					array [j+1] = item; //insert it one space after j
					index = j+1;
					j = 0; //break out of loop
				}
			}
			
		}		
		if (index == -1) //whole array was null, assign to first index
		{
			array[0] = item;
			index = 0;
		}
		return index;		
	}

	/**@return the number of items in the list.**/
	public int size() {
		// TODO: implement this
		int size = 0;
		for (int i = 0; i < array.length; i++) //starts counting from beginning of array
		{
			if (array[i] == null) //when array hits first null, it's reached the end of the list
			{
				size = i; //size is the index of the first null
				break;
			}
		}
		return size;
	}

	/**
	 * Gets an item from the ordered array list.
	 * @param index
	 *            the index to get an item from
	 * @return an item at the specified index
	 */
	public T get(int index) {
		T key;
		key = null;
		if (0 <= index && index <= array.length - 1) //if the value inserted is within the array range
		{
			key = array[index]; //the key is assigned the index item
		}
		else
		{
			throw new IndexOutOfBoundsException("error"); //key is not within the range
		}
		return key;
	}


	/**
	 * Counts the items in the ordered array list that are equal to the item at
	 * the specified index.
	 * 
	 * @param index
	 *            an index in the range 0..(size-1)
	 * @return the number of items in the list equal to the item returned by
	 *         get(index)
	 */
	public int countEquivalent(int index) {
		int counter = 0;
		int i = index;
		if (index < 0 || index > size()) //check if index is not in array range
		{
			return counter;
		}
		while (i < size() && array[i].equals(array[index])) //looking to the right, making sure it doesn't exceed array
		{
			i++;
			counter++; //add to the counter
		}
		i = index-1;
		while (i >= 0 && array[i].equals(array[index])) //looking to the left, making sure it doesn't go below 0
		{
			i--;
			counter++; //add to the counter
		}
		return counter;
	}

	/**
	 * Finds the location of the first object that is equal to the specified
	 * object.
	 * 
	 * @param obj
	 *            an object to search for in the list
	 * @return the first index of an object equal to the one specified, or -1 if
	 *         no such object is found.
	 */
	public int find(T obj) {
		int index;
		for ( index = 0 ; index <= array.length-1; index++) //linear search starting from beginning of array
		{
			if (array[index] == obj) 
			{ 
				return index;
			}
		}
		return -1;
	}

	/**
	 * Removes all the objects equal to the specified object.
	 * 
	 * @param obj
	 *            an object equal to the one(s) you'd like to remove
	 * @return the number of objects removed
	 */
	public int remove(T obj) {
		int counter = 0;
		int test = find(obj);
		while (test >= 0)
		{
			array[test] = null; //make the specified object null
			for (int i = test; i < array.length-1; i++) //check to see if any objects exist after the new null element
			{
				if (array[i+1] != null);
				{
					array[i] = array[i+1]; //shift null until it's at the end of the list
				}
			}
			counter = counter + 1; //add one to the counter
			test = find(obj);
		}
		return counter;
	}
}
