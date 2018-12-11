import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VertexClickListener extends MouseAdapter {

    private GameState gameState;
    private GraphPanel graphPanel;
    private int vertexSize;

    public VertexClickListener(GameState gameState, GraphPanel graphPanel, int vertexSize) {
        this.gameState = gameState;
        this.graphPanel = graphPanel;
        this.vertexSize = vertexSize;
    }

    public void mouseClicked(MouseEvent e) {
        for(Vertex vertex: gameState.getGraph().getVertices()) {
            if(vertex == null) { continue; }
            if(vertex.hasOverlap(e.getX(), e.getY(), vertexSize)) {
                vertex.setColor(gameState.getSelectedColor());
                graphPanel.repaint();

                Graph graph = gameState.getGraph();
                if(gameState.getMode() == GameMode.BITTER_END && graph.isOptimallyColored()) {
                    GameTimer.timer.stop();
                    JOptionPane.showMessageDialog(
                            Run.window,
                            "You are the winner! You took: " + gameState.getSinceStartFormatted()
                    );
                    Run.displaySpecificationScreen();
                }

                if(gameState.getMode() == GameMode.UPPER_BOUND && graph.isGraphColoredCorrectly()) {
                    GameTimer.timer.stop();
                    JOptionPane.showMessageDialog(
                            Run.window,
                            "You are the winner!\n" +
                                    "You took: " + gameState.getSinceStartFormatted() + "\n" +
                                    "You have used " + graph.getColorCount() + " colors!\n" +
                                    "While the graph can be colored with " + graph.getChromaticNumber() + "!"
                    );
                    Run.displaySpecificationScreen();
                }

            }
        }
    }
}
