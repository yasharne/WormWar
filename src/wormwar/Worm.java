package wormwar;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author yashar nesabian
 */
public class Worm extends ScreenObject implements Runnable, Serializable {

    int dx = 15;
    int dy = Block.dy * 3;
    private int turn = 0;
    boolean reverseTime = true;
    private int booleanTime = 0;
    private int rightToLeftMove = 0;

    transient ScreenManager sm;

    int xP[][] = {{x, x, x + 60, x + 60},
    //----------------------------------------------------
    {x + 5, x + 5, x + 10, x + 10,
        x + 15, x + 15, x + 45, x + 45,
        x + 50, x + 50, x + 55, x + 55,
        x + 48, x + 48, x + 41, x + 41,
        x + 19, x + 19, x + 12, x + 12
    },
    //----------------------------------------------------
    {x + 10, x + 10, x + 15, x + 15,
        x + 20, x + 20, x + 25, x + 25,
        x + 35, x + 35, x + 40, x + 40,
        x + 45, x + 45, x + 50, x + 50,
        x + 42, x + 42, x + 34, x + 34,
        x + 26, x + 26, x + 18, x + 18
    },
    //----------------------------------------------------
    {x + 5, x + 5, x + 10, x + 10,
        x + 15, x + 15, x + 45, x + 45,
        x + 50, x + 50, x + 55, x + 55,
        x + 48, x + 48, x + 41, x + 41,
        x + 19, x + 19, x + 12, x + 12
    }};

    int yP[][] = {{y, y - 5, y - 5, y},
    //-----------------------------------------------------
    {y, y - 5, y - 5, y - 10,
        y - 10, y - 15, y - 15, y - 10,
        y - 10, y - 5, y - 5, y,
        y, y - 5, y - 5, y - 10,
        y - 10, y - 5, y - 5, y
    },
    //-----------------------------------------------------
    {y, y - 5, y - 5, y - 10,
        y - 10, y - 15, y - 15, y - 20,
        y - 20, y - 15, y - 15, y - 10,
        y - 10, y - 5, y - 5, y,
        y, y - 5, y - 5, y - 10,
        y - 10, y - 5, y - 5, y},
    //-----------------------------------------------------
    {y, y - 5, y - 5, y - 10,
        y - 10, y - 15, y - 15, y - 10,
        y - 10, y - 5, y - 5, y,
        y, y - 5, y - 5, y - 10,
        y - 10, y - 5, y - 5, y
    }};

    public Worm(int x, int y, int w, int h, Color cl, ScreenManager sm) {
        super(x, y, w, h, cl);
        this.sm = sm;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(cl);
        g.fillPolygon(xP[turn % 4], yP[turn % 4], xP[turn % 4].length);
    }

    @Override
    public void run() {
        while (yP[2][7] <= WormWar.SCREEN_HEIGHT) {
            if (WormWar.isRunnig) {
                turn++;
                for (int i = 0; i < xP[turn % 4].length; i++) { //---HorizontalMove---
                    xP[turn % 4][i] -= dx;
                }

                //---reverse moving---
                if (reverseTime && (xP[0][0] <= BlockGenerator.BLOCK_WIDTH + BlockGenerator.SIDE_DISTANCE
                        || xP[0][2] >= WormWar.SCREEN_WIDTH - (BlockGenerator.BLOCK_WIDTH + BlockGenerator.SIDE_DISTANCE))) {
                    if (xP[0][2] >= WormWar.SCREEN_WIDTH - (BlockGenerator.BLOCK_WIDTH + BlockGenerator.SIDE_DISTANCE)) {
                        rightToLeftMove = 0;
                    }
                    dx = -dx;
                    reverseTime = false;
                }

                if (booleanTime == 80) {
                    reverseTime = true;
                    booleanTime = 0;
                }

                if (rightToLeftMove % 7 == 0) {
                    for (int i = 0; i < yP.length; i++) { //---VerticalMove---
                        for (int j = 0; j < yP[i].length; j++) {
                            yP[i][j] += Block.dy * 3;
                        }
                    }
                }
                booleanTime++;
                rightToLeftMove++;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }

        }
        sm.ww.wormMoving.stop();
        sm.removeScreenObject(this);
    }

    @Override
    public String toString() {
        return setClassValue();
    }

    @Override
    public String setClassValue() {
        StringBuilder name = new StringBuilder("worm  ");
        name.setLength(6);
        this.className = name.toString();
        //------------------------------------------------
        StringBuilder sx = null;
        if (x <= 9) {
            sx = new StringBuilder("00" + x);
        } else if (x <= 99) {
            sx = new StringBuilder("0" + x);
        } else {
            sx = new StringBuilder(x + "");
        }
        sx.setLength(3);
        this.xVal = sx.toString();
        //------------------------------------------------
        StringBuilder sy = null;
        if (y <= 9) {
            sy = new StringBuilder("00" + y);
        } else if (y <= 99) {
            sy = new StringBuilder("0" + y);
        } else {
            sy = new StringBuilder(y + "");
        }
        sx.setLength(3);
        this.yVal = sy.toString();
        //------------------------------------------------
        StringBuilder sw = null;
        if (w <= 9) {
            sw = new StringBuilder("00" + w);
        } else if (w <= 99) {
            sw = new StringBuilder("0" + w);
        } else {
            sw = new StringBuilder(w + "");
        }
        sw.setLength(3);
        this.wVal = sw.toString();
        //-----------------------------------------------
        StringBuilder sh = null;
        if (h <= 9) {
            sh = new StringBuilder("00" + h);
        } else if (h <= 99) {
            sh = new StringBuilder("0" + h);
        } else {
            sh = new StringBuilder(h + "");
        }
        sh.setLength(3);
        this.hVal = sh.toString();
        //--------------------------------------------------
        StringBuilder sdx = null;
        if (dx <= 9) {
            sdx = new StringBuilder("00" + dx);
        } else if (dx <= 99) {
            sdx = new StringBuilder("0" + dx);
        } else {
            sdx = new StringBuilder(dx + "");
        }
        sdx.setLength(3);
        this.dxVal = sdx.toString();
        //-------------------------------------------------
        StringBuilder sdy = null;
        if (dy <= 9) {
            sdy = new StringBuilder("00" + dy);
        } else if (dy <= 99) {
            sdy = new StringBuilder("0" + dy);
        } else {
            sdy = new StringBuilder(dy + "");
        }
        sdy.setLength(3);
        this.dyVal = sdy.toString();
        //-------------------------------------------------
        StringBuilder scx = null;
        if (cl.getRed() <= 9) {
            scx = new StringBuilder("00" + cl.getRed());
        } else if (cl.getRed() <= 99) {
            scx = new StringBuilder("0" + cl.getRed());
        } else {
            scx = new StringBuilder(cl.getRed() + "");
        }
        scx.setLength(3);
        this.colXVal = scx.toString();
        //-------------------------------------------------
        StringBuilder scy = null;
        if (cl.getGreen() <= 9) {
            scy = new StringBuilder("00" + cl.getGreen());
        } else if (cl.getGreen() <= 99) {
            scy = new StringBuilder("0" + cl.getGreen());
        } else {
            scy = new StringBuilder(cl.getGreen() + "");
        }
        scy.setLength(3);
        this.colYVal = scy.toString();
        //--------------------------------------------------
        StringBuilder scz = null;
        if (cl.getBlue() <= 9) {
            scz = new StringBuilder("00" + cl.getBlue());
        } else if (cl.getBlue() <= 99) {
            scz = new StringBuilder("0" + cl.getBlue());
        } else {
            scz = new StringBuilder(cl.getBlue() + "");
        }
        scz.setLength(3);
        this.colZVal = scz.toString();
        //-------------------------------------------------
        this.details = className + xVal + yVal + wVal + hVal + dxVal + dyVal + colXVal + colYVal + colZVal;
        return details;
    }

    /*public void bombing() {
     int rand = (int) (8 * Math.random());
     switch (rand) {
     case 1:
     Bomb b = new Bomb(x, y, 25, 25, Color.white, sm);
     sm.addScreenObject(b);
     Thread t = new Thread(b);
     t.start();
     break;
     default:
     break;
     }

     }*/
}
