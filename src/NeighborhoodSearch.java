import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class NeighborhoodSearch {

    private Graph graph;
    private int[] vertexColor;
    private int timeLimit;

    public NeighborhoodSearch(Graph graph, int timeLimit) {
        this.graph = graph;
        this.timeLimit = timeLimit;
        vertexColor = new int[graph.getNumberOfVertices() + 1];
    }

    public void findUpperBound() {

        int numberOfVertices = graph.getNumberOfVertices();
        Random rng = new Random();

        Integer[] sortedVertices = graph.getSortedVertices().toArray(new Integer[0]);
        Integer[] newSortedVertices = sortedVertices.clone();

        long lastUpdateAt = System.currentTimeMillis();
        while(!graph.hasChromaticNumber()) {

            int newUpperBound = findColoring(newSortedVertices, graph.getUpperBound());
            if(newUpperBound != 0) {
                if(graph.getUpperBound() > newUpperBound) {
                    lastUpdateAt = System.currentTimeMillis();
                    graph.addSortedVertices(newUpperBound, new ConcurrentSkipListSet<>(Arrays.asList(newSortedVertices)));
                }
                sortedVertices = newSortedVertices;
            }

            if(lastUpdateAt < (System.currentTimeMillis() - timeLimit)) return;

            newSortedVertices = sortedVertices.clone();
            swapVertices(numberOfVertices, rng, newSortedVertices);

        }

    }

    private void swapVertices(int numberOfVertices, Random rng, Integer[] newSortedVertices) {
        int indexA = rng.nextInt(numberOfVertices);
        int vertexA = newSortedVertices[indexA];

        int indexB = rng.nextInt(numberOfVertices);
        while(indexA == indexB) indexB = rng.nextInt(numberOfVertices);
        int vertexB = newSortedVertices[indexB];

        newSortedVertices[indexA] = vertexB;
        newSortedVertices[indexB] = vertexA;
    }

    private int findColoring(Integer[] vertices, int maxColors) {
        int upperBound = 0;
        vertexColor = new int[graph.getNumberOfVertices() + 1];
        for (int vertex: vertices) {
            boolean canColor = false;
            for (int color = 1; color <= maxColors; color++) {
                if(isAvailable(vertex, color)) {
                    vertexColor[vertex] = color;
                    upperBound = color > upperBound ? color : upperBound;
                    canColor = true;
                    break;
                }
            }
            if(!canColor) return 0;
        }
        return upperBound;
    }

    private boolean isAvailable(int vertexA, int color) {
        for (int vertexB: graph.getNeighbours(vertexA)) {
            if (vertexColor[vertexB] == color) {
                return false;
            }
        }
        return true;
    }

}
