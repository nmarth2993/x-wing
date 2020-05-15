import java.awt.event.*;
import java.awt.geom.Path2D.*;

import javax.swing.SwingUtilities;

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

    @Override
    public void keyPressed(KeyEvent e) {
    
    	
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        	if(game.getPlayer().isDead())
        	{
        		SwingUtilities.invokeLater(() -> {
                    new XWing();
                });
        		game.close();
        	}
        }
        	
        	/*
	        synchronized (game.getEnemyShips()) {
	        
	        game.getEnemyShips().add(new EnemyShip(XWing.SCREEN_WIDTH - XWing.TIEwidth -
	        1, XWing.SCREEN_HEIGHT / 2,
	        XWing.TIEwidth, XWing.TIEheight, Player.LASER_DAMAGE, EnemyShip.TIE, new
	        Path2D.Double()));
	        
	        game.getEnemyShips()
	        .add(new EnemyShip(XWing.SCREEN_WIDTH - XWing.strikerWidth - 1,
	        XWing.SCREEN_HEIGHT / 2,
	        XWing.strikerWidth, XWing.strikerHeight, Player.LASER_DAMAGE*3,
	        EnemyShip.STRIKER, new Path2D.Double()));
	        
	        game.getEnemyShips()
	        .add(new EnemyShip(XWing.SCREEN_WIDTH - XWing.strikerWidth - 1,
	        XWing.SCREEN_HEIGHT / 2,
	        XWing.interceptorWidth, XWing.interceptorHeight, Player.LASER_DAMAGE*3,
	        EnemyShip.INTERCEPTOR, new Path2D.Double()));
			
	        
	        }
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = true;
            
            synchronized (game.getEnemyShips()) {
    	        
    	        game.getEnemyShips().add(new EnemyShip(XWing.SCREEN_WIDTH - XWing.TIEwidth -
    	        1, XWing.SCREEN_HEIGHT / 2,
    	        XWing.destroyerWidth, XWing.destroyerHeight, Player.LASER_DAMAGE*500, EnemyShip.DESTROYER, new
    	        Path2D.Double()));
    			
    	        
    	        }
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
        */
        if (e.getKeyCode() == KeyEvent.VK_E) {
        	game.setEasy();
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