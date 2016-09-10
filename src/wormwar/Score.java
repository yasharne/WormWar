package wormwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author yashar nesabian
 */
public class Score extends ScreenObject implements Serializable {

    int score = 0;
    String sValue = null;

    public Score(int x, int y, int w, int h, Color cl) {
        super(x, y, w, h, cl);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(cl);
        g.setFont(new Font("Arial", Font.BOLD, h));
        g.drawString(score + "", x, y);
    }

    public void addScore(int d) {
        score += d;
    }

    @Override
    public String toString() {
        return setClassValue();
    }

    @Override
    public String setClassValue() {
        StringBuilder name = new StringBuilder("score ");
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
        if (score <= 9) {
            sdx = new StringBuilder("00000" + score);
        } else if (score <= 99) {
            sdx = new StringBuilder("0000" + score);
        } else if (score <= 999) {
            sdx = new StringBuilder("000" + score);
        } else if (score <= 9999) {
            sdx = new StringBuilder("00" + score);
        } else if (score <= 99999) {
            sdx = new StringBuilder("0" + score);
        } else {
            sdx = new StringBuilder("" + score);
        }
        sdx.setLength(6);
         this.sValue = sdx.toString();
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
        this.details = className + xVal + yVal + wVal + hVal + sValue + colXVal + colYVal + colZVal;
        return details;
    }

}
