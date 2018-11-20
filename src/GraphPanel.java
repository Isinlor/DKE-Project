import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphPanel extends JPanel {

    protected Graph graph;

    protected VertexCoordinates[] coordinates;

    protected int panelWidth = 800;
    protected int panelHeight = 800;

    protected int vertexSize = 25;

    public GraphPanel(Graph graph) {

        this.graph = graph;
        initializeCoordinates();

    }

    class VertexCoordinates {

        public int x;
        public int y;

        public VertexCoordinates(int x, int y) {
                this.x = x;
                this.y = y;
        }

        public boolean hasOverlap(int x, int y, int vertexSize) {

            boolean hasHorizontalOverlap = Math.abs(this.x-x) <= vertexSize;

            boolean hasVerticalOverlap =  Math.abs(this.y-y) <= vertexSize;

            return hasVerticalOverlap && hasHorizontalOverlap;

        }

    }

    private void initializeCoordinates() {

        coordinates = new VertexCoordinates[graph.getNumberOfVertices() + 1];

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
                if(coordinates[i].hasOverlap(x, y, vertexSize*3)) {
                    hasOverlap = true;
                    break;
                }
            }

            if(hasOverlap) {
                continue;
            }

            coordinates[index] = new VertexCoordinates(x, y);
            coordinatesToSet--;

        }

        optimizeStrategyA(100000);
        optimizeStrategyB(100000);

    }

    public void paintComponent(Graphics g) {

        drawFrame(g);
        drawVertices(g);
        drawEdges(g);

    }

    protected void drawFrame(Graphics g) {
        g.drawLine(0, 0, 0, panelHeight);
        g.drawLine(0, 0, panelWidth, 0);
        g.drawLine(0, panelWidth, panelWidth, panelHeight);
        g.drawLine(panelHeight, 0, panelWidth, panelHeight);
    }

    protected void drawVertices(Graphics g) {
        for(VertexCoordinates coordinate: coordinates) {
            if(!(coordinate instanceof VertexCoordinates)) { continue; }
            g.fillOval(coordinate.x, coordinate.y, vertexSize, vertexSize);
        }
    }

    protected void drawEdges(Graphics g) {

        for(int i = 0; i < graph.getEdges().length; i++) {

            Edge edge = graph.getEdges()[i];

            VertexCoordinates from = coordinates[edge.from];
            VertexCoordinates to = coordinates[edge.to];

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

        VertexCoordinates a = coordinates[swapA];
        VertexCoordinates b = coordinates[swapB];

        coordinates[swapA] = b;
        coordinates[swapB] = a;

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
        for(int j = 0; j < graph.getEdges().length; j++) {

            Edge edge = graph.getEdges()[j];

            int vertexFrom = edge.from;
            int vertexTo = edge.to;

            VertexCoordinates from = coordinates[vertexFrom];
            VertexCoordinates to = coordinates[vertexTo];

            distance += Math.abs(from.x-to.x) + Math.abs(from.y-to.y);

        }
        return distance;
    }

}
