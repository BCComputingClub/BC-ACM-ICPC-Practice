package Djikstra;
/**
 * 	@author Austin Lane
 *	Djiskta finds the shortest path from one point in a graph to another
 *	There's no main method, you can just pass a graph into the run method in a driver program.
 */
import java.util.ArrayList;
import java.util.Hashtable;

import common.graph.Tree;
import common.graph.Vertex;
import common.graph.WeightedEdge;
import common.graph.WeightedGraph;
import common.graph.exception.InvalidConstructionException;

public class Djikstra <E> 
{	
	/**
	 * This method can be used in the driver program to run Djikstras algorithm
	 * @param graph = the graph that is being searched through
	 * @param start = the vertex to start at
	 * @param end = the vertex that needs to be found
	 * @return a graph of only the vertecies in the shortest path
	 * @throws InvalidConstructionException
	 */
	public static <E> WeightedGraph<E> run (WeightedGraph<E> graph, Vertex<E> start, Vertex<E> end) 
			throws InvalidConstructionException
	{
		ArrayList<Vertex<E>> checked = new ArrayList<>();
		ArrayList<Vertex<E>> que = new ArrayList<>();
		Hashtable<Vertex<E>, ArrayList<WeightedEdge<E>>> adj = graph.getHashtable();
		Hashtable<Vertex<E>, Integer> dist = new Hashtable<>();
		Vertex<E> curNode = null;
		Vertex<E> lowVert = null;
		WeightedGraph<E> tree = new WeightedGraph<E>();
		boolean found;
		int alt;
		int lowDist;
		int x;
		
		// gets all vertecies and sets their parent to null and set the distance to infinity (integer max)
		que = graph.getVertecies();
		for (Vertex<E> vert : que)
		{
			dist.put(vert, Integer.MAX_VALUE);
			vert.setParent(null);
			if (vert.equals(start))
				dist.replace(vert, 0);
		}
		
		// finds the first node and sets it as the current node
		x = 0;
		found = false;
		while (x < que.size() && !found)
		{
			if (que.get(x).equals(start))
				curNode = que.remove(x);
			x++;
		}
		checked.add(curNode);
		
		do
		{
			lowDist = Integer.MAX_VALUE;
			
			// goes through all edges connected to the checked list
			for (Vertex<E> curNode1 : checked)
			{
				for (WeightedEdge<E> edge : adj.get(curNode1))
				{
					if(!edge.getFirst().equals(curNode1) && !checked.contains(edge.getFirst()))
					{
						alt = dist.get(curNode1) + edge.getWeight();
						if (alt < dist.get(edge.getFirst()))
						{
							dist.replace(edge.getFirst(), alt);
							edge.getFirst().setParent(edge.getSecond());
						}
						// if this is the lowest vertex
						if (alt < lowDist)
						{
							lowDist = alt;
							lowVert = edge.getFirst();
						}
					}
					else if (!checked.contains(edge.getSecond()))
					{
						alt = dist.get(curNode1) + edge.getWeight();
						if (alt < dist.get(edge.getSecond()))
						{
							dist.replace(edge.getSecond(), alt);
							edge.getSecond().setParent(edge.getFirst());
						}
						// if this is the lowest vertex
						if (alt < lowDist)
						{
							lowDist = alt;
							lowVert = edge.getSecond();
						}
					}
				}
			}
			
			// finds the next node
			x = 0;
			found = false;
			while (x < que.size() && !found)
			{
				if (que.get(x).equals(lowVert))
				{
					curNode = que.remove(x);
					checked.add(curNode);
				}
				x++;
			}
		} while (curNode.getData() != end.getData());
		
		// finds the first and last nodes
		for (Vertex<E> e : checked)
		{
			if (e.equals(end))
				curNode = e;
		}
	
		// adds all the relevent nodes to the graph
		while (!curNode.equals(start))
		{
			for (int i = 0; i < adj.get(curNode).size(); i++)
			{
				if (adj.get(curNode).get(i).getFirst().equals(curNode.getparent()) || 
						adj.get(curNode).get(i).getFirst().equals(curNode.getparent()))
				{
					tree.addEdge(adj.get(curNode).get(i).getFirst(), adj.get(curNode).get(i).getSecond(), 
							adj.get(curNode).get(i).getWeight());
				}
			}
			curNode = curNode.getparent();
		}
		
		return tree;
	}
}
