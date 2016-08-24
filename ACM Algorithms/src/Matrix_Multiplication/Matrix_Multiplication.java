package Matrix_Multiplication;

/**
 * @author Trevor Bostic
 * This program can multiply to matrices together, so long as they fit the requirements found in the read me
 */

public class Matrix_Multiplication {

	public static void main(String[] args) {
		//Test Cases
		//int[][] A = {{5,4},{3,2}}; //2x2 matrix times a 2x2 should result in a 2x2
		//int[][] B = {{7,3},{9,6}};
		int[][] A = {{5,4,4}}; //A 1x3 matrix times a 3x1 matrix should result in a 1x1
		int[][] B = {{7}, {3}, {12}};
		int[][] C = new int[A.length][B[1].length]; //Set C to the length of matrix we know it will be
		
		for(int i = 0; i < A.length; i++) //For each row in A
		{
			for(int j = 0; j < B[1].length; j++) //For each column in J
			{
				for(int k = 0; k < B.length; k++) //For each value in the rows and columns
				{
					C[i][j] = C[i][j] + (A[i][k] * B[k][j]); //Add up all of the multiplications
				}
			}
		}
		
		//Print out C
		for(int i = 0; i < C.length; i++)
		{
			for(int j = 0; j < C[i].length; j++)
			{
				System.out.print(C[i][j] + ", ");
			}
			System.out.println();
		}

	}

}
