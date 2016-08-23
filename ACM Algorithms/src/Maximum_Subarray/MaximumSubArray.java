/**
 * 
 */
package Maximum_Subarray;

/**
 * @author Trevor Bostic
 * This program will find the maximum sub array within an array in linear time.
 * I have several examples here. I put Kadane's algorithm in the main method if we would need it quickly
 * If we need Kadane's as a method, I created a Tuple class so that we could pass all of the necessary variables
 * Realistically we would use an integer array to hold the maxSum, start, and end, but I want to try to make a tuple
 * Class than could be very generalized and be used whenever situations like this occur.
 * If we only need a maxSum, I made a short method for that
 */
public class MaximumSubArray {

	public static void main(String[] args) {
		//Test Arrays, uncomment to try them out, make your own and see if you can break it,
		//if you do break it let me know, and remember to write down the test case broke it
		int[] arr = {13, -3, -25, 20, -3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
		//int[] arr = {10, -13, -5, -8, -9, -23, -43, -32}; 
		//int[] arr = {-23, -13, -5, -8, -9, 10, -43, -32};
		//int[] arr = {-23, -13, -5, -8, -9, -10, -43, -32};
		//int[] arr = {13};
		//int[] arr = {-13};
		//int[] arr = generateArray(10); //Change second parameter to increase or decrease array size
		
		//Here is code for if we need a quick solution that can go into the main method
		int maxSum = Integer.MIN_VALUE;
		int maxStartIndex = 0;
		int maxEndIndex = 0;
		int currentMaxSum = 0;
		int currentStartIndex = 0;
		
		for(int currentEndIndex = 0; currentEndIndex < arr.length; currentEndIndex++)
		{
			currentMaxSum = currentMaxSum + arr[currentEndIndex]; //Add the next value in the array to our current sum
			if (currentMaxSum > maxSum) //If it is greater than our maxSum
			{
				maxSum = currentMaxSum; //Update maxSum
				maxStartIndex = currentStartIndex; //Update startIndex if it needs it
				maxEndIndex = currentEndIndex; //Update endIndex
			}
			
			if (currentMaxSum < 0) //If our currentMaxSum is < 0, begin checking a new sub array
			{
				currentMaxSum = 0; //Reset the number
				currentStartIndex = currentEndIndex + 1; //Place our current start index at the next spot, this is us starting to check a new subarray
			}
		}
		
		//Just printing all of them to show they are all the same
		for (int num : arr)
			System.out.print(num + ", ");
		
		System.out.println("\nStart Index: " + maxStartIndex + " End Index: " + maxEndIndex + " Sum: " + maxSum);
		
		Tuple t = maxSubArray(arr);
		
		System.out.println("Start Index: " + t.maxStartIndex + " End Index: " + t.maxEndIndex + " Sum: " + t.maxSum);
		
		maxSum = maxSum(arr);
				
		System.out.println("Sum: " + maxSum);

	}
	
	/**
	 * Will generate a random array that can be used for testing the algorithm
	 * @param indices The number of indices to be in the array 
	 * @return The generated array
	 */
	public static int[] generateArray(int indices)
	{
		int[] arr = new int[indices];
		for(int i = 0; i < indices; i++)
		{
			arr[i] = (int) Math.floor(Math.random() * 100);
			if (Math.random() < .5)
				arr[i] = arr[i] * -1;
		}
		return arr;
	}
	
	/**
	 * If we need to implement the maxSubArray as a method this is a pretty easy way
	 * @param arr The entered array
	 * @return A tuple consisting of the maxSum, and start and end indices for the max array
	 * We could use an integer array for this as well as it would be quicker. I want to create a generic tuple class
	 * That could be used for any of these sorts of occasions
	 */
	public static Tuple maxSubArray(int[] arr)
	{
		//Please see comments on this algorithm above for clarification.
		Tuple t = new Tuple(Integer.MIN_VALUE, 0, 0); //Contains our max sum, start and end indices, so we can pass them all back
		int currentMaxSum = 0;
		int currentStartIndex = 0;
		
		for(int currentEndIndex = 0; currentEndIndex < arr.length; currentEndIndex++)
		{
			currentMaxSum = currentMaxSum + arr[currentEndIndex];
			if (currentMaxSum > t.maxSum)
			{
				t.maxSum = currentMaxSum;
				t.maxStartIndex = currentStartIndex;
				t.maxEndIndex = currentEndIndex;
			}
			
			if (currentMaxSum < 0)
			{
				currentMaxSum = 0;
				currentStartIndex = currentEndIndex + 1;
			}
		}
		
		return t;
	}
	
	/**
	 * If we only the the maxSum in the array, and dont need the indices
	 * @param arr The entered array
	 * @return The max sum of the array
	 */
	public static int maxSum(int[] arr)
	{
		int newSum = arr[0];
		int maxSum = arr[0];
		
		for(int i = 1; i < arr.length; i++)
		{
			newSum = Math.max(newSum + arr[i], arr[i]); //Is the newSum greater by adding, or just moving on to the next number, like in the 
														//examples above, if newSum < 0 and arr[i] > 0, then it will start checking a new sub array
			maxSum = Math.max(maxSum, newSum); //Check if newSum is greater than our current max, if so change it.
		}
		return maxSum;
	}
	
	

}
