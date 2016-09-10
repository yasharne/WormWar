package wormwar;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author yashar nesabian
 */
public class WormAndCabinGenerator extends Thread implements Serializable {

    public static boolean Generate = true;
    public static int Level = 1;
    public static int killedWormsAndCabins = 0;
    public int generateAmount;
    private boolean a = true;
    private boolean b = true;
    private boolean c = true;
    private boolean d = true;
    private boolean e = true;
    private boolean f = true;

    ScreenManager sm;

    public WormAndCabinGenerator(ScreenManager sm) {
        this.sm = sm;
    }

    public void levelUp() {
        Level++;
        killedWormsAndCabins = 0;
        sm.ww.score.addScore(500);
        a = true;
        b = true;
        c = true;
        d = true;
        e = true;
        f = true;
    }

    @Override
    public void run() {
        while (true) {
            int randomCabinGenerator = (int) (3 * Math.random());
            boolean generateCabin = false;
            if (randomCabinGenerator == 1) {
                generateCabin = true;
            }
            calculateGenerateAmount();//---calculate how much worm or cabin should generate---
            if (Generate) {
                for (int i = 0; i < generateAmount; i++) {
                    if (WormWar.isRunnig) {
                        if (generateCabin) {
                            int a = 90 + (int) (400 * Math.random());
                            int b = 70 + (int) (200 * Math.random());
                            Cabin cabin = new Cabin(a, b, 0, 0, Color.gray, sm);
                            sm.addScreenObject(cabin);
                            Thread t = new Thread(cabin);
                            t.start();
                            generateCabin = false;
                        } else {
                            int b = 70 + (int) (290 * Math.random());
                            Worm worm = new Worm(WormWar.SCREEN_WIDTH - (BlockGenerator.BLOCK_WIDTH + BlockGenerator.SIDE_DISTANCE) - 75,
                                    b, 60, 50, Color.YELLOW, sm);
                            sm.addScreenObject(worm);
                            Thread t = new Thread(worm);
                            sm.ww.wormMoving.loop();
                            t.start();
                        }
                        try {
                            sleep(1000);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
                Generate = false;
            } else {
                try {
                    sleep(2000);
                } catch (InterruptedException ex) {
                }
            }
        }

    }

    public void calculateGenerateAmount() {
        if (killedWormsAndCabins == 0) {
            if (a) {
                generateAmount = 1;
                Generate = true;
                a = false;
            }
        } else if (killedWormsAndCabins == 1) {
            if (b) {
                generateAmount = 2;
                Generate = true;
                b = false;
            }
        } else if (killedWormsAndCabins == 3) {
            if (c) {
                generateAmount = 3;
                Generate = true;
                c = false;
            }
        } else if (killedWormsAndCabins == 6) {
            if (d) {
                generateAmount = 4;
                Generate = true;
                d = false;
            }
        } else if (killedWormsAndCabins == 10) {
            if (e) {
                generateAmount = 5;
                Generate = true;
                e = false;
            }
        } else if (killedWormsAndCabins == 15) {
            if (f) {
                generateAmount = 5;
                Generate = true;
                f = false;
            }
        } else if (killedWormsAndCabins == 20) {
            levelUp();
        }
    }

}
