import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Source: https://stackoverflow.com/questions/28337718/java-swing-timer-countdown
 *
 * Some small changes to better format time.
 *
 * Doing:
 *  SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");
 *  df.format(timeLeft);
 * Lead to issues with timeLeft = 0 being formated as 01:00:00
 * probably due to winter time or something like that...
 *
 */
public class CountDown {

    public static void main(String[] args) {
        new CountDown();
    }

    public CountDown() {

        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TestPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public class TestPane extends JPanel {

        private Timer timer;
        private long startTime = -1;
        private long duration = 25*60*60*1000 + 5*1000;

        private JLabel label;

        public TestPane() {
            setLayout(new GridBagLayout());
            timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (startTime < 0) {
                        startTime = System.currentTimeMillis();
                    }

                    long now = System.currentTimeMillis();
                    long clockTime = now - startTime;
                    if (clockTime >= duration) {
                        clockTime = duration;
                        timer.stop();
                    }

                    long timeInSeconds = (duration - clockTime) / 1000;
                    label.setText(String.format(
                        "%d:%02d:%02d", // hours:minutes:seconds
                        timeInSeconds / (60 * 60), // hours
                        (timeInSeconds / 60) % 60, // minutes
                        timeInSeconds % 60         // seconds
                    ));

                }
            });
            timer.setInitialDelay(0);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!timer.isRunning()) {
                        startTime = -1;
                        timer.start();
                    }
                }
            });
            label = new JLabel("...");
            add(label);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

    }

}
