import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LocalSearch2 {

    private Graph graph;
    private int[] coloring;
    private int conflicts;
    private Map<Integer, Integer> conflictsPerVertex = new HashMap<>();

    private int timeLimit;
    private Random rng = new Random();

    public LocalSearch2(Graph graph, int timeLimit) {
        this.graph = graph;
        this.timeLimit = timeLimit;
        randomColoring();
    }

    private void randomColoring() {
        Random rng = new Random();
        coloring = new int[graph.getNumberOfVertices() + 1];
        for (int vertex: graph.getVertices()) {
            coloring[vertex] = nextPositiveInt(graph.getUpperBound() - 1);
        }
        conflicts = 0;
        for (Edge edge: graph.getEdges()) {
            if(coloring[edge.from] == coloring[edge.to]) conflicts++;
        }
        for(int vertex: graph.getVertices()) {
            int vertexConflicts = getVertexConflicts(vertex, coloring[vertex]);
            conflictsPerVertex.put(vertex, vertexConflicts);
        }

    }

    private int getVertexConflicts(int vertex, int vertexColor) {
        int vertexConflicts = 0;
        for(int neighbor: graph.getNeighbours(vertex)) {
            if(vertexColor == coloring[neighbor]) vertexConflicts++;
        }
        return vertexConflicts;
    }

    public void findUpperBound() {

        int numberOfVertices = graph.getNumberOfVertices();


        while(true || !graph.hasChromaticNumber()) {

            long lastUpdateAt = System.currentTimeMillis();
            int attemptedUpperBound = graph.getUpperBound() - 1;

            while(conflicts > 0) {

                if(lastUpdateAt < (System.currentTimeMillis() - timeLimit)) return;

                int vertex = nextPositiveInt(numberOfVertices);
                int vertexColor = coloring[vertex];

                int newVertexColor = nextPositiveInt(attemptedUpperBound);
                while(vertexColor == newVertexColor) {
                    newVertexColor = nextPositiveInt(attemptedUpperBound);
                }

                int newConflicts = getVertexConflicts(vertex, newVertexColor);
                if(newConflicts < conflictsPerVertex.get(vertex)) {
//                    System.out.println("change: " + (newConflicts - conflictsPerVertex.get(vertex)));
                    conflicts = conflicts - conflictsPerVertex.get(vertex) + newConflicts;
                    coloring[vertex] = newVertexColor;
                    conflictsPerVertex.put(vertex, newConflicts);
                }

            }

            System.out.println("New u: " + attemptedUpperBound);
            graph.addUpperBound(attemptedUpperBound);
            randomColoring();

        }

    }

    private int nextPositiveInt(int max) {
        return rng.nextInt(max - 1) + 1;
    }

}
