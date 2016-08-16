import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
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
public class OfTheChildren_zeil {
	
	
	static Logger LOG = Logger.getLogger(OfTheChildren_zeil.class.getName());
	
	static {
		Handler h = new ConsoleHandler();
		LOG.setLevel(Level.INFO);  // Change to INFO/FINER to toggle debug output
		h.setLevel(Level.FINEST);
		LOG.addHandler(h);
	}

	int[] moneyCollected;


	public class Edge {
		public int source;
		public int dest;
		public int transportationCost;
		
		public Edge (int from, int to, int cost) {
			source = from;
			dest = to;
			transportationCost = cost;
		}
				
		public String toString() {return "[" + source + "," + dest + "]:" + transportationCost;}
		
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
		
		public void addDirectedEdge (int from, int to, int cost) {
			nVertices = Math.max(nVertices, from+1);
			nVertices = Math.max(nVertices, to+1);
			HashSet<Edge> outEdges = outgoing.get(from);
			if (outEdges == null) {
				outEdges = new HashSet<>();
				outgoing.put(from, outEdges);
			}
			outEdges.add (new Edge(from, to, cost));
		}
		
		public void addEdge (int from, int to, int cost) {
			addDirectedEdge(from, to, cost);
			addDirectedEdge (to, from, cost);
		}
		
		public void removeDirectedEdge (int from, int to) {
			HashSet<Edge> outEdges = outgoing.get(from);
			if (outEdges == null) {
				outEdges = new HashSet<>();
				outgoing.put(from, outEdges);
			}
			outEdges.remove (new Edge(from, to, 0));
		}
		
		public void removeEdge (int from, int to) {
			removeDirectedEdge(from, to);
			removeDirectedEdge(to, from);
		}

	}

	DiGraph transport;
	
	
	public OfTheChildren_zeil(Scanner in, int nP)
	{
		transport = new DiGraph();
		moneyCollected = new int[nP];
		for (int i = 1; i < nP-1; ++i) {
			int c = in.nextInt();
			moneyCollected[i] = c;
			LOG.log(Level.FINER, "cash at " + i + "=" + c);
		}
		int from = in.nextInt();
		while (from >= 0) {
			int to = in.nextInt();
			int cost = in.nextInt();
			LOG.log(Level.FINER, "edge " + new Edge(from, to, cost));
			transport.addEdge(from, to, cost);
			from = in.nextInt();
		}	
		in.nextInt(); // discard an integer from the input
		in.nextInt(); // discard another integer from the input
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
		OfTheChildren_zeil problem = new OfTheChildren_zeil(in , nP);
		System.out.println (problem.minInitialCost());
	}


	class Budget {
		int city;
		int deficit;
		
		Budget (int c, int def) {
			city = c;
			deficit = def;
		}
		
		public String toString()
		{
			return "" + city + ":" + deficit; 
		}
		
	}
	
	private String setToString(int set) {
		int s = set;
		int i = 0;
		StringBuffer result = new StringBuffer();
		while (set > 0) {
			if (set % 2 == 1) {
				result.append(i + ",");
			}
			set = set / 2;
			i++;
		}
		return result.toString();
	}

	private int minInitialCost() {
		int nC = transport.nVertices;
		int nSets = 1 << nC;
		
		// deficit[c][S] = min cost to complete trip starting from city c
		//    given that we have yet to visit any cities in S. S is an
		//    encoding of a set if  cities using 0 and 1's in the i_th bit
		//    to indicate the membership of city i.
		int[][] deficit = new int[nSets][nC];
		for (int city = 0; city < nC; ++city) {
			for (int set = nSets/2; set < nSets; ++set) {
				deficit[set][city] = Integer.MAX_VALUE / 2;
			}
		}
		for (int set = 0; set < nSets/2; ++set) {
			deficit[set][nC-1] = Integer.MAX_VALUE / 2;
		}
		for (int set = nSets/2; set < nSets; ++set) {
			deficit[set][nC-1] = 0;
		}
		for (int set = nSets/2 - 1; set > 0; --set) {
			//System.out.println("set=" + set );
			for (int city = 0; city < nC-1; ++city) {
				deficit[set][city] = Integer.MAX_VALUE/2;
				Set<Edge> edges = transport.outgoing.get(city);
				if ((edges != null) && (((1 << city) & set) != 0)) {
					deficit[set][city] = Integer.MAX_VALUE/4;
					for (Edge e: edges) {
						int otherSet = set | (1 << e.dest);
						if (otherSet != set) {
							int def = Math.max(0, deficit[otherSet][e.dest] 
									+ (e.transportationCost - moneyCollected[city]));
							if (def < deficit[set][city]) {
								LOG.log(Level.FINE, "Reducing city " + city + ", {" + setToString(set) + "} from "
										+ deficit[set][city] + " to " + def + " based on edge " + e);
								deficit[set][city] = def;
							}
						}
					}
				}
			}
		}
		return deficit[1][0];
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

	

}
