import java.util.Random;
import java.awt.geom.*;

public class EnemyShip extends Ship {
    final static int DELTA = 2;
    final static Random r = new Random();
    final static int CHANCE = 500; // where the chance is 1/x the
                                   // probabilityof a match

    private PathIterator pathIterator; // interface
    private Path2D path; // interface

    private int type;
    private int healthDrop; // drops could be health/shields/powerup
    private int powerupDrop;

    public EnemyShip(int x, int y, int width, int height, int health, int type, Path2D p) {
        super(x, y, width, height, health);
        this.type = type;
        healthDrop = r.nextInt(CHANCE);
        powerupDrop = r.nextInt(CHANCE);
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

    @Override
    public void setPosX(int posX) {
        // TODO: complete method
        this.posX = posX;
    }

    @Override
    public void setPosY(int posY) {
        // TODO: complete method
        this.posY = posY;

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
            System.out.print("done at point: ");
            System.out.println("(" + path.getCurrentPoint().getX() + ", " + path.getCurrentPoint().getY() + ")");
            return;
        }
        pathIterator.next();
        System.out.print("setting point to: ");
        System.out.println("(" + path.getCurrentPoint().getX() + ", " + path.getCurrentPoint().getY() + ")");
        setPosX((int) path.getCurrentPoint().getX());
        setPosY((int) path.getCurrentPoint().getY());
    }

}