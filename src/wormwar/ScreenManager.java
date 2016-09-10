package wormwar;

import java.awt.Color;
import static java.awt.Color.red;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author yashar nesabian
 */
public class ScreenManager implements Serializable {

    public static final int LINE_Y = 45;
    ArrayList ol;
    WormWar ww;

    public ScreenManager(WormWar ww) {
        this.ww = ww;
        ol = new ArrayList();
    }

    public void addScreenObject(ScreenObject so) {
        ol.add(so);
    }

    public void removeScreenObject(ScreenObject so) {
        ol.remove(so);
    }

    public void draw(Graphics g) {
        checkConflict();
        ArrayList temp = new ArrayList(2);
        Image offscreen = ww.createImage(ww.getWidth(), ww.getHeight());
        Graphics offg = offscreen.getGraphics();

        for (int i = 0; i < ol.size(); i++) {
            if (ol.get(i) instanceof Score || ol.get(i) instanceof Timer) {
                temp.add(ol.get(i));
                continue;//---if Score OR Timer continiue---
            }
            ScreenObject so = (ScreenObject) ol.get(i);
            so.draw(offg);
        }

        offg.setColor(Color.BLACK);//---draw Score OR Timer individually---
        offg.fillRect(0, 0, ww.getWidth(), LINE_Y);

        for (int i = 0; i < temp.size(); i++) {
            ScreenObject so = (ScreenObject) temp.get(i);
            so.draw(offg);
        }
        g.drawImage(offscreen, 0, 0, ww);
    }

    private void checkConflict() {
        ArrayList<Block> BlockL = new ArrayList();
        ArrayList<Worm> WormL = new ArrayList();
        ArrayList<Bullet> BulletL = new ArrayList();
        ArrayList<Cabin> CabinL = new ArrayList();
        //ArrayList<Bomb> Bombl = new ArrayList();
        Score score = null;
        Timer timer = null;

        for (int i = 0; i < ol.size(); i++) {
            if (ol.get(i) instanceof Bullet) {
                BulletL.add((Bullet) (ol.get(i)));
            } else if (ol.get(i) instanceof Worm) {
                WormL.add((Worm) ol.get(i));
            } else if (ol.get(i) instanceof Block) {
                BlockL.add((Block) ol.get(i));
            } else if (ol.get(i) instanceof Score) {
                score = (Score) ol.get(i);
            } else if (ol.get(i) instanceof Timer) {
                timer = (Timer) ol.get(i);
            } else if (ol.get(i) instanceof Cabin) {
                CabinL.add((Cabin) ol.get(i));
            }
        }
        if (WormWar.A) {
            for (Bullet bullet : BulletL) {
                removeScreenObject(bullet);
                Worm w = new Worm(bullet.x, bullet.y, LINE_Y, LINE_Y, Color.red, this);
                addScreenObject(w);
                Thread t = new Thread(w);
                t.start();
            }
            WormWar.A = false;
        }

        for (Block block : BlockL) {//---Block conflict with Bullet OR Tank---
            int py = block.y + block.h;
            for (Bullet bullet : BulletL) {
                int px = block.x + block.w;

                //---Block-Bullet conflict---
                if (py >= bullet.y && (bullet.x + bullet.w >= block.x && bullet.x <= px)) {
                    score.addScore(20);
                    removeScreenObject(block);
                    removeScreenObject(bullet);
                    ww.shootBlock.play();
                    break;
                }
            }

            //---Block-Tank conflict---
            if (py >= ww.tank.y && ((ww.tank.xPos[0] >= block.x && ww.tank.xPos[0] <= block.x + block.w)
                    || (ww.tank.xPos[0] + ww.tank.w >= block.x && ww.tank.xPos[0] + ww.tank.w <= block.x + block.w)
                    || (ww.tank.xPos[0] + ww.tank.w / 2 >= block.x && ww.tank.xPos[0] + ww.tank.w / 2 <= block.x + block.w))) {
                timer.removeTimer(6);
                removeScreenObject(block);
                ww.tankConflict.play();
                break;
            }
        }

        for (Worm worm : WormL) {//Worm conflict with Bullet or Tank
            for (Bullet bullet : BulletL) {
                int px = bullet.x + bullet.w / 2;

                //---Worm-Bullet conflict---
                if (bullet.y <= worm.yP[0][0] && (px >= worm.xP[0][0] && px <= worm.xP[0][3])) {
                    WormAndCabinGenerator.killedWormsAndCabins++;
                    if (worm.cl== Color.red) {

                    } else{
                        score.addScore(20);
                        removeScreenObject(worm);
                        removeScreenObject(bullet);
                        ww.wormMoving.stop();
                        ww.wormShooting.stop();
                        ww.wormShooting.play();
                        break;
                    }
                    
                }
            }

            //---Worm-Tank conflict---
            if (worm.yP[0][0] >= ww.tank.yPos[4] && ((ww.tank.xPos[0] >= worm.xP[0][0] && ww.tank.xPos[0] <= worm.xP[0][3])
                    || (ww.tank.xPos[9] >= worm.xP[0][0] && ww.tank.xPos[9] <= worm.xP[0][3])
                    || (ww.tank.xPos[4] >= worm.xP[0][0] && ww.tank.xPos[4] <= worm.xP[0][3]))) {
                timer.removeTimer(10);
                removeScreenObject(worm);
                WormAndCabinGenerator.killedWormsAndCabins++;
                ww.wormMoving.stop();
                ww.tankConflict.play();

            }
        }

        for (Cabin cabin : CabinL) {//---cabin conflict with Bullet or Tank

            //---Cabin-Bullet conflict---
            for (Bullet bullet : BulletL) {
                if (bullet.y <= cabin.yPos[14] && (bullet.x >= cabin.xPos[1] && bullet.x <= cabin.xPos[11])) {
                    WormAndCabinGenerator.killedWormsAndCabins++;
                    removeScreenObject(bullet);
                    removeScreenObject(cabin);
                    ww.cabinExplode.play();

                    for (Worm worm : WormL) {//---remove worms if Shoot Cabin---
                        removeScreenObject(worm);
                        WormAndCabinGenerator.killedWormsAndCabins++;
                    }
                    ww.wormMoving.stop();
                    break;
                }
            }

            //---Cabin-Tank conflict---
            if (ww.tank.yPos[4] <= cabin.yPos[14] && ((ww.tank.xPos[0] >= cabin.xPos[1] && ww.tank.xPos[0] <= cabin.xPos[11])
                    || (ww.tank.xPos[9] >= cabin.xPos[1] && ww.tank.xPos[9] <= cabin.xPos[11])
                    || (ww.tank.xPos[4] >= cabin.xPos[1] && ww.tank.xPos[4] <= cabin.xPos[11]))) {
                WormAndCabinGenerator.killedWormsAndCabins++;
                removeScreenObject(cabin);
                timer.addTimer(5);
                ww.eatCabin.play();

            }
        }
    }

}
