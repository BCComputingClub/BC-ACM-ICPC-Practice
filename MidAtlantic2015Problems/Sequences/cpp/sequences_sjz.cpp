/*************************************************************
 **                                                          **
 **  Sample solution for "Positive Con Sequences"            **
 **       Steven J Zeil                                      **
 **       10/26/2015                                         **
 **                                                          **
 *************************************************************/


#include <algorithm>
#include <fstream>
#include <iomanip>
#include <iostream>


using namespace std;


bool validate (int a[])
{
	for (int i = 0; i < 4; ++i)
		if (a[i] <= 0)
			return false;


	// Check for arithmetic sequence
	bool OK = true;
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




void solve (istream& in)
{
	int a[4];

	while ((in >> a[0] >> a[1] >> a[2] >> a[3]) && (a[0] >= 0 || a[1] >= 0))
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
				cout << a[openPos] << endl;
			}
			else
			{
				cout << -1 << endl;
			}
		}
		else
		{
			cout << -1 << endl;
		}
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
