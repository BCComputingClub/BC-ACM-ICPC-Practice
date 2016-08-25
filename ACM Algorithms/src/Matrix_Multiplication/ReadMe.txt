Strassen's algorithm for matrix multiplication

Page 75 in Introduction to Algorithms, 3rd ed. by Cormen, et.al,

Problem: Given to nxn matrix A and B, find the product C of A and B, that is A·B=C 

Comment: The typical algorithm to multiply matrices runs in O(n^3) time, Strassens runs
in O(n^2.8). Since Strassens is more difficult to program and barely save on time, I will be
showing the standard algorithm that just loops through the matrices.

Restrictions/Edge Cases
When multiplying two matrices the columns of the first matrix must equal the rows of the second.
That is, if you have a NxM matrix A and a KxL matrix B, M=K must be true in order to multiply
the matrices. Also notice, that the resulting matrix will be of size NxL.
No ragged matrices. That is, all rows should have the same number of columns.

Summary/Example
In order to summarize how this algorithm works, I will show an example of how you would
multiply matrices manually. Say you have the 2 matrices below
	|5, 4|	   |7, 3|
A = |3, 2|  B= |9, 6|

To reference individual elements in the matrix the notation below is used
A_11 = 5, B_21 = 9 etc.

So A·B=C can be calculated like
C_11 = (A_11 * B_11) + (A_12 * B_21) = (5*7) + (4*9) = 35 + 36 = 71
C_12 = (A_11 * B_12) + (A_12 * B_22) = (5*3) + (4*6) = 15 + 24 = 39
C_21 = (A_21 * B_11) + (A_22 * B_21) = (3*7) + (2*9) = 21 + 18 = 39
C_22 = (A_21 * B_12) + (A_22 * B_22) = (3*3) + (2*6) = 9 + 12 = 21

	 |71, 39|	
So C=|32, 21|

Psuedocode
We will assume that A and B meet the criteria to be multiplied
Matrix-Multiply(A,B)
n = A.rows
m = A.cols
k = B.cols

C = nxk matrix

for i = 1 to n //Multiply each row in A
	for j = 1 to k//By each column in J
		for x = 1 to m //For each value in the rows and columns
			C_ij = C_ij + A_ix · B_xj

return C










