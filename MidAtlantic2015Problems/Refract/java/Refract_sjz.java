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


public class Refract_sjz {


	double requiredPrecision = 0.001 * Math.PI / 180.0;


	void solve (Scanner in)
	{
		double d = in.nextDouble();
		double h = in.nextDouble();
		double x = in.nextDouble();
		double n1 = in.nextDouble();
		double n2 = in .nextDouble();

		while (n1 >= 1.0) {

			double phiMax= Math.PI / 2.0;
			double phiMin = 0.0;
			double phi;

			while (phiMax - phiMin > requiredPrecision)
			{
				phi = (phiMin + phiMax) / 2.0;
				double x1 = d / Math.tan(phi);
				double theta1 = Math.PI / 2.0 - phi;
				double theta2 = Math.asin((n2 / n1) * Math.sin(theta1));
				double x2 = h * Math.tan(theta2);
				if (x1 + x2 > x)
				{
					// phi is too shallow
					phiMin = phi;
				}
				else
				{
					// phi is too steep
					phiMax = phi;
				}
			}
			phi = (phiMin + phiMax) / 2.0;
			double phiInDegrees = 180.0 * phi / Math.PI;
			System.out.printf("%.2f%n", phiInDegrees);
			
			d = in.nextDouble();
			h = in.nextDouble();
			x = in.nextDouble();
			n1 = in.nextDouble();
			n2 = in .nextDouble();

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

		new Refract_sjz().solve(input);

		input.close();
	}
}
