import screenCapture.mainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class main {

    private static final int WIDTH = 2048;
    private static final int HEIGHT = 1152;


    //call screenCapture.mainFrame for updating screenshots
    //call oneImage(); for just a screenshot
    public static void main(String[] args) throws AWTException {
        new mainFrame(); // when pop-up appears make sure to move it to second monitor or something
        //oneImage();
    }

    private static void oneImage() throws AWTException {

        Robot robot;
        JFrame frame;
        Canvas canvas;

        robot = new Robot();

        BufferedImage image = robot.createScreenCapture(new Rectangle(0, 0, WIDTH, HEIGHT));

        frame = new JFrame();

        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setBackground(Color.BLUE);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        JPanel pane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, main.WIDTH, main.HEIGHT,null);
            }
        };

        frame.add(pane);
    }
}
