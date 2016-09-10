package wormwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;

/**
 *
 * @author yashar nesabian
 */
public abstract class ScreenObject implements Serializable {

    int x;
    int y;
    int w;
    int h;
    int id;
    Image im;
    Color cl;
    String className;
    String xVal;
    String yVal;
    String wVal;
    String hVal;
    String dxVal;
    String dyVal;
    String colXVal;
    String colYVal;
    String colZVal;
    String details;

    public ScreenObject(int x, int y, int w, int h, Image im) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.im = im;
    }

    public ScreenObject(int x, int y, int w, int h, Color cl) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.cl = cl;
    }

    public abstract void draw(Graphics g);
    public abstract String setClassValue();
}
