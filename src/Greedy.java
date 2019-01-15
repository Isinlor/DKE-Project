import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Greedy {

    static Graph graph;
    static int[] vertexColor;

    static void findUpperBound(Graph ofGraph) {

        graph = ofGraph;
        vertexColor = new int[graph.getNumberOfVertices() + 1];

        graph.addUpperBound(findColoring(graph.getVertices()));
        graph.addUpperBound(findColoring(graph.getDescendingDegreeSortedVertices()));
        graph.addUpperBound(findColoring(graph.getAscendingDegreeSortedVertices()));

    }

    private static int findColoring(Set<Integer> vertices) {
        int upperBound = 0;
        for (int vertex: vertices) {
            for (int color = 1; color <= graph.getNumberOfVertices(); color++) {
                if(isAvailable(vertex, color)) {
                    vertexColor[vertex] = color;
                    upperBound = color > upperBound ? color : upperBound;
                    break;
                }
            }
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
