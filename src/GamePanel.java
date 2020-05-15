import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.glass.ui.Window;

public class GamePanel extends JPanel {

    final static int HEALTHBAR_LENGTH = 200;

    private XWing game;
    private Player player;

    public GamePanel(XWing game) {
        this.game = game;
        player = game.getPlayer();
        this.setBackground(Color.black);

    }

    public void drawPlayerFighter(Graphics2D g2d) {
        g2d.drawImage(XWing.xWingSprite, (int) (player.getPosX() - XWing.xWingWidth / 2),
                (int) (player.getPosY() - XWing.xWingHeight / 2), null);
    }

    public void drawEnemyFighters(Graphics2D g2d) {
    	try {
    		for (EnemyShip ship : game.getEnemyShips()) {
                if(ship.getType() == ship.DESTROYER)
                {
                	g2d.drawImage(ship.getSprite(), (int) ship.getPosX()-game.destroyerWidth/2, 
                			(int) ship.getPosY()-game.destroyerHeight/2, null);
                	for(Turret t : ship.getTurrets())
                	{
                		g2d.setColor(Color.red);
                		g2d.fillOval(t.getX(), t.getY(), 5, 5);
                		AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(t.getDirection()),
                                XWing.turretWidth / 2, XWing.turretWidth / 2);
                        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                        g2d.drawImage(op.filter(XWing.turretSprite, null), t.getX() - XWing.turretWidth / 2,
                                t.getY() - XWing.turretWidth / 2, null);
                	}
                } else {
                	g2d.drawImage(ship.getSprite(), (int) ship.getPosX(), (int) ship.getPosY(), null);
                }
            }
    	} catch (Exception e)
    	{ 	}
        
    }

    public void drawPlayerHitbox(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.draw(game.getPlayer().getHitbox());
    }

    public void drawEnemyHitbox(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        for (EnemyShip ship : game.getEnemyShips()) {
            g2d.draw(ship.getHitbox());
            // System.out.println("(" + ship.getHitbox().getX() + ", " +
            // ship.getHitbox().getY() + ")");
        }
    }

    public void drawLaserHitbox(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        for (Laser laser : game.getPlayer().getLasers()) {
            g2d.draw(laser.getHitbox());
        }
    }

    public void drawStars(Graphics g, Star[] stars) {
        for (Star s : stars) {
            g.setColor(Color.white);
            g.fillOval(s.getX(), s.getY(), s.getSize(), s.getSize());
        }

    }

    public void drawGui(Graphics2D g2d) {
        // top bar
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, XWing.SCREEN_WIDTH, XWing.BORDER);

        // health bar
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString("Health: ", 3, fm.getHeight() + 3);

        int topCorner = 3 + fm.stringWidth("Health: X") + 5;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(topCorner, fm.getHeight() / 2, HEALTHBAR_LENGTH, fm.getHeight() / 2);

        g2d.setColor(Color.GREEN);
        double playerHealth = (double) game.getPlayer().getHealth() / game.getPlayer().getMaxHealth();
        g2d.fillRect(topCorner, fm.getHeight() / 2, (int) ((double) HEALTHBAR_LENGTH * playerHealth),
                fm.getHeight() / 2);

        int divisions = 8;
        int spacing = HEALTHBAR_LENGTH / (divisions);
        g2d.setColor(Color.DARK_GRAY);
        for (int i = 1; i < divisions; i++) {
            g2d.drawLine(topCorner + i * spacing, fm.getHeight() / 2, topCorner + i * spacing, fm.getHeight());
        }
    }

    public void drawLasers(Graphics2D g2d) {
        ArrayList<Laser> playerLasers = new ArrayList<Laser>(game.getPlayer().getLasers());
        for (Laser laser : playerLasers) {
            if (laser.getDirection() == 0) {
                g2d.drawImage(XWing.redLaserSprite, laser.getX() - XWing.laserWidth / 2,
                        laser.getY() - XWing.laserHeight / 2, null);
            } else {
                AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(laser.getDirection()),
                        XWing.laserWidth / 2, XWing.laserHeight / 2);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                g2d.drawImage(op.filter(XWing.redLaserSprite, null), laser.getX() - XWing.laserWidth / 2,
                        laser.getY() - XWing.laserHeight / 2, null);
            }
        }
        ArrayList<Laser> enemyLasers = new ArrayList<Laser>(game.getEnemyLasers());
        for (Laser laser : enemyLasers) {
            AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(laser.getDirection()),
                    XWing.laserWidth / 2, XWing.laserHeight / 2);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            g2d.drawImage(op.filter(XWing.greenLaserSprite, null), laser.getX() - XWing.laserWidth / 2,
                    laser.getY() - XWing.laserHeight / 2, null);
        }
        ArrayList<Missile> enemyMissiles = new ArrayList<Missile>(game.getEnemyMissiles());
        for (Missile missile : enemyMissiles) {
            AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(missile.getDirection()),
                    XWing.missileWidth / 2, XWing.missileHeight / 2);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            g2d.drawImage(op.filter(XWing.missileSprite, null), missile.getX() - XWing.missileWidth / 2,
                    missile.getY() - XWing.missileHeight / 2, null);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        synchronized (game) {
            Graphics2D g2d = (Graphics2D) g;

            drawStars(g, game.getStars());
            drawPlayerFighter(g2d);
            drawEnemyFighters(g2d);
            drawLasers(g2d);
            drawGui(g2d);

            if (game.getPlayer().isDead()) {
                g.setColor(new Color(0, 0, 0, 1));
                g.drawRect(0, 0, XWing.SCREEN_WIDTH, XWing.SCREEN_HEIGHT);
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 48));
                g.drawString("Game Over", XWing.SCREEN_WIDTH / 3, XWing.SCREEN_HEIGHT / 2);

                g.setFont(new Font("Arial", Font.PLAIN, 24));
                g.drawString("Press space to play again", XWing.SCREEN_WIDTH / 3, XWing.SCREEN_HEIGHT / 2+60);
                
            }
        }
    }
    
    public void closeFrame()
    {
    	  java.awt.Window win = SwingUtilities.getWindowAncestor(this);
    	  win.dispose();
    }

}