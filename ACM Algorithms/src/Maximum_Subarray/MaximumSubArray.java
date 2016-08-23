/**
 * 
 */
package Maximum_Subarray;

/**
 * @author Trevor Bostic
 * This program will find the maximum sub array within an array in linear time.
 */
public class MaximumSubArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//double[] arr = {13, -3, -25, 20, -3,-16,-23,18,20,-7,12,-5,-22,15,-4,7};
		double[] arr = new double[10];
		generateArray(arr, 10); //Change second parameter to increase or decrease array size
		double newSum = arr[0];
		double max = arr[0];
		int startIndex = 0;
		int endIndex = 0;
		
		for(int i = 1; i < arr.length; i++)
		{
 			newSum = Math.max(newSum + arr[i], arr[i]);
  			max = Math.max(max,  newSum);
			if (newSum == arr[i])
				startIndex = i;
			else if (max == newSum)
				endIndex = i;		
		}
		for (double num : arr)
			System.out.print(num + ", ");
		
		System.out.println("\nStart Index: " + startIndex + " End Index: " + endIndex + " Sum: " + (int) max);
		

	}
	
	public static double[] generateArray(double[] arr, int indices)
	{
		for(int i = 0; i < indices; i++)
		{
			arr[i] = Math.floor(Math.random() * 100);
			if (Math.random() < .5)
				arr[i] = arr[i] * -1;
		}
		
		return arr;
	}

}
