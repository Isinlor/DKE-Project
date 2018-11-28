import javax.swing.*;
import java.awt.*;

enum GameMode {
    BITTER_END, UPPER_BOUND, RANDOM_ORDER
};

public class GameState {

    private Graph graph;
    private GameMode mode;
    private Color selectedColor = Color.BLACK;
    private long startedAt;

    public GameState(Graph graph, GameMode mode) {
        this.graph = graph;
        this.mode = mode;
        this.startedAt = System.currentTimeMillis();
    }

    public Graph getGraph() {
        return graph;
    }

    public GameMode getMode() {
        return mode;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void selectColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public long getSinceStart() {
        return System.currentTimeMillis() - getStartedAt();
    }

}
