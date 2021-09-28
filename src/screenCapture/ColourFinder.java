package screenCapture;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ColourFinder {

    private static final int REMOVE_ALPHA = 0b00000000111111111111111111111111;
    private BufferedImage image;
    private int xCoord;
    private int yCoord;
    private static final int DOT_WIDTH = 7;
    private boolean foundPixel = false;
    private int outlineRGB;
    private int[][] grid;
    private LinkedList<int[]> targets = new LinkedList<>();
    private int targetsFound = 0;

    public ColourFinder(BufferedImage image, int r, int g, int b) {
        this.image = image;
        outlineRGB = rgbConverter(r, g, b);
        grid = new int[image.getWidth()][image.getHeight()];
        processImageMultiTarget();
        if (targetsFound > 0) {
            drawDotOnImage1();
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

    private void processImageMultiTarget() {

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int argb = image.getRGB(i,j);
                if (grid[i][j] == 0 && checkMatch(argb)) {
                    targetsFound++;
                    BFS(i, j);
                }
            }
        }
    }

    private boolean inBounds(int x, int y) {
        return (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight());
    }

    private void BFS(int x, int y) {
        LinkedList<int[]> queue = new LinkedList<>();

        int pixelCount = 0;
        int widthTotal = 0;
        int heightTotal = 0;

        grid[x][y] = targetsFound;
        queue.add(new int[]{x,y});
        pixelCount++;
        widthTotal += x;
        heightTotal += y;

        while (queue.size() > 0) {
            int[] currCord = queue.pollFirst();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (inBounds(currCord[0] + i, currCord[1] + j)) {
                        if (grid[currCord[0] + i][currCord[1] + j] == 0 && checkMatch(image.getRGB(currCord[0] + i, currCord[1] + j))) {
                            //System.out.println(currCord[0] + "  " + currCord[1]);
                            grid[currCord[0] + i][currCord[1] + j] = targetsFound;
                            queue.add(new int[]{currCord[0] + i, currCord[1] + j});
                            pixelCount++;
                            widthTotal += currCord[0] + i;
                            heightTotal += currCord[1] + j;
                        }
                    }
                }
            }
        }
        int targetX = widthTotal / pixelCount;
        int targetY = heightTotal / pixelCount;

        targets.add(new int[]{targetX, targetY});
    }


    private boolean checkMatch(int argb) {
        argb = argb & REMOVE_ALPHA;
        return outlineRGB == argb;
    }

    private void drawDotOnImage1() {
        for (int[] coord : targets) {
            for (int i = -DOT_WIDTH; i <= DOT_WIDTH; i++) {
                for (int j = -DOT_WIDTH; j <= DOT_WIDTH; j++) {
                    if (inBounds(coord[0] + i, coord[1] + j)) {
                        image.setRGB(coord[0] + i, coord[1] + j, rgbConverterDraw(0, 0, 255, 255));
                    }
                }
            }
        }
    }

    private void drawDotOnImage() {
            for (int i = -DOT_WIDTH; i <= DOT_WIDTH; i++) {
                for (int j = -DOT_WIDTH; j <= DOT_WIDTH; j++) {
                    if (inBounds(xCoord + i, yCoord + j)) {
                        image.setRGB(xCoord + i, yCoord + j, rgbConverterDraw(255, 0, 0, 255));
                    }
                }
            }

    }

    private int rgbConverter(int r, int g, int b) {
        return (r << 16) + (g << 8) + b;
    }

    private int rgbConverterDraw(int r, int g, int b, int a) {
        return (a << 24) + (r << 16) + (g << 8) + b;
    }

    public int getX(){
        return xCoord;
    }

    public int getY(){
        return yCoord;
    }

    public LinkedList<int[]> getListOfTargets() {
        return targets;
    }

    public boolean isFoundPixel() {
        return foundPixel;
    }
}
