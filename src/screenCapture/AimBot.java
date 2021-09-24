package screenCapture;

import javax.swing.*;
import java.awt.*;

public class AimBot extends JFrame{

    private static final int WIDTH = 2048;
    private static final int HEIGHT = 1152;
    private static final String TITLE = "Screen Record";

    public AimBot(int r, int g, int b, int a) throws AWTException {
        this.setTitle(TITLE);

        this.setPreferredSize(new Dimension(1000, 750));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(1000, 750));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(true);
        this.requestFocus();
        this.addKeyListener(new KeyHandler());
        this.addMouseListener(new MouseHandler());
        this.setEnabled(true);

        RobotScreenShot loop = new RobotScreenShot(r, g, b, a);
        this.add(loop);
        loop.start();
        this.setVisible(true);
    }
}
