import java.awt.*;

/**
 * These are simple acceptance tests of graph related functionaries.
 *
 * Author: Tomek
 *
 * Important! Must be run with -ea or -enableassertions options!
 */
class GraphTests extends SimpleUnitTest {

    /**
     * Must be run with -ea or -enableassertions options!
     */
    public static void main(String[] args) {

        System.out.println("\nExact algorithm:");

        it("computes chromatic number in simple case", () -> {

            Edge[] edges = {new Edge(1, 2)};
            assert ExactAlgorithm.getChromaticNumber(2, edges) == 2;

        });

        it("computes chromatic number in simple line", () -> {

            Edge[] edges = {new Edge(1, 2), new Edge(2, 3)};
            assert ExactAlgorithm.getChromaticNumber(3, edges) == 2;

        });

        it("computes chromatic number in triangle", () -> {

            Edge[] edges = {new Edge(1, 2), new Edge(2, 3), new Edge(3, 1)};
            assert ExactAlgorithm.getChromaticNumber(3, edges) == 3;

        });

        System.out.println("\nRandom:");

        it("generates simple random graph", () -> {

            Graph graph = Random.generate(2, 1);
            assert graph.getNumberOfEdges() == 1;
            assert graph.getNumberOfVertices() == 2;

        });

        it("generates fully connected graph", () -> {

            int vertices = 10;
            int edges = (vertices * (vertices - 1)) / 2;

            Graph graph = Random.generate(vertices, edges);
            assert graph.getNumberOfEdges() == edges;
            assert graph.getNumberOfVertices() == vertices;

        });

        System.out.println("\nGraph:");

        it("allows to retrieve chromatic number", () -> {

            Graph graph = Random.generate(2, 1);
            assert graph.getChromaticNumber() == 2;

        });

        it("check whether graph is fully colored", () -> {

            Vertex[] vertices = {null, new Vertex(0, 0), new Vertex(5, 5)};
            Graph graph = Random.generate(2, 1);
            graph.setVertices(vertices);

            assert !graph.isFullyColored();

            vertices[1].setColor(Color.BLACK);
            vertices[2].setColor(Color.BLACK);

            assert graph.isFullyColored();

        });

        it("check whether graph is correctly colored", () -> {

            Vertex[] vertices = {null, new Vertex(0, 0), new Vertex(5, 5)};
            Graph graph = Random.generate(2, 1);
            graph.setVertices(vertices);

            assert !graph.isGraphColoredCorrectly();

            vertices[1].setColor(Color.BLACK);
            vertices[2].setColor(Color.BLACK);

            assert !graph.isGraphColoredCorrectly();

            vertices[2].setColor(Color.WHITE);

            assert graph.isGraphColoredCorrectly();

        });

        it("check whether graph is optimally colored", () -> {

            Vertex[] vertices = {null, new Vertex(0, 0), new Vertex(5, 5), new Vertex(10, 10)};
            Graph graph = Random.generate(3, 2);
            graph.setVertices(vertices);

            assert !graph.isOptimallyColored();

            vertices[1].setColor(Color.BLACK);
            vertices[2].setColor(Color.BLACK);
            vertices[3].setColor(Color.BLACK);

            assert !graph.isOptimallyColored();

            vertices[1].setColor(Color.RED);
            vertices[2].setColor(Color.BLACK);
            vertices[3].setColor(Color.WHITE);

            assert !graph.isOptimallyColored();

        });

    }
}