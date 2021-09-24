package screenCapture;

import java.awt.event.*;

public class MouseHandler extends MouseAdapter {

    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse clicked");
        if (e.getButton() == MouseEvent.BUTTON2) {
            System.exit(1);
        }
    }
}
