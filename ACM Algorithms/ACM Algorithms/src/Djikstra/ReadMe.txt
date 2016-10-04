Djikstra

Algorithm:
https://www.youtube.com/watch?v=gdmfOwyQlcI
http://www.geeksforgeeks.org/greedy-algorithms-set-6-dijkstras-shortest-path-algorithm/

Problem:
Djikstras algorithm finds the shortest path (lowest sum of edge weights along path) from
one point to another in a weighted graph

Edge Cases:
graph must be undirected, weighted, and connected
there must be at least 2 vertecies

Summary:
Djikstra's algorithm searches for the lowest sum path through a graph. Each vertex is given a
distance (this is the distance from the start vertex to that vertex), the start vertex begins
at 0 and the rest begin at infinity. The algorithm searches through all the edges from the start
node and finds the lowest total distance to the vertex, whichever edge has the lowest distance
is added to a checked list annd its parent is set to the previous vertex in the path. The algorithm 
continuously searches through the edges connected to the vertecies in the checked list and adds the 
lowest distance until it finds the end vertex. After the end vertex is found the graph is populated
by tracing the parent of the end node back to the start node (which has the parent null).

Pseudocode:
function Dijkstra(Graph, source):

	create vertex set Q

	for each vertex v in Graph:
		dist[v] = INFINITY
		prev[v] = UNDEFINED
		add v to Q

	dist[source] = 0
  
	while Q is not empty:
		u = vertex in Q with min dist[u]
		remove u from Q 

		for each neighbor v of u:
			alt = dist[u] + length(u, v)
			if alt < dist[v]:
				dist[v] = alt 
				prev[v] = u 

	graph = empty sequence
	u = target
	while parent[u] is defined:
		insert u to graph
		u = parent[u]
	insert u to graph

	return graph

Example:
the graph:
  1-----2
 /|     |
0 | /---8
 \|/    |
  7-----6
with the edges (first, second, weight):
(0, 1, 4)
(0, 7, 8)
(1, 2, 8)
(1, 7, 11)
(2, 8, 2)
(7, 8, 7)
(7, 6, 1)
(6, 8, 6)
is sorted into:
  1------2
 /       |
0        8
with the edges
(0, 1, 4)
(1, 2, 8)
(2, 8, 2)