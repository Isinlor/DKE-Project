import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphPanel extends JPanel {

    protected Graph graph;

    protected int panelWidth = 600;
    protected int panelHeight = 600;

    protected int vertexSize = 25;

    protected Color defaultColor = Color.black;

    public GraphPanel(GameState gameState) {

        this.graph = gameState.getGraph();
        initializeCoordinates();

        setPreferredSize(new Dimension(
            panelWidth, panelHeight
        ));

        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseListener(new VertexClickListener(gameState, this, vertexSize));

    }

    private void initializeCoordinates() {

        Vertex[] vertices = new Vertex[graph.getNumberOfVertices() + 1];

        int coordinatesToSet = graph.getNumberOfVertices();
        while(coordinatesToSet > 0) {

            int x = (int)(Math.random()*(panelWidth - vertexSize*2));
            int y = (int)(Math.random()*(panelHeight - vertexSize*2));

            // creates spacing from the frame of graph panel
            if(x < vertexSize || y < vertexSize) {
                continue;
            }

            int index = (graph.getNumberOfVertices() - coordinatesToSet) + 1;

            boolean hasOverlap = false;
            for(int i = 1; i < index; i++) {
                if(vertices[i].hasOverlap(x, y, vertexSize*3)) {
                    hasOverlap = true;
                    break;
                }
            }

            if(hasOverlap) {
                continue;
            }

            vertices[index] = new Vertex(x, y);
            coordinatesToSet--;

        }

        graph.setVertices(vertices);

        optimizeStrategyA(100000);
        optimizeStrategyB(100000);

    }

    public void paintComponent(Graphics g) {

        // See: https://stackoverflow.com/a/13281121/893222
        // without it panel get visual artifacts after repaint
        super.paintComponent(g);

        turnAntialiasingOn(g);
        drawEdges(g);
        drawVertices(g);

    }

    protected void turnAntialiasingOn(Graphics g) {
        ((Graphics2D)g).setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
    }

    protected void drawVertices(Graphics g) {
        for(Vertex vertex: graph.getVertices()) {
            //noinspection ConstantConditions
            if(!(vertex instanceof Vertex)) { continue; }
            if(vertex.hasColor()) {
                // colored vertex
                g.setColor(vertex.getColor());
                g.fillOval(vertex.x, vertex.y, vertexSize, vertexSize);
            } else {
                // white space vertex
                g.setColor(Color.white);
                g.fillOval(vertex.x, vertex.y, vertexSize, vertexSize);
                g.setColor(defaultColor);
                g.drawOval(vertex.x, vertex.y, vertexSize, vertexSize);
            }
        }
    }

    protected void drawEdges(Graphics g) {

        g.setColor(defaultColor);

        Vertex[] vertices = graph.getVertices();
        for(Edge edge: graph.getEdges()) {

            Vertex from = vertices[edge.from];
            Vertex to = vertices[edge.to];

            g.drawLine(from.x+vertexSize/2, from.y+vertexSize/2, to.x+vertexSize/2, to.y+vertexSize/2);

        }

    }

    /**
     * Optimizes vertices location based on swaping each vertex position.
     *
     * @param limitOfIterations Allows to limit number of swaps for big graphs.
     */
    protected void optimizeStrategyA(int limitOfIterations) {

        int counter = 0;
        int minDistance = Integer.MAX_VALUE;
        for(int swapA = 1; swapA <= graph.getNumberOfVertices(); swapA++) {

            for(int swapB = 1; swapB <= graph.getNumberOfVertices(); swapB++) {

                if(counter > limitOfIterations) {
                    return;
                }

                swapCoordinates(swapA, swapB);
                int distance = computeTotalDistance();
                if(minDistance > distance) {
                    minDistance = distance;
                } else {
                    swapCoordinates(swapA, swapB);
                }

                counter++;

            }

        }

    }

    /**
     * Optimizes vertices location based on random swaps.
     *
     * @param iterations Number of random swaps to test.
     */
    protected void optimizeStrategyB(int iterations) {

        int minDistance = Integer.MAX_VALUE;
        for(int i = 0; i < iterations; i++) {

            int swapA = (int)(Math.random()*graph.getNumberOfVertices())+1;
            int swapB = (int)(Math.random()*graph.getNumberOfVertices())+1;

            swapCoordinates(swapA, swapB);
            int distance = computeTotalDistance();
            if(minDistance > distance) {
                minDistance = distance;
            } else {
                swapCoordinates(swapA, swapB);
            }

        }

    }

    protected void swapCoordinates(int swapA, int swapB) {

        Vertex[] vertices = graph.getVertices();

        Vertex a = vertices[swapA];
        Vertex b = vertices[swapB];

        vertices[swapA] = b;
        vertices[swapB] = a;

    }

    /**
     * Computes total length of all edges based on vertices coordinates.
     *
     * Notice! The length is not euclidean. It's manhattan distance.
     *
     * @return Total length of edges.
     */
    protected int computeTotalDistance() {
        int distance = 0;
        Vertex[] vertices = graph.getVertices();
        for(int j = 0; j < graph.getEdges().length; j++) {

            Edge edge = graph.getEdges()[j];

            int vertexFrom = edge.from;
            int vertexTo = edge.to;

            Vertex from = vertices[vertexFrom];
            Vertex to = vertices[vertexTo];

            distance += Math.abs(from.x-to.x) + Math.abs(from.y-to.y);

        }
        return distance;
    }

}
