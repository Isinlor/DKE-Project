import javax.swing.*;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


public class Frame1Viewer {
    public static void main(String[] args) {


        JFrame frame = new JFrame();

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Game name");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        JLabel label1 = new JLabel("Choose the game mode:");
        label1.setSize(400,40);
        label1.setLocation(37,5);

        JRadioButton firstButton = new JRadioButton();
        firstButton.setSelected(true);
        firstButton.setText("1st game mode");
        firstButton.setSize(140,20);
        firstButton.setLocation(30,40);
        // get the game mode with a listener

        ActionListener RadioButtonListener1 = new RadioButtonListener();
        firstButton.addActionListener(RadioButtonListener1);

        JRadioButton secondButton = new JRadioButton();
        secondButton.setText("2nd game mode");
        secondButton.setSize(140,20);
        secondButton.setLocation(30,60);
        // get the game mode with a listener
       // if (mediumButton.isSelected()) {
       // }

        JRadioButton thirdButton = new JRadioButton();
        thirdButton.setText("3rd game mode");
        thirdButton.setSize(140,20);
        thirdButton.setLocation(30,80);
        // get the game mode with a listener

        ButtonGroup group = new ButtonGroup();

        //JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        //separator1.setVisible(true);
        //separator1.setLocation(5, 5);

        group.add(firstButton);
        group.add(secondButton);
        group.add(thirdButton);


        JLabel label2 = new JLabel("Number of vertices: ");
        label2.setSize(200,20);
        label2.setLocation(40,130);

        JLabel label3 = new JLabel("Number of edges: ");
        label3.setSize(200,20);
        label3.setLocation(40, 150);


        JSpinner spinner1 = new JSpinner();
        spinner1.setSize(45,18);
        spinner1.setLocation(180,130);

        JSpinner spinner2 = new JSpinner();
        spinner2.setSize(45,18);
        spinner2.setLocation(180,152);


        JLabel label4 = new JLabel("Choose a graph from a file:");
        label4.setSize(250,20);
        label4.setLocation(40, 200);

        JLabel label5 = new JLabel("Hello");
        label5.setSize(250,20);
        label5.setLocation(40, 200);

        JLabel label6 = new JLabel("Game rules");
        label6.setSize(250,20);
        label6.setLocation(40, 200);


        JButton button1 = new JButton("Play");
        button1.setSize(100,50);
        button1.setLocation(100,320);


        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton button2 = new JButton("Game rules");
        button2.setSize(100,30);
        button2.setLocation(190,10);

        JButton button3 = new JButton("Choose file ...");
        button3.setSize(110,30);
        button3.setLocation(40, 220);


        // File chooser. It prints the path of the file in the terminal
        button3.addActionListener(e ->{
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Select a graph");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("text", "txt");
            jfc.addChoosableFileFilter(filter);

            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                System.out.println(jfc.getSelectedFile().getPath());
            }
        });

        panel.add(label1);
        panel.add(firstButton);
        panel.add(secondButton);
        panel.add(thirdButton);
        panel.add(label2);
        panel.add(label3);
        panel.add(spinner1);
        panel.add(spinner2);
        panel.add(label4);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);


        // new panel for a new frame for the game rules (explications of the different game mode")
        JPanel panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.add(label6);


        // go to the next user interface by replacing the panel of the frame
        button2.addActionListener(e -> {
            frame.add(panel3);
            frame.setVisible(true);
            panel.setVisible(false);
        });



        // new panel for a new frame ...
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.add(label5);


        frame.add(panel);

        // go to the next user interface by replacing the panel of the frame
        button1.addActionListener(e -> {
            frame.add(panel2);
            frame.setVisible(true);
            panel.setVisible(false);
        });


        /* frame.add(label1);
        frame.add(firstButton);
        frame.add(secondButton);
        frame.add(thirdButton);
        frame.add(label2);
        frame.add(label3);
        frame.add(spinner1);
        frame.add(spinner2);
        frame.add(label4);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        */

        frame.setVisible(true);
    }

    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 400;
}
