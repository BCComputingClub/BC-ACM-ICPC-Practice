/**
 * 
 */
package Greatest_Common_Divisor;

/**
 * @author Trevor Bostic
 * This program is designed to find the GCD of two arguements
 */
public class GCD {


	public static void main(String[] args) {
		//Change these numbers if you want to see other cases
		int num1 = 180; //Try making num2 > num1 see what happens, does it still work?
		int num2 = 48;
		
		System.err.println(Euclid(num1, num2)); //Print out the answer of the algorithm

	}
	/**
	 * The algorithm recursively runs until it finds the greatest common denominator of the two numbers
	 * @param a An integer >= 0
	 * @param b An integer >= 0
	 * @return The GCD of a and b
	 */
	public static int Euclid(int a, int b)
	{
		System.err.println("a: " + a + " b: " + b); //So we can see what the algorithm is doing
		if(b == 0) //If we have reached the common denominator
			return a; //Return the answer
		else //Else recursively check the next
			return Euclid(b, a % b);		
	}

}
