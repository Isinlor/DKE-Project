import java.awt.*;

/**
 * Represents an edge from vertex "from" to vertex "to"
 *
 * Remember that vertices start from 1!
 *
 * Author: Tomek
 */
public class Edge {

	public int from;
	public int to;

	/**
	 * @param from
	 * @param to
	 */
	public Edge(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public String toString() {
		return from + " " + to + "\n";
	}

}
