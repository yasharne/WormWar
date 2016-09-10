package wormwar;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author yashar
 */
public class Cabin extends ScreenObject implements Runnable, Serializable {

    transient ScreenManager sm;
    int dy = 1;
    int dx = 0;

    public Cabin(int x, int y, int w, int h, Color cl, ScreenManager sm) {
        super(x, y, w, h, cl);
        this.sm = sm;
    }

    public final int xPos[] = {x, x - 10, x - 10, x, x,
        x + 10, x + 10, x + 40, x + 40, x + 50,
        x + 50, x + 60, x + 60, x + 50, x + 50,
        x + 40, x + 40, x + 10, x + 10, x,
        x, x + 50, x + 50, x + 40, x + 40,
        x + 10, x + 10, x, x};

    public int yPos[] = {y, y, y - 10, y - 10, y - 15,
        y - 15, y - 20, y - 20, y - 15, y - 15,
        y - 10, y - 10, y, y, y + 20,
        y + 20, y + 10, y + 10, y + 20, y + 20,
        y, y, y - 5, y - 5, y - 10,
        y - 10, y - 5, y - 5, y};

    @Override
    public void draw(Graphics g) {
        g.setColor(cl);
        g.fillPolygon(xPos, yPos, 29);
    }

    @Override
    public void run() {
        while (yPos[6] <= WormWar.SCREEN_HEIGHT) {
            if (WormWar.isRunnig) {
                for (int i = 0; i < yPos.length; i++) {
                    yPos[i] += dy;

                }
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
            }
        }
        sm.removeScreenObject(this);
    }

    @Override
    public String toString() {
        return setClassValue();
    }

    @Override
    public String setClassValue() {
        StringBuilder name = new StringBuilder("cabin ");
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
