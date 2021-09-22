package screenCapture;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Loop extends Canvas implements Runnable{

    private static final int WIDTH = 2048;
    private static final int HEIGHT = 1152;

    private int updateNum;

    public Thread thread;
    public boolean running = false;

    private Robot robot = new Robot();

    private BufferedImage screenShot;

    public Loop() throws AWTException {
    }

    public synchronized void start() {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        this.requestFocus();
        long now;
        long updateTime;
        long wait;
        int frames = 0;
        long timer = System.currentTimeMillis();

        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        while (running) {
            now = System.nanoTime();

            try {
                update();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            render();

            frames++;

            updateTime = System.nanoTime() - now;
            wait = (OPTIMAL_TIME - updateTime) / 1000000;

            if (wait > 0) {

                try {
                    thread.sleep(wait);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

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

    private void update() throws AWTException {

        screenShot= robot.createScreenCapture(new Rectangle(0, 0, WIDTH, HEIGHT));
        updateNum++;
    }
}
