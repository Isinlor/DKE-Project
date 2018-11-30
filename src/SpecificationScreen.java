import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * This screen allows to specify details of a game.
 */
public class SpecificationScreen extends JPanel {

    enum GraphMethod {
        FILE, RANDOM
    }

    GameMode selectedGameMode;

    GraphMethod graphMethod;

    int selectedNumberOfVertices = 0;
    int selectedNumberOfEdges = 0;

    String pathToFileWithGraph;

    int verticalComponentsCounter = 0;

    public SpecificationScreen() {

        setLayout(new GridBagLayout());

        addGameTitlePanel();
        addGameModeSelectionPanel();
        addGraphMethodPanel();
        addPlayButton();

    }

    private void addGameTitlePanel() {
        JLabel welcomeLabel = new JLabel("Graph Coloring");
        welcomeLabel.setFont(new Font(welcomeLabel.getName(), Font.PLAIN, 30));
        addScreenComponent(welcomeLabel);
    }

    /**
     * Adds a panel that allows to select game modes.
     */
    private void addGameModeSelectionPanel() {

        Object[][] gameModes = {
                {GameMode.BITTER_END, "Bitter end ..."},
                {GameMode.UPPER_BOUND, "Upper bound ..."},
                {GameMode.RANDOM_ORDER, "Random order ..."}
        };

        RadioButtons gameModeSelectionPanel = new RadioButtons(
                "Choose the game mode: ",
                gameModes,
                selection -> { selectedGameMode = (GameMode)selection; },
                true
        );

        addScreenComponent(gameModeSelectionPanel);

    }

    /**
     * Adds a panel that allows to switch between different methods of a graph creation.
     */
    private void addGraphMethodPanel() {

        JPanel graphMethodPanel = new JPanel(new GridBagLayout());

        JPanel randomGraphSpecificationPanel = getRandomGraphSpecificationPanel();
        JPanel fileSelectionPanel = getFileSelectionPanel();

        JPanel graphMethodDisplay = new JPanel();
        graphMethodDisplay.add(randomGraphSpecificationPanel);
        graphMethodDisplay.add(fileSelectionPanel);

        Object[][] options = {{GraphMethod.FILE, "a file"}, {GraphMethod.RANDOM, " generate random"}};
        RadioButtons graphMethodSelection = new RadioButtons(
                "Get a graph from: ",
                options,
                selection -> {

                    graphMethod = (GraphMethod)selection;

                    if(graphMethod == GraphMethod.FILE) {
                        randomGraphSpecificationPanel.setVisible(false);
                        fileSelectionPanel.setVisible(true);
                    } else {
                        randomGraphSpecificationPanel.setVisible(true);
                        fileSelectionPanel.setVisible(false);
                    }

                },
                false
        );
        graphMethodPanel.add(graphMethodSelection, makeLayoutSettings(0, 0));
        graphMethodPanel.add(graphMethodDisplay, makeLayoutSettings(0, 1));

        addScreenComponent(graphMethodPanel);

    }

    /**
     * @return a panel that allows to specify number of vertices and edges.
     */
    private JPanel getRandomGraphSpecificationPanel() {

        JPanel randomGraphSpecification = new JPanel(new GridBagLayout());

        JPanel verticesPanel = new JPanel();
        verticesPanel.add(new JLabel("Number of vertices: "));
        JSpinner verticesSelector = new JSpinner();
        verticesSelector.getComponent(2).setPreferredSize(new Dimension(22, 18));
        verticesPanel.add(verticesSelector);
        randomGraphSpecification.add(verticesPanel, makeLayoutSettings(0, 0));

        JPanel edgesPanel = new JPanel();
        edgesPanel.add(new JLabel("Number of edges: "));
        JSpinner edgesSelector = new JSpinner();
        edgesSelector.getComponent(2).setPreferredSize(new Dimension(22, 18));
        edgesPanel.add(edgesSelector);
        randomGraphSpecification.add(edgesPanel, makeLayoutSettings(0, 1));

        verticesSelector.addChangeListener(e -> {selectedNumberOfVertices = (int)verticesSelector.getValue();});
        edgesSelector.addChangeListener(e -> {selectedNumberOfEdges = (int)edgesSelector.getValue();});

        return randomGraphSpecification;

    }

    /**
     * @return A panel that allows to select a file to load a graph.
     */
    private JPanel getFileSelectionPanel() {

        JPanel fileSelectionPanel = new JPanel();
        fileSelectionPanel.setLayout(new BoxLayout(fileSelectionPanel, BoxLayout.PAGE_AXIS));

        JButton selectGraphButton = new JButton("Select graph from a file");
        selectGraphButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JFileChooser fileChooser = new JFileChooser();

        JLabel selectedFileLabel = new JLabel();
        selectedFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        selectGraphButton.addActionListener(e -> {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                pathToFileWithGraph = selectedFile.getAbsolutePath();
                selectedFileLabel.setText(selectedFile.getName());
            }
        });

        fileSelectionPanel.add(selectGraphButton);
        fileSelectionPanel.add(selectedFileLabel);

        return fileSelectionPanel;

    }

    /**
     * Adds a button that based on specification creates a graph screen.
     */
    private void addPlayButton() {

        JButton playButton = new JButton("Play");
        playButton.setSize(100,50);
        playButton.addActionListener(e -> {

            Graph graph;
            if(graphMethod == GraphMethod.FILE) {
                graph = FileLoader.load(pathToFileWithGraph);
            } else {
                graph = Random.generate(selectedNumberOfVertices, selectedNumberOfEdges);
            }

            Run.displayGraphScreen(new GameState(graph, selectedGameMode));

        });

        addScreenComponent(playButton);

    }

    /**
     * Add a component to the screen.
     * Each component is added in a column and is visually separated.
     *
     * @param component
     */
    private void addScreenComponent(JComponent component) {
        GridBagConstraints layoutSettings = makeLayoutSettings(0, verticalComponentsCounter);
        layoutSettings.insets = new Insets(20, 50, 20, 50);
        add(component, layoutSettings);
        verticalComponentsCounter++;
    }

    private GridBagConstraints makeLayoutSettings(int x, int y) {
        GridBagConstraints layoutSettings = new GridBagConstraints();
        layoutSettings.gridx = x;
        layoutSettings.gridy = y;
        return layoutSettings;
    }

    interface RadioButtonSwitchListener {
        void radioButtonSwitched(Object newOption);
    }

    /**
     * A helper class abstracting away some details of radio buttons.
     */
    class RadioButtons extends JPanel {

        private ActionListener onSwitchListener;

        /**
         * Creates a new radio button panel.
         *
         * @param label String indicating a choice to be made.
         * @param options Available options.
         * @param onSwitchListener Allows to listen to radio button state changes.
         * @param vertical Whether radio buttons should be horizontal or vertical.
         */
        public RadioButtons(
            String label,
            Object[][] options,
            RadioButtonSwitchListener onSwitchListener,
            boolean vertical
        ) {

            GridBagConstraints layoutSettings;

            setLayout(new GridBagLayout());

            JLabel chooseGameModeLabel = new JLabel(label);
            layoutSettings = new GridBagConstraints();
            layoutSettings.gridx = 0;
            layoutSettings.gridy = 0;
            add(chooseGameModeLabel, layoutSettings);

            int index = 0;
            ButtonGroup group = new ButtonGroup();
            JPanel radioButtonsPanel = new JPanel(new GridBagLayout());
            for(Object[] option: options) {
                JRadioButton modeRadioButton = new JRadioButton();
                modeRadioButton.addActionListener(e -> {
                    onSwitchListener.radioButtonSwitched(option[0]);
                });
                modeRadioButton.setText((String)option[1]);
                layoutSettings = new GridBagConstraints();
                layoutSettings.gridx = vertical ? 0 : index;
                layoutSettings.gridy = vertical ? index : 0;
                group.add(modeRadioButton);
                radioButtonsPanel.add(modeRadioButton, layoutSettings);
                index++;
            }

            layoutSettings = new GridBagConstraints();
            layoutSettings.gridx = 0;
            layoutSettings.gridy = 1;
            add(radioButtonsPanel, layoutSettings);

            // select default game mode
            onSwitchListener.radioButtonSwitched(options[0][0]);
            group.getElements().nextElement().setSelected(true);

        }

    }

}
