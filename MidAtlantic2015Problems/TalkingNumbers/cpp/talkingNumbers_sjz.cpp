/*************************************************************
 **                                                          **
 **  Sample solution for "Talking about Numbers"             **
 **       Steven J Zeil                                      **
 **       10/16/2015                                         **
 **                                                          **
 *************************************************************/


#include <algorithm>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <string>


using namespace std;

const long int million = 1000000;
const long int thousand = 1000;


string digits[] = {"zero", "one", "two", "three", "four",
		"five", "six", "seven", "eight", "nine"};

string tens[] = {"", "", "twenty", "thirty", "forty",
		"fifty", "sixty", "seventy", "eighty", "ninety"};

string teens[] = {"ten", "eleven", "twelve", "thirteen", "fourteen",
		"fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};

void print999 (int k, bool prior)
{
	if (k >= 100)
	{
		int hundreds = k / 100;
		cout << digits[hundreds] << " hundred";
		if (k % 100 > 0)
		{
			cout << " and ";
		}
	}
	int m = k % 100;
	if (m >= 20) {
		cout << tens[m / 10];
		int d = m % 10;
		if (d > 0)
		{
			cout << "-" << digits[d];
		}
	}
	else if (m >= 10){
		cout << teens[m-10];
	}
	else if (m > 0)
	{
		cout << digits[m];
	}
	else
	{
		if (!prior && k == 0)
			cout << "zero";
	}
}


void print (long N) {
	bool printedMillions = false;
	if (N >= million)
	{
		printedMillions = true;
		int millionsPart = N / million;
		N = N % million;
		print999 (millionsPart, false);
		cout << " million";
	}
	bool printedThousands = false;
	if (N >= thousand)
	{
		printedThousands = true;
		int thousandsPart = N / thousand;
		N = N % thousand;
		if (printedMillions)
		{
			cout << ", ";
		}
		print999 (thousandsPart, printedMillions);
		cout << " thousand";
	}
	if (N > 99)
	{
		if (printedMillions || printedThousands)
		{
			cout << ", ";
		}
		print999 (N, printedMillions || printedThousands);
	}
	else if (N > 0)
	{
		if (printedMillions || printedThousands)
		{
			cout << " and ";
		}
		print999 (N, printedMillions || printedThousands);

	}
	else // N == 0
	{
		if ((!printedMillions) && (!printedThousands))
		{
			cout << "zero";
		}

	}
	cout << endl;
}

void solve (istream& in)
{
	long N;

	while ((in >> N) && (N >= 0))
	{
	  print(N);
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
