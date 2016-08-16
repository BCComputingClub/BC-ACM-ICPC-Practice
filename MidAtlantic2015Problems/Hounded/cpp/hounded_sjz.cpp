/*************************************************************
 **                                                          **
 **  Sample solution for "Right-Hand Rule"                   **
 **       Steven J Zeil                                      **
 **       10/29/2005                                         **
 **                                                          **
 *************************************************************/


#include <algorithm>
#include <cassert>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <list>
#include <queue>
#include <string>
#include <sstream>
#include <map>

#define NDEBUG 1

using namespace std;

const int MaxMazeSize = 50;

char maze[MaxMazeSize][MaxMazeSize];

struct Location {
	int row;
	int col;

	Location (int r = 0, int c = 0)
	{
		row = r;
		col = c;
	}

	bool operator== (Location loc) const
			{
		return row == loc.row && col == loc.col;
			}
};

Location offsets[4] = {Location(-1, 0), Location(1, 0), Location(0, -1), Location(0,1)};

ostream& operator<< (ostream& out, const Location& loc)
{
	out << '(' << loc.row << ',' << loc.col << ')';
	return out;
}

ostream& operator<< (ostream& out, int dist[MaxMazeSize][MaxMazeSize])
{
	for (int row = 0; row < MaxMazeSize; ++row)
	{
		int printToHere = -1;
		for (int col = 0; col < MaxMazeSize; ++col)
			if (dist[row][col] >= 0)
				printToHere = col;
		if (printToHere >= 0)
		{
			for (int col = 0; col <= printToHere; ++col)
			{
				int d = dist[row][col];
				out << setw(3) << d;
			}
			out << endl;
		}
	}
	return out;
}


list<Location> exits;
Location thiefStart;
Location kennel;


void readMaze (int w, int h, istream& in)
{
	exits.clear();
	string line;
	getline (in, line);
	for (int row = 0; row < h; ++row)
	{
		getline (in, line);
		assert (line.size() <= w);
		for (int col = 0; col < line.size(); ++col) {
			char c = line[col];
			maze[row][col] = c;
			if (c == 'T')
				thiefStart = Location(row, col);
			else if (c == 'K')
				kennel = Location(row, col);
			else if (c == 'E')
				exits.push_back (Location(row, col));
		}
		for (int col = line.size(); col < w; ++col) {
			maze[row][col] = 'X';
		}
	}
}


bool legal(Location loc, int w, int h)
{
	return loc.row >= 0 && loc.row < h
			&& loc.col >= 0 && loc.col < w;
}

void flood (int dist[MaxMazeSize][MaxMazeSize], int w, int h, Location start)
{
	for (int row = 0; row < MaxMazeSize; ++row)
		for (int col = 0; col < MaxMazeSize; ++col)
			dist[row][col] = -1;
	dist[start.row][start.col] = 0;
	queue<Location, list<Location> > q;
	q.push (start);
	while (!q.empty()) {
		Location here = q.front();
		q.pop();
		int d = dist[here.row][here.col];
#ifdef DEBUG
		//cerr << "flood\n" <<  dist;
		//cerr << "here: " << here << "d: " << d << endl;
#endif
		for (Location offset: offsets)
		{
			int dr = offset.row;
			int dc = offset.col;
			Location loc (here.row + dr, here.col + dc);
			if ((dr != 0 || dc != 0) && legal(loc, w, h))
			{
				if (maze[loc.row][loc.col] != 'X' && dist[loc.row][loc.col] < 0)
				{
					dist[loc.row][loc.col] = d + 1;
					q.push(loc);
				}
			}
		}
	}
}


bool floodThief (int w, int h, int kennelDist[MaxMazeSize][MaxMazeSize], int exitDist[MaxMazeSize][MaxMazeSize])
{
#ifdef DEBUG
	cerr << "floodThief\n  kennel\n" <<  kennelDist;
	cerr << "  exit\n"<< exitDist << endl;
#endif
	int dist[MaxMazeSize][MaxMazeSize];
	for (int row = 0; row < MaxMazeSize; ++row)
		for (int col = 0; col < MaxMazeSize; ++col)
			dist[row][col] = -1;
	dist[thiefStart.row][thiefStart.col] = 0;
	queue<Location, list<Location> > q;
	q.push (thiefStart);
	while (!q.empty()) {
		Location here = q.front();
		q.pop();
		int d = dist[here.row][here.col];
		int kd = kennelDist[here.row][here.col];
#ifdef DEBUG
		cerr << "here: " << here << " d: " << d << " kd: " << kd << endl;
#endif

		if (d >= kd)
		{
			// Constable can get here before or at same time as thief
			continue;
		}
		int ed = exitDist[here.row][here.col];
		int thiefCanReachExitOnTurn = d + ed;
		int houndCanReachExitOnTurn = kd + (ed + 1)/2;
		if (thiefCanReachExitOnTurn >= houndCanReachExitOnTurn)
		{
			// Hound can reach the exit before or at same time as thief
			continue;
		}
		if (exitDist[here.row][here.col] == 0)
		{
#ifdef DEBUG
			cerr << "escape at " << here << " on turn " << d << endl;
#endif
			return true;  // Thief escapes
		}

		for (Location offset: offsets)
		{
			int dr = offset.row;
			int dc = offset.col;
			Location loc (here.row + dr, here.col + dc);
			if ((dr != 0 || dc != 0) && legal(loc, w, h))
			{
				if (maze[loc.row][loc.col] != 'X' && dist[loc.row][loc.col] < 0)
				{
					dist[loc.row][loc.col] = d + 1;
					q.push(loc);
				}
			}
		}
#ifdef DEBUG
		cerr << "thief\n" <<  dist;
#endif
	}
	return false;
}


void solve (istream& in)
{
	int w, h;

	int kennelDist[MaxMazeSize][MaxMazeSize];

	while ((in >> w >> h) && (w >= 3) && (h >= 3))
	{
		readMaze (w, h, in);
		bool escaped = false;
		flood (kennelDist, w, h, kennel);
		for (Location exit: exits)
		{
			int exitDist[MaxMazeSize][MaxMazeSize];
			flood (exitDist, w, h, exit);
			escaped = floodThief(w, h, kennelDist, exitDist);
			if (escaped)
				break;
		}
		if (escaped)
		{
			cout << "KEEP IT" << endl;
		}
		else
		{
			cout << "DROP IT" << endl;
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
