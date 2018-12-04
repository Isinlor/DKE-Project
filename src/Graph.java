import java.awt.*;
import java.util.ArrayList;

public class Graph {

    /**
     * The number of vertices.
     *
     * Remember that vertices start from 1!
     */
    private int numberOfVertices;

    private Edge[] edges;

    protected Vertex[] vertices;

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

        for (Edge edge: edges) {

            // fill adjacency matrix in standard format
            adjacencyMatrix[edge.from][edge.to] = 1;
            adjacencyMatrix[edge.to][edge.from] = 1;

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

                    // standardize adjacency matrix
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

    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    /**
     * Returns the number of vertices.
     */
    public Edge[] getEdges() {
        return edges;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    /**
     * Check whether vertex A and vertex B share an edge.
     */
    public boolean isAdjacent(int vertexA, int vertexB) {
        return adjacencyMatrix[vertexA][vertexB] == 1;
    }

    public boolean isFullyColored() {
        for(Vertex vertex: vertices) {
            if(vertex == null) { continue; }
            if(!vertex.hasColor()) {
                return false;
            }
        }
        return true;
    }

    public int getColorCount() {

        int count = 0;
        ArrayList<Color> colors = new ArrayList<Color>();
        for(Vertex vertex: vertices) {
            if(vertex == null) { continue; }
            if(vertex.hasColor() && !colors.contains(vertex.getColor())) {
                count++;
                colors.add(vertex.getColor());
            }
        }

        return count;

    }

    public int getChromaticNumber() {
        return 2;
    }

    public boolean isOptimallyColored() {
        if(isFullyColored() && getColorCount() <= getChromaticNumber()) {
            return true;
        }
        return false;
    }

    public boolean isGraphColoredCorrectly() {
        if(!isFullyColored()) {
            return false;
        }
        // check edges
        return false;
    }

}
