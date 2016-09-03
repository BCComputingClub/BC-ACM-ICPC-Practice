Mergesort

Algorithm:
https://www.khanacademy.org/computing/computer-science/algorithms/merge-sort/a/overview-of-merge-sort
http://www.algorithmist.com/index.php/Merge_sort

Problem:
Sorts an array of comparable objects into an array of ascending order.

Edge Cases:
array must have more than one element

Summary:
Mergesort is a divide and conquer algorithm that will break an array into smaller arrays to compare and sort.
An array is divided in half recursively until the sections of the array contain one element. After the array
is divided the sections are merged by sorting each pair of sections into ascending order. This continues until
the two halves of the original array are sorted into ascending order.

Pseudocode:
func mergesort( var a as array )
     if ( n == 1 ) return a

     var l1 as array = a[0] ... a[n/2]
     var l2 as array = a[n/2+1] ... a[n]

     l1 = mergesort( l1 )
     l2 = mergesort( l2 )

     return merge( l1, l2 )
end func

func merge( var a as array, var b as array )
     var c as array

     while ( a and b have elements )
          if ( a[0] > b[0] )
               add b[0] to the end of c
               remove b[0] from b
          else
               add a[0] to the end of c
               remove a[0] from a
     while ( a has elements )
          add a[0] to the end of c
          remove a[0] from a
     while ( b has elements )
          add b[0] to the end of c
          remove b[0] from b
     return c
end func

Example:
Integer[] containing 35, 33, 42, 10, 14, 19, 27, 44, 26, 31 is sorted to 19, 26, 27, 31, 33, 35, 42, 44
Character[] containing 'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a' is sorted to 
a, b, c, d, e, f, g, h, i, j