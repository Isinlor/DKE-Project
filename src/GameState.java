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

    public long getTimeLimit() {

        int timeForVertices = getGraph().getNumberOfVertices() * 3;

        int timeForEdges = getGraph().getNumberOfEdges() / 3;

        int difficulty = Math.min(
                getGraph().getChromaticNumber(),
                getGraph().getNumberOfVertices() - getGraph().getChromaticNumber()
        ) + 1;

        return (timeForVertices + timeForEdges + (long)Math.pow(difficulty, 3)) * 1000;

    }

    public long getTillEnd() {
        return Math.max(0, getTimeLimit() - getSinceStart());
    }

    public boolean isTimeUp() {
        return getTillEnd() == 0;
    }

    public String getSinceStartFormatted() {
        long timeInSeconds = getSinceStart() / 1000;
        return getFormattedTime(timeInSeconds);
    }

    public String getTillEndFormatted() {
        return getFormattedTime(getTillEnd() / 1000);
    }

    private String getFormattedTime(long timeInSeconds) {
        return String.format(
                "%d:%02d:%02d", // hours:minutes:seconds
                timeInSeconds / (60 * 60), // hours
                (timeInSeconds / 60) % 60, // minutes
                timeInSeconds % 60         // seconds
        );
    }

}
