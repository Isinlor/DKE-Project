import javax.swing.*;
import java.awt.event.*;

public class GameTimer extends JLabel {

    static Timer timer;

    public GameTimer(GameState state) {
        super();

        GameTimer label = this;

        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText(state.getSinceStartFormatted());
            }
        });
        timer.setInitialDelay(0);
        timer.start();

    }

}