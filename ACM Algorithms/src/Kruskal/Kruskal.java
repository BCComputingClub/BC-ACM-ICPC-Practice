package Kruskal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import common.graph.Edge;
import common.graph.Graph;
import common.graph.Tree;
import common.graph.Vertex;
import common.graph.WeightedEdge;
import common.graph.WeightedGraph;
import common.graph.exception.InvalidConstructionException;
/**
 * 	@author Austin Lane
 *	
 *	There's no main method, you can just pass a graph into the run method in a driver program.
 */
public class Kruskal 
{
	/**
	 * This method can be used in the driver program to run Kruskal's algorithm
	 * @param graph = an undirected, weighted, and connected graph that will be processed
	 * @return a mst of the input graph
	 * @throws InvalidConstructionException 
	 */
	public static <E> Graph<Vertex<E>> run(WeightedGraph<E> graph) throws InvalidConstructionException
	{
		Hashtable<Vertex<E>, Vertex<E>> parents = new Hashtable<>();
		Hashtable<Vertex<E>, Integer> rank = new Hashtable<>();
		ArrayList<Vertex<E>> verts = graph.getVertecies();
		ArrayList<WeightedEdge<E>> x = new ArrayList<>();		// holds edges that will be added to the mst
		ArrayList<WeightedEdge<E>> extra = new ArrayList<>();	// holds extra tree edges
		Graph<Vertex<E>> tree = null;
		int highestRank = -1;						// holds the rank of the root vertex
		Vertex<E> root = null;
		
		
		// assigns each vertex as its own parent and sets ranks to 0
		for (Vertex<E> vert : verts)
		{
			parents.put(vert, vert);
			rank.put(vert, 0);
		}
		
		// sorts edges by ascending order of weight
		ArrayList<WeightedEdge<E>> edges = graph.getWeightedEdges();
		edges.sort(null);

		// takes out the lightest edge
		for (WeightedEdge<E> edge : edges)
		{
			// if an edge does not create a cycle it is added and union is called
			if (find(edge.getFirst(), parents) != find(edge.getSecond(), parents))
			{
				x.add(edge);
				union(edge, parents, rank);
			}
		}

		// finds root
		for (Vertex<E> v : rank.keySet())
		{
			if (rank.get(v) > highestRank)
			{
				highestRank = rank.get(v);
				root = v;
			}
		}

		// sets up tree
		tree = new Tree<Vertex<E>>(root);
		for (WeightedEdge<E> edge : x)
		{
			try {
			tree.addEdge(edge.getFirst(), edge.getSecond());
			} catch (InvalidConstructionException e) {
				extra.add(edge);
			}
		}
		for (WeightedEdge<E> edge : extra)
			tree.addEdge(edge.getFirst(), edge.getSecond());
		
		tree.print();
		return tree;
	}
	
	/**
	 * Utility method that compares vertecies and their parents
	 * @param vert = vertex to be compared
	 * @param set = hashtable of parents
	 * @return the vertex that was compared
	 */
	private static <E> Vertex<E> find(Vertex<E> vert, Hashtable<Vertex<E>, Vertex<E>> set)
	{
		while (vert != set.get(vert))
			vert = set.get(vert);
		
		return vert;
	}
	
	/**
	 * Utility method that reassigns ranks and parents
	 * @param edge = edge that was added to the mst
	 * @param table = hashtable of parents
	 * @param ranks = hashtable of ranks
	 */
	private static <E> void union(Edge<E> edge, Hashtable<Vertex<E>, Vertex<E>> table, Hashtable<Vertex<E>, Integer> ranks)
	{
		Vertex<E> firstPar = find(edge.getFirst(), table);
		Vertex<E> secondPar = find(edge.getSecond(), table);
		
		if (firstPar.equals(secondPar))
			return;
		
		if (ranks.get(firstPar) > ranks.get(secondPar))
			table.replace(secondPar, firstPar);
		else
		{
			table.replace(firstPar, secondPar);
			if (ranks.get(firstPar) == ranks.get(secondPar))
				ranks.replace(secondPar, ranks.get(secondPar) + 1);
		}
	}
}
