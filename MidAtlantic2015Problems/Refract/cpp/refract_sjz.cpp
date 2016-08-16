/*************************************************************
 **                                                          **
 **  Sample solution for "Refract Facts"                   **
 **       Steven J Zeil                                      **
 **       10/18/2015                                         **
 **                                                          **
 *************************************************************/


#include <algorithm>
#include <cassert>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <sstream>
#include <cmath>


using namespace std;

const double PI = 3.141592653589793238463;
const double requiredPrecision = 0.001 * PI / 180.0;

/**
 * This function is not part of the sample solution per se, but is used by the
 * judges to check for and eliminate test cases that would be sensitive to small
 * rounding errors
 */
void checkSensitivity(double d, double h, double x, double n1, double n2, double phiInDegrees)
{
	static int testCounter = 0;
	ostringstream out;
	out << fixed << setprecision(3) << phiInDegrees;
	string phiRendered = out.str();
	char lastDigit = phiRendered[phiRendered.size()-1];
	if (lastDigit == '5')
	{
		cerr << "Possible unstable test " << testCounter << ": "
				<< d << " " << h << " " << x << " " << n1 << " " << n2
				<< ": " << phiRendered << endl;
	}
	++testCounter;
}


void solve (istream& in)
{
	double d, h, x, n1, n2;

	while ((in >> d >> h >> x >> n1 >> n2) && (n1 >= 1.0))
	{
		assert (d >= 1.0);
		assert (d <= 800.0);
		assert (h >= 100.0);
		assert (h <= 10000.0);
		assert (x >= 0.0);
		assert (x <= 10000.0);
		assert (n1 > 1.0);
		assert (n1 <= 2.5);
		assert (n2 >= 1.0);
		assert (n2 < n1);

		double phiMax= PI / 2.0;
		double phiMin = 0.0;
		double phi;

		while (phiMax - phiMin > requiredPrecision)
		{
			phi = (phiMin + phiMax) / 2.0;
			double x1 = d / tan(phi);
			double theta1 = PI / 2.0 - phi;
			double theta2 = asin((n2 / n1) * sin(theta1));
			double x2 = h * tan(theta2);
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
		double phiInDegrees = 180.0 * phi / PI;
		cout << fixed << setprecision(2) << phiInDegrees << endl;
		checkSensitivity(d, h, x, n1, n2, phiInDegrees);
	}
}


/*
 * Run the program. Because Eclipse does not support easy redirection of
 * input, if a command line parameter is supplied, treat that as the name
 * of the input file. Otherwise, read from cin.
 */
int main(int argc, char** argv)
{
	if (argc > 1)
	{
		ifstream input (argv[1]);
		solve (input);
	}
	else
	{
		solve (cin);
	}
	return 0;
}
