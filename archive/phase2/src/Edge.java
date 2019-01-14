import java.awt.*;

/**
 * Represents an edge from vertex "from" to vertex "to"
 *
 * Remember that vertices start from 1!
 *
 * Author: Tomek
 */
public class Edge {

    final static public Color DEFAULT_COLOR = Color.black;
    final static public Color INVALID_COLOR = Color.red;

	public int from;
	public int to;

    /**
     * Whether from and to have the same colors.
     *
     * If either of ends is not colored, edge is valid.
     */
	public boolean valid = true;

	/**
	 * @param from
	 * @param to
	 */
	public Edge(int from, int to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * @return edge color indicating whether edge is valid
	 */
	public Color getColor() {
	    return valid ? DEFAULT_COLOR : INVALID_COLOR;
    }

}
