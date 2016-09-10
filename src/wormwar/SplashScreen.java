package wormwar;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Yashar Nesabian
 */
public class SplashScreen extends JFrame implements ActionListener {

    AudioClip intro;
    WormWar ww;
    JButton about, save, load, resume, exit, newGame;
    JFrame frame;

    public SplashScreen(WormWar ww) {
        this.ww = ww;
        frame = new JFrame("Welcom to WormWar!");
        frame.setLocationRelativeTo(ww);
        frame.setLayout(new BorderLayout());
        newGame = new JButton("New Game");
        resume = new JButton("Resume");
        save = new JButton("Save");
        load = new JButton("Load");
        about = new JButton("About");
        exit = new JButton("Exit");
    }

    public void showSplash(boolean firstTime) {

        frame.setBounds(250, 120, 240, 330);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pageAxisPanel = new JPanel();
        pageAxisPanel.setBackground(Color.BLACK);
        pageAxisPanel.setLayout(null);

        newGame.setBounds(20, 20, 200, 30);
        pageAxisPanel.add(newGame);
        newGame.addActionListener(this);

        resume.setBounds(20, 70, 200, 30);
        pageAxisPanel.add(resume);
        resume.addActionListener(this);

        save.setBounds(20, 120, 200, 30);
        pageAxisPanel.add(save);
        save.addActionListener(this);

        load.setBounds(20, 170, 200, 30);
        pageAxisPanel.add(load);
        load.addActionListener(this);

        about.setBounds(20, 220, 200, 30);
        pageAxisPanel.add(about);
        about.addActionListener(this);

        exit.setBounds(20, 270, 200, 30);
        pageAxisPanel.add(exit);
        exit.addActionListener(this);

        frame.add(pageAxisPanel);
        frame.setVisible(true);

        if (firstTime) {
            resume.setEnabled(false);
            save.setEnabled(false);
        } else {
            resume.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            ww.BackToWormWar(0);
            frame.setVisible(false);
        } else if (e.getSource() == newGame) {
            ww.BackToWormWar(1);
            frame.setVisible(false);
        } else if (e.getSource() == resume) {
            ww.BackToWormWar(2);
            frame.setVisible(false);
        } else if (e.getSource() == save) {
            ww.BackToWormWar(3);
            frame.setVisible(false);
        } else if (e.getSource() == load) {
            ww.BackToWormWar(4);
            frame.setVisible(false);
        } else if (e.getSource() == about) {
            JOptionPane.showMessageDialog(rootPane, "Made By Yashar Nesabian\nFor Advanced Programming Project\nyasharnesabian@yahoo.com", "About", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("How Could You???");
        }

    }

}
