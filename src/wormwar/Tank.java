package wormwar;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author yashar nesabian
 */
public class Tank extends ScreenObject implements Serializable {

    transient ScreenManager sm;
    int dx = 0;
    int dy = 0;

    public Tank(int x, int y, int w, int h, Color cl, ScreenManager sm) {
        super(x, y, w, h, cl);
        this.sm = sm;
    }

    public int xPos[] = {x, x + 5, x + 5, x + 10, x + 10,
        x + 15, x + 15, x + 20, x + 20, x + w,
        x + w, x + 20, x + 20, x + 15, x + 15,
        x + 10, x + 10, x + 5, x + 5, x};
    public final int yPos[] = {y, y, y + 3, y + 3, y - 4,
        y - 4, y + 3, y + 3, y, y,
        y + 12, y + 12, y + 9, y + 9, y + 12,
        y + 12, y + 9, y + 9, y + 12, y + 12};

    @Override
    public void draw(Graphics g) {
        g.setColor(cl);
        g.fillPolygon(xPos, yPos, 20);
    }

    public void left() {
        if (WormWar.isRunnig) {
            if (xPos[0] > BlockGenerator.BLOCK_WIDTH + BlockGenerator.SIDE_DISTANCE + w / 3) {
                for (int i = 0; i < xPos.length; i++) {
                    xPos[i] -= w / 3;
                }
            }
        }
    }

    public void right() {
        if (WormWar.isRunnig) {
            if (xPos[9] <= WormWar.SCREEN_WIDTH - (BlockGenerator.BLOCK_WIDTH + BlockGenerator.SIDE_DISTANCE + w / 3)) {
                for (int i = 0; i < xPos.length; i++) {
                    xPos[i] += w / 3;
                }
            }
        }
    }

    public void fire() {
        if (WormWar.isRunnig) {
            Bullet b = new Bullet(xPos[0] + w / 2 - 5 / 2, y - 17, 5, 25, Color.YELLOW, sm);
            sm.addScreenObject(b);
            Thread t = new Thread(b);
            t.start();
        }
    }

    @Override
    public String toString() {
        return setClassValue();
    }

    @Override
    public String setClassValue() {
        StringBuilder name = new StringBuilder("tank  ");
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

}
