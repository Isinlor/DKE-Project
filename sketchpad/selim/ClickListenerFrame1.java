import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;



public class ClickListenerFrame1 implements ActionListener  {

    public void actionPerformed(ActionEvent event) {

        //switching from the 1st GUI to the 2nd one
        frame.dispose();
       // Frame2 frame2 = new Frame2;
        frame2.setVisible(true);
    }

    private JFrame frame;
    private JFrame frame2;

}
