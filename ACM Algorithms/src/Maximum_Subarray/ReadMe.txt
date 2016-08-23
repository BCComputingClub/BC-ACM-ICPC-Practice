Maximum Subarray Problem

Page 68 in Introduction to Algorithms, 3rd ed. by Cormen, et.al,

Problem:
Given an array of length n, find the contiguous subarray with the greatest sum of its elements

Restrictions/Edge Cases
Size of the array >= 1
If all elements in the array are positive, then the answer is the entire array
If all elements in the array are negative, then the answer is the least negative number

Summary
The book outlines an algorithm for this problem that is much longer than the commonly used algorithm today.
The book shows an algorithm that runs in O(nlogn) time, but leaves as an exercise a problem to find the solution
to this problem that runs in O(n) time. This algorithm is called Kadane's algorithm and is what I will show.
The basic idea behind Kadane's algorithm, is that you add up all numbers in a continuous subarray, so long as
their sum remains positive. If it turns negative you begin a new subarray. **Notice** this covers the case if 
every number is negative or positive or a mixture of the two. If every number is negative, it will start a new subarray after each index,
which will lead to the least negative number being the max subarray. If every number is positive, it will continue to
add to the array, since the sum never goes negative. The case for where the numbers are both negative and positive
is a bit harder, but think of it like this: if the sum of the current sub array turns negative, then you can stop
checking that sub array and begin a new one. Why? If an array A has  a negative sum from A[1 to n], then 
A[1 to n] + A[n+1] will always be less than A[n+1]. Please see the example below for a concrete example. 

Psuedocode
Kadane's Algorithm(array[0..n])
(maxSum, maxStartIndex, maxEndIndex) := (-INFINITY, 0, 0)
currentMaxSum = 0
    currentStartIndex = 0
    for currentEndIndex 0= 0 to n do
        currentMaxSum = currentMaxSum + array[currentEndIndex]
        if currentMaxSum > maxSum then
            (maxSum, maxStartIndex, maxEndIndex) = (currentMaxSum, currentStartIndex, currentEndIndex)
        endif

        if currentMaxSum < 0 then
            currentMaxSum = 0
            currentStartIndex = currentEndIndex + 1
        endif
    endfor

    return (maxSum, maxStartIndex, maxEndIndex)
Algorithm from http://www.algorithmist.com/index.php/Kadane's_Algorithm

Example
I will outline a short example here, but for best examples, please see all of the test cases in the code.
I will only be keeping track of the currentMaxSum and the maxSum.
Suppose we have an array A={5,6,-12,8, 9}
Using the algorithm above
maxSum = 0
1st iteration
currentMaxSum = 5
maxSum = 5
2nd iteration
currentMaxSum = 11
maxSum = 11
3rd iteration //Notice that at this point in the algorithm we begin searching a new subarray, since this subarray, will no longer positively contribute to the sum
currentMaxSum = -1
maxSum = 11
In the 2nd if statement, since currentMaxSum < 0
currentMaxSum = 0
4th iteration
currentMaxSum = 8
maxSum = 11
5th iteration
currentMaxSum = 17
maxSum = 17

Using the algorithm we can keep track of the max sum, as well as the indices for where the max subarray occured.



