package screenCapture;

import java.awt.image.BufferedImage;

public class ColourFinder {

    private static final int REMOVE_ALPHA = 0b00000000111111111111111111111111;
    private BufferedImage image;
    private int xCoord;
    private int yCoord;
    private static final int DOT_WIDTH = 7;
    private boolean foundPixel;
    private int outlineRGB;

    public ColourFinder(BufferedImage image, int r, int g, int b, int a) {
        this.image = image;
        outlineRGB = REMOVE_ALPHA & rgbConverter(r, g, b, a);
        processImage();
        if (foundPixel) {
            drawDotOnImage();
        }
    }

    private void processImage() {
        int pixelCount = 0;
        int widthTotal = 0;
        int heightTotal = 0;

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int argb = image.getRGB(i,j);
                if (checkMatch(argb)) {
                    pixelCount++;
                    widthTotal += i;
                    heightTotal += j;
                }
            }
        }

        if (pixelCount > 0) {
            xCoord = widthTotal/pixelCount;
            yCoord = heightTotal/pixelCount;
            foundPixel = true;
        }
    }

    private boolean checkMatch(int argb) {
        argb = argb & REMOVE_ALPHA;
        return outlineRGB == argb;
    }

    private void drawDotOnImage() {

        for (int i = -DOT_WIDTH; i < DOT_WIDTH; i++) {
            for (int j = -DOT_WIDTH; j < DOT_WIDTH; j++) {
                image.setRGB(xCoord + i, yCoord + j, rgbConverter(0, 0,255, 255));
            }
        }
    }

    private int rgbConverter(int r, int g, int b, int a) {
        return (a << 24) + (r << 16) + (g << 8) + b;
    }

    public int getX(){
        return xCoord;
    }

    public int getY(){
        return yCoord;
    }

    public boolean isFoundPixel() {
        return foundPixel;
    }
}
