package wormwar;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author yashar nesabian
 */
public class BlockGenerator extends Thread implements Serializable {

    ScreenManager sm;
    Color cl;
    public static final int BLOCK_WIDTH = 20;
    public static final int BLOCK_HEIGHT = 40;
    public static final int SIDE_DISTANCE = 55;
    public static int LEVEL = 1;
    private final int val = 20;
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private static boolean first = true;
    public static boolean Generate = true;
    int idx;

    public BlockGenerator(ScreenManager sm) {
        this.sm = sm;
    }

    @Override
    public void run() {
        while (true) {

            if (WormWar.isRunnig) {

                randomColor();
                Block b1 = new Block(SIDE_DISTANCE, 0, BLOCK_WIDTH, BLOCK_HEIGHT, cl, sm);
                sm.addScreenObject(b1);
                Thread t1 = new Thread(b1);
                t1.start();
                Block b2 = new Block(sm.ww.getWidth() - (BLOCK_WIDTH + SIDE_DISTANCE), 0, BLOCK_WIDTH, BLOCK_HEIGHT, cl, sm);
                sm.addScreenObject(b2);
                Thread t2 = new Thread(b2);
                t2.start();

                if (WormAndCabinGenerator.Level >= 2) {//---generate middle blocks---
                    int a = (int) (16 * Math.random());
                    if (a == 2 || a == 4 || a == 6 || a == 8 || a == 10 || a == 12 || a == 14 || a == 15 || a == 5) {
                        int b = (int) (24 * Math.random());
                        Block b3 = new Block(SIDE_DISTANCE + BLOCK_WIDTH + b * BLOCK_WIDTH, 0, BLOCK_WIDTH, BLOCK_HEIGHT, cl, sm);
                        sm.addScreenObject(b3);
                        Thread t3 = new Thread(b3);
                        t3.start();

                        if (a == 4 || a == 8 || a == 12 || a == 16 || a == 5) {
                            int d = (int) (24 * Math.random());
                            Block b4 = new Block(SIDE_DISTANCE + BLOCK_WIDTH + d * BLOCK_WIDTH, 0, BLOCK_WIDTH, BLOCK_HEIGHT, cl, sm);
                            sm.addScreenObject(b4);
                            Thread t4 = new Thread(b4);
                            t4.start();
                            if (a == 4 || a == 8 || a == 12) {
                                int e = (int) (24 * Math.random());
                                Block b5 = new Block(SIDE_DISTANCE + BLOCK_WIDTH + e * BLOCK_WIDTH, 0, BLOCK_WIDTH, BLOCK_HEIGHT, cl, sm);
                                sm.addScreenObject(b5);
                                Thread t5 = new Thread(b5);
                                t5.start();
                            }
                        }
                    }
                }
            }
            try {
                sleep(1800);
            } catch (InterruptedException ex) {
            }

        }
    }

    public void randomColor() {
        colorGenerator();
        cl = new Color(red, green, blue);
    }

    public void colorGenerator() {
        if (first) {
            if (red < 236) {
                red += val;
            } else if (green < 236) {
                green += val;
            } else if (blue < 236) {
                blue += val;
            } else {
                first = false;
            }
        } else {
            if (red > 20) {
                red -= val;
            } else if (green > 20) {
                green -= val;
            } else if (blue > 20) {
                blue -= val;
            } else {
                first = true;
            }
        }
    }

}
