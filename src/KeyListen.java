import java.awt.event.*;

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
            game.setPlayerY(game.getPlayerY() - XWing.DELTA);
        }
        if (downPressed || sPressed) {
            game.setPlayerY(game.getPlayerY() + XWing.DELTA);
        }
        if (leftPressed || aPressed) {
            game.setPlayerX(game.getPlayerX() - XWing.DELTA);
        }
        if (rightPressed || dPressed) {
            game.setPlayerX(game.getPlayerX() + XWing.DELTA);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            synchronized (game.getLasers()) {
                game.getLasers()
                        .add(new Laser(game.getPlayerX(), game.getPlayerY() + (XWing.DIAMETER / 2 - Laser.HEIGHT / 2)));
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