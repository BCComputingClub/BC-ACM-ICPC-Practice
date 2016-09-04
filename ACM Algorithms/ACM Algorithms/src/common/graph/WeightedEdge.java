package common.graph;

import common.graph.Edge;

public class WeightedEdge <T> extends Edge<T> implements Comparable<WeightedEdge<T>>
{
	private int weight;
	
	public WeightedEdge (Vertex<T> v1, Vertex<T> v2, int w) throws NullPointerException 
	{
		super(v1, v2);

		this.weight = w;
	}	
	
	public int getWeight()
	{
		return weight;
	}
	
	public String toString() {
		return super.getFirst().toString() + "," + super.getSecond().toString() + "," + weight;
	}

	@Override
	public int compareTo(WeightedEdge<T> agr0) {
		if (weight < agr0.getWeight())
			return -1;
		else if (weight == agr0.getWeight())
			return 0;
		else
			return 1;
	}
}
