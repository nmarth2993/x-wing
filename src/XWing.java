import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class XWing {

    // final static int
    public static BufferedImage xWingSprite;
    public final static int xWingWidth;
    public final static int xWingHeight;
    public static BufferedImage redLaserSprite;
    public static BufferedImage greenLaserSprite;
    public final static int laserWidth;
    public final static int laserHeight;
    public static BufferedImage missileSprite;
    public final static int missileWidth;
    public final static int missileHeight;
    public static BufferedImage TIESprite;
    public final static int TIEwidth;
    public final static int TIEheight;

    static {

        try {
            xWingSprite = ImageIO.read(new File("img/Xwing.png"));
            redLaserSprite = ImageIO.read(new File("img/Laser.png"));
            greenLaserSprite = ImageIO.read(new File("img/greenLaser.png"));
            missileSprite = ImageIO.read(new File("img/Missile.png"));
            TIESprite = ImageIO.read(new File("img/TIE.png"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            int xWingW = xWingSprite.getWidth();
            int xWingH = xWingSprite.getHeight();
            int redLaserW = redLaserSprite.getWidth();
            int redLaserH = redLaserSprite.getHeight();
            int missileW = missileSprite.getWidth();
            int missileH = missileSprite.getHeight();
            int tieW = TIESprite.getWidth();
            int tieH = TIESprite.getHeight();

            xWingWidth = xWingW;
            xWingHeight = xWingH;
            laserWidth = redLaserW;
            laserHeight = redLaserH;
            missileWidth = missileW;
            missileHeight = missileH;
            TIEwidth = tieW;
            TIEheight = tieH;
        }
    }

    final static int SCREEN_WIDTH = 1600;
    final static int SCREEN_HEIGHT = 800;

    final static int DIAMETER = 25;
    // final static int DELTA = 2; // change in pixels per tick (speed)
    final static int TIMING = 10; // delay between repaints in ms
    final static int BORDER = 40; // space for info display

    JFrame frame;
    GamePanel panel;
    KeyListen k;

    Player player;
    ArrayList<Laser> removalList;
    ArrayList<EnemyShip> enemyShips; // stores an arraylist of enemy ships

    public XWing() {
        player = new Player();
        removalList = new ArrayList<Laser>();
        enemyShips = new ArrayList<EnemyShip>();

        frame = new JFrame();
        panel = new GamePanel(this);

        // panel.setBackground(new Color(13, 13, 13));

        k = new KeyListen(this);
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
                synchronized (player.getLasers()) {
                    if (removalList.size() > 0) {
                        // System.out.println("rem");
                        // for (Laser laser : removalList) {
                        // System.out.println("removing laser: " + laser);
                        // }
                    }
                    player.getLasers().removeAll(removalList);
                    removalList.removeAll(removalList);
                    for (Laser laser : player.getLasers()) {
                        if (laser.getX() > SCREEN_WIDTH + Laser.WIDTH || laser.getX() < 0 - Laser.WIDTH) {
                            removalList.add(laser);
                        }
                        laser.tick();
                    }
                }
                synchronized (enemyShips) {
                    for (EnemyShip ship : enemyShips) {
                        ship.step();
                    }
                }
                panel.repaint();
            }
        }).start();
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<EnemyShip> getEnemyShips() {
        return enemyShips;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new XWing();
        });
    }
}