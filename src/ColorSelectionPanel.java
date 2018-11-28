import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorSelectionPanel extends JPanel {

    private GameState gameState;
    private int buttonSize = 35;
    private int rows = 2;

    class ColorButton extends JButton {

        private Color color;

        public ColorButton(Color color, int size) {

            this.color = color;

            setBackground(color);
            setPreferredSize(new Dimension(size, size));

            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    gameState.selectColor(color);
                }
            });

        }

    }

    public ColorSelectionPanel(GameState gameState) {

        this.gameState = gameState;

        // How to Use GridBagLayout:
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        setLayout(new GridBagLayout());

        // GridBagLayout elements settings (or GridBagConstraints):
        // http://voyager.deanza.edu/~hso/cis35a/lecture/java16/layout/set.html
        // http://voyager.deanza.edu/~hso/cis35a/lecture/java16/layout/work.html
        GridBagConstraints layoutSettings;
        Insets insets = new Insets(5, 5, 5, 5);

        int numberOfVertices = gameState.getGraph().getNumberOfVertices();
        for (int i = 0; i < numberOfVertices; i++) {

            layoutSettings = new GridBagConstraints();
            layoutSettings.gridx = i % rows;
            layoutSettings.gridy = i / rows;
            layoutSettings.insets = insets;

            add(
                new ColorButton(
                    new Color(Color.HSBtoRGB(
                            (float)i/(float)numberOfVertices,
                            1.0f,
                            1.0f
                    )),
                    buttonSize
                ),
                layoutSettings
            );

        }

    }
}
