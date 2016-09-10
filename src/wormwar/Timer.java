package wormwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author yashar nesabian
 */
public class Timer extends ScreenObject implements Runnable {

    public static int timer = 100;
    String tValue = null;
    

    public Timer(int x, int y, int w, int h, Color cl) {
        super(x, y, w, h, cl);
    }
    
    public void setTimer(int t){
        timer = t;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(cl);
        g.setFont(new Font("Arial", Font.BOLD, h));
        g.drawString(timer + "", x, y);
    }

    public void addTimer(int d) {
        timer = timer + d <= 100 ? timer + d : 100;
    }

    public void removeTimer(int d) {
        timer = timer - d >= 0 ? timer - d : 0;
    }

    @Override
    public void run() {
        while (timer > 0) {
            if (WormWar.isRunnig) {
                timer--;
            }
            try {
                Thread.sleep(8000);
            } catch (InterruptedException ex) {
            }
        }
    }

    

   @Override
    public String toString() {
        return setClassValue();
    }

    @Override
    public String setClassValue() {
        StringBuilder name = new StringBuilder("timer ");
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
        if (timer <= 9) {
        sdx = new StringBuilder("00" + timer);
        } else if (timer <= 99) {
        sdx = new StringBuilder("0" + timer);
        } else {
        sdx = new StringBuilder(timer + "");
        }
        sdx.setLength(3);
        this.tValue = sdx.toString();
        //-------------------------------------------------
        StringBuilder sdy = new StringBuilder("000");
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
        this.details = className + xVal + yVal + wVal + hVal + tValue + dyVal + colXVal + colYVal + colZVal;
        return details;
    }

}
