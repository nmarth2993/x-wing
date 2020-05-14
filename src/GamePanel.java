import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

    final static int HEALTHBAR_LENGTH = 200;

    private XWing game;
    private Player player;

    public GamePanel(XWing game) {
        this.game = game;
        player = game.getPlayer();
    }

    public void drawPlayerFighter(Graphics2D g2d) {
        g2d.drawImage(XWing.xWingSprite, (int) (player.getPosX() /* - XWing.xWingWidth / 2 */),
                (int) (player.getPosY() /* - XWing.xWingHeight / 2 */), null);
    }

    public void drawEnemyFighters(Graphics2D g2d) {
        for (EnemyShip ship : game.getEnemyShips()) {
            g2d.drawImage(ship.getSprite(), (int) ship.getPosX(), (int) ship.getPosY(), null);
        }
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        synchronized (game) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.fillRect(0, 0, XWing.SCREEN_WIDTH, XWing.BORDER);
            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString("Health: ", 3, fm.getHeight() + 3);
            g2d.fillRect(3 + fm.stringWidth("Health: X") + 5, fm.getHeight() / 2, HEALTHBAR_LENGTH, fm.getHeight() / 2);
            g2d.setColor(Color.RED);

            drawPlayerFighter(g2d);
            drawEnemyFighters(g2d);

            // g2d.fillOval(game.getPlayer().getPosX(), game.getPlayer().getPosY(),
            // XWing.xWingWidth, XWing.xWingHeight);
            g2d.setColor(Color.GREEN);
            // synchronized (game.getPlayer().getLasers()) {
            for (Laser laser : game.getPlayer().getLasers()) {
                g2d.drawImage(XWing.redLaserSprite, laser.getX() - XWing.laserWidth / 2,
                        laser.getY() - XWing.laserHeight / 2, null);
                // g2d.fillRect(laser.getX(), laser.getY(), Laser.WIDTH, Laser.HEIGHT);
            }
            // }

            drawPlayerHitbox(g2d);
            drawEnemyHitbox(g2d);
            drawLaserHitbox(g2d);

            if (game.getPlayer().isDead()) {
                g.setColor(new Color(0, 0, 0, 1));
                g.drawRect(0, 0, XWing.SCREEN_WIDTH, XWing.SCREEN_HEIGHT);
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 48));
                g.drawString("Game Over", XWing.SCREEN_WIDTH / 3, XWing.SCREEN_HEIGHT / 2);
            }
        }
    }
}