import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class Backtracking {

    private Graph graph;
    private int[] vertexColor;
    private int maxColors;
    private int startingVertex;
    private SortedMap<Integer, Integer> nextVertexMap;

    public Backtracking(Graph graph) {
        this.graph = graph;
        setupVertexColor();
        setupNextVertexMap();
    }

    public void colorClique(Set<Integer> clique) {
        setupVertexColor();
        int color = 1;
        for (int vertex: clique) {
            vertexColor[vertex] = color;
            color++;
        }
    }

    public void findUpperBound() {

        if(graph.getUpperBound() == graph.getLowerBound()) return;

        int upperBound = graph.getUpperBound() - 1;
        while (upperBound >= graph.getLowerBound() && canBeColoredWith(upperBound)) {
            graph.addUpperBound(upperBound);
            upperBound--;
        }
        graph.addChromaticNumber(upperBound + 1);

    }

    private void setupVertexColor() {
        vertexColor = new int[graph.getNumberOfVertices() + 1];
        Arrays.fill(vertexColor, 0);
    }

    private void setupNextVertexMap() {
        nextVertexMap = new ConcurrentSkipListMap<>();
        SortedSet<Integer> sortedVertices = graph.getSortedVertices();
        Iterator<Integer> sortedVerticesIteratorFrom = sortedVertices.iterator();
        Iterator<Integer> sortedVerticesIteratorTo = sortedVertices.iterator();

        startingVertex = sortedVerticesIteratorTo.next();
        while(sortedVerticesIteratorFrom.hasNext()) {
            int from = sortedVerticesIteratorFrom.next();
            int to = sortedVerticesIteratorTo.hasNext() ? sortedVerticesIteratorTo.next() : 0;
            nextVertexMap.put(from, to);
        }
    }

    private boolean canBeColoredWith(int maxColors) {
        this.maxColors = maxColors;
        return tryToColor(startingVertex);
    }

    /**
     * ExactAlgorithm algorithm
     */
    private boolean tryToColor(int vertex) {

        if(vertex == 0) return true;
        if(vertexColor[vertex] > 0) return tryToColor(nextVertex(vertex));

        for(int color = 1; color <= maxColors; color++) {

            if(isAvailable(vertex, color)) {
                vertexColor[vertex] = color;
                if (tryToColor(nextVertex(vertex))) {
                    vertexColor[vertex] = 0;
                    return true;
                }
                vertexColor[vertex] = 0;
            }

        }

        return false;
    }

    private int nextVertex(int currentVertex) {
        if(Thread.interrupted()) throw new RuntimeException();
        return nextVertexMap.get(currentVertex);
    }

    /**
     * Check if we can color vertex with a specific color
     */
    private boolean isAvailable(int vertexA, int color) {
        for (int vertexB: graph.getNeighbours(vertexA)) {
            if (vertexColor[vertexB] == color) return false;
        }
        return true;
    }

}
