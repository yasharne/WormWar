package wormwar;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JApplet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author yashar nesabian
 */
public class WormWar extends JApplet implements Runnable, KeyListener, MouseMotionListener, MouseListener, Serializable {

    public static final int SCREEN_WIDTH = 630;
    public static final int SCREEN_HEIGHT = 400;
    public static final int OBJECTS_SIZE = 66;
    public static int NEXT_POSE;
    public static boolean isRunnig = true;
    public static boolean firstTimeRunning = true;
    public static boolean loadedFromFile = false;
    private boolean right = false;
    private boolean left = false;
    private boolean fireB = false;
    public static boolean A = false;

    ScreenManager sm;
    Thread refresher;
    Thread timerRefresher;
    BlockGenerator bg;
    WormAndCabinGenerator wg;
    Score score;
    Timer timer;
    Tank tank;
    SplashScreen ss;

    public static RandomAccessFile file;

    AudioClip fire;
    AudioClip wormMoving;
    AudioClip cabinExplode;
    AudioClip newGame;
    AudioClip eatCabin;
    AudioClip shootBlock;
    AudioClip wormShooting;
    AudioClip tankConflict;

    private boolean firstMouseMove = true;
    private Point oldMouseMove;

    @Override
    public void init() {
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setFocusable(true);
        this.requestFocus();
        this.setBackground(Color.BLACK);

        Toolkit toolkit = Toolkit.getDefaultToolkit();//---Remove Cursor---
        Point hotSpot = new Point(0, 0);
        BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
        Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "InvisibleCursor");
        setCursor(invisibleCursor);

        sm = new ScreenManager(this);
        refresher = new Thread(this);
        bg = new BlockGenerator(sm);
        wg = new WormAndCabinGenerator(sm);
        ss = new SplashScreen(this);

        if (!loadedFromFile) {
            score = new Score(getWidth() / 2 - 5, 20, 80, 15, Color.red);
            sm.addScreenObject(score);

            timer = new Timer(getWidth() / 2 - 10, 40, 80, 15, Color.red);
            sm.addScreenObject(timer);

        }
        tank = new Tank(302, getHeight() - 14, 25, 14, Color.GREEN, sm);
        sm.addScreenObject(tank);
        timerRefresher = new Thread(timer);

        fire = getAudioClip(getCodeBase(), "Data/Sounds/fire.wav");
        wormMoving = getAudioClip(getCodeBase(), "Data/Sounds/movingworms.wav");
        cabinExplode = getAudioClip(getCodeBase(), "Data/Sounds/cabinexplode.wav");
        newGame = getAudioClip(getCodeBase(), "Data/Sounds/start.wav");
        eatCabin = getAudioClip(getCodeBase(), "Data/Sounds/eatcabin.wav");
        shootBlock = getAudioClip(getCodeBase(), "Data/Sounds/shootblock.wav");
        wormShooting = getAudioClip(getCodeBase(), "Data/Sounds/wormshooting.wav");
        tankConflict = getAudioClip(getCodeBase(), "Data/Sounds/tankconf.wav");

    }

    @Override
    public void start() {
        if (firstTimeRunning) {//---showFirstSplashScreen---
            ss.showSplash(true);
            isRunnig = false;
            firstTimeRunning = false;
        }
        refresher.start();
        bg.start();
        wg.start();
        timerRefresher.start();
    }

    public void BackToWormWar(int status) {
        isRunnig = true;
        switch (status) {
            case 0://---Exit Game---
                System.exit(0);
                break;
            case 1://---new Game---
                this.removeKeyListener(this);
                this.removeMouseListener(this);
                this.removeMouseMotionListener(this);
                WormAndCabinGenerator.Level = 1;
                WormAndCabinGenerator.killedWormsAndCabins = 0;
                init();
                start();
                isRunnig = true;
                newGame.play();
                break;
            case 2://---resume Game---
                isRunnig = true;
                bg.resume();
                Block.dy = 1;
                wg.resume();
                wormMoving.play();
                break;
            case 3://---save Game---
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fc.showOpenDialog(this);
                if (result == JFileChooser.CANCEL_OPTION) {
                    return;
                }
                File fileName = fc.getSelectedFile();
                if (fileName == null
                        || fileName.getName().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid File Name", "Invalid File Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                try {
                    file = new RandomAccessFile(fileName, "rw");
                    NEXT_POSE = (int) (file.length() / OBJECTS_SIZE);
                } catch (FileNotFoundException ex) {
                    System.out.println(ex);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                saveToFile(file, sm.ol);
                isRunnig = true;
                bg.resume();
                Block.dy = 1;
                wg.resume();
                break;
            case 4://---load Game---
                JFileChooser lfc = new JFileChooser();
                lfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int lresult = lfc.showOpenDialog(this);
                if (lresult == JFileChooser.CANCEL_OPTION) {
                    return;
                }
                File loadFile = lfc.getSelectedFile();
                if (loadFile == null
                        || loadFile.getName().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid File Name", "Invalid File Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                loadFromFile(file, sm.ol, loadFile);
                break;
        }
    }

    @Override
    public void paint(Graphics g) {
        sm.draw(g);
    }

    @Override
    public void run() {
        while (true) {
            if (WormWar.isRunnig) {
                if (Timer.timer <= 0) {
                    this.removeKeyListener(this);
                }
                if (right) {
                    tank.right();
                }
                if (left) {
                    tank.left();
                }
                repaint();
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_SPACE:
                fireB = true;
                fire.play();
                break;
            case KeyEvent.VK_ESCAPE:
                if (isRunnig) {
                    ss.showSplash(false);
                    bg.suspend();
                    Block.dy = 0;
                    wg.suspend();
                    wormMoving.stop();
                    isRunnig = false;
                }
                break;
            case KeyEvent.VK_S:
                try {
                    writeShapesToFile();
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                break;
            case KeyEvent.VK_L:
                try {
                    readShapesFromFile();
                } catch (ClassNotFoundException ex) {
                } catch (IOException ex) {
                }
                break;
            case KeyEvent.VK_Q:
                A = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_SPACE:
                if (fireB) {
                    tank.fire();
                    fireB = false;
                    fire.play();
                }
                
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (firstMouseMove) {
            firstMouseMove = false;
            oldMouseMove = e.getPoint();
            return;
        }
        if (e.getX() >= oldMouseMove.x) {
            tank.right();
        } else {
            tank.left();
        }
        oldMouseMove = e.getPoint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        tank.fire();
        fire.play();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    //---save Game---
    private void saveToFile(RandomAccessFile file, ArrayList ol) {
        try {
            for (Object object : ol) {
                file.seek(NEXT_POSE * OBJECTS_SIZE);
                file.writeChars(object.toString());
                NEXT_POSE++;
                System.out.println(NEXT_POSE);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //---load Game---
    public void loadFromFile(RandomAccessFile file, ArrayList ol, File fileName) {
        try {
            file = new RandomAccessFile(fileName, "rw");
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        loadedFromFile = true;
        init();
        start();
        try {
            while (true) {
                char className[] = new char[6];
                for (int i = 0; i < className.length; i++) {
                    className[i] = file.readChar();
                }
                System.out.println(className);
                int x = 0;
                for (int j = 0; j < 3; j++) {
                    char c = file.readChar();
                    int temp = Character.getNumericValue(c);
                    x = 10 * x + temp;
                }
                System.out.println("x = " + x);
                //-----------------------------------------
                int y = 0;
                for (int j = 0; j < 3; j++) {
                    char c = file.readChar();
                    int temp = Character.getNumericValue(c);
                    y = 10 * y + temp;
                }
                System.out.println("y = " + y);
                //--------------------------------------------
                int w = 0;
                for (int j = 0; j < 3; j++) {
                    char c = file.readChar();
                    int temp = Character.getNumericValue(c);
                    w = 10 * w + temp;
                }
                System.out.println("w = " + w);
                //---------------------------------------------
                int h = 0;
                for (int j = 0; j < 3; j++) {
                    char c = file.readChar();
                    int temp = Character.getNumericValue(c);
                    h = 10 * h + temp;
                }
                System.out.println("h = " + h);
                //----------------------------------------------
                int dx = 0;
                for (int j = 0; j < 3; j++) {
                    char c = file.readChar();
                    int temp = Character.getNumericValue(c);
                    dx = 10 * dx + temp;
                }
                System.out.println("dx = " + dx);
                //-----------------------------------------------
                int dy = 0;
                for (int j = 0; j < 3; j++) {
                    char c = file.readChar();
                    int temp = Character.getNumericValue(c);
                    dy = 10 * dy + temp;
                }
                System.out.println("dy = " + dy);
                //-----------------------------------------------
                int clx = 0;
                for (int j = 0; j < 3; j++) {
                    char c = file.readChar();
                    int temp = Character.getNumericValue(c);
                    clx = 10 * clx + temp;
                }
                System.out.println("clx = " + clx);
                //-----------------------------------------------
                int cly = 0;
                for (int j = 0; j < 3; j++) {
                    char c = file.readChar();
                    int temp = Character.getNumericValue(c);
                    cly = 10 * cly + temp;
                }
                System.out.println("cly = " + cly);
                //-----------------------------------------------
                int clz = 0;
                for (int j = 0; j < 3; j++) {
                    char c = file.readChar();
                    int temp = Character.getNumericValue(c);
                    clz = 10 * clz + temp;
                }
                System.out.println("clz = " + clz);
                //----------------------------------------------
                Color cl = new Color(clx, cly, clz);
                if (className[0] == 'b') {//---block or bullet---
                    if (className[1] == 'l') {//---it is BLOCK!---
                        Block block = new Block(x, y, w, h, cl, sm);
                        sm.addScreenObject(block);
                        Thread t = new Thread(block);
                        t.start();

                    } else {//---it is definitely Bullet!---
                        Bullet bullet = new Bullet(x, y, w, h, cl, sm);
                        sm.addScreenObject(bullet);
                        Thread t = new Thread(bullet);
                        t.start();
                    }

                } else if (className[0] == 'c') {//---Cabin---
                    Cabin cabin = new Cabin(x, y, w, h, cl, sm);
                    sm.addScreenObject(cabin);
                    Thread t = new Thread(cabin);
                    t.start();
                } else if (className[0] == 's') {//---Score---
                    Score score = new Score(x, y, w, h, cl);
                    score.addScore(dx);
                    sm.addScreenObject(score);
                } else if (className[0] == 't') {//---Tank or Timer---
                    if (className[1] == 'a') {//---Tank---
                        //Tank tank = new Tank(x, y, w, h, cl, sm);
                        //sm.addScreenObject(tank);
                    } else {//---Timer---
                        Timer timer = new Timer(x, y, w, h, cl);
                        timer.setTimer(dx);
                        sm.addScreenObject(timer);
                        Thread t = new Thread(timer);
                        t.start();
                    }
                } else {//---Worm---
                    Worm worm = new Worm(x, y, w, h, cl, sm);
                    sm.addScreenObject(worm);
                    Thread t = new Thread(worm);
                    t.start();
                }
            }

        } catch (IOException ex) {
            System.out.println("Load Successful!");
        }
    }

    public void writeShapesToFile() throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream("shapes.ser");
        ObjectOutputStream objOutputStream = new ObjectOutputStream(fos);
        for (Object obj : sm.ol) {

            objOutputStream.writeObject(obj);
            objOutputStream.flush();
        }
        objOutputStream.close();
    }

    private void readShapesFromFile() throws ClassNotFoundException, IOException {
        FileInputStream fis = new FileInputStream("shapes.ser");
        ObjectInputStream obj = new ObjectInputStream(fis);
        sm.ol.clear();
        try {
            while (fis.available() != -1) {
                if (obj.readObject() instanceof Tank) {
                    ScreenObject screenobject = (ScreenObject) obj.readObject();
                    sm.ol.add(screenobject);
                } else if (obj.readObject() instanceof Timer) {
                    ScreenObject screenobject = (ScreenObject) obj.readObject();
                    sm.ol.add(screenobject);
                } else if (obj.readObject() instanceof Score) {
                    ScreenObject screenobject = (ScreenObject) obj.readObject();
                    sm.ol.add(screenobject);
                } else {
                    ScreenObject screenobject = (ScreenObject) obj.readObject();
                    sm.ol.add(screenobject);
                    Thread t = new Thread((Runnable) screenobject);
                    t.start();
                }
            }
        } catch (EOFException ex) {
        }
        obj.close();
    }

}
