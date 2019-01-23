import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * The main class containing methods and information related to the graph.
 *
 * The class can be constructed based on adjacency matrix or list of edges.
 *
 * It can indicate whether the graph is fully, correctly and optimally colored.
 *
 * Author: Tomek
 */
public class Graph {

    static boolean PRINT_BOUNDS = true;

    /**
     * List of vertices.
     */
    private Set<Integer> vertices = new HashSet<Integer>();

    /**
     * List of vertices sorted in a way that a greedy algorithm to use the least amount of colors.
     */
    private SortedSet<Integer> sortedVertices = new ConcurrentSkipListSet<>();
    private int amountOfColorsNeededForSortedVertices;

    /**
     * List of edges.
     */
    private Edge[] edges;

    /**
     * Other representation of edges in a graph.
     *
     * Value 1 at adjacencyMatrix[x][y] means that vertices x and y share an edge.
     */
    private int[][] adjacencyMatrix;

    /**
     * Yet another representation of edges in a graph.
     *
     * It's a map from a vertex to the set of vertex neighbours.
     */
    private Map<Integer, Set<Integer>> adjacencyMap = new HashMap<>();

    private int lowerBound;
    private int upperBound;

    /**
     * Constructor based on list of edges.
     *
     * @param edges
     * @param numberOfVertices
     */
    public Graph(Edge[] edges, int numberOfVertices) {

        setVertices(numberOfVertices);
        this.edges = edges;

        // initialize adjacency matrix
        adjacencyMatrix = new int[numberOfVertices+1][numberOfVertices+1];

        for (Edge edge: edges) {

            // fill adjacency matrix in standard format
            adjacencyMatrix[edge.from][edge.to] = 1;
            adjacencyMatrix[edge.to][edge.from] = 1;

        }

        createAdjacencyMap();
        setBounds();

    }

    /**
     * Constructor based on adjacency matrix.
     *
     * @param adjacencyMatrix
     * @param numberOfVertices
     * @param numberOfEdges
     */
    public Graph(int[][] adjacencyMatrix, int numberOfVertices, int numberOfEdges) {

        setVertices(numberOfVertices);

        this.adjacencyMatrix = adjacencyMatrix;

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
            throw new WrongGraphSpecificationException(
                "Expected number of edges " + numberOfEdges + ", " +
                "from adjacency matrix retrieved " + counter + "!"
            );
        }

        createAdjacencyMap();
        setBounds();

    }

    private void setVertices(int numberOfVertices) {
        setUpperBound(numberOfVertices);
        for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
            vertices.add(vertex);
        }
    }

    private void createAdjacencyMap() {
        for (int vertexA: vertices) {
            Set<Integer> neighbours = new HashSet<Integer>();
            for (int vertexB: vertices) {
                if(vertexA == vertexB) continue;
                if(isAdjacent(vertexA, vertexB)) neighbours.add(vertexB);
            }
            adjacencyMap.put(vertexA, neighbours);
        }
    }

    private void setBounds() {
        if(getNumberOfVertices() > 0) { addLowerBound(1); }
        if(getNumberOfEdges() > 0) { addLowerBound(2); }
        if(isComplete()) { addChromaticNumber(getNumberOfVertices()); }
    }

    public int getNumberOfVertices() {
        return vertices.size();
    }

    public int getNumberOfEdges() {
        return edges.length;
    }

    public int getMaxNumberOfEdges() {
        return (getNumberOfVertices() * (getNumberOfVertices() - 1)) / 2;
    }

    public double getDensity() {
        return Math.round((float)getNumberOfEdges() / (float)getMaxNumberOfEdges()  * 100.0) / 100.0;
    }

    public boolean isComplete() {
        return getMaxNumberOfEdges() == getNumberOfEdges();
    }

    public void addChromaticNumber(int chromaticNumber) {
        addLowerBound(chromaticNumber);
        addUpperBound(chromaticNumber);
    }

    public void addLowerBound(int lowerBound) {
        if(lowerBound > this.lowerBound) {
            setLowerBound(lowerBound);
        }
        if(Thread.interrupted()) throw new RuntimeException();
    }

    private void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
        if(PRINT_BOUNDS) System.out.println("NEW BEST LOWER BOUND = " + lowerBound);
    }

    public void addUpperBound(int upperBound) {
        if(upperBound < this.upperBound) {
            setUpperBound(upperBound);
        }
        if(Thread.interrupted()) throw new RuntimeException();
    }

    private void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
        if(PRINT_BOUNDS) System.out.println("NEW BEST UPPER BOUND = " + upperBound);
    }

    public int getLowerBound() {
        if(Thread.interrupted()) throw new RuntimeException();
        return lowerBound;
    }

    public int getUpperBound() {
        if(Thread.interrupted()) throw new RuntimeException();
        return upperBound;
    }

    public boolean hasChromaticNumber() {
        if(Thread.interrupted()) throw new RuntimeException();
        return upperBound == lowerBound;
    }

    /**
     * Returns the number of vertices.
     */
    public Edge[] getEdges() {
        return edges;
    }

    public Set<Integer> getVertices() {
        return vertices;
    }

    public SortedSet<Integer> getSortedVertices() {
        if(sortedVertices.isEmpty()) {
            return getDescendingDegreeSortedVertices();
        }
        return sortedVertices;
    }

    public void addSortedVertices(int colors, SortedSet<Integer> sortedVertices) {

        addUpperBound(colors);

        if(sortedVertices.size() != getNumberOfVertices()) {
            (new Exception("Wrong amount of sorted vertices!")).printStackTrace();
            System.exit(0);
        }

        if(colors < amountOfColorsNeededForSortedVertices) {
            amountOfColorsNeededForSortedVertices = colors;
            this.sortedVertices = sortedVertices;
        }

    }

    public SortedSet<Integer> getDescendingDegreeSortedVertices() {
        SortedSet<Integer> sortedVertices = new ConcurrentSkipListSet<Integer>(
                (Integer vertexA, Integer vertexB) -> {
                    if(getDegree(vertexA) == getDegree(vertexB)) {
                        return Integer.compare(vertexA, vertexB);
                    }
                    return Integer.compare(getDegree(vertexA), getDegree(vertexB));
                }
        );
        sortedVertices.addAll(vertices);
        return sortedVertices;
    }

    public SortedSet<Integer> getAscendingDegreeSortedVertices() {
        SortedSet<Integer> sortedVertices = new ConcurrentSkipListSet<Integer>(
                (Integer vertexA, Integer vertexB) -> {
                    if(getDegree(vertexA) == getDegree(vertexB)) {
                        return -Integer.compare(vertexA, vertexB);
                    }
                    return -Integer.compare(getDegree(vertexA), getDegree(vertexB));
                }
        );
        sortedVertices.addAll(vertices);
        return sortedVertices;
    }

    /**
     * Check whether vertex A and vertex B share an edge.
     */
    public boolean isAdjacent(int vertexA, int vertexB) {
        return adjacencyMatrix[vertexA][vertexB] == 1;
    }

    public Map<Integer, Set<Integer>> getAdjacencyMap() {
        return adjacencyMap;
    }

    public Set<Integer> getNeighbours(int vertex) {
        return adjacencyMap.get(vertex);
    }

    public int getDegree(int vertex) {
        return getNeighbours(vertex).size();
    }

    public Graph simplify() {

        // list vertices with degree 1 to remove
        ArrayList<Integer> verticesToRemove = new ArrayList<Integer>();
        for (Integer vertex: vertices) {
            if(getDegree(vertex) < 2) {
                verticesToRemove.add(vertex);
            }
        }

        if(verticesToRemove.isEmpty()) {
            return this;
        }

        // retain continuity in list of vertices
        // map old vertices to new vertices
        int removedVerticesCounter = 0;
        HashMap<Integer, Integer> oldToNewVertexMapping = new HashMap<>();
        for(Integer vertex: vertices) {
            if(verticesToRemove.contains(vertex)) {
                removedVerticesCounter++;
            } else {
                oldToNewVertexMapping.put(vertex, vertex - removedVerticesCounter);
            }
        }

        // list edges to retain, only edges of not removed vertices are retained
        ArrayList<Edge> retainedEdges = new ArrayList<>();
        for (Edge edge: edges) {
            if(!verticesToRemove.contains(edge.from) && !verticesToRemove.contains(edge.to)) {
                retainedEdges.add(edge);
            }
        }

        // map old retained edges, to new edges
        ArrayList<Edge> newEdges = new ArrayList<>();
        for(Edge oldEdge: retainedEdges) {
            newEdges.add(new Edge(
                oldToNewVertexMapping.get(oldEdge.from),
                oldToNewVertexMapping.get(oldEdge.to)
            ));
        }

        if(edges.length != 0 && newEdges.isEmpty()) {
            Edge[] lastEdge = {new Edge(1, 2)};
            return new Graph(lastEdge, 2);
        }

        if(newEdges.isEmpty() && !vertices.isEmpty()) {
            return new Graph(new Edge[0], 1);
        }

        // return simplified graph
        return (new Graph(newEdges.toArray(new Edge[0]), oldToNewVertexMapping.size())).simplify();

    }

    public String getShortDescription() {
        String string = "";
        string += "VERTICES = " + getNumberOfVertices() + "\n";
        string += "EDGES = " + getNumberOfEdges() + "\n";
        return string;
    }

    public String toString() {
        StringBuilder string = new StringBuilder(getShortDescription());
        for (Edge edge: edges) {
            string.append(edge);
        }
        return string.toString();
    }

}
