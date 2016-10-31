Kruskal

Algorithm:
https://www.youtube.com/watch?v=71UQH7Pr9kU

Problem:
Kruskal's algorithm creates a minimum spanning tree out of a graph. This algorithm returns
a graph, not a tree. A minimum spanning tree is a tree that contains every vertex and the 
has the minimum total weight of all edges

Edge Cases:
graph must be undirected, weighted, and connected
the algorithm runs until there are v - 1 edges, so there must be at least 2 vertecies

Summary:
Kruskal's algorithm takes in an undirected, connected, and weighted graph with edges with comparable objects
as the weight. The edges are sorted into ascending order and a loop pulls the lightest edges for processing.
First a two hashtables are built to hold the parent of each vertex and the distance from the top of the tree.
If the edge picked from the sorted list does not create a cycle if added to the list of edges in the mst then 
it is added to the mst and a method reprocesses the rank and parents of the existing graph.

Pseudocode:
Input: A connected undirected graph G = (V,E) with edge weights we
Output: A minimum spanning tree defined by the edges X

for all u in V :
makeset(u)

X = {}
Sort the edges E by weight
for all edges {u, v} in E, in increasing order of weight:
if find(u) != find(v):
add edge {u, v} to X
union(u, v)
--------------------
makeset(x)
parent(x) = x
rank(x) = 0
--------------------
union
rx = find(x)
ry = find(y)
if rx = ry: return
if rank(rx) > rank(ry):
parent(ry) = rx
else:
parent(rx) = ry
if rank(rx) = rank(ry) : rank(ry) = rank(ry) + 1
---------------------
find
while x != parent(x) : x = parent(x)
return x

Example:
the graph:
1------4-----6-----7
| \	  /     /
|	3     /
| /   \ /
2------5
with the edges (first, second, weight):
(1, 2, 2)
(2, 3, 4)
(3, 4, 5)
(3, 1, 3)
(2, 5, 3)
(5, 6, 8)
(6, 7, 9)
(6, 4, 7)
(4, 1, 3)
(5, 3, 1)
is sorted into:
1---4---6---7
| \
|	3
|	  \
2		5
with the edges
(5, 3, 1)
(1, 2, 2)
(3, 1, 3)
(4, 1, 3)
(6, 4, 7)
(6, 7, 9)