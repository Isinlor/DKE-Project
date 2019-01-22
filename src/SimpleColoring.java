import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Check whether a graph is 2 colorable.
 *
 * Based on: http://www.cs.cmu.edu/afs/cs/academic/class/15451-s10/www/recitations/rec0318.txt
 */
public class SimpleColoring {

    /**
     * Try to color a graph with 2 colors.
     *
     * @param graph
     */
    public static void color(Graph graph) {

        if(graph.hasChromaticNumber()) return;

        // select an arbitrary vertex
        int vertex = graph.getVertices().iterator().next();

        int[] coloring = new int[graph.getNumberOfVertices() + 1];
        coloring[vertex] = 1; // color first vertex

        // fringe is a list of colored vertices of which neighbors need to be colored
        Queue<Integer> fringe = new LinkedList<Integer>();
        fringe.add(vertex);

        while(!fringe.isEmpty()) { // run until there is nothing left to color

            vertex = fringe.remove();

            // select the other color of the previously colored vertex
            int color = (coloring[vertex] % 2) + 1;

            // color neighbors of the previously colored vertex
            // selecting the other color should assure no conflicts if graph is 2-colorable
            for (int neighbor: graph.getNeighbours(vertex)) {

                // do not color neighbors twice
                if(coloring[neighbor] != 0) continue;

                // color the neighbor and add to the fringe
                coloring[neighbor] = color;
                fringe.add(neighbor);

            }

            // because fringe is progressing by coloring neighbors
            // if the starting vertex is not reachable from all other vertices
            // we may not color all vertices in the graph
            if(fringe.isEmpty()) {
                // make sure that all vertices are colored
                for (vertex = 1; vertex <= graph.getNumberOfVertices(); vertex++) {
                    // if a vertex is not colored, color it and add to the fringe
                    if(coloring[vertex] == 0) {
                        coloring[vertex] = 1;
                        fringe.add(vertex);
                        break;
                    }
                }
            }

        }

        // verify that graph is correctly colored
        for (Edge edge: graph.getEdges()) {
            if(coloring[edge.from] == coloring[edge.to]) {
                // graph was not correctly colored
                // because each coloring was forced the graph is not 2-colorable
                graph.addLowerBound(3);
                return;
            }
        }

        // the graph was correctly colored with two colors
        graph.addChromaticNumber(2);

    }

}
