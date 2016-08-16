/**
 * Sample solution for the Hounded by Indecision problem.
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Hounded_sjz {

	static Logger LOG = Logger.getLogger(Hounded_sjz.class.getName());

	static {
		Handler h = new ConsoleHandler();
		LOG.setLevel(Level.INFO);  // Change to INFO/FINER to toggle debug output
		h.setLevel(Level.FINER);
		LOG.addHandler(h);
	}

	final int MaxMazeSize = 40;

	char[][] maze = new char[MaxMazeSize][MaxMazeSize];

	public class  Location {
		int row;
		int col;

		public Location () {
			row = 0;
			col = 0;
		}

		public Location (int r, int c) {
			row = r;
			col = c;
		}

		public boolean equals (Object obj) {
			Location loc = (Location)obj;
			return row == loc.row && col == loc.col;
		}
		
		public String toString() {
			return "(" + row + "," + col + ")";
		}
	};

	public Location[] offsets = {new Location(-1, 0), new Location(1, 0),
			new Location(0, -1), new Location(0,1)};


	private int w;
	private int h;
	private Scanner input;
	
	public Hounded_sjz(int w0, int h0, Scanner input0) {
		w = w0;
		h = h0;
		input = input0;
	}


	public String toString(int[][] dist) {
		StringBuffer out = new StringBuffer();
		for (int row = 0; row < MaxMazeSize; ++row) {
			int printToHere = -1;
			for (int col = 0; col < MaxMazeSize; ++col) {
				if (dist[row][col] >= 0) {
					printToHere = col;
				}
			}
			if (printToHere >= 0)
			{
				for (int col = 0; col <= printToHere; ++col)
				{
					int d = dist[row][col];
					String ds = new Integer(d).toString();
					for (int i = ds.length(); i < 3; ++i)
						out.append(' ');
					out.append(ds);
				}
				out.append ("\n");
			}
		}
		return out.toString();
	}


	List<Location> exits;
	Location thiefStart;
	Location kennel;


	void readMaze (int w, int h, Scanner in)
	{
		exits = new ArrayList<Location>();
		String line = in.nextLine();
		for (int row = 0; row < h; ++row)
		{
			line = in.nextLine();
			assert (line.length() <= w);
			for (int col = 0; col < line.length(); ++col) {
				char c = line.charAt(col);
				maze[row][col] = c;
				if (c == 'T')
					thiefStart = new Location(row, col);
				else if (c == 'K')
					kennel = new Location(row, col);
				else if (c == 'E')
					exits.add (new Location(row, col));
			}
			for (int col = line.length(); col < w; ++col) {
				maze[row][col] = 'X';
			}
		}
	}


	boolean legal(Location loc, int w, int h)
	{
		return loc.row >= 0 && loc.row < h
				&& loc.col >= 0 && loc.col < w;
	}

	void flood (int[][] dist, int w, int h, Location start)
	{
		for (int row = 0; row < MaxMazeSize; ++row)
			for (int col = 0; col < MaxMazeSize; ++col)
				dist[row][col] = -1;
		dist[start.row][start.col] = 0;
		Queue<Location> q = new LinkedList<Location>();
		q.add (start);
		while (!q.isEmpty()) {
			Location here = q.element();
			q.remove();
			int d = dist[here.row][here.col];
			if (LOG.isLoggable(Level.FINE)) {
				LOG.log(Level.FINE, "in flood, dist=" + dist);
				LOG.log(Level.FINE, toString(dist));
			}
			
			for (Location offset: offsets)
			{
				int dr = offset.row;
				int dc = offset.col;
				Location loc = new Location(here.row + dr, here.col + dc);
				if ((dr != 0 || dc != 0) && legal(loc, w, h))
				{
					if (maze[loc.row][loc.col] != 'X' && dist[loc.row][loc.col] < 0)
					{
						dist[loc.row][loc.col] = d + 1;
						q.add(loc);
					}
				}
			}
		}
	}


	boolean floodThief (int w, int h, int[][] kennelDist, int[][] exitDist)
	{
		if (LOG.isLoggable(Level.FINE)) {
			LOG.log(Level.FINE, "in floodThief, kennelDist:");
			LOG.log(Level.FINE, toString(kennelDist));
			LOG.log(Level.FINE, "in floodThief, exitDist:");
			LOG.log(Level.FINE, toString(exitDist));
		}
		int[][] dist = new int[MaxMazeSize][MaxMazeSize];
		for (int row = 0; row < MaxMazeSize; ++row)
			for (int col = 0; col < MaxMazeSize; ++col)
				dist[row][col] = -1;
		dist[thiefStart.row][thiefStart.col] = 0;
		Queue<Location> q = new LinkedList<Location>();
		q.add (thiefStart);
		while (!q.isEmpty()) {
			Location here = q.element();
			q.remove();
			int d = dist[here.row][here.col];
			int kd = kennelDist[here.row][here.col];
			LOG.log(Level.FINE,  "here: " + here + " d: " + d + " kd: " + kd);

			if (d >= kd) {
				// Constable can get here before or at same time as thief
				continue;
			}
			int ed = exitDist[here.row][here.col];
			int thiefCanReachExitOnTurn = d + ed;
			int houndCanReachExitOnTurn = kd + (ed + 1)/2;
			if (thiefCanReachExitOnTurn >= houndCanReachExitOnTurn) {
				// Hound can reach the exit before or at same time as thief
				continue;
			}
			if (exitDist[here.row][here.col] == 0) {
				LOG.log(Level.FINE,  "escape at " + here + " on turn " + d);
				return true;  // Thief escapes
			}

			for (Location offset: offsets)
			{
				int dr = offset.row;
				int dc = offset.col;
				Location loc = new Location (here.row + dr, here.col + dc);
				if ((dr != 0 || dc != 0) && legal(loc, w, h))
				{
					if (maze[loc.row][loc.col] != 'X' && dist[loc.row][loc.col] < 0)
					{
						dist[loc.row][loc.col] = d + 1;
						q.add(loc);
					}
				}
			}
			if (LOG.isLoggable(Level.FINE)) {
				LOG.log(Level.FINE,  "thief");
				LOG.log(Level.FINE, toString(dist));
			}
		}
		return false;
	}


	void solve ()
	{
		int[][] kennelDist = new int[MaxMazeSize][MaxMazeSize];

		readMaze (w, h, input);
		boolean escaped = false;
		flood (kennelDist, w, h, kennel);
		for (Location exit: exits)
		{
			int exitDist[][] = new int[MaxMazeSize][MaxMazeSize];
			flood (exitDist, w, h, exit);
			escaped = floodThief(w, h, kennelDist, exitDist);
			if (escaped)
				break;
		}
		if (escaped)
		{
			System.out.println("KEEP IT");
		}
		else
		{
			System.out.println("DROP IT");
		}
	}





	/**
	 * Run the program.  Because redirecting input is impossible in Eclipse,
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

		int W = input.nextInt();
		while (W >= 3) {
			int H = input.nextInt();
			Hounded_sjz problem = new Hounded_sjz (W, H, input);
			problem.solve();
			W = input.nextInt();
		}
		input.close();
	}
}
