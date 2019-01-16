import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;

public class LocalSearch {

    static Graph graph;
    static int[] vertexColor;

    static void findUpperBound(Graph ofGraph) {

        graph = ofGraph;

        int numberOfVertices = graph.getNumberOfVertices();
        java.util.Random rng = new java.util.Random();
        Integer[] sortedVertices = graph.getDescendingDegreeSortedVertices().toArray(new Integer[0]);

        long lastUpdateAt = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {

            Integer[] newSortedVertices = sortedVertices.clone();

            swapVertices(numberOfVertices, rng, newSortedVertices);

            int newUpperBound = findColoring(newSortedVertices, graph.getUpperBound() - 1);
            if(newUpperBound != 0) {
                graph.addUpperBound(newUpperBound);
                sortedVertices = newSortedVertices;
                lastUpdateAt = System.currentTimeMillis();
            }

            if(lastUpdateAt < (System.currentTimeMillis() - 1000)) return;

        }

    }

    private static void swapVertices(int numberOfVertices, Random rng, Integer[] newSortedVertices) {
        int indexA = rng.nextInt(numberOfVertices);
        int vertexA = newSortedVertices[indexA];

        int indexB = rng.nextInt(numberOfVertices);
        while(indexA == indexB) indexB = rng.nextInt(numberOfVertices);
        int vertexB = newSortedVertices[indexB];

        newSortedVertices[indexA] = vertexB;
        newSortedVertices[indexB] = vertexA;
    }

    private static int findColoring(Integer[] vertices, int maxColors) {
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

    private static boolean isAvailable(int vertexA, int color) {
        for (int vertexB: graph.getNeighbours(vertexA)) {
            if (vertexColor[vertexB] == color) {
                return false;
            }
        }
        return true;
    }

}
