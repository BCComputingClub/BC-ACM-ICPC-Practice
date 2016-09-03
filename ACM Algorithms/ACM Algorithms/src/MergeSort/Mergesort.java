package MergeSort;
/**
 * 	@author Austin Lane
 *	
 *	There's no main method, you can just pass an array into the run method in a driver program.
 */
public class Mergesort 
{	
	/**
	 * This method can be used in the driver program to run the Mergesort algorithm
	 * @param array = array of comparable objects that needs to be sorted
	 * @return sorted array of comparable objects
	 */
	public static Comparable[] run(Comparable[] array)
	{
		Comparable[] temp = new Comparable[array.length];
		
		split(array, temp, 0, array.length - 1);
		for(int i = 0; i < array.length; i++)
			System.out.println(array[i]);
		
		return array;
	}
	
	/**
	 * Recursive method that splits an array until each section has one element
	 * @param ar = array of comparable objects that needs to be sorted
	 * @param tp = array of that will hold duplicate comparable objects
	 * @param beginning = index this section begins at
	 * @param end = index this section ends at
	 */
	private static void split(Comparable[] ar, Comparable[] tp, int beginning, int end)
	{
		int center = (beginning + end) / 2;	// center indexd of section of array

		// continues splitting until it reaches the end of the array
		if (beginning < end)
		{
			split(ar, tp, beginning, center);
			split(ar, tp, center + 1, end);
			merge(ar, tp, beginning, center + 1, end);
		}
	}
	
	/**
	 * Method to sort and merge two sections of array
	 * @param a = array of comparable objects that needs to be sorted
	 * @param t = array of that will hold duplicate comparable objects
	 * @param begin = index this section begins at
	 * @param end = index this section ends at
	 * @param rightBound
	 */
	private static void merge(Comparable[] a, Comparable[] t, int begin, int end, int rightBound)
	{
		int leftBound = end - 1;
		int index = begin;				// pointer for left index
		int num = rightBound - begin + 1;
		
		while(begin <= leftBound && end <= rightBound)
		{
            if(a[begin].compareTo(a[end]) <= 0)
                t[index++] = a[begin++];
            else
                t[index++] = a[end++];
		}
		
		// copies temporary array to the final array
		while(begin <= leftBound)
            t[index++] = a[begin++];

        while(end <= rightBound)
            t[index++] = a[end++];
        
        for(int i = 0; i < num; i++, rightBound--)
            a[rightBound] = t[rightBound];
	}
}
