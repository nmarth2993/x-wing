import java.util.ArrayList;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JFrame;

public class XWing {
    final static int SCREEN_WIDTH = 1600;
    final static int SCREEN_HEIGHT = 800;

    final static int DIAMETER = 25;
    // final static int DELTA = 2; // change in pixels per tick (speed)
    final static int TIMING = 50; // delay between repaints in ms
    final static int BORDER = 40; // space for info display

    MouseListen m;
    private int mouseX;
    private int mouseY;

    JFrame frame;
    GamePanel panel;
    KeyListen k;

    Player player;
    ArrayList<Laser> removalList;
    ArrayList<EnemyShip> enemyShips; // stores an arraylist of enemy ships
    ArrayList<Ship> shipRemovalList;

    static BufferedImage xWingSprite;
    static int xWingWidth;
    static int xWingHeight;

    static BufferedImage redLaserSprite;
    static BufferedImage greenLaserSprite;
    static int laserWidth;
    static int laserHeight;

    static BufferedImage missileSprite;
    static int missileWidth;
    static int missileHeight;

    static BufferedImage TIESprite;
    static int TIEwidth;
    static int TIEheight;

    static BufferedImage strikerSprite;
    static int strikerWidth;
    static int strikerHeight;

    static BufferedImage interceptorSprite;
    static int interceptorWidth;
    static int interceptorHeight;

    static BufferedImage destroyerSprite;
    static int destroyerWidth;
    static int destroyerHeight;

    public XWing() {
        initSprites();
        player = new Player();
        removalList = new ArrayList<Laser>();
        shipRemovalList = new ArrayList<Ship>();
        enemyShips = new ArrayList<EnemyShip>();

        frame = new JFrame();
        panel = new GamePanel(this);

        // panel.setBackground(new Color(13, 13, 13));

        // k = new KeyListen(this);
        // frame.addKeyListener(k);
        m = new MouseListen(this);
        frame.addMouseMotionListener(m);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setVisible(true);
        panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        frame.pack();

        new Thread(() -> {
            while (!player.isDead()) {
                try {
                    Thread.sleep(TIMING);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // k.tick();
                player.shootLaser();
                player.updatePos(mouseX, mouseY);
                synchronized (enemyShips) {
                    for (EnemyShip enemyShip : enemyShips) {
                        synchronized (enemyShip.getLasers()) {
                            for (Laser enemyLaser : enemyShip.getLasers()) {
                                if (enemyLaser.getHitbox().intersects(player.getHitbox())) {
                                    player.setHealth(player.getHealth() - 33);
                                    // System.out.println("COLLISION");
                                }
                            }
                        }
                        synchronized (player.getLasers()) {
                            for (Laser laser : player.getLasers()) {
                                if (laser.getHitbox().intersects(enemyShip.getHitbox())) {
                                    enemyShip.setHealth(enemyShip.getHealth() - Player.LASER_DAMAGE);
                                    removalList.add(laser);
                                    if (enemyShip.isDead()) {
                                        shipRemovalList.add(enemyShip);
                                    }
                                    // System.out.println("COLLISION");
                                }
                            }
                        }
                        synchronized (player) {
                            if (player.getHitbox().intersects(enemyShip.getHitbox())) {
                                player.setHealth(0);
                                // System.out.println("player is dead? " + player.isDead());
                            }
                        }
                    }
                }
                // after collisions, check player alive
                // if (player.isDead()) {
                // panel.repaint();
                // XXX: define custom panel
                // JPanel p = new JPanel();
                // JFrame f = new JFrame();
                // f.setContentPane(p);
                // f.setPreferredSize(new Dimension(500, 500));
                // f.setVisible(true);
                // f.pack();
                // }

                synchronized (player.getLasers()) {
                    // if (removalList.size() > 0) {
                    // System.out.println("rem");
                    // for (Laser laser : removalList) {
                    // System.out.println("removing laser: " + laser);
                    // }
                    // }
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
                    enemyShips.removeAll(shipRemovalList);
                    for (EnemyShip ship : enemyShips) {
                        ship.step();
                        // System.out.println(ship);
                    }
                }
                panel.repaint();
            }
        }).start();
    }

    public void initSprites() {
        try {
            xWingSprite = ImageIO.read(new File("img/Xwing.png"));
            redLaserSprite = ImageIO.read(new File("img/Laser.png"));
            greenLaserSprite = ImageIO.read(new File("img/greenLaser.png"));
            missileSprite = ImageIO.read(new File("img/Missile.png"));
            TIESprite = ImageIO.read(new File("img/TIE.png"));
            interceptorSprite = ImageIO.read(new File("img/TIE Interceptor.png"));
            strikerSprite = ImageIO.read(new File("img/TIE Striker.png"));
            destroyerSprite = ImageIO.read(new File("img/Destroyer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        TIEwidth = TIESprite.getWidth();
        TIEheight = TIESprite.getHeight();
        xWingWidth = xWingSprite.getWidth();
        xWingHeight = xWingSprite.getHeight();
        laserWidth = redLaserSprite.getWidth();
        laserHeight = redLaserSprite.getHeight();
        missileWidth = missileSprite.getWidth();
        missileHeight = missileSprite.getHeight();
        strikerWidth = strikerSprite.getWidth();
        strikerHeight = strikerSprite.getHeight();
        destroyerWidth = destroyerSprite.getWidth();
        destroyerHeight = destroyerSprite.getHeight();
        interceptorWidth = interceptorSprite.getWidth();
        interceptorHeight = interceptorSprite.getHeight();
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<EnemyShip> getEnemyShips() {
        return enemyShips;
    }

    public void setMouseCoords(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    public Point getMouseCoords() {
        return new Point(mouseX, mouseY);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new XWing();
        });
    }
}