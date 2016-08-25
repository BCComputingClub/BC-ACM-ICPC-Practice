Quicksort

Algorithm: 
http://www.tutorialspoint.com/data_structures_algorithms/quick_sort_algorithm.htm

Problem:
Sorts an array of comparable objects into an array of ascending order.

Edge Cases:
array must have more than one element

Summary:
The algorithm compares objects to a pivot object. This implementation always uses the rightmost element as
the pivot. A left pointer is assigned to the leftmost index and a right pointer is assigned to the rightmost 
index of the array, but left of the pivot. The left pointer is moved right until an element that is bigger than
the pivot. When a greater than element if found the right pointer is moved left until it finds an element less
than the pivot. If a greater than and less than element is found then the two elements are swapped and the left
pointer is moved right by one. When the two pointers meet the element they meet at is compared to the pivot. If
it is greater than the pivot then it is swapped with the pivot. The pivot is now in its rightful place in the
array. The algorithm recursively repeats for the left and right of the pivot. The recursive method stops running
there is no element to compare to the pivot.

Pseudocode:
function partitionFunc(left, right, pivot)
   leftPointer = left -1
   rightPointer = right

   while True do
      while A[++leftPointer] < pivot do
         //do-nothing            
      end while
		
      while rightPointer > 0 && A[--rightPointer] > pivot do
         //do-nothing         
      end while
		
      if leftPointer >= rightPointer
         break
      else                
         swap leftPointer,rightPointer
      end if
		
   end while 
	
   swap leftPointer,right
   return leftPointer
	
end function

Example:
Integer[] containing 17, 41, 5, 22, 54, 6, 29, 3, 13 is sorted to 3, 5, 6, 13, 17, 22, 29, 41, 54
Character[] containing 'a', 'z', 'c', 'b', 'q', 'A' is sorted to A, a, b, c, q, z