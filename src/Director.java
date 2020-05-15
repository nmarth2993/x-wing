import java.awt.geom.Path2D;
import java.util.Random;

public class Director {
    private XWing game;
    private Random r;
    private long startTime;
    private int level;

    public Director(XWing g) {
        startTime = System.currentTimeMillis();
        r = new Random();
        game = g;
        level = 0;
        new Thread(() -> {
            while (!game.getPlayer().isDead()) {

                long time = System.currentTimeMillis() - startTime;

                if (time / 1000 >= 30 && time / 1000 < 60) {
                    level = 1;
                } else if (time / 1000 >= 60 && time / 1000 < 90) {
                    level = 2;
                } else if (time / 1000 >= 90 && time / 1000 < 180) {
                    level = 3;
                } else if (time / 1000 > 360) {
                    level = 4;
                }
                try {
                    Thread.sleep(600 * (r.nextInt(5 - level) + 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (game.getEnemyShips()) {
                    EnemyShip tieFighter = new EnemyShip(XWing.SCREEN_WIDTH - 20,
                            XWing.BORDER + r.nextInt(XWing.SCREEN_HEIGHT), XWing.TIEwidth, XWing.TIEheight, 20,
                            EnemyShip.TIE, new Path2D.Double());
                    game.getEnemyShips().add(tieFighter);
                }
            }
        }).start();
        new Thread(() -> {
            while (!game.getPlayer().isDead()) {

                long time = System.currentTimeMillis() - startTime;
                if (time / 1000 >= 30 && time / 1000 < 60) {
                    level = 1;
                } else if (time / 1000 >= 60 && time / 1000 < 90) {
                    level = 2;
                } else if (time / 1000 >= 90 && time / 1000 < 180) {
                    level = 3;
                } else if (time / 1000 > 360) {
                    level = 4;
                }

                try {
                    Thread.sleep(2000 * (r.nextInt(7 - level) + 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (game.getEnemyShips()) {
                    EnemyShip interceptor = new EnemyShip(XWing.SCREEN_WIDTH - 20,
                            XWing.BORDER + 80 + r.nextInt(XWing.SCREEN_HEIGHT - 240), XWing.interceptorWidth,
                            XWing.interceptorHeight, 30, EnemyShip.INTERCEPTOR, new Path2D.Double());
                    game.getEnemyShips().add(interceptor);
                }
            }
        }).start();
        
        new Thread(() -> {
            while (!game.getPlayer().isDead()) {

                long time = System.currentTimeMillis() - startTime;
                if (time / 1000 >= 30 && time / 1000 < 60) {
                    level = 1;
                } else if (time / 1000 >= 60 && time / 1000 < 90) {
                    level = 2;
                } else if (time / 1000 >= 90 && time / 1000 < 180) {
                    level = 3;
                } else if (time / 1000 > 360) {
                    level = 4;
                }

                try {
                    Thread.sleep(1200 * (r.nextInt(7 - level) + 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (game.getEnemyShips()) {
                    EnemyShip striker = new EnemyShip(XWing.SCREEN_WIDTH - 20,
                            XWing.BORDER + 40+r.nextInt(XWing.SCREEN_HEIGHT - 200), XWing.strikerWidth,
                            XWing.strikerHeight, 50, EnemyShip.STRIKER, new Path2D.Double());
                    game.getEnemyShips().add(striker);
                }
            }
        }).start();
        
        new Thread(() -> {
            while (!game.getPlayer().isDead()) {

                long time = System.currentTimeMillis() - startTime;
                if (time / 1000 >= 30 && time / 1000 < 60) {
                    level = 1;
                } else if (time / 1000 >= 60 && time / 1000 < 90) {
                    level = 2;
                } else if (time / 1000 >= 90 && time / 1000 < 180) {
                    level = 3;
                } else if (time / 1000 > 360) {
                    level = 4;
                }

                try {
                    Thread.sleep(2000 * (r.nextInt(7 - level) + 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (game.getEnemyShips()) {
                	if((int)(Math.random()*(7-level)) == 2)
                	{
                		game.getEnemyShips().add(new EnemyShip(XWing.SCREEN_WIDTH + XWing.destroyerWidth+
                    	        1, XWing.SCREEN_HEIGHT / 4 + (int)(Math.random()*XWing.SCREEN_HEIGHT/2),
                    	        XWing.destroyerWidth, XWing.destroyerHeight, 500, EnemyShip.DESTROYER, new
                    	        Path2D.Double()));
                	}
                    
                }
            }
        }).start();
    }
}