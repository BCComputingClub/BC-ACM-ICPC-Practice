import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 */

/**
 * @author zeil
 *
 */
public class Gardener_zeil {
	
	
	static Logger LOG = Logger.getLogger(Gardener_zeil.class.getName());
	
	static {
		Handler h = new ConsoleHandler();
		LOG.setLevel(Level.INFO);  // Change to INFO/FINER to toggle debug output
		h.setLevel(Level.FINEST);
		LOG.addHandler(h);
	}

	public class Point extends java.awt.Point {
		public Point (int x, int y) {
			super(x,y);
		}
		
		public String toString() {
			return "(" + x + "," + y + ")";
		}
		
		public boolean comesBefore (Point p)
		{
			return x < p.x || (x == p.x && y < p.y);
		}
	}
	
	ArrayList<Point> points;


	public class Edge {
		public int source;
		public int dest;
		
		public Edge (int from, int to) {
			source = from;
			dest = to;
		}
				
		public String toString() {return "[" + source + "," + dest + "]";}
		
		@Override
		public int hashCode() {
			return source + 13 * dest;
		}
		
		public boolean equals (Object obj) {
			Edge w = (Edge)obj;
			return w.source == source
					&& w.dest == dest;
		}
	}
	
	
	public class DiGraph {
		public int nVertices;
		public HashMap<Integer, HashSet<Edge>> outgoing;
		
		public DiGraph()
		{
			nVertices = 0;
			outgoing = new HashMap<Integer, HashSet<Edge>>();
		}
		
		public void addDirectedEdge (int from, int to) {
			nVertices = Math.max(nVertices, from+1);
			nVertices = Math.max(nVertices, to+1);
			HashSet<Edge> outEdges = outgoing.get(from);
			if (outEdges == null) {
				outEdges = new HashSet<>();
				outgoing.put(from, outEdges);
			}
			outEdges.add (new Edge(from, to));
		}
		
		public void addEdge (int from, int to) {
			addDirectedEdge(from, to);
			addDirectedEdge (to, from);
		}
		
		public void removeDirectedEdge (int from, int to) {
			HashSet<Edge> outEdges = outgoing.get(from);
			if (outEdges == null) {
				outEdges = new HashSet<>();
				outgoing.put(from, outEdges);
			}
			outEdges.remove (new Edge(from, to));
		}
		
		public void removeEdge (int from, int to) {
			removeDirectedEdge(from, to);
			removeDirectedEdge(to, from);
		}

	}

	DiGraph garden;
	
	public class Path extends ArrayList<Edge> { 
		
		public boolean equals(Object obj)
		{
			Path p = (Path)obj;
			if (p.size() != size())
				return false;
			for (int i = 0; i < size(); ++i)
				if (!get(i).equals(p.get(i)))
					return false;
			return true;
		}
		
	}
	
	
	class Plot {
		public Path perimeter;
		public double area;
		public int id;
		
		public Plot (Path p)
		{
			id = 0;
			perimeter = p;
			long sum = 0L;
			for (Edge e: perimeter) {
				Point p0 = points.get(e.source);
				Point p1 = points.get(e.dest);
				sum += (long)p0.y * (long)p1.x - (long)p1.y*(long)p0.x;
			}
			area = Math.abs(((double)sum)/2.0);
		}
		
		public boolean equals(Object obj) {
			Plot p = (Plot)obj;
			return id == p.id;
		}
		
		public int hashCode() {
			return id;
		}
	}		

	
	ArrayList<Plot> plots;
	
	HashMap<Edge, Plot> edgeBelongsTo;
	
	
	static boolean DEBUG = false;
	
	public Gardener_zeil(Scanner in, int nP)
	{
		garden = new DiGraph();
		points = new ArrayList<Gardener_zeil.Point>();
		int nE = in.nextInt();
		for (int i = 0; i < nP; ++i) {
			int x1, y1;
			x1 = in.nextInt();
			y1 = in.nextInt();
			Point p = new Point(x1, y1);
			points.add(p);
		}
		for (int i = 0; i < nE; ++i) {
			int v1, v2;
			v1 = in.nextInt();
			v2 = in.nextInt();
			garden.addEdge(v1, v2);
		}	
	}
	
	final static void run (BufferedReader bin)
	{
		Scanner in = new Scanner(bin);
		int nP = in.nextInt();
		while (nP > 0)
		{
			processDataSet(in, nP);
			nP = in.nextInt();
		}
	}

	private static void processDataSet(Scanner in, int nP) {
		Gardener_zeil garden = new Gardener_zeil(in , nP);
		garden.findPlots();
		garden.buildAdjacencyGraph();
		int deepest = garden.findDeepestPlot();
		System.out.println(deepest);
	}


	class PlotDist {
		Plot plot;
		int distance;
		
		PlotDist (Plot p, int d) {
			plot = p;
			distance = d;
		}
		
		public String toString() {
			return plot.perimeter.toString() + ":" + distance; 
		}
	}
	
	private int findDeepestPlot() {
		// plots.get(0) is the external area. Do a level-first
		// traversal starting from there to find the plot the farthest
		// from the outside;
		
		LinkedList<PlotDist> q = new LinkedList<Gardener_zeil.PlotDist>();
		Set<Plot> visited = new HashSet<Plot>();
		q.add(new PlotDist(plots.get(0), 0));
		visited.add(plots.get(0));
		int maxDistance = 0;
		while (!q.isEmpty()) {
			PlotDist current = q.getFirst();
			int lastDistance = current.distance;
			maxDistance = Math.max(maxDistance, lastDistance);
			q.removeFirst();
			for (Edge e: current.plot.perimeter) {
				Edge reversed = new Edge(e.dest, e.source);
				Plot adjacent = edgeBelongsTo.get(reversed);
				if (!visited.contains(adjacent)) {
					q.add (new PlotDist(adjacent, current.distance+1));
					visited.add(adjacent);
				}
			}
		}
		return maxDistance-1;
	}

	private void buildAdjacencyGraph() {
		edgeBelongsTo = new HashMap<Edge, Plot>();
		for (Plot plot: plots) {
			for (Edge e: plot.perimeter) {
				edgeBelongsTo.put(e, plot);
			}
		}
	}

	private void findPlots() {
		
		LinkedList<Path> cycles = findChordlessCycles();
		// There will be one "inappropriate" cycle around the perimeter of the points.
		// We can recognize this because it will have the max area of all cycles;
		double maxArea = -1.0;
		int largest = -1;
		plots = new ArrayList<Plot>();
		for (Path p: cycles) {
			Plot r = new Plot(p);
			if (r.area > maxArea) {
				maxArea = r.area;
				largest = plots.size();
			}
			if (r.area > 0.0) {
				r.id = plots.size();
				plots.add(r);
			}
		}
		// Move that to position 0 in plots.
		Plot plot0 = plots.get(0);
		Plot external = plots.get(largest);
		plots.set(0,  external);
		external.id = 0;
		plots.set (largest, plot0);
		plot0.id = largest;
	}


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main (String[] argv) throws IOException
	{
		BufferedReader in;
		if (argv.length > 0)
		{
			in = new BufferedReader(new FileReader(argv[0]));
		}
		else {
			in = new BufferedReader (new InputStreamReader(System.in));
		}
		run (in);
	}

	// Find all of the chordless cycles (cycles that do not contain interior cycles)
	// in the house graph
	LinkedList<Path> findChordlessCycles() {
		Set<Edge> untraversed = new HashSet<Edge>();
		for (Integer v: garden.outgoing.keySet()) {
			HashSet<Edge> edges = garden.outgoing.get(v);
			for (Edge e: edges) {
				untraversed.add(e);
			}
		}
		
		LinkedList<Path> cycles = new LinkedList<Path>();
		while (!untraversed.isEmpty()) {
			Edge start = untraversed.iterator().next();
			untraversed.remove(start);
			LOG.log(Level.FINER, "starting a cycle with edge " + start);
			Path p = traceACycle (start, untraversed);
			LOG.log(Level.FINE, "found a cycle: " + p);
			cycles.add(p);
		}
		return cycles;
	}

	

	/**
	 * Trace a cycle through a planar graph, removing edges as you go, by always
	 * taking the rightmost "turn" at each vertex.
	 * 
	 * @param cycle On output, contains the discovered cycle On input, contains the starting
	 * edge.
	 * 
	 * @param graph the graph being traversed
	 */
    private Path traceACycle(Edge start, Set<Edge> untraversed) {
    	Path cycle = new Path();
    	cycle.add(start);
		int v0 = start.source;
		int v1 = start.dest;
		while (v1 != start.source) {
			Point p0 = points.get(v0);
			Point p1 = points.get(v1);
			double largestAngle = -3.0 * Math.PI;
			Edge bestEdge = null;
			for (Edge e: garden.outgoing.get(v1)) {
				if (untraversed.contains(e)) {
					int v2 = e.dest;
					Point p2 = points.get(v2);
					
					double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x) - Math.atan2(p0.y - p1.y, p0.x - p1.x);
					while (angle < 0.0) {
						angle += 2.0*Math.PI;
					}
					LOG.log (Level.FINEST, "Angle for " + e + " between " + p0 + ":" + p1 + ":" + p2 + " is " + angle);
					if (angle > largestAngle) {
						largestAngle = angle;
						bestEdge = e;
					}
				}
			}
			cycle.add(bestEdge);
			LOG.log (Level.FINER, "partial cycle is now  " + cycle);
			untraversed.remove(bestEdge);
			v0 = bestEdge.source;
			v1 = bestEdge.dest;
		}
		return cycle;
	}
	
	
	

}
