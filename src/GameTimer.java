import javax.swing.*;
import java.awt.event.*;

public class GameTimer extends JLabel {

    static Timer timer;

    public GameTimer(GameState state) {
        super();

        GameTimer label = this;

        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch(state.getMode()) {
                    case BITTER_END:
                        label.setText(state.getSinceStartFormatted());
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
                    case RANDOM_ORDER:
                        label.setText(state.getSinceStartFormatted());
                }
                label.setText(state.getTillEndFormatted());
            }
        });
        timer.setInitialDelay(0);
        timer.start();

    }

}