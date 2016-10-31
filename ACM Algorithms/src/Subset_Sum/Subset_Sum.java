import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Trevor Bostic
 * This program is a brute force algorithm for finding the sum of a subset in a set S that is less than some given number t
 */
public class Subset_Sum {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		//Test Cases
		//int[] S = {1,2,5,6}; //Our initial set
		//int t = 10; //Our t value
		int[] S = {5, 3, 0, 4, 1, 7};
		int t = 18;

		ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>(); //Lists of lists to keep track of everything
		//**Notice** if space is an issue, we actually only need to keep track of two lists at a time, so we could delete some once we are done
		lists.add(new ArrayList<Integer>()); //Get our first set, used in the merge method below
		lists.get(0).add(0); //Add 0 as a starting point

		for(int i = 1; i <= S.length; i++)
		{
			lists.add(merge(lists.get(i-1), S[i-1])); //Merge the lists to obtain every possible combination of the elements so far
			int arraySize = lists.get(i).size();
			for(int x = 0; x < arraySize; x++) 
			{ //Remove all entries in the list greater than t
				if(lists.get(i).get(x) > t) //Since the list is sorted, we can delete this element and all that follow it
				{
					while (x < arraySize) //Arraysize will decrease as we remove elements
					{
						lists.get(i).remove(x);
						arraySize--;
					}
					break; //Get out of the for loop
				}
			}
			
			System.out.println(lists.get(i).toString()); //Print the list so people can see what is happening
		}
		ArrayList<Integer> last = lists.get(lists.size()-1);
		System.out.println("Answer: " + last.get(last.size() - 1)) ; //Get last number from last lists

	}

	public static ArrayList<Integer> merge(ArrayList<Integer> arr, int add)
	{
		ArrayList<Integer> merged = new ArrayList<Integer>(); //The two lists merged together
		ArrayList<Integer> numAdded = new ArrayList<Integer>(); //The original list with the Set element added to each element of the list

		for (int num: arr) //Doing the adding
			numAdded.add(num + add);

		//Now merge
		int arrIndex = 0;
		int numAddedIndex = 0;

		while(arrIndex < arr.size() || numAddedIndex < numAdded.size()) //Merge the two lists
		{
			if(arrIndex < arr.size() && numAddedIndex < numAdded.size() && arr.get(arrIndex) <= numAdded.get(numAddedIndex)) //Check which one is greater, this will make it a sorted list
			{ //The <= sign is necessary, in order to make sure we dont go to the else statement when it is out of range, essentially it is giving arr, precedence over numAdded
				if(merged.contains(arr.get(arrIndex))) //To prevent duplicates, though it may not be worth checking since it won't effect the answer, 
					arrIndex++;
				else
				{
					merged.add(arr.get(arrIndex)); //If not a duplicate, add it to the array
					arrIndex++;
				}
			}
			else
			{
				if(merged.contains(numAdded.get(numAddedIndex))) //To prevent duplicates, though it may not be worth checking since it won't effect the answer
					numAddedIndex++;
				else
				{
					merged.add(numAdded.get(numAddedIndex)); //If not a duplicate, add it to the array
					numAddedIndex++;
				}
			}
			

		}
		
		System.out.println(merged.toString());
		
		return merged;
	}

}
