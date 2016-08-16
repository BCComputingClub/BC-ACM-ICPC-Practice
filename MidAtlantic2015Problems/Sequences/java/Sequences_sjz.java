/**
 * Sample solution for the Sequences problem.
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Sequences_sjz {

	static Logger LOG = Logger.getLogger(Sequences_sjz.class.getName());

	static {
		Handler h = new ConsoleHandler();
		LOG.setLevel(Level.INFO);  // Change to INFO/FINER to toggle debug output
		h.setLevel(Level.FINER);
		LOG.addHandler(h);
	}

	boolean validate (int a[])
	{
		for (int i = 0; i < 4; ++i)
			if (a[i] <= 0)
				return false;


		// Check for arithmetic sequence
		boolean OK = true;
		int k = a[1] - a[0];
		for (int i = 2; i < 4; ++i)
			if (a[i] != a[0] + i * k)
				OK = false;
		if (!OK) {
			OK = true;
			// Check for geometric sequence
			k = a[1] / a[0];
			if (a[1] % a[0] != 0)
				return false;
			for (int i = 2; i < 4; ++i)
				if (a[i] != a[i-1] * k)
					OK = false;
		}
		return OK;
	}


	void solve0(int a[])
	{
		int k = a[2] - a[1];
		a[0] = a[1] - k;
		if (!validate(a)) {
			k = a[2] / a[1];
			a[0] = a[1] / k;
			if (!validate(a))
			{
				a[0] = -1;
			}
		}
	}

	void solve1(int a[])
	{
		int k = a[3] - a[2];
		a[1] = a[2] - k;
		if (!validate(a)) {
			k = a[3] / a[2];
			a[1] = a[2] / k;
			if (!validate(a))
			{
				a[1] = -1;
			}
		}
	}

	void solve2(int a[])
	{
		int k = a[1] - a[0];
		a[2] = a[1] + k;
		if (!validate(a)) {
			k = a[1] / a[0];
			a[2] = a[1] * k;
			if (!validate(a))
			{
				a[2] = -1;
			}
		}
	}

	void solve3(int a[])
	{
		int k = a[1] - a[0];
		a[3] = a[2] + k;
		if (!validate(a)) {
			k = a[1] / a[0];
			a[3] = a[2] * k;
			if (!validate(a))
			{
				a[3] = -1;
			}
		}
	}



	public void solve(int[] a)
	{
		int openPos = 0;
		for (int i = 0; i < 4; i++)
			if (a[i] < 0)
				openPos = i;
		if (openPos == 0)
			solve0(a);
		else if (openPos == 1)
			solve1(a);
		else if (openPos == 2)
			solve2(a);
		else
			solve3(a);

		if (a[openPos] >= 0)
		{
			if (validate(a))
			{
				System.out.println (a[openPos]);
			}
			else
			{
				System.out.println (-1);
			}
		}
		else
		{
			System.out.println (-1);
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

		int[] seq = new int[4];
		for (int i = 0; i < 4; ++i)
			seq[i] =  input.nextInt();
		while (seq[0] > 0 || seq[1] > 0) {
			Sequences_sjz problem = new Sequences_sjz ();
			problem.solve(seq);
			for (int i = 0; i < 4; ++i)
				seq[i] =  input.nextInt();
		}
		input.close();
	}
}
