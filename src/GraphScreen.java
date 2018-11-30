import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphScreen extends JPanel {

    public GraphScreen(GameState gameState) {

        Graph graph = gameState.getGraph();

        // A Visual Guide to Layout Managers:
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html

        // How to Use GridBagLayout:
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        setLayout(new GridBagLayout());

        JButton restartButton = new JButton("Restart game");
        restartButton.addActionListener(e -> {
            Run.displaySpecificationScreen();
        });

        GameTimer timer = new GameTimer(gameState);

        ColorSelectionPanel colorsSelectionPanel = new ColorSelectionPanel(gameState);

        JLabel colorsUsed = new JLabel("Colors used: x");
        JButton hintButton = new JButton("Hint");

        GraphPanel graphPanel = new GraphPanel(gameState);

        // GridBagLayout elements settings (or GridBagConstraints):
        // http://voyager.deanza.edu/~hso/cis35a/lecture/java16/layout/set.html
        // http://voyager.deanza.edu/~hso/cis35a/lecture/java16/layout/work.html
        GridBagConstraints layoutSettings;
        Insets insets = new Insets(5, 5, 5, 5);

        layoutSettings = new GridBagConstraints();
        layoutSettings.gridx = 0;
        layoutSettings.gridy = 0;
        layoutSettings.insets = insets;
        add(restartButton, layoutSettings);

        layoutSettings = new GridBagConstraints();
        layoutSettings.gridx = 2;
        layoutSettings.gridy = 0;
        layoutSettings.anchor = GridBagConstraints.EAST;
        layoutSettings.insets = insets;
        add(timer, layoutSettings);

        layoutSettings = new GridBagConstraints();
        layoutSettings.gridx = 0;
        layoutSettings.gridy = 1;
        layoutSettings.insets = insets;
        add(colorsSelectionPanel, layoutSettings);

        layoutSettings = new GridBagConstraints();
        layoutSettings.gridx = 0;
        layoutSettings.gridy = 2;
        layoutSettings.insets = insets;
        add(colorsUsed, layoutSettings);

        layoutSettings = new GridBagConstraints();
        layoutSettings.gridx = 2;
        layoutSettings.gridy = 2;
        layoutSettings.anchor = GridBagConstraints.EAST;
        layoutSettings.insets = insets;
        add(hintButton, layoutSettings);

        layoutSettings = new GridBagConstraints();
        layoutSettings.gridx = 1;
        layoutSettings.gridy = 1;
        layoutSettings.gridwidth = 2;
        layoutSettings.gridheight = 1;
        layoutSettings.insets = insets;
        add(graphPanel, layoutSettings);

    }

}
