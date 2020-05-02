import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;

public class XWing {
    final static int SCREEN_WIDTH = 1800;
    final static int SCREEN_HEIGHT = 800;

    final static int DIAMETER = 25;
    final static int DELTA = 2;
    final static int TIMING = 5;

    JFrame frame;
    GamePanel panel;

    ArrayList<Laser> lasers;
    ArrayList<Laser> removalList;

    int playerX;
    int playerY;

    public XWing() {
        lasers = new ArrayList<Laser>();
        removalList = new ArrayList<Laser>();
        playerX = playerY = 0;

        frame = new JFrame();
        panel = new GamePanel(this);

        panel.setBackground(new Color(13, 13, 13));

        KeyListen k = new KeyListen(this);
        frame.addKeyListener(k);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setVisible(true);
        panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        frame.pack();

        new Thread(() -> {
            for (;;) {
                try {
                    Thread.sleep(TIMING);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                k.tick();
                synchronized (lasers) {
                    if (removalList.size() > 0) {
                        // System.out.println("rem");
                        // for (Laser laser : removalList) {
                        // System.out.println("removing laser: " + laser);
                        // }
                    }
                    lasers.removeAll(removalList);
                    removalList.removeAll(removalList);
                    for (Laser laser : lasers) {
                        if (laser.getX() > SCREEN_WIDTH + Laser.WIDTH || laser.getX() < 0 - Laser.WIDTH) {
                            removalList.add(laser);
                        }
                        laser.tick();
                    }
                }
                panel.repaint();
            }
        }).start();
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new XWing();
        });
    }
}