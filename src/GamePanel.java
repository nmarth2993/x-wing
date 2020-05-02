import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.*;

public class GamePanel extends JPanel {

    final static int HEALTHBAR_LENGTH = 200;

    XWing game;

    public GamePanel(XWing game) {
        this.game = game;
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
        g2d.fillOval(game.getPlayer().getPosX(), game.getPlayer().getPosY(), Player.WIDTH, Player.HEIGHT);
        g2d.setColor(Color.GREEN);
        synchronized (game.getPlayer().getLasers()) {
            for (Laser laser : game.getPlayer().getLasers()) {
                g2d.fillRect(laser.getX(), laser.getY(), Laser.WIDTH, Laser.HEIGHT);
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