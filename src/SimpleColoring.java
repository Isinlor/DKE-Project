import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class SimpleColoring {

    public static void color(Graph graph) {

        if(graph.hasChromaticNumber()) return;

        int vertex = graph.getVertices().iterator().next();

        int[] vertexColor = new int[graph.getNumberOfVertices() + 1];
        vertexColor[vertex] = 1;

        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(vertex);

        while(!queue.isEmpty()) {
            vertex = queue.remove();
            int color = (vertexColor[vertex] % 2) + 1;
            for (int neighbor: graph.getNeighbours(vertex)) {
                if(vertexColor[neighbor] != 0) continue;
                vertexColor[neighbor] = color;
                queue.add(neighbor);
            }
            if(queue.isEmpty()) {
                for (vertex = 1; vertex <= graph.getNumberOfVertices(); vertex++) {
                    if(vertexColor[vertex] == 0) {
                        vertexColor[vertex] = 1;
                        queue.add(vertex);
                        break;
                    }
                }
            }
        }

        for (Edge edge: graph.getEdges()) {
            if(vertexColor[edge.from] == vertexColor[edge.to]) {
                graph.addLowerBound(3);
                return;
            }
        }

        graph.addChromaticNumber(2);

    }

}
