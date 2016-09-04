package common.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import common.graph.exception.InvalidConstructionException;

public class WeightedGraph <T> extends ConnectedGraph<T>
{
	private Hashtable<Vertex<T>, ArrayList<WeightedEdge<T>>> adjacencyList = null;
	
	public WeightedGraph()
	{
		super();
		adjacencyList = new Hashtable<Vertex<T>, ArrayList<WeightedEdge<T>>>();
	}
	
	public ArrayList<Vertex<T>> getVertecies()
	{
		ArrayList<Vertex<T>> list = new ArrayList<>();
		list.addAll(adjacencyList.keySet());
		
		return list;
	}
	
	public ArrayList<WeightedEdge<T>> getWeightedEdges()
	{
		ArrayList<WeightedEdge<T>> list = new ArrayList<>();
		Set<Vertex<T>> set = adjacencyList.keySet();
		ArrayList<WeightedEdge<T>> vals = null;
			
		for (Vertex<T> vert : set)
		{
			vals = adjacencyList.get(vert);
				
			for (WeightedEdge<T> edge : vals)
			{
				if (!list.contains(edge))
				list.add(edge);
			}
		}
			
		return list;
	}
	
	public WeightedEdge<T> addEdge(T elm1, T elm2, int weight) throws InvalidConstructionException{
		if (elm1 == null || elm2 == null)
			throw new NullPointerException();

		Vertex<T> u = findVertex(elm1);
		
		if (u == null)
			u = _addVertex(elm1);

		Vertex<T> v = findVertex(elm2);
		if (v == null)
			v = _addVertex(elm2);
		
		WeightedEdge<T> edge = new WeightedEdge<T>(u, v, weight);
		
		ArrayList<WeightedEdge<T>> list = adjacencyList.get(u);
		list.add(edge);
		adjacencyList.put(u, list);

		list = adjacencyList.get(v);
		list.add(edge);
		adjacencyList.put(v, list);

		if (!v.equals(u))
			unionComponents(u, v);

		return edge;
	}
	
	private Vertex<T> findComponent(Vertex<T> v) {
		Vertex<T> curVertex = v;
		while(!curVertex.equals(connectedComponents.get(curVertex)))
			curVertex = connectedComponents.get(curVertex);
		return curVertex;
	}

	private HashSet<Vertex<T>> getAllComponents(){
		HashSet<Vertex<T>> set = new HashSet<>();
		
		for(Vertex<T> v: connectedComponents.keySet())
			set.add(findComponent(v));
		
		return set;
	}
	
	public Vertex<T> findVertex(T elm) {
		if (elm == null)
			throw new NullPointerException();

		HashSet<Vertex<T>> set = getAllComponents();

		for (Vertex<T> v: set) {
			Vertex<T> foundVertex = BFSearch(v, elm);

			if (foundVertex != null)
				return foundVertex;
		}

		return null;
	}
	
	private void setAllVisitedFalse() {
		for (Vertex<T> u : adjacencyList.keySet())
			u.setVisited(false);
	}
	
	public Vertex<T> BFSearch(Vertex<T> start, T elm) {
		if (elm == null || start == null)
			throw new NullPointerException();

		if (!adjacencyList.containsKey(start)) {
			System.out.println("Search failed: invalid start vertex");
			return null;
		}

		setAllVisitedFalse();

		ArrayDeque<Vertex<T>> queue = new ArrayDeque<>();
		queue.add(start);
		start.setVisited(true);

		//System.out.print("Search: ");
		while (!queue.isEmpty()) {
			Vertex<T> u = queue.remove();
			//System.out.print(u.getData() + " ");

			if (u.getData().equals(elm)) {
				//System.out.println();
				return u;
			}

			ArrayList<WeightedEdge<T>> list = adjacencyList.get(u);
			if (list == null)
				continue;

			for (Edge<T> e: list) {
				Vertex<T> v = e.getAdjacentVertex(u);
				if (v.getVisited() == false) {
					v.setVisited(true);
					queue.add(v);
				}
			}
		}
		//System.out.println();
		return null;
	}
	
	private Vertex<T> _addVertex(T elm) throws InvalidConstructionException{
		if (elm == null)
			throw new NullPointerException();

		Vertex<T> v = findVertex(elm);

		if (v == null) {
			v = new Vertex<T>(elm);
			adjacencyList.put(v, new ArrayList<>());
			connectedComponents.put(v, v);
			//System.out.println("Added (" + elm + ")");
		}

		return v;
	}
	
	public void print() {
		System.out.println("Graph");
		for (Vertex<T> v: adjacencyList.keySet()) {
			System.out.print("[" + v.toString() + "] ");
			ArrayList<WeightedEdge<T>> edges = adjacencyList.get(v);
			if (edges != null) {
				for (WeightedEdge<T> e: edges) {
					if (e.getFirst().equals(v))
						System.out.print(e.getSecond().toString());
					else
						System.out.print(e.getFirst().toString());

					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
}
