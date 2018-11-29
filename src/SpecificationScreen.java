import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SpecificationScreen extends JPanel {

    GameMode selectedGameMode;
    int selectedNumberOfVertices = 0;
    int selectedNumberOfEdges = 0;

    int verticalComponentsCounter = 0;

    public SpecificationScreen() {

        setLayout(new GridBagLayout());

        addGameTitlePanel();
        addGameModeSelectionPanel();
        addRandomGraphSpecificationPanel();

        JButton playButton = new JButton("Play");
        playButton.setSize(100,50);
        playButton.addActionListener(e -> {
            setVisible(false);
            GameState gameState = new GameState(
                    Random.generate(selectedNumberOfVertices, selectedNumberOfEdges),
                    selectedGameMode
            );
            Run.displayGraphScreen(gameState);
        });
        addScreenComponent(playButton);


    }

    private void addGameTitlePanel() {
        JLabel welcomeLabel = new JLabel("Graph Coloring");
        welcomeLabel.setFont(new Font(welcomeLabel.getName(), Font.PLAIN, 30));
        addScreenComponent(welcomeLabel);
    }

    private void addGameModeSelectionPanel() {

        GridBagConstraints layoutSettings;

        JPanel gameModeSelectionPanel = new JPanel(new GridBagLayout());

        JLabel chooseGameModeLabel = new JLabel("Choose the game mode:");
        chooseGameModeLabel.setSize(400,40);
        layoutSettings = new GridBagConstraints();
        layoutSettings.gridx = 0;
        layoutSettings.gridy = 0;
        gameModeSelectionPanel.add(chooseGameModeLabel, layoutSettings);

        Object[][] gameModes = {
                {GameMode.BITTER_END, "Bitter end ..."},
                {GameMode.UPPER_BOUND, "Upper bound ..."},
                {GameMode.RANDOM_ORDER, "Random order ..."}
        };

        int index = 0;
        ButtonGroup group = new ButtonGroup();
        for(Object[] gameMode: gameModes) {
            JRadioButton modeRadioButton = new JRadioButton();
            modeRadioButton.setAction(new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    selectedGameMode = (GameMode)gameMode[0];
                }
            });
            modeRadioButton.setText((String)gameMode[1]);
            modeRadioButton.setSize(140,20);
            layoutSettings = new GridBagConstraints();
            layoutSettings.gridx = 0;
            layoutSettings.gridy = 1 + index;
            group.add(modeRadioButton);
            gameModeSelectionPanel.add(modeRadioButton, layoutSettings);
            index++;
        }

        // select default game mode
        selectedGameMode = (GameMode)gameModes[0][0];
        group.getElements().nextElement().setSelected(true);

        addScreenComponent(gameModeSelectionPanel);

    }

    private void addRandomGraphSpecificationPanel() {

        GridBagConstraints layoutSettings;
        JPanel randomGraphSpecification = new JPanel(new GridBagLayout());

        layoutSettings = new GridBagConstraints();
        layoutSettings.gridx = 0;
        layoutSettings.gridy = 0;
        JPanel verticesPanel = new JPanel();
        verticesPanel.add(new JLabel("Number of vertices: "));
        JSpinner verticesSelector = new JSpinner();
        verticesSelector.getComponent(2).setPreferredSize(new Dimension(22, 18));
        verticesPanel.add(verticesSelector);
        randomGraphSpecification.add(verticesPanel, layoutSettings);

        layoutSettings = new GridBagConstraints();
        layoutSettings.gridx = 0;
        layoutSettings.gridy = 1;
        JPanel edgesPanel = new JPanel();
        edgesPanel.add(new JLabel("Number of edges: "));
        JSpinner edgesSelector = new JSpinner();
        edgesSelector.getComponent(2).setPreferredSize(new Dimension(22, 18));
        edgesPanel.add(edgesSelector);
        randomGraphSpecification.add(edgesPanel, layoutSettings);

        verticesSelector.addChangeListener(e -> {selectedNumberOfVertices = (int)verticesSelector.getValue();});
        edgesSelector.addChangeListener(e -> {selectedNumberOfEdges = (int)edgesSelector.getValue();});

        addScreenComponent(randomGraphSpecification);

    }

    private void addScreenComponent(JComponent component) {
        GridBagConstraints layoutSettings = new GridBagConstraints();
        layoutSettings.gridx = 0;
        layoutSettings.gridy = verticalComponentsCounter;
        layoutSettings.insets = new Insets(20, 50, 20, 50);
        add(component, layoutSettings);
        verticalComponentsCounter++;
    }

}
