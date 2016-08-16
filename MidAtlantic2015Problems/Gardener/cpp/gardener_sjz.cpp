/*************************************************************
 **                                                          **
 **  Sample solution for "Right-Hand Rule"                   **
 **       Steven J Zeil                                      **
 **       10/29/2005                                         **
 **                                                          **
 *************************************************************/


#include <algorithm>
#include <cassert>
#include <cmath>
#include <fstream>
#include <iomanip>
#include <iostream>
#include <list>
#include <vector>
#include <map>
#include <set>
#include <utility>

using namespace std;


static bool DEBUG = false;

struct Point
{
	int x;
	int y;

	Point (int x0 = 0, int y0 = 0)
	: x(x0), y(y0)
	{
	}

	bool comesBefore (Point p)
	{
		return x < p.x || (x == p.x && y < p.y);
	}
};

ostream& operator << (ostream& out, Point p)
{
	out << "(" << p.x << "," << p.y << ")";
	return out;
}


struct Edge
{
	int source;
	int dest;

	Edge (int from, int to)
	: source(from), dest(to)
	{
	}

	bool operator< (Edge e) const
	{
		return (source < e.source)
				|| (source == e.source && dest < e.dest);
	}


	bool operator== (Edge e)
	{
		return e.source == source
				&& e.dest == dest;
	}
};

ostream& operator << (ostream& out, Edge e)
{
	out << "[" << e.source << "," << e.dest << "]";
	return out;
}


class DiGraph
{
public:
	int nVertices;
	vector<set<Edge> > outgoing;

	DiGraph()
	: nVertices(0)
	{
	}

	void addDirectedEdge (int from, int to)
	{
		nVertices = max(nVertices, from+1);
		nVertices = max(nVertices, to+1);
		if (nVertices > outgoing.size())
			outgoing.resize(nVertices);
		set<Edge>& outEdges = outgoing[from];
		outEdges.insert (Edge(from, to));
	}

	void addEdge (int from, int to)
	{
		addDirectedEdge(from, to);
		addDirectedEdge (to, from);
	}

	void removeDirectedEdge (int from, int to) {
		set<Edge>& outEdges = outgoing[from];
		outEdges.erase (Edge(from, to));
	}

	void removeEdge (int from, int to) {
		removeDirectedEdge(from, to);
		removeDirectedEdge(to, from);
	}

};


typedef vector<Edge> Path;

ostream& operator<< (ostream& out, const Path& p)
{
	out << "[";
	bool first = true;
	for (auto& e: p)
	{
		if (!first)
			out << ", ";
		out << e;
		first = false;
	}
	out << "]";
	return out;
}


class Plot
{
public:
	Path perimeter;
	double area;
	int id;

	Plot (const Path& p, const vector<Point>& knownPoints);

	bool operator== (const Plot& p) const
	{
		return id == p.id;
	}

	bool operator< (const Plot& p) const
	{
		return id < p.id;
	}

};


struct PlotDist {
	int plotNum;
	int distance;

	PlotDist (int p, int d)
	: plotNum(p), distance(d)
	{
	}

};

ostream& operator<< (ostream& out, const PlotDist& pd)
{
	out << pd.plotNum << ":" << pd.distance;
	return out;
}




class Garden
{
public:
	vector<Point> points;

	DiGraph garden;
	vector<Plot> plots;
	map<Edge, int> edgeBelongsTo;



	Garden (istream& in, int nP, int nE)
	{
		for (int i = 0; i < nP; ++i) {
			int x1, y1;
			in >> x1 >> y1;
			Point p (x1, y1);
			points.push_back(p);
		}
		for (int i = 0; i < nE; ++i) {
			int v1, v2;
			in >> v1 >> v2;
			garden.addEdge(v1, v2);
		}
	}


	int findDeepestPlot() {
		// plots[0] is the external area. Do a level-first
		// traversal starting from there to find the plot the farthest
		// from the outside;

		list<PlotDist> q;
		set<int> visitedPlots;
		q.push_back (PlotDist(0, 0));
		visitedPlots.insert (0);
		int maxDistance = 0;
		while (!q.empty()) {
			PlotDist current = q.front();
			int lastDistance = current.distance;
			maxDistance = max(maxDistance, lastDistance);
			q.pop_front();
			for (Edge e: plots[current.plotNum].perimeter) {
				Edge reversed (e.dest, e.source);
				int adjacent = edgeBelongsTo[reversed];
				if (visitedPlots.count(adjacent) == 0) {
					q.push_back (PlotDist(adjacent, current.distance+1));
					visitedPlots.insert (adjacent);
				}
			}
		}
		return maxDistance-1;
	}

	void buildAdjacencyGraph() {
		edgeBelongsTo.clear();
		for (unsigned plotNum = 0; plotNum < plots.size(); ++plotNum) {
			for (Edge e: plots[plotNum].perimeter) {
				edgeBelongsTo[e] = plotNum;
			}
		}
	}

	void findPlots() {

		list<Path> cycles = findChordlessCycles();
		// There will be one "inappropriate" cycle around the perimeter of the points.
		// We can recognize this because it will have the max area of all cycles;
		double maxArea = -1.0;
		int largest = -1;
		plots.clear();
		for (Path p: cycles) {
			Plot r (p, points);
			if (r.area > maxArea) {
				maxArea = r.area;
				largest = plots.size();
			}
			if (r.area > 0.0) {
				r.id = plots.size();
				plots.push_back(r);
			}
		}
		// Move that to position 0 in plots.
		swap (plots[0], plots[largest]);
		plots[0].id = 0;
		plots[largest].id = largest;
	}



	// Find all of the chordless cycles (cycles that do not contain interior cycles)
	// in the house graph
	list<Path> findChordlessCycles() {
		set<Edge> untraversed;
		for (auto& edges: garden.outgoing) {
			for (Edge e: edges) {
				untraversed.insert(e);
			}
		}

		list<Path> cycles;
		while (!untraversed.empty()) {
			Edge start = *(untraversed.begin());
			untraversed.erase(start);
			if (DEBUG)
				cerr << "starting a cycle with edge " <<  start << endl;
			Path p = traceACycle (start, untraversed);
			if (DEBUG)
				cerr << "found a cycle: " << p << endl;
			cycles.push_back(p);
		}
		return cycles;
	}

	const double PI = 3.1415926535897;

	/**
	 * Trace a cycle through a planar graph, removing edges as you go, by always
	 * taking the rightmost "turn" at each vertex.
	 *
	 * @param cycle On output, contains the discovered cycle On input, contains the starting
	 * edge.
	 *
	 * @param graph the graph being traversed
	 */
	Path traceACycle(Edge start, set<Edge>& untraversed) {
		Path cycle;
		cycle.push_back(start);
		int v0 = start.source;
		int v1 = start.dest;
		while (v1 != start.source) {
			Point p0 = points[v0];
			Point p1 = points[v1];
			double largestAngle = -3.0 * PI;
			Edge bestEdge(0,1);
			for (Edge e: garden.outgoing[v1]) {
				if (untraversed.count(e) > 0) {
					int v2 = e.dest;
					Point p2 = points[v2];

					double angle = atan2(p2.y - p1.y, p2.x - p1.x) - atan2(p0.y - p1.y, p0.x - p1.x);
					while (angle < 0.0) {
						angle += 2.0*PI;
					}
					if (DEBUG)
						cerr << "Angle for " << e << " between " << p0 << ":" << p1 << ":" << p2 << " is " << angle << endl;
					if (angle > largestAngle) {
						largestAngle = angle;
						bestEdge = e;
					}
				}
			}
			cycle.push_back(bestEdge);
			if (DEBUG)
				cerr << "partial cycle is now  " << cycle << endl;
			untraversed.erase(bestEdge);
			v0 = bestEdge.source;
			v1 = bestEdge.dest;
		}
		return cycle;
	}

};


Plot::Plot (const Path& p, const vector<Point>& points)
: perimeter(p), area(0.0), id(0)
{
	long sum = 0L;
	for (Edge e: perimeter)
	{
		Point p0 = points[e.source];
		Point p1 = points[e.dest];
		sum += (long)p0.y * (long)p1.x - (long)p1.y*(long)p0.x;
	}
	area = abs(((double)sum)/2.0);
}









void processDataSet(istream& in, int nP, int nE) {
	Garden garden (in , nP, nE);
	garden.findPlots();
	garden.buildAdjacencyGraph();
	int deepest = garden.findDeepestPlot();
	cout << deepest << endl;
}



void solve (istream& in)
{
	int P, E;

	while ((in >> P >> E) && (P > 0))
	{
		processDataSet (in, P, E);
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
