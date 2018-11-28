import javax.swing.*;
import java.awt.event.*;

public class GameTimer extends JLabel {

    public GameTimer(GameState state) {
        super();

        GameTimer label = this;

        Timer timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long timeInSeconds = state.getSinceStart() / 1000;
                label.setText(String.format(
                        "%d:%02d:%02d", // hours:minutes:seconds
                        timeInSeconds / (60 * 60), // hours
                        (timeInSeconds / 60) % 60, // minutes
                        timeInSeconds % 60         // seconds
                ));
            }
        });
        timer.setInitialDelay(0);
        timer.start();

    }

}