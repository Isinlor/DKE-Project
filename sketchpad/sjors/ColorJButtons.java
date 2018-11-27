import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ColorJButtons{

     public JPanel createContentPane (){

        // Create a JPanel to put all the buttons on.
        JPanel totalGUI = new JPanel();

        // Set layout manager to null so that the buttons can be manually placed
        totalGUI.setLayout(null);
        //Panels for all the colors

        Color Red = new Color(230, 25, 75);
        Color Green = new Color(60, 180, 75);
        Color Yellow = new Color(255, 225, 25);
        Color Blue = new Color(0, 130, 200);
        Color Orange = new Color(245, 130, 48);
        Color Purple = new Color(145, 30, 180);
        Color Cyan = new Color(70, 240, 240);
        Color Magenta = new Color(240, 50, 230);
        Color Lime = new Color(210, 245, 60);
        Color Pink = new Color(250, 190, 190);
        Color Teal = new Color(0, 128, 128);
        Color Lavender = new Color(230, 190, 255);
        Color Brown = new Color(170, 110, 40);
        Color Beige = new Color(255, 250, 200);
        Color Maroon = new Color(128, 0, 0);
        Color Mint = new Color(170, 255, 195);
        Color Olive = new Color(128, 128, 0);
        Color Apricot = new Color(255, 215, 180);
        Color Navy = new Color(0, 0, 128);
        Color Grey = new Color(128, 128, 128);
        Color White = new Color(255, 255, 255);


        JButton redButton = new JButton();
        redButton.setBackground(Red);
        redButton.setLocation(10, 10);
        redButton.setSize(35,35);
        redButton.setToolTipText("Red");
        totalGUI.add(redButton);
      //  redButton.addActionListener(red);
        redButton.setVisible(true);

        JButton greenButton = new JButton();
        greenButton.setBackground(Green);
        greenButton.setLocation(55, 10);
        greenButton.setSize(35, 35);
        greenButton.setToolTipText("Green");
        totalGUI.add(greenButton);
        greenButton.setVisible(false);

        JButton YellowButton = new JButton();
        YellowButton.setBackground(Yellow);
        YellowButton.setLocation(100, 10);
        YellowButton.setSize(35, 35);
        YellowButton.setToolTipText("Yellow");
        totalGUI.add(YellowButton);
        YellowButton.setVisible(false);

        JButton BlueButton = new JButton();
        BlueButton.setBackground(Blue);
        BlueButton.setLocation(10, 55);
        BlueButton.setSize(35, 35);
        BlueButton.setToolTipText("Yellow");
        totalGUI.add(BlueButton);
        BlueButton.setVisible(false);

        JButton OrangeButton = new JButton();
        OrangeButton.setBackground(Blue);
        OrangeButton.setLocation(55, 55);
        OrangeButton.setSize(35, 35);
        OrangeButton.setToolTipText("Cyan");
        totalGUI.add(OrangeButton);
        OrangeButton.setVisible(false);

        JButton PurpleButton = new JButton();
        PurpleButton.setBackground(Purple);
        PurpleButton.setLocation(100, 55);
        PurpleButton.setSize(35, 35);
        PurpleButton.setToolTipText("Purple");
        totalGUI.add(PurpleButton);
        PurpleButton.setVisible(false);

        JButton CyanButton = new JButton();
        CyanButton.setBackground(Cyan);
        CyanButton.setLocation(10, 100);
        CyanButton.setSize(35, 35);
        CyanButton.setToolTipText("Cyan");
        totalGUI.add(CyanButton);

        JButton MagentaButton = new JButton();
        MagentaButton.setBackground(Magenta);
        MagentaButton.setLocation(55, 100);
        MagentaButton.setSize(35, 35);
        MagentaButton.setToolTipText("Magenta");
        totalGUI.add(MagentaButton);

        JButton LimeButton = new JButton();
        LimeButton.setBackground(Lime);
        LimeButton.setLocation(100, 100);
        LimeButton.setSize(35, 35);
        LimeButton.setToolTipText("Lime");
        totalGUI.add(LimeButton);

        JButton pinkButton = new JButton();
        pinkButton.setBackground(Pink);
        pinkButton.setLocation(10, 145);
        pinkButton.setSize(35, 35);
        pinkButton.setToolTipText("Pink");
        totalGUI.add(pinkButton);

        JButton TealButton = new JButton();
        TealButton.setBackground(Teal);
        TealButton.setLocation(55, 145);
        TealButton.setSize(35, 35);
        TealButton.setToolTipText("Teal");
        totalGUI.add(TealButton);

        JButton LavenderButton = new JButton();
        LavenderButton.setBackground(Lavender);
        LavenderButton.setLocation(100, 145);
        LavenderButton.setSize(35, 35);
        LavenderButton.setToolTipText("Lavender");
        totalGUI.add(LavenderButton);

        JButton BrownButton = new JButton();
        BrownButton.setBackground(Brown);
        BrownButton.setLocation(10, 190);
        BrownButton.setSize(35, 35);
        BrownButton.setToolTipText("Brown");
        totalGUI.add(BrownButton);

        JButton BeigeButton = new JButton();
        BeigeButton.setBackground(Beige);
        BeigeButton.setLocation(55, 190);
        BeigeButton.setSize(35, 35);
        BeigeButton.setToolTipText("Beige");
        totalGUI.add(BeigeButton);

        JButton MaroonButton = new JButton();
        MaroonButton.setBackground(Maroon);
        MaroonButton.setLocation(100, 190);
        MaroonButton.setSize(35, 35);
        MaroonButton.setToolTipText("Maroon");
        totalGUI.add(MaroonButton);

        JButton MintButton = new JButton();
        MintButton.setBackground(Mint);
        MintButton.setLocation(10, 235);
        MintButton.setSize(35, 35);
        MintButton.setToolTipText("Mint");
        totalGUI.add(MintButton);

        JButton OliveButton = new JButton();
        OliveButton.setBackground(Olive);
        OliveButton.setLocation(55, 235);
        OliveButton.setSize(35, 35);
        OliveButton.setToolTipText("Olive");
        totalGUI.add(OliveButton);

        JButton ApricotButton = new JButton();
        ApricotButton.setBackground(Apricot);
        ApricotButton.setLocation(100, 235);
        ApricotButton.setSize(35, 35);
        ApricotButton.setToolTipText("Apricot");
        totalGUI.add(ApricotButton);

        JButton NavyButton = new JButton();
        NavyButton.setBackground(Navy);
        NavyButton.setLocation(10, 280);
        NavyButton.setSize(35, 35);
        NavyButton.setToolTipText("Navy");
        totalGUI.add(NavyButton);

        JButton GreyButton = new JButton();
        GreyButton.setBackground(Grey);
        GreyButton.setLocation(55, 280);
        GreyButton.setSize(35, 35);
        GreyButton.setToolTipText("Grey");
        totalGUI.add(GreyButton);

        JButton WhiteButton = new JButton();
        WhiteButton.setBackground(White);
        WhiteButton.setLocation(100, 280);
        WhiteButton.setSize(35, 35);
        WhiteButton.setToolTipText("White");
        totalGUI.add(WhiteButton);





        JButton AddColor = new JButton(new ImageIcon("ADD.png"));


       // AddColor.setBackground(Color.white);
        AddColor.setLocation(55, 10);
        AddColor.setSize(35, 35);
        totalGUI.add(AddColor);

        class ColorJButtonsListener implements ActionListener {
        	int count = 0;
        	public void actionPerformed(ActionEvent event){

        	if(event.getSource() instanceof JButton) {
        			if (count == 0) {
        				greenButton.setVisible(true);
        				AddColor.setLocation(100, 10);
        			}
        			if (count == 1) {
        				YellowButton.setVisible(true);
        				AddColor.setLocation(10, 55);
        			}
        			if (count == 2) {
        				BlueButton.setVisible(true);
        				AddColor.setLocation(55, 55);
        			}
        			if (count == 3) {
        				OrangeButton.setVisible(true);
        				AddColor.setLocation(100, 55);
        			}
        			setSelectedColor(((JButton) event.getSource()).getBackground());
        		//	WhiteButton.setBackground(getSelectedColor());
        			count++;

        		}

        	}

        	public Color getSelectedColor() {
        		return SelectedColor;
        	}

        	public void setSelectedColor(Color selectedColor) {
        		SelectedColor = selectedColor;
        	}

        	private Color SelectedColor;

        	}

        ActionListener addcolor = new ColorJButtonsListener();
        AddColor.addActionListener(addcolor);
        ActionListener greybutton  = new ColorJButtonsListener();
        GreyButton.addActionListener(greybutton);
        ActionListener bluebutton = new ColorJButtonsListener();
        BlueButton.addActionListener(bluebutton);
        ActionListener orangebutton  = new ColorJButtonsListener();
        OrangeButton.addActionListener(orangebutton);

        // Returns the final JPanel
        totalGUI.setOpaque(true);
        return totalGUI;


    }

    private static void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Color table");

        //Create and set up the content pane.
        ColorJButtons demo = new ColorJButtons();
        frame.setContentPane(demo.createContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(160, 400);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Making sure the program keeps running
        //Creating and showing the GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
