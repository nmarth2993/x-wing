import java.util.Random;
import java.awt.Rectangle;
import java.awt.geom.*;

public class EnemyShip extends Ship {

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

    private int type;
    private int healthDrop; // drops could be health/shields/powerup
    private int powerupDrop;

    private int width;
    private int height;

    public EnemyShip(int x, int y, int width, int height, int health, int type, Path2D p) {
        super(x, y, width, height, health);
        this.type = type;
        setBounds();

        // healthDrop = r.nextInt(CHANCE);
        // powerupDrop = r.nextInt(CHANCE);
        path = p;
        pathIterator = path.getPathIterator(new AffineTransform());

        // create test path:
        path.moveTo(x, y);
        for (int i = 0; i < 20000; i++) {
            path.lineTo(r.nextInt(XWing.SCREEN_WIDTH / 2), r.nextInt(XWing.SCREEN_HEIGHT / 2));
        }
        path.lineTo(XWing.SCREEN_WIDTH / 2, XWing.SCREEN_HEIGHT / 2);
        path.closePath();
        pathIterator = path.getPathIterator(new AffineTransform());
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
            default:
                width = XWing.TIEwidth;
                height = XWing.TIEheight;
        }
    }

    @Override
    public void setPosX(int posX) {
        if (posX + XWing.xWingWidth + 1 > XWing.SCREEN_WIDTH) {
            posX = XWing.SCREEN_WIDTH - XWing.xWingWidth - 1;
        }
        if (posX <= 1) {
            posX = 1;
        }
        this.posX = posX;
        hitbox = new Rectangle(getPosX(), getPosY(), width, height);
    }

    @Override
    public void setPosY(int posY) {
        if (posY + XWing.xWingWidth + 1 > XWing.SCREEN_HEIGHT) {
            posY = XWing.SCREEN_HEIGHT - XWing.xWingWidth - 1;
        }
        if (posY <= XWing.BORDER) {
            posY = XWing.BORDER + 1;
        }
        this.posY = posY;
        hitbox = new Rectangle(getPosX(), getPosY(), width, height);
    }

    public int getType() {
        return type;
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
        // System.out.print("setting point to: ");
        // System.out.println("(" + path.getCurrentPoint().getX() + ", " +
        // path.getCurrentPoint().getY() + ")");
        setPosX((int) path.getCurrentPoint().getX());
        setPosY((int) path.getCurrentPoint().getY());
    }

}