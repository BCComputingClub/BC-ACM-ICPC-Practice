/**
 * Sample solution for the Kinfolk problem.
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Kinfolk_sjz {
	
	static Logger LOG = Logger.getLogger(Kinfolk_sjz.class.getName());
	
	static {
		Handler h = new ConsoleHandler();
		LOG.setLevel(Level.INFO);  // Change to INFO/FINER to toggle debug output
		h.setLevel(Level.FINER);
		LOG.addHandler(h);
	}
		
	
		
	private static String[][] relationships =
		{{"self", "child", "grandchild", "great-grandchild", "great-great-grandchild"},
		{"parent", "sibling", "nephew", "grandnephew", "great-grandnephew", "great-great-grandnephew"}, 
		{"grandparent", "uncle", "1st cousin", "1st cousin once removed", "1st cousin twice removed", "1st cousin thrice removed"}, 
		{"great-grandparent", "granduncle", "1st cousin once removed", "2nd cousin", "2nd cousin once removed", "2nd cousin twice removed", "2nd cousin thrice removed"}, 
		{"great-great-grandparent", "great-granduncle", "1st cousin twice removed", "2nd cousin once removed", "3rd cousin", "3rd cousin once removed", "3rd cousin twice removed", "3rd cousin thrice removed"},
		{"kin", "great-great-granduncle", "1st cousin thrice removed", "2nd cousin twice removed", "3rd cousin once removed"},
		{"kin", "kin", "kin", "2nd cousin thrice removed", "3rd cousin twice removed"},
		{"kin", "kin", "kin", "kin", "3rd cousin thrice removed"}
		};
	
	private int personA, personB;
	private char genderB;
	
	public Kinfolk_sjz(int person1, Scanner input) {
		personA = person1;
		personB = input.nextInt();
		genderB = input.next().charAt(0);
	}
	
	public void solve()
	{
		ArrayList<Integer> ancestryA = getAncestryOf(personA);
		ArrayList<Integer> ancestryB = getAncestryOf(personB);
		int commonAncestor = getMostRecentCommonAncestor(ancestryA, ancestryB);
		int generationA = generationsBetween(personA, commonAncestor);
		int generationB = generationsBetween(personB, commonAncestor);
		String relation = "kin";
		try {
			relation = relationships[generationA][generationB];
		} catch (IndexOutOfBoundsException ex) {
			// do nothing
		}
		if (genderB == 'F') {
			relation = relation.replaceAll("nephew", "niece");
			relation = relation.replaceAll("uncle", "aunt");
		}
		System.out.println (relation);
	}



	private int generationsBetween(int person, int commonAncestor) {
		int count = 0;
		while (person > commonAncestor) {
			person = (person - 1) / 2;
			++count;
		}
		return count;
	}

	private int getMostRecentCommonAncestor(ArrayList<Integer> ancestryA,
			ArrayList<Integer> ancestryB) {
		int lastCommon = 0;
		for (int i = 0; i < ancestryA.size() && i < ancestryB.size(); ++i) {
			int ancestorA = ancestryA.get(ancestryA.size() - i - 1); 
			int ancestorB = ancestryB.get(ancestryB.size() - i - 1); 
			if (ancestorA != ancestorB) {
				return ancestryA.get(ancestryA.size() - i);
			}
			lastCommon = ancestorA;
		}
		return lastCommon;
	}

	private ArrayList<Integer> getAncestryOf(int person) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		while (person > 0) {
			result.add(person);
			person = (person - 1) / 2;
		}
		result.add(0);
		return result;
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
		
		int personA = input.nextInt();
		while (personA >= 0) {
			Kinfolk_sjz problem = new Kinfolk_sjz (personA, input);
			problem.solve();
			personA = input.nextInt();
		}
		input.close();
	}
}
