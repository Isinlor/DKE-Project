/**
 * Allows to generate a random graph.
 *
 * Author: Tomek
 */
public class Random {

    public static void main(String[] args) {
        System.out.println(Random.generate(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1])
        ));
    }

    /**
     * @param vertices
     * @param edges
     * @return
     */
    public static Graph generate(long vertices, long edges) {

        // number of edges in a fully connected graph
        long maxEdges = (vertices * (vertices - 1)) / 2;
        if(edges > maxEdges) {
            throw new WrongGraphSpecificationException(""
                + "Graph having " + vertices + " vertices "
                + "can have maximally " + maxEdges + " edges!\n"
                + "While you requested " + edges + " edges!"
            );
        }

        if(vertices < 1) {
            throw new WrongGraphSpecificationException("Graph must have at least one vertex!");
        }

        if(edges < 0) {
            throw new WrongGraphSpecificationException("Number of edges must be a positive number!");
        }

        int[][] adjacencyMatrix = new int[(int) (vertices + 1)][(int) (vertices + 1)];

        long edgesToMake = edges;
        while(edgesToMake > 0) {

            int x = (int)(Math.random() * vertices) + 1;
            int y = (int)(Math.random() * vertices) + 1;

            if(x != y && adjacencyMatrix[x][y] == 0) {
                adjacencyMatrix[x][y] = 1;
                adjacencyMatrix[y][x] = 1;
                edgesToMake--;
            }

        }

        return new Graph(adjacencyMatrix, (int)vertices, (int)edges);

    }

}
