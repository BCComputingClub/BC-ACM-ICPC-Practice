package QuickSort; 
/**
 * 	@author Austin Lane
 *	Quicksort sorts an array of comparable objects into ascending order by comparing a length of 
 *	the array to a pivot.
 *	There's no main method, you can just pass an array into the run method in a driver program.
 */
public class Quicksort 
{	
	/**
	 * This method can be used in the driver program to run the Quicksort algorithm
	 * @param array = array of comparable objects that needs to be sorted
	 * @return sorted array of comparable objects
	 */
	public static Comparable[] run(Comparable[] array)
	{
		sort(array, 0, array.length - 2, array.length - 1);
		for (int i = 0; i < array.length; i++)
			System.out.println(array[i]);
		
		return array;
	}
	
	/**
	 * Recursive method that compares a length of the array to the pivot
	 * @param ar = full array of comparable objects
	 * @param start = leftmost index in the array this step will search
	 * @param end = rightmost index in the array (left of the pivot) this step will search
	 * @param pivot = index of the element that will be used for comparisons (rightmost element)
	 */
	private static void sort(Comparable[] ar, int start, int end, int pivot)
	{
		int left = start;	// pointer for the left index
		int right = end;	// pointer for the right index
		boolean move;		// used to control a loop
		Comparable var;		// used to swap two elements
		
		// only runs if there there is something to compare the pivot to
		if (start < pivot && start >= 0)
		{
			while (left != right)
			{
				// if left element is bigger than the pivot it may be moved
				if (ar[left].compareTo(ar[pivot]) > 0)
				{
					move = false;
					// left element is only moved if there is a right element that is less than the pivot
					while(left != right && !move)
					{
						if (ar[right].compareTo(ar[pivot]) < 0)
						{
							/* if the right element is nor less than the pivot the pointer moves until
							   it finds the less than element or the left element */
							var = ar[left];
							ar[left] = ar[right];
							ar[right] = var;
							left++;
							move = true;
						}
						else
							right--;
					}
				}
				// if the left element is not 
				else
					left++;
			}
			
			/* once the left and right pointers if the pivot is less than the pointer element
			   the pivot and pointer elements are swapped, the pivot will not be moved again */
			if (ar[pivot].compareTo(ar[left]) < 0)	
			{
				var = ar[pivot];
				ar[pivot] = ar[left];
				ar[left] = var;
			}
			
			sort(ar, start, left - 1, left);
			sort(ar, left + 1, end, pivot);
		}
	}
}
