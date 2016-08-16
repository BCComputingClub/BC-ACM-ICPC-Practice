/**
 * Sample solution for the Arrrgument problem.
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Arrrgument_sjz {
	
	static Logger LOG = Logger.getLogger(Arrrgument_sjz.class.getName());
	
	static {
		Handler h = new ConsoleHandler();
		LOG.setLevel(Level.INFO);  // Change to INFO/FINER to toggle debug output
		h.setLevel(Level.FINER);
		LOG.addHandler(h);
	}
		
	
	class TreasureChest implements Cloneable {
		TreeMap<String, LinkedList<Integer>> gems;
		
		public TreasureChest() {
			gems = new TreeMap<String, LinkedList<Integer>>();
		}
		
		/**
		 * What kind of gem is the most expensive remaining
		 * if we ignore the ones listed in excludingGems?
		 * 
		 * @param excludingGems list of gem types to ignore
		 * @return kind of most expensive remaining gem
		 */
		String bestChoice (String excludingGems) {
			String bestKind = "";
			int bestValue = -1;
			for (String gemType: gems.keySet()) {
				if (!excludingGems.contains(gemType)) {
					int value = gems.get(gemType).getFirst();
					if (value > bestValue) {
						bestValue = value;
						bestKind = gemType;
					}
				}
			}
			return bestKind;
		}
		
		/**
		 * Choose a gem from the treasure chest
		 */
		void removeGem(String gemType) {
			gems.get(gemType).removeFirst();
		}
		
		public Object clone() {
			TreasureChest tc = new TreasureChest();
			for (String gemType: gems.keySet()) {
				LinkedList<Integer> values = new LinkedList<Integer>();
				for (Integer v: gems.get(gemType)) {
					values.add(v);
				}
				tc.gems.put(gemType, values);
			}
			return tc;
		}
	}
		
	
	
	private int numGems, numCrew;
	private TreasureChest chest;
	
	public Arrrgument_sjz(int numGems, Scanner input) {
		this.numGems = numGems;
		numCrew = input.nextInt();
		chest = new TreasureChest();
		for (int g = 0; g < numGems; ++g) {
			String gemType = input.next();
			LinkedList<Integer> values = new LinkedList<Integer>();
			for (int c = 0; c < numCrew + 1; ++c) {
				int v = input.nextInt();
				values.add(v);
			}
			chest.gems.put(gemType, values);
		}
	}
	
	public void solve()
	{
		int bestTotalValue = -1;
		String bestGemChoice = "";
		for (String firstChoice: chest.gems.keySet()) {
			int totalValue = chest.gems.get(firstChoice).getFirst();
			LOG.log(Level.FINE, "If you start with " + firstChoice 
					+ " for value " + totalValue);
			TreasureChest chest2 = (TreasureChest) chest.clone();
			chest2.removeGem(firstChoice);
			// Simulate crew choices
			for (int c = 0; c < numCrew; ++c) {
				String gem1 = chest2.bestChoice(firstChoice);
				chest2.removeGem(gem1);
				String exclusion = firstChoice + gem1;
				if (numGems == 2)
					exclusion = gem1;
				String gem2 = chest2.bestChoice(exclusion);
				chest2.removeGem(gem2);
			}
			String secondChoice = chest2.bestChoice(firstChoice);
			int secondValue = chest2.gems.get(secondChoice).getFirst();
			totalValue += secondValue;
			LOG.log(Level.FINE, "   you will later choose " + secondChoice 
					+ " for value " + secondValue
					+ ", total " + totalValue);
			
			if (totalValue > bestTotalValue) {
				bestTotalValue = totalValue;
				bestGemChoice = firstChoice;
			}
		}
		System.out.println(bestGemChoice);
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
		
		int nGemKinds = input.nextInt();
		while (nGemKinds > 0) {
			Arrrgument_sjz problem = new Arrrgument_sjz (nGemKinds, input);
			problem.solve();
			nGemKinds = input.nextInt();
		}
		input.close();
	}
}
