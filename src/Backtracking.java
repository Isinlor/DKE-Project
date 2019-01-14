import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Backtracking {

    private Graph graph;
    private int[] vertexColor;
    private int maxColors;

    public Backtracking(Graph graph) {
        this.graph = graph;
        setupVertexColor();
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

        if(graph.getLowerBound() == 2) {
            if (canBeColoredWith(2)) {
                graph.addChromaticNumber(2);
                return;
            } else {
                graph.addLowerBound(3);
            }
        }

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

    private boolean canBeColoredWith(int maxColors) {
        this.maxColors = maxColors;
        return tryToColor(1);
    }

    /**
     * ExactAlgorithm algorithm
     */
    private boolean tryToColor(int vertex) {

        if(vertex > graph.getNumberOfVertices()) return true;
        if(vertexColor[vertex] > 0) return tryToColor(vertex + 1);

        for(int color = 1; color <= maxColors; color++) {

            if(isAvailable(vertex, color)) {
                vertexColor[vertex] = color;
                if (tryToColor(vertex + 1)) {
                    vertexColor[vertex] = 0;
                    return true;
                }
                vertexColor[vertex] = 0;
            }

        }

        return false;
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
