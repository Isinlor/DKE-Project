import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

public class TimedBacktracking {

    private Graph graph;
    private int[] vertexColor;
    private int[] startColoring;
    private int maxColors;
    private int startingVertex;
    private SortedMap<Integer, Integer> nextVertexMap;

    private long startTime;

    public TimedBacktracking(Graph graph) {
        this.graph = graph;
        setupVertexColor();
        setupNextVertexMap();
    }

    public void colorClique(Set<Integer> clique) {
        setupVertexColor();
        int color = 1;
        for (int vertex: clique) {
            vertexColor[vertex] = color;
            startColoring[vertex] = color;
            color++;
        }
    }

    public void findUpperBound() {

        if(graph.getUpperBound() == graph.getLowerBound()) return;

        int nextLowerBound = graph.getLowerBound();
        int nextUpperBound = graph.getUpperBound() - 1;

        boolean flag = true;
        while (nextLowerBound != nextUpperBound) {
            try {

                int colors;
                if(flag) {
                    colors = nextLowerBound;
                    nextLowerBound++;
                } else {
                    colors = nextUpperBound;
                    nextUpperBound--;
                }

                if (canBeColoredWith(colors)) {
                    graph.addUpperBound(colors);
                } else {
                    graph.addLowerBound(colors);
                }

            } catch (TimeoutException e) {
                flag = !flag;
            }
        }

    }

    private boolean canBeColoredWith(int maxColors) throws TimeoutException {
        this.startTime = System.currentTimeMillis();
        this.maxColors = maxColors;
        this.vertexColor = startColoring.clone();
        return tryToColor(startingVertex);
    }

    /**
     * ExactAlgorithm algorithm
     */
    private boolean tryToColor(int vertex) throws TimeoutException {

        if(System.currentTimeMillis() - startTime > 5000) {
            if(Thread.interrupted()) throw new RuntimeException();
            throw new TimeoutException();
        }

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
        return nextVertexMap.get(currentVertex);
    }

    private void setupVertexColor() {
        vertexColor = new int[graph.getNumberOfVertices() + 1];
        Arrays.fill(vertexColor, 0);
        startColoring = new int[graph.getNumberOfVertices() + 1];
        Arrays.fill(startColoring, 0);
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
