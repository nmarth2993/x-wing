import java.awt.event.*;
import java.awt.geom.Path2D.*;
import java.awt.geom.Path2D;

public class KeyListen implements KeyListener {

    XWing game;

    boolean upPressed;
    boolean downPressed;
    boolean leftPressed;
    boolean rightPressed;

    boolean wPressed;
    boolean aPressed;
    boolean sPressed;
    boolean dPressed;

    public KeyListen(XWing game) {
        this.game = game;

        upPressed = downPressed = leftPressed = rightPressed = false;
        wPressed = aPressed = sPressed = dPressed = false;
    }

    public void tick() {
        if (upPressed || wPressed) {
            game.getPlayer().setPosY(game.getPlayer().getPosY() - game.getPlayer().getDelta()); // XXX
        }
        if (downPressed || sPressed) {
            game.getPlayer().setPosY(game.getPlayer().getPosY() + game.getPlayer().getDelta());
        }
        if (leftPressed || aPressed) {
            game.getPlayer().setPosX(game.getPlayer().getPosX() - game.getPlayer().getDelta());
        }
        if (rightPressed || dPressed) {
            game.getPlayer().setPosX(game.getPlayer().getPosX() + game.getPlayer().getDelta());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            synchronized (game.getPlayer().getLasers()) {
                game.getPlayer().getLasers()
                        .add(new Laser(game.getPlayer().getPosX() + 20, game.getPlayer().getPosY()));
            }
            synchronized (game.getEnemyShips()) {
                game.getEnemyShips().add(new EnemyShip(XWing.SCREEN_WIDTH - 100, XWing.SCREEN_HEIGHT / 2, 30, 30, 50, 1,
                        new Path2D.Double()));
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            wPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            aPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            sPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            dPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            wPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            aPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            sPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            dPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyPressed(e);
    }

}