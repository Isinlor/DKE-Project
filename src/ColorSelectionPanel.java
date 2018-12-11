import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ColorSelectionPanel extends JPanel {

    private GameState gameState;
    private int buttonSize = 35;

    private ArrayList<ColorButton> buttons = new ArrayList<ColorButton>();

    class ColorButton extends JButton {

        private Color color;

        public ColorButton(Color color, int size) {

            this.color = color;

            setBackground(color);
            setPreferredSize(new Dimension(size, size));
            setBorder(BorderFactory.createRaisedBevelBorder());

            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    press();
                }
            });

        }

        public void press() {
            // reset buttons
            for(ColorButton button: buttons) {
                button.setBorder(BorderFactory.createRaisedBevelBorder());
            }
            // indicate selected color
            setBorder(BorderFactory.createLoweredBevelBorder());
            gameState.selectColor(color);
        }

    }

    public ColorSelectionPanel(GameState gameState) {

        this.gameState = gameState;

        int numberOfVertices = gameState.getGraph().getNumberOfVertices();
        int columns = Math.min((numberOfVertices / 10) + 1, 3);
        int rows = numberOfVertices / columns;

        // How to Use GridBagLayout:
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        setLayout(new GridBagLayout());

        // GridBagLayout elements settings (or GridBagConstraints):
        // http://voyager.deanza.edu/~hso/cis35a/lecture/java16/layout/set.html
        // http://voyager.deanza.edu/~hso/cis35a/lecture/java16/layout/work.html
        GridBagConstraints layoutSettings;
        Insets insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < numberOfVertices; i++) {

            int column = i % columns;
            int row = i / columns;

            layoutSettings = new GridBagConstraints();
            layoutSettings.gridx = column;
            layoutSettings.gridy = row;
            layoutSettings.insets = insets;

            // 0.0 and 1.0 is just red, using only 0.9 avoids full wrap
            float hue = 0.90f * ((float) row / (float) rows);
            // possible modification, squeeze greens and blues
            // see: https://www.desmos.com/calculator/xzdt6ungql
            // hue = (float)(Math.sin(Math.PI*hue-Math.PI/2.0) + 1.0f) / 2.0f;

            // modify saturation to avoid close hues leading to similar colors
            float saturation = 1.0f - 0.5f * (row % 2);

            // increase color space by incorporating different brightness
            float brightness = 1.0f - 0.6f * (float) (column) / (float) (columns);

            ColorButton button = new ColorButton(
                new Color(Color.HSBtoRGB(hue, saturation, brightness)),
                buttonSize - Math.min(20, numberOfVertices / 5) // allows to handle bigger graphs
            );

            add(button, layoutSettings);
            buttons.add(button);

        }

        // select first color
        buttons.get(0).press();

    }
}
