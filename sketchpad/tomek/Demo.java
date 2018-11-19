import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Demo {

    public static void main(String[] args) {

        JFrame mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setSize(new Dimension(
            1000 + mainFrame.getInsets().right + mainFrame.getInsets().left,
            1000 + mainFrame.getInsets().top + mainFrame.getInsets().bottom
        ));

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.add(new JPanel() {

            class Edge {
                public int from;
                public int to;
                public Edge(int from, int to) {
                    this.from = from;
                    this.to = to;
                }
            }

            class VertexCoordinates {

                public int x;
                public int y;

                public VertexCoordinates(int x, int y) {
                        this.x = x;
                        this.y = y;
                }

                public boolean hasOverlap(int x, int y, int size) {

                    boolean hasHorizontalOverlap = Math.abs(this.x-x) <= size;

                    boolean hasVerticalOverlap =  Math.abs(this.y-y) <= size;

                    return hasVerticalOverlap && hasHorizontalOverlap;

                }

            }

            class Graph {

                public int vertices;
                public Edge[] edges;

                public Graph(int numberOfVertices, int numberOfEdges) {

                    vertices = numberOfVertices;

                    int[][] adjacencyMatrix = new int[vertices + 1][vertices + 1];

                    int numberOfEdgesToCreate = numberOfEdges;
                    while(numberOfEdgesToCreate > 0) {

                        int x = (int)(Math.random() * vertices) + 1;
                        int y = (int)(Math.random() * vertices) + 1;

                        if(x > y && adjacencyMatrix[x][y] == 0) {
                            adjacencyMatrix[x][y] = 1;
                            adjacencyMatrix[y][x] = 1;
                            numberOfEdgesToCreate--;
                        }

                    }

                    edges = new Edge[numberOfEdges];

                    int counter = 0;
                    for(int i = 1; i <= numberOfVertices; i++) {
                        for(int j = i+1; j <= numberOfVertices; j++) {

                            if(adjacencyMatrix[i][j] == 1 || adjacencyMatrix[j][i] == 1) {
                                edges[counter] = new Edge(i, j);
                                counter++;
                            }

                        }
                    }

                }

            }

            public void paint(Graphics g) {

                int maxVertices = 50;
                int vertices = (int)(Math.random()*maxVertices)+1;

                int maxEdges = (int)((vertices*(vertices-1))/2);
                int edges = (int)(Math.random()*maxEdges)+1;

                Graph graph = new Graph(
                    20, 30
                );

                VertexCoordinates[] coordinates = new VertexCoordinates[graph.vertices + 1];

                int windowsWidth = 1000;
                int windowsHeight = 1000;

                g.drawLine(0, 0, 0, windowsHeight);
                g.drawLine(0, 0, windowsWidth, 0);
                g.drawLine(0, windowsWidth, windowsWidth, windowsHeight);
                g.drawLine(windowsHeight, 0, windowsWidth, windowsHeight);

                int size = 25;

                // int rows = (int)Math.floor(Math.sqrt(graph.vertices));
                // int columns = (int)Math.ceil((double)graph.vertices / rows);
                //
                // int horizontalSpacing = windowsWidth / (columns+1);
                // int verticalSpacing = windowsHeight / (rows+1);

                int verticesToDraw = graph.vertices;
                while(verticesToDraw > 0) {

                    int x = (int)(Math.random()*(windowsWidth - size*2));
                    int y = (int)(Math.random()*(windowsHeight - size*2));

                    if(x < size || y < size) {
                        continue;
                    }

                    int index = (graph.vertices - verticesToDraw) + 1;

                    boolean hasOverlap = false;
                    for(int i = 1; i < index; i++) {
                        if(coordinates[i].hasOverlap(x, y, size*3)) {
                            hasOverlap = true;
                            break;
                        }
                    }

                    if(hasOverlap) {
                        continue;
                    }

                    g.fillOval(x, y, size, size);
                    coordinates[index] = new VertexCoordinates(x, y);

                    verticesToDraw--;

                }

                int minDistance = Integer.MAX_VALUE;

                boolean optimize = Math.round(Math.random()) == 1;
                System.out.println("Optimize: " + optimize);

                int counter = 0;
                for(int swapA = 1; swapA <= graph.vertices && optimize; swapA++) {

                    for(int swapB = 1; swapB <= graph.vertices; swapB++) {

                        if(counter > 100000) {
                            continue;
                        }

                        VertexCoordinates a = coordinates[swapA];
                        VertexCoordinates b = coordinates[swapB];

                        coordinates[swapA] = b;
                        coordinates[swapB] = a;

                        int distance = 0;

                        for(int j = 0; j < graph.edges.length; j++) {

                            Edge edge = graph.edges[j];

                            int vertexFrom = edge.from;
                            int vertexTo = edge.to;

                            VertexCoordinates from = coordinates[vertexFrom];
                            VertexCoordinates to = coordinates[vertexTo];

                            distance += Math.abs(from.x-to.x) + Math.abs(from.y-to.y);

                        }

                        if(minDistance > distance) {

                            minDistance = distance;

                            //System.out.println("New min distance: " + minDistance);

                        } else {

                            a = coordinates[swapA];
                            b = coordinates[swapB];

                            coordinates[swapA] = b;
                            coordinates[swapB] = a;

                        }

                        counter++;

                    }

                }

                System.out.println("Final all to all min distance: " + minDistance);

                for(int i = 0; i < 1000000 && optimize; i++) {

                    int distance = 0;

                    int swapA = (int)(Math.random()*graph.vertices)+1;
                    int swapB = (int)(Math.random()*graph.vertices)+1;

                    VertexCoordinates a = coordinates[swapA];
                    VertexCoordinates b = coordinates[swapB];

                    coordinates[swapA] = b;
                    coordinates[swapB] = a;

                    for(int j = 0; j < graph.edges.length; j++) {

                        Edge edge = graph.edges[j];

                        int vertexFrom = edge.from;
                        int vertexTo = edge.to;

                        VertexCoordinates from = coordinates[vertexFrom];
                        VertexCoordinates to = coordinates[vertexTo];

                        distance += Math.abs(from.x-to.x) + Math.abs(from.y-to.y);

                    }

                    if(minDistance > distance) {

                        minDistance = distance;

                        //System.out.println("New min distance: " + minDistance);

                    } else {

                        a = coordinates[swapA];
                        b = coordinates[swapB];

                        coordinates[swapA] = b;
                        coordinates[swapB] = a;

                    }

                }

                System.out.println("Final min distance: " + minDistance);


                for(int i = 0; i < graph.edges.length; i++) {

                    Edge edge = graph.edges[i];

                    VertexCoordinates from = coordinates[edge.from];
                    VertexCoordinates to = coordinates[edge.to];

                    g.drawLine(from.x+size/2, from.y+size/2, to.x+size/2, to.y+size/2);

                }


            }
        });

    }

}
