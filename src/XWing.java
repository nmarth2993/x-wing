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
    final static int TIMING = 20; // delay between repaints in ms
    final static int BORDER = 40; // space for info display

    private boolean easyMode;
    
    KeyListen k;
    MouseListen m;
    private int mouseX;
    private int mouseY;

    JFrame frame;
    GamePanel panel;

    Player player;
    ArrayList<Laser> removalList;
    ArrayList<Laser> enemyLasers;
    ArrayList<EnemyShip> enemyShips; // stores an arraylist of enemy ships
    ArrayList<Ship> shipRemovalList;
    ArrayList<Laser> enemyRemovalList;
    ArrayList<Missile> enemyMissiles;
    ArrayList<Missile> enemyMissileRemoval;

    ArrayList<Star> stars;
    ArrayList<Star> removalStars;

    static final boolean drawStars = true;

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
    
    static BufferedImage turretSprite;
    static int turretWidth;
    static int turretHeight;

    public XWing() {
    	easyMode = false;
        initSprites();
        player = new Player();
        removalList = new ArrayList<Laser>();
        shipRemovalList = new ArrayList<Ship>();
        enemyShips = new ArrayList<EnemyShip>();

        enemyLasers = new ArrayList<Laser>();
        enemyRemovalList = new ArrayList<Laser>();
        enemyMissiles = new ArrayList<Missile>();
        enemyMissileRemoval = new ArrayList<Missile>();

        stars = new ArrayList<Star>();
        removalStars = new ArrayList<Star>();

        frame = new JFrame();
        panel = new GamePanel(this);

        // panel.setBackground(new Color(13, 13, 13));

        k = new KeyListen(this);
        frame.addKeyListener(k);
        m = new MouseListen(this);
        frame.addMouseMotionListener(m);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setVisible(true);
        panel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        frame.pack();

        initStar();

        new Thread(() -> {
            while (!player.isDead()) {
                try {
                    Thread.sleep(TIMING);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                player.shootLaser();
                player.updatePos(mouseX, mouseY);

                synchronized (enemyShips) {
                    for (EnemyShip enemyShip : enemyShips) {
                        enemyShip.shootLaser(new Point((int) player.getPosX(), (int) player.getPosY()), enemyLasers,
                                enemyMissiles);
                        if(enemyShip.getType() == enemyShip.DESTROYER)
                        {
                        	for(Turret t: enemyShip.getTurrets())
                        	{
                        		t.Tick(new Point((int)player.getPosX(), (int)player.getPosY()), enemyLasers);
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
                                }
                            }
                        }
                        synchronized (player) {
                            if (player.getHitbox().intersects(enemyShip.getHitbox()) && !easyMode) {
                                player.setHealth(0);
                            }
                        }
                    }
                }

                synchronized (player.getLasers()) {
                    player.getLasers().removeAll(removalList);
                    removalList.removeAll(removalList);
                    for (Laser laser : player.getLasers()) {
                        if (laser.getX() > SCREEN_WIDTH + Laser.WIDTH || laser.getX() < 0 - Laser.WIDTH) {
                            removalList.add(laser);
                        }
                        laser.tick();
                    }
                }

                synchronized (enemyLasers) {
                    enemyLasers.removeAll(enemyRemovalList);
                    for (Laser laser : enemyLasers) {

                        if (laser.getX() > SCREEN_WIDTH + Laser.WIDTH || laser.getX() < 0 - Laser.WIDTH) {
                            enemyRemovalList.add(laser);
                        }
                        if (laser.getHitbox().intersects(player.getHitbox()) && !easyMode) {
                            player.setHealth(player.getHealth() - 33);
                            enemyRemovalList.add(laser);
                        }
                        laser.tick();
                    }
                }

                synchronized (enemyMissiles) {
                    enemyMissiles.removeAll(enemyMissileRemoval);
                    for (Missile missile : enemyMissiles) {

                        if (missile.getX() > SCREEN_WIDTH + Laser.WIDTH || missile.getX() < 0 - Laser.WIDTH) {
                            enemyMissileRemoval.add(missile);
                        }
                        if (missile.getHitbox().intersects(player.getHitbox()) && !easyMode) {
                            player.setHealth(player.getHealth() - 33);
                            enemyMissileRemoval.add(missile);
                        }
                        missile.tick(new Point((int) player.getPosX(), (int) player.getPosY()));
                    }

                }

                synchronized (enemyShips) {
                    enemyShips.removeAll(shipRemovalList);
                    for (EnemyShip ship : enemyShips) {
                        ship.step();
                        // System.out.println(ship);
                    }
                }
                synchronized (stars) {
                    try {
                        stars.removeAll(removalStars);
                        for (Star s : stars)
                            s.move();
                    } catch (Exception e) {
                    }

                }
                panel.repaint();
            }
        }).start();

        if (drawStars)
            startStars();

        new Director(this);
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
            turretSprite = ImageIO.read(new File("img/Turret.png"));
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
        turretWidth = turretSprite.getWidth();
        turretHeight = turretSprite.getHeight();
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<EnemyShip> getEnemyShips() {
        return enemyShips;
    }

    public ArrayList<Laser> getEnemyLasers() {
        return enemyLasers;
    }

    public ArrayList<Missile> getEnemyMissiles() {
        return enemyMissiles;
    }

    public void startStars() {
        new Thread(() -> {
            while (!player.isDead()) {
                try {
                    Thread.sleep((int) (0.3 + (Math.random() * 300)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                makeStar();
            }
        }).start();
    }

    public void initStar() {
        for (int i = 0; i < 40; i++) {
            stars.add(new Star((int) (Math.random() * panel.getWidth()), (int) (Math.random() * panel.getHeight()),
                    (int) (2 + Math.random() * 4), 1 + (int) (Math.random() * 3)));
        }
    }

    public void makeStar() {
        stars.add(new Star(panel.getWidth(), (int) (Math.random() * panel.getHeight()), (int) (2 + Math.random() * 4),
                1 + (int) (Math.random() * 3)));
        try {
            for (Star s : stars) {
                if (s.offScreen())
                    removalStars.add(s);
            }
        } catch (Exception e) {
        }

    }

    public Star[] getStars() {
        return stars.toArray(new Star[stars.size()]);
    }

    public void setMouseCoords(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    public Point getMouseCoords() {
        return new Point(mouseX, mouseY);
    }
    
    public void setEasy()
    {
    	easyMode = true;
    }

    public void close()
    {
    	panel.closeFrame();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new XWing();
        });
    }
}