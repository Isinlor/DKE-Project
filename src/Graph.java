public class Graph {

    /**
     * The number of vertices.
     *
     * Remember that vertices start from 1!
     */
    private int numberOfVertices;

    private Edge[] edges;

    /**
     * Other representation of edges in a graph.
     *
     * Value 1 at adjacencyMatrix[x][y] means that vertices x and y share an edge.
     */
    private int[][] adjacencyMatrix;

    public Graph(Edge[] edges, int numberOfVertices) {

        this.edges = edges;
        this.numberOfVertices = numberOfVertices;

        // initialize adjacency matrix
        adjacencyMatrix = new int[numberOfVertices+1][numberOfVertices+1];

        for(int i=0; i < edges.length; i++) {

            // fill adjacency matrix in standard format
            adjacencyMatrix[edges[i].from][edges[i].to] = 1;
            adjacencyMatrix[edges[i].to][edges[i].from] = 1;

        }

    }

    public Graph(int[][] adjacencyMatrix, int numberOfVertices, int numberOfEdges) {

        this.adjacencyMatrix = adjacencyMatrix;
        this.numberOfVertices = numberOfVertices;

        edges = new Edge[numberOfEdges];

        int counter = 0;
        for(int i = 1; i <= numberOfVertices; i++) {
            for(int j = i+1; j <= numberOfVertices; j++) {

                if(adjacencyMatrix[i][j] == 1 || adjacencyMatrix[j][i] == 1) {

                    // standarize adjacency matrix
                    adjacencyMatrix[i][j] = 1;
                    adjacencyMatrix[j][i] = 1;

                    // create edge
                    edges[counter] = new Edge(i, j);

                    counter++;

                }

            }
        }

        if(numberOfEdges != counter) {
            throw new IllegalArgumentException(
                "Expected number of edges " + numberOfEdges + ", " +
                "from adjacency matrix retrived " + counter + "!"
            );
        }

    }

    /**
     * Returns the number of vertices.
     */
    public Edge[] getEdges() {
        return edges;
    }

    /**
     * Check whether vertex A and vertex B share an edge.
     */
    public boolean isAdjacent(int vertexA, int vertexB) {
        return adjacencyMatrix[vertexA][vertexB] == 1;
    }

}
