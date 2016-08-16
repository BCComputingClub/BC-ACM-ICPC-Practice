import java.util.ArrayList;


public class KinfolkGenerator {

	
	private static String[][] relationships =
		{{"self", "child", "grandchild", "great-grandchild", "great-great-grandchild"},
		{"parent", "sibling", "nephew", "grandnephew", "great-grandnephew", "great-great-grandnephew"}, 
		{"grandparent", "uncle", "1st cousin", "1st cousin, once removed", "1st cousin, twice removed", "1st cousin, thrice removed"}, 
		{"great-grandparent", "granduncle", "1st cousin, once removed", "2nd cousin", "2nd cousin, once removed", "2nd cousin, twice removed", "2nd cousin, thrice removed"}, 
		{"great-great-grandparent", "great-granduncle", "1st cousin, twice removed", "2nd cousin, once removed", "3rd cousin", "3rd cousin, once removed", "3rd cousin, twice removed", "3rd cousin, thrice removed"},
		{"kin", "great-great-granduncle", "1st cousin, thrice removed", "2nd cousin, twice removed", "3rd cousin, once removed"},
		{"kin", "kin", "kin", "2nd cousin, thrice removed", "3rd cousin, twice removed"},
		{"kin", "kin", "kin", "kin", "3rd cousin, thrice removed"}
		};

	public static void main(String[] args) {
		final int base = 22;
		String lastRelationShip = ""; 
		int offset = 0;
		for (int i = base+1; i < 20000; ++i) {
			int person1 = base;
			int person2 = i;
			for (int off = 0; off < offset; ++off) {
				person1 = 2 * person1 + ((off % 2 == 0)? 1 : 2);
				person2 = 2 * person2 + ((off % 2 == 0)? 1 : 2);
				if (person2 > 32767) {
					offset = -1;
					person1 = base;
					person2 = i;
					off = offset;
				}
			}
			++offset;
			String relationShip = solve(person1, person2, 'M');
			if (!relationShip.equals(lastRelationShip)) {
				System.out.println (person1 + " " + person2 + " F");
				System.out.println (person1 + " " + person2 + " M");
				System.out.println (person2 + " " + person1 + " F");
				System.out.println (person2 + " " + person1 + " M");
				lastRelationShip = relationShip;
			}
		}
	}
	
	
	public static String solve(int personA, int personB, char genderB)
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
		return relation;
	}



	private static int generationsBetween(int person, int commonAncestor) {
		int count = 0;
		while (person > commonAncestor) {
			person = (person - 1) / 2;
			++count;
		}
		return count;
	}

	private static int getMostRecentCommonAncestor(ArrayList<Integer> ancestryA,
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

	private static ArrayList<Integer> getAncestryOf(int person) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		while (person > 0) {
			result.add(person);
			person = (person - 1) / 2;
		}
		result.add(0);
		return result;
	}


}
