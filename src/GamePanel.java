import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {

    XWing game;

    public GamePanel(XWing game) {
        this.game = game;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillOval(game.getPlayerX(), game.getPlayerY(), Player.WIDTH, Player.HEIGHT);
        g2d.setColor(Color.GREEN);
        synchronized (game.getLasers()) {
            for (Laser laser : game.getLasers()) {
                g2d.fillRect(laser.getX(), laser.getY(), Laser.WIDTH, Laser.HEIGHT);
            }
        }
    }
}