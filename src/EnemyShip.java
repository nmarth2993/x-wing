import java.util.Random;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

public class EnemyShip extends Ship {

    final static int INTERCEPT_DENOM_X = 5;
    final static int INTERCEPT_DENOM_Y = 1;

    final static int DESTROYER = 1;
    final static int INTERCEPTOR = 2;
    final static int STRIKER = 3;
    final static int TIE = 4;

    final static int DELTA = 2;
    final static Random r = new Random();
    final static int CHANCE = 500; // where the chance is 1/x the
                                   // probabilityof a match

    private PathIterator pathIterator; // interface
    private Path2D path; // interface

    private BufferedImage sprite;

    private int type;
    private int healthDrop; // drops could be health/shields/powerup
    private int powerupDrop;

    private int width;
    private int height;

    public EnemyShip(int x, int y, int width, int height, int health, int type, Path2D p) {
        super(x, y, width, height, health);
        this.type = type;
        setSprite();
        setBounds();

        // healthDrop = r.nextInt(CHANCE);
        // powerupDrop = r.nextInt(CHANCE);
        path = p;
        path.moveTo(x, y);
        setPath(x, y);
        pathIterator = path.getPathIterator(null);
    }

    public void setBounds() {
        switch (type) {
            case (DESTROYER):
                width = XWing.destroyerWidth;
                height = XWing.destroyerHeight;
                break;
            case (INTERCEPTOR):
                width = XWing.interceptorWidth;
                height = XWing.interceptorHeight;
                break;
            case (STRIKER):
                width = XWing.strikerWidth;
                height = XWing.strikerHeight;
                break;
            case (TIE):
                width = XWing.TIEwidth;
                height = XWing.TIEheight;
                break;
        }
    }

    public void setSprite() {
        switch (type) {
            case (DESTROYER):
                sprite = XWing.destroyerSprite;
                break;
            case (INTERCEPTOR):
                sprite = XWing.interceptorSprite;
                break;
            case (STRIKER):
                sprite = XWing.strikerSprite;
                break;
            case (TIE):
                sprite = XWing.TIESprite;
                break;
        }
    }

    public void setPath(int x, int y) {
        switch (type) {
            case (DESTROYER):
                // no path...
                break;
            case (INTERCEPTOR):
                double pathX = x;
                double pathY = y;
                while (pathX > -XWing.interceptorWidth) {
                    for (double j = 1; j < 21; j++) {
                        pathX -= (j / INTERCEPT_DENOM_X);
                        pathY += (j / INTERCEPT_DENOM_Y);
                        path.lineTo(pathX, pathY);
                        path.lineTo(pathX, pathY);
                    }
                    path.lineTo(pathX, pathY);
                    for (double j = 1; j < 21; j++) {
                        pathX -= (j / INTERCEPT_DENOM_X);
                        pathY -= (j / INTERCEPT_DENOM_Y);
                        path.lineTo(pathX, pathY);
                        path.lineTo(pathX, pathY);
                    }
                }
                break;
            case (STRIKER):
                width = XWing.strikerWidth;
                height = XWing.strikerHeight;
                break;
            case (TIE):
                for (int i = 0; i < XWing.SCREEN_WIDTH + 100; i++) {
                    path.lineTo(x - i, y);
                }
                break;
        }
    }

    @Override
    public void setPosX(double posX) {
        // if (posX + XWing.xWingWidth + 1 > XWing.SCREEN_WIDTH) {
        // posX = XWing.SCREEN_WIDTH - XWing.xWingWidth - 1;
        // }
        // if (posX <= 1) {
        // posX = 1;
        // }
        this.posX = posX;
        hitbox = new Rectangle2D.Double(getPosX(), getPosY(), width, height);
    }

    @Override
    public void setPosY(double posY) {
        // if (posY + XWing.xWingWidth + 1 > XWing.SCREEN_HEIGHT) {
        // posY = XWing.SCREEN_HEIGHT - XWing.xWingWidth - 1;
        // }
        // if (posY <= XWing.BORDER) {
        // posY = XWing.BORDER + 1;
        // }
        this.posY = posY;
        hitbox = new Rectangle2D.Double(getPosX(), getPosY(), width, height);
    }

    public int getType() {
        return type;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public boolean isHealthDrop() {
        return healthDrop == 47; // ;)
    }

    public boolean isPowerupDrop() {
        return powerupDrop == 47; // ;)
    }

    public Path2D getPath() {
        return path;
    }

    public void step() {
        if (pathIterator.isDone()) {
            // System.out.print("done at point: ");
            // System.out.println("(" + path.getCurrentPoint().getX() + ", " +
            // path.getCurrentPoint().getY() + ")");
            return;
        }
        pathIterator.next();
        double[] coords = new double[6];
        pathIterator.currentSegment(coords);
        path.moveTo(coords[0], coords[1]);
        // System.out.print("setting point to: ");
        // System.out.println("(" + path.getCurrentPoint().getX() + ", " +
        // path.getCurrentPoint().getY() + ")");
        setPosX((int) coords[0]);
        setPosY((int) coords[1]);
    }

    public String toString() {
        return (type == DESTROYER ? "Destroyer"
                : type == INTERCEPTOR ? "Interceptor" : type == STRIKER ? "Striker" : "Tie Fighter") + " at " + "("
                + getPosX() + ", " + getPosY() + ")";
    }

}