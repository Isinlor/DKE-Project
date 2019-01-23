import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeoutException;

class DSatur {

    Graph graph;

    /**
     * number of colored vertices
     */
    int coloredVertices;

    /**
     * list of all vertices in a graph
     */
    ArrayList<Node> nodes = new ArrayList<>();

    private Map<Integer, Set<Node>> adjacencyMap = new HashMap<>();

    ArrayList<Integer> colors = new ArrayList<Integer>(); /** array of colors */

    /**
     * Constructor
     */
    DSatur(Graph ofGraph) {

        graph = ofGraph;

        coloredVertices = 0;

        for (int i = 0; i <= graph.getNumberOfVertices(); i++)
            nodes.add(new Node(i));

        colors.add(1);

        createAdjacencyMap();

    }

    private void createAdjacencyMap() {
        for (Node vertexA: nodes) {
            Set<Node> neighbours = new HashSet<Node>();
            for (Node vertexB: nodes) {
                if(vertexA.getLabel() == vertexB.getLabel()) continue;
                if(graph.isAdjacent(vertexA.getLabel(), vertexB.getLabel())) neighbours.add(vertexB);
            }
            adjacencyMap.put(vertexA.getLabel(), neighbours);
        }
    }

    /**
     * Returns neighbours of node x.
     */
    public Set<Node> getNeighbours(Node x) {
        return adjacencyMap.get(x.getLabel());
    }

    /**
     * Checks if we can color vertex x with a specific color.
     */
    public boolean isAvailable(Node x, int color) {

        Set<Node> neighbours = getNeighbours(x);

        for (Node ngh : neighbours)
            if (ngh.getColor() == color)
                return false;

        return true;
    }

    /**
     * Sets degrees of all nodes and then sorts them by the degree
     */
    public void setDegree() {

        for (Node n : nodes)
            n.setDegree(getNeighbours(n).size());

        Collections.sort(nodes, Collections.reverseOrder());
    }

    /**
     * Sets saturation degree of all the nodes.
     */
    public void setSaturationDegree() {

        Set<Node> neighbours = new HashSet<>();
        HashSet<Integer> neighbourColors = new HashSet<Integer>();

        for (Node n : nodes) {

            neighbours = getNeighbours(n);
            neighbourColors.clear();

            for (Node ngh : neighbours)
                if (ngh.getColor() != -1)
                    neighbourColors.add(ngh.getColor());

            n.setSatDegree(neighbourColors.size());
        }

    }

    /**
     * Returns the node with the max saturation degree.
     */
    public Node maxSaturationDegree() {

        Node max = nodes.get(graph.getNumberOfVertices());

        for (Node n : nodes)
            if ((max.compareTo(n) == -1) && (n.getColor() == -1))
                max = n;

        return max;
    }

    /**
     * Graph coloring with DSATUR algorithm
     */
    public int DSATUR() throws TimeoutException {

        SortedSet<Integer> sortedVertices = new ConcurrentSkipListSet<>();

        nodes.get(0).setColor(1);
        sortedVertices.add(nodes.get(0).getLabel());
        coloredVertices++;
        setSaturationDegree();

        long startTime = System.currentTimeMillis();
        while (coloredVertices < graph.getNumberOfVertices()) {

            if(Thread.interrupted()) throw new RuntimeException();
            long runningTime = System.currentTimeMillis() - startTime;
            long estimatedTime = ((runningTime/(coloredVertices+2)) * graph.getNumberOfVertices());
            if(runningTime > 90000 || estimatedTime > 90000) {
                throw new TimeoutException();
            }

            Node max = maxSaturationDegree();
            boolean colored = false;

            for (Integer color : colors)
                if (isAvailable(max, color)) {
                    max.setColor(color);
                    colored = true;
                    break;
                }

            if (!colored) {

                int newColor = colors.size() + 1;

                colors.add(newColor);
                max.setColor(newColor);

            }

            sortedVertices.add(max.getLabel());
            coloredVertices++;
            setSaturationDegree();

        }

        graph.addSortedVertices(colors.size(), sortedVertices);

        return colors.size();
    }

    /**
     * Sets up the degrees of vertices, initial saturation degrees and executes DSATUR algorithm.
     */
    public void executeDSATUR() {

        try {
            setDegree();
            setSaturationDegree();
            graph.addUpperBound(DSATUR());
        } catch (TimeoutException e) {
            // no op
        }

    }

}

	
	


    


    
