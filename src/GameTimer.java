import javax.swing.*;
import java.awt.event.*;

/**
 * Allows to time the game.
 *
 * Based on game mode the counter will count up or down.
 * If time runs out the game ends.
 *
 * Author: Tomek
 * Contribution on counting down: Wen
 */
public class GameTimer extends JLabel {

    static Timer timer;

    /**
     * @param state
     */
    public GameTimer(GameState state) {
        super();

        GameTimer label = this;

        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch(state.getMode()) {
                    case BITTER_END:
                        label.setText(state.getSinceStartFormatted());
                        break;
                    case UPPER_BOUND:
                        label.setText(state.getTillEndFormatted());
                        if(state.isTimeUp()) {
                            timer.stop();
                            JOptionPane.showMessageDialog(
                                    Run.window,
                                    "You have run out of time!"
                            );
                            Run.displaySpecificationScreen();
                        }
                        break;
                    case RANDOM_ORDER:
                        label.setText(state.getSinceStartFormatted());
                        break;
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();

    }

    /**
     * Allows to reset timer if user exits game before finishing.
     */
    static public void reset() {
        if(timer != null) {
            timer.stop();
        }
    }

}