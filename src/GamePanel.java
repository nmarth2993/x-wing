import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {

    final static int HEALTHBAR_LENGTH = 200;

    XWing game;
    BufferedImage xWingSprite;
    int xWingWidth;
    int xWingHeight;

    BufferedImage redLaserSprite;
    BufferedImage greenLaserSprite;
    int laserWidth;
    int laserHeight;
    
    BufferedImage missileSprite;
    int missileWidth;
    int missileHeight;
    
    BufferedImage TIESprite;
    int TIEwidth;
    int TIEheight;
    
    public GamePanel(XWing game) {
        this.game = game;
        
        initSprites();
        
    }
    
    public void initSprites()
    {
    	try {
			xWingSprite = ImageIO.read(new File("img/Xwing.png"));
			redLaserSprite = ImageIO.read(new File("img/Laser.png"));
			greenLaserSprite = ImageIO.read(new File("img/greenLaser.png"));
			missileSprite = ImageIO.read(new File("img/Missile.png"));
			TIESprite = ImageIO.read(new File("img/TIE.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        xWingWidth = xWingSprite.getWidth();
        xWingHeight = xWingSprite.getHeight();
        laserWidth = redLaserSprite.getWidth();
        laserHeight = redLaserSprite.getHeight();
        missileWidth = missileSprite.getWidth();
        missileHeight = missileSprite.getHeight();
        TIEwidth = TIESprite.getWidth();
        TIEheight = TIESprite.getHeight();
        
    }
    
    public Graphics2D drawPlayerFighter(Graphics2D g2d)
    {
    	g2d.drawImage(xWingSprite, 
    				game.getPlayer().getPosX()-xWingWidth/2, game.getPlayer().getPosY()-xWingHeight/2, 
    				null);
    	return g2d;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRect(0, 0, XWing.SCREEN_WIDTH, XWing.BORDER);
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString("Health: ", 3, fm.getHeight() + 3);
        g2d.fillRect(3 + fm.stringWidth("Health: X") + 5, fm.getHeight() / 2, HEALTHBAR_LENGTH, fm.getHeight() / 2);
        g2d.setColor(Color.RED);
        
        g2d = drawPlayerFighter(g2d);
        
        g2d.fillOval(game.getPlayer().getPosX(), game.getPlayer().getPosY(), Player.WIDTH, Player.HEIGHT);
        g2d.setColor(Color.GREEN);
        synchronized (game.getPlayer().getLasers()) {
            for (Laser laser : game.getPlayer().getLasers()) {
            	g2d.drawImage(redLaserSprite, laser.getX()-laserWidth/2, laser.getY()-laserHeight/2, null);
                //g2d.fillRect(laser.getX(), laser.getY(), Laser.WIDTH, Laser.HEIGHT);
            }
        }
        g2d.setColor(Color.BLUE);
        synchronized (game.getEnemyShips()) {
            for (EnemyShip ship : game.getEnemyShips()) {
                g2d.fill(ship.getHitbox());
            }
        }
    }
}