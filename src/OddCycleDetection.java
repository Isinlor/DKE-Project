import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeoutException;

public class OddCycleDetection {

    Graph graph;
    long start = System.currentTimeMillis();

    public OddCycleDetection(Graph graph) {
        this.graph = graph;
        hasOddCycle();
    }

    private boolean hasOddCycle() {
        try {
            for (int vertex : graph.getVertices()) {
                if (hasOddCycle(vertex, 0, new ArrayList<Integer>())) {
                    graph.addLowerBound(3);
                    return true;
                }
            }
        } catch (TimeoutException e) {
            // No op
        }
        return false;
    }

    private boolean hasOddCycle(int vertex, int parent, ArrayList<Integer> visited) throws TimeoutException {

        if(System.currentTimeMillis() - start  > 500) {
            throw new TimeoutException();
        }

        visited.add(vertex);
        for (int neighbor: graph.getNeighbours(vertex)) {
            if(visited.contains(neighbor)) {
                if(neighbor != parent) {
                    int distance = visited.size() - visited.indexOf(neighbor);
                    if(distance % 2 == 1) {
                        return true;
                    }
                }
            } else if(hasOddCycle(neighbor, vertex, visited)) {
                return true;
            }
        }
        visited.remove(visited.size() - 1);

        return false;

    }

}
