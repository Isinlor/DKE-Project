/**
 * Allows to generate a random graph.
 *
 * Author: Tomek
 */
public class Random {

    /**
     * @param vertices
     * @param edges
     * @return
     */
    public static Graph generate(int vertices, int edges) {

        // number of edges in a fully connected graph
        int maxEdges = (vertices * (vertices - 1)) / 2;
        if(edges > maxEdges) {
            throw new WrongGraphSpecificationException(""
                + "GraphX having " + vertices + " vertices "
                + "can have maximally " + maxEdges + " edges!\n"
                + "While you requested " + edges + " edges!"
            );
        }

        if(vertices < 1) {
            throw new WrongGraphSpecificationException("GraphX must have at least one vertice!");
        }

        if(edges < 0) {
            throw new WrongGraphSpecificationException("Number of edges must be a positive number!");
        }

        int[][] adjacencyMatrix = new int[vertices + 1][vertices + 1];

        int edgesToMake = edges;
        while(edgesToMake > 0) {

            int x = (int)(Math.random() * vertices) + 1;
            int y = (int)(Math.random() * vertices) + 1;

            if(x > y && adjacencyMatrix[x][y] == 0) {
                adjacencyMatrix[x][y] = 1;
                adjacencyMatrix[y][x] = 1;
                edgesToMake--;
            }

        }

        return new Graph(adjacencyMatrix, vertices, edges);

    }

}
