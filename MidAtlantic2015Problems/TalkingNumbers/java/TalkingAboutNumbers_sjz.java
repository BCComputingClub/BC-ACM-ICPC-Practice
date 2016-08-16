/**
 * Sample solution for the Refract Facts problem.
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TalkingAboutNumbers_sjz {


	final long million = 1000000;
	final long thousand = 1000;


	String[] digits = {"zero", "one", "two", "three", "four",
			"five", "six", "seven", "eight", "nine"};

	String[] tens = {"", "", "twenty", "thirty", "forty",
			"fifty", "sixty", "seventy", "eighty", "ninety"};

	String[] teens = {"ten", "eleven", "twelve", "thirteen", "fourteen",
			"fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};

	void print999 (long k, boolean prior)
	{
		if (k >= 100)
		{
			int hundreds = (int)(k / 100);
			System.out.print (digits[hundreds] + " hundred");
			if (k % 100 > 0)
			{
				System.out.print (" and ");
			}
		}
		int m = (int)(k % 100);
		if (m >= 20) {
			System.out.print(tens[m / 10]);
			int d = m % 10;
			if (d > 0)
			{
				System.out.print ("-" + digits[d]);
			}
		}
		else if (m >= 10){
			System.out.print(teens[m-10]);
		}
		else if (m > 0)
		{
			System.out.print(digits[m]);
		}
		else
		{
			if (!prior && k == 0)
				System.out.print("zero");
		}
	}


	void print (long N) {
		boolean printedMillions = false;
		if (N >= million)
		{
			printedMillions = true;
			long millionsPart = N / million;
			N = N % million;
			print999 (millionsPart, false);
			System.out.print(" million");
		}
		boolean printedThousands = false;
		if (N >= thousand)
		{
			printedThousands = true;
			int thousandsPart = (int)(N / thousand);
			N = N % thousand;
			if (printedMillions)
			{
				System.out.print(", ");
			}
			print999 (thousandsPart, printedMillions);
			System.out.print(" thousand");
		}
		if (N > 99)
		{
			if (printedMillions || printedThousands)
			{
				System.out.print(", ");
			}
			print999 (N, printedMillions || printedThousands);
		}
		else if (N > 0)
		{
			if (printedMillions || printedThousands)
			{
				System.out.print(" and ");
			}
			print999 (N, printedMillions || printedThousands);

		}
		else // N == 0
		{
			if ((!printedMillions) && (!printedThousands))
			{
				System.out.print("zero");
			}

		}
		System.out.println();
	}



	void solve (Scanner in)
	{
		long N = in.nextLong();
		while (N >= 0L) {
			print(N);
			N = in.nextLong();
		}
	}








	/**
	 * Run the program.  Because redirecting input is awkward in Eclipse,
	 *    if a command line parameter is given, it is treated as the name
	 *    of an input file. If no command parameter is given, input is taken
	 *    from System.in. 
	 * @param args  Command line parameters  
	 * @throws IOException 
	 */
	public static void main (String[] args) throws IOException
	{
		Scanner input;
		if (args.length == 0)
			input = new Scanner(System.in);
		else
			input = new Scanner(new File(args[0]));

		new TalkingAboutNumbers_sjz().solve(input);

		input.close();
	}
}
