Subset Sum Problem

Page 1128 in Introduction to Algorithms, 3rd ed. by Cormen, et.al

Problem: Given a set S and an integer t, find the subset S' of S such that the sum of the elements
in S' is the closest to t without exceeding t.

Restrictions/Edge Cases
Runs in exponential time, the book describes an additional algorithm that runs in polynomial
time, however, it only approximates the answer, so I will not be describing it here.

The algorithm could also take up exponential space in a worst case.

Summary
The algorithm below essentially work by trying all possible subsets of the given set. This is
done during the stage where it merges the list. The merge itself is exactly the same to what is used
in merge sort. By adding to the lists and merging them, it makes sure that every possible number is represented.
Possibly what is most important about this algorithm is its form. This is a very quick and easy way to brute
force and could be used in any situation where you are trying all the subsets of a set.



Pseudocode
Subscripts are denoted L_i
S = {x_1, x_2, ... , x_i)
**Notice** The notation L_i + x_i means x_i is added to every element in L_i
This algorithm will tell you if the subset exists and what the max is, but will not tell you the subset
that gave it the sum
Subset-Sum (S,t)
n = |S|
L_0 = 0
for i = 1 to n
	L_i = Merge-Lists(L_i-1, L_i-1 + x_i)
	remove every element from L_i that is greater than t
return the largest element in L_n

Example
Suppose S = {1, 2, 5, 6} and t = 10
Using the algorithm 
n = |S| //So n = 4
L_0 = 0
//Begin Loop
L_1 = {0,1} //After first iteration
L_2 = {0,1,2,3} //After second iteration
L_3 = {0,1,2,3,5,6,7,8} //After third iteration
L_4 = {0,1,2,3,5,6,7,8,9,11,12,13,14}//During fourth iteration
//Since L_4 has elements > t we remove them so
L_4 = {0,1,2,3,5,6,7,8,9}
//We then return the greatest element in L_n and since n = 4
return 9




