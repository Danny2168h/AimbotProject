package screenCapture;

import screenCapture.Loop;

import javax.swing.*;
import java.awt.*;

public class mainFrame extends JFrame{

    private static final int WIDTH = 2048;
    private static final int HEIGHT = 1152;
    private static final String TITLE = "Screen Record";

    public mainFrame() throws AWTException {
        this.setTitle(TITLE);

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(true);

        Loop loop = new Loop();
        this.add(loop);
        loop.start();
        this.setVisible(true);
    }
}
