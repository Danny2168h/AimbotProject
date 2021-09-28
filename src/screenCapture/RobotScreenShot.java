package screenCapture;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class RobotScreenShot extends Canvas implements Runnable{

    private static final int WIDTH = 2048;
    private static final int HEIGHT = 1152;

    private int updateNum;

    public Thread thread;
    public boolean running = false;

    private Robot robot = new Robot();

    private BufferedImage screenShot;
    private int r;
    private int g;
    private int b;
    private boolean first = true;
    private boolean mousePressed = false;


    public RobotScreenShot(int i, int i1, int i2) throws AWTException {
        r = i;
        g = i1;
        b = i2;
    }

    public synchronized void start() {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    private void update() throws InterruptedException {

        if (first) {
            Thread.sleep(3500);
            first = false;
        }

        screenShot = robot.createScreenCapture(new Rectangle(0, 0, WIDTH, HEIGHT));
        ColourFinder cf = new ColourFinder(screenShot, r, g, b);
        LinkedList<int[]> targetList = cf.getListOfTargets();

        for (int[] coords : targetList) {
            robot.mouseMove(coords[0], coords[1]);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }

//        if (cf.isFoundPixel()) {
//            //robot.mouseMove(cf.getX(), cf.getY());
//            //robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//            //robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//            mousePressed = true;
//        } else if (mousePressed){
//            //robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
//            mousePressed = false;
//        }
        updateNum++;
    }

    @Override
    public void run() {
        //this.requestFocus();
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            try {
                update();
            } catch (Exception e) {
                e.printStackTrace();
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + "  " + "Updates: " + updateNum);
                frames = 0;
                updateNum = 0;
            }
        }
    }

    private void render() {
        this.setVisible(true);

        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = buffer.getDrawGraphics();

        g.drawImage(screenShot, 0, 0, WIDTH, HEIGHT, null);

        g.dispose();
        buffer.show();
    }

}
